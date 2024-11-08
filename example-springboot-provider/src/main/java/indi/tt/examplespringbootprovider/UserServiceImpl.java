package indi.tt.examplespringbootprovider;

import indi.tt.example.common.model.User;
import indi.tt.example.common.service.UserService;
import indi.tt.ttrpc.springboot.starter.annotation.RpcService;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 *
 * @author tt
 */
@Service
@RpcService
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
