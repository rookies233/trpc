package indi.tt.yurpc.server;

/**
 * HTTP 服务接口
 */
public interface HttpServer {

    /**
     * 启动服务器
     *
     * @param port
     */
    void doStart(int port);
}
