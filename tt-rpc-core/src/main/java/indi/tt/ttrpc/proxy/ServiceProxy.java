package indi.tt.ttrpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import indi.tt.ttrpc.RpcApplication;
import indi.tt.ttrpc.config.RpcConfig;
import indi.tt.ttrpc.constant.RpcConstant;
import indi.tt.ttrpc.fault.retry.RetryStrategy;
import indi.tt.ttrpc.fault.retry.RetryStrategyFactory;
import indi.tt.ttrpc.fault.retry.RetryStrategyKeys;
import indi.tt.ttrpc.fault.tolerant.TolerantStrategy;
import indi.tt.ttrpc.fault.tolerant.TolerantStrategyFactory;
import indi.tt.ttrpc.loadbalancer.LoadBalancer;
import indi.tt.ttrpc.loadbalancer.LoadBalancerFactory;
import indi.tt.ttrpc.model.RpcRequest;
import indi.tt.ttrpc.model.RpcResponse;
import indi.tt.ttrpc.model.ServiceMetaInfo;
import indi.tt.ttrpc.protocol.*;
import indi.tt.ttrpc.registry.Registry;
import indi.tt.ttrpc.registry.RegistryFactory;
import indi.tt.ttrpc.serializer.JdkSerializer;
import indi.tt.ttrpc.serializer.Serializer;
import indi.tt.ttrpc.serializer.SerializerFactory;
import indi.tt.ttrpc.server.tcp.VertxTcpClient;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.CompletableFuture;

/**
 * 服务代理（JDK 动态代理）
 *
 * @author tt
 */
public class ServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        try {

            // 从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }

            // 负载均衡
            LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
            // 将调用方法名（请求路径）作为负载均衡参数
            Map<String, Object> requestParams = new HashMap<>();
            requestParams.put("methodName", rpcRequest.getMethodName());
            ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);

            // RPC 请求
            // 使用重试机制
            RpcResponse rpcResponse;
            try {
                RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
                // TODO 不理解 lambda 表达式，为什么不用 return
                rpcResponse = retryStrategy.doRetry(() ->
                        VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo)
                );
            } catch (Exception e) {
                // 容错机制
                TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
                rpcResponse = tolerantStrategy.doTolerant(null, e);
            }
            return rpcResponse.getData();
        } catch (Exception e) {
            throw new RuntimeException("调用失败");
        }
    }
}
