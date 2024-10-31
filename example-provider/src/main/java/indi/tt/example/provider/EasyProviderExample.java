package indi.tt.example.provider;

import indi.tt.example.common.service.UserService;
import indi.tt.ttrpc.RpcApplication;
import indi.tt.ttrpc.registry.LocalRegistry;
import indi.tt.ttrpc.server.HttpServer;
import indi.tt.ttrpc.server.VertxHttpServer;

/**
 * 简易服务提供者示例
 * @author tt
 */
public class EasyProviderExample {
    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();

        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 启动 Web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
