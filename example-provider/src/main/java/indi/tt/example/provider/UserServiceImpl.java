package indi.tt.example.provider;

import indi.tt.example.common.model.User;
import indi.tt.example.common.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名: " + user.getName());
        return user;
    }
}
