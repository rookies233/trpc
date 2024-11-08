package indi.tt.ttrpc.bootstrap;

import indi.tt.ttrpc.RpcApplication;
import indi.tt.ttrpc.config.RegistryConfig;
import indi.tt.ttrpc.config.RpcConfig;
import indi.tt.ttrpc.model.ServiceMetaInfo;
import indi.tt.ttrpc.model.ServiceRegisterInfo;
import indi.tt.ttrpc.registry.LocalRegistry;
import indi.tt.ttrpc.registry.Registry;
import indi.tt.ttrpc.registry.RegistryFactory;
import indi.tt.ttrpc.server.tcp.VertxTcpServer;

import java.util.List;

/**
 * @author tt
 */
public class ProviderBootstrap {
    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList) {
        // RPC 框架初始化（配置和注册中心）
        RpcApplication.init();
        // 全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            String serviceName = serviceRegisterInfo.getServiceName();
            // 本地注册
            LocalRegistry.register(serviceName, serviceRegisterInfo.getImplClass());

            // 注册服务到注册中心
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
        }

        // 启动服务器
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
