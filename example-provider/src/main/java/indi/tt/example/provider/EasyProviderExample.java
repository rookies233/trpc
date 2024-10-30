package indi.tt.example.provider;

import indi.tt.example.common.service.UserService;
import indi.tt.yurpc.registry.LocalRegistry;
import indi.tt.yurpc.server.HttpServer;
import indi.tt.yurpc.server.VertxHttpServer;

/**
 * 简易服务提供者示例
 * @author tt
 */
public class EasyProviderExample {
    public static void main(String[] args) {
        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 启动 Web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
