package indi.tt.example.consumer;

import indi.tt.example.common.model.User;
import indi.tt.example.common.service.UserService;
import indi.tt.ttrpc.config.RpcConfig;
import indi.tt.ttrpc.proxy.ServiceProxyFactory;
import indi.tt.ttrpc.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 简易服务消费者示例
 *
 * @author tt
 */
@Slf4j
public class EasyConsumerExample {
    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpc);
        // 获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("tt");

        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
//            log.info("newUser.getName() = {}", newUser.getName());
        } else {
            System.out.println("user == null");
//            log.info("user == null");
        }

        // 连续调用服务 3 次
//        for (int i = 0; i < 3; ++i) {
//            if (i == 2) {
//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            User newUser = userService.getUser(user);
//            if (newUser != null) {
//                System.out.println(newUser.getName());
//            } else {
//                System.out.println("user == null");
//            }
//        }

//        long number = userService.getNumber();
//        System.out.println(number);
    }
}
