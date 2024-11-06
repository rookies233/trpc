package indi.tt.example.provider;

import indi.tt.example.common.service.UserService;
import indi.tt.ttrpc.RpcApplication;
import indi.tt.ttrpc.config.RegistryConfig;
import indi.tt.ttrpc.config.RpcConfig;
import indi.tt.ttrpc.model.ServiceMetaInfo;
import indi.tt.ttrpc.registry.LocalRegistry;
import indi.tt.ttrpc.registry.Registry;
import indi.tt.ttrpc.registry.RegistryFactory;
import indi.tt.ttrpc.server.HttpServer;
import indi.tt.ttrpc.server.VertxHttpServer;
import indi.tt.ttrpc.server.tcp.VertxTcpClient;
import indi.tt.ttrpc.server.tcp.VertxTcpServer;
import io.vertx.core.Vertx;

/**
 * 简易服务提供者示例
 * @author tt
 */
public class EasyProviderExample {
    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();

        // 注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());

        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 启动 Web 服务
//        HttpServer httpServer = new VertxHttpServer();
//        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());

        // 启动 TCP 服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
