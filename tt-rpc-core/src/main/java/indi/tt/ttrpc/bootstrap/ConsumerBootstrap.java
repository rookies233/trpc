package indi.tt.ttrpc.bootstrap;

import indi.tt.ttrpc.RpcApplication;

/**
 * 服务消费者启动类（初始化）
 *
 * @author tt
 */
public class ConsumerBootstrap {
    /**
     * 初始化
     */
    public static void init() {
        // RPC 框架初始化（配置和注册中心）
        RpcApplication.init();
    }
}
