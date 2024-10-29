package indi.tt.example.provider;

import indi.tt.yurpc.server.HttpServer;
import indi.tt.yurpc.server.VertxHttpServer;

/**
 * 简易服务提供者示例
 * @author tt
 */
public class EasyProviderExample {
    public static void main(String[] args) {
        // 启动 Web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
