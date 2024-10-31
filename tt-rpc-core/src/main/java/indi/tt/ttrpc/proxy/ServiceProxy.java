package indi.tt.ttrpc.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import indi.tt.ttrpc.RpcApplication;
import indi.tt.ttrpc.model.RpcRequest;
import indi.tt.ttrpc.model.RpcResponse;
import indi.tt.ttrpc.serializer.JdkSerializer;
import indi.tt.ttrpc.serializer.Serializer;
import indi.tt.ttrpc.serializer.SerializerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ServiceLoader;

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
        RpcRequest rpcRequest = RpcRequest.builder().serviceName(method.getDeclaringClass().getName()).methodName(method.getName()).parameterTypes(method.getParameterTypes()).args(args).build();

        try {
            // 序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);

            // 发送请求
            // TODO 注意，这里地址被硬编码了（需要使用注册中心和服务发现机制）
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080").body(bodyBytes).execute()) {
                byte[] result = httpResponse.bodyBytes();
                // 反序列化
                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
                return rpcResponse.getData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
