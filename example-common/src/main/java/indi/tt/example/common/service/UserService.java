package indi.tt.example.common.service;

import indi.tt.example.common.model.User;

/**
 * 用户服务
 *
 * @author tt
 */
public interface UserService {

    /**
     * 获取用户
     *
     * @param user
     * @return
     */
    User getUser(User user);

    /**
     * 获取数字
     * 测试 mock 代理服务是否生效，即查看调用的是模拟服务还是真实服务。
     *
     * @return
     */
    default short getNumber() {
        return 1;
    }
}
