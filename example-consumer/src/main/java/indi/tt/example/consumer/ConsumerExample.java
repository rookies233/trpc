package indi.tt.example.consumer;

import indi.tt.example.common.model.User;
import indi.tt.example.common.service.UserService;
import indi.tt.ttrpc.bootstrap.ConsumerBootstrap;
import indi.tt.ttrpc.proxy.ServiceProxyFactory;

/**
 * @author tt
 */
public class ConsumerExample {
    public static void main(String[] args) {
        // 服务提供者初始化
        ConsumerBootstrap.init();

        // 获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("yupi");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
