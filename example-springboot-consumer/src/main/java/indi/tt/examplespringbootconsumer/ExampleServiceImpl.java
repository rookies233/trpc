package indi.tt.examplespringbootconsumer;

import indi.tt.example.common.model.User;
import indi.tt.example.common.service.UserService;
import indi.tt.ttrpc.springboot.starter.annotation.RpcReference;
import org.springframework.stereotype.Service;

/**
 * @author tt
 */
@Service
public class ExampleServiceImpl {

    @RpcReference
    private UserService userService;

    public void test() {
        User user = new User();
        user.setName("tt");
        User resultUser = userService.getUser(user);
        System.out.println(resultUser.getName());
    }
}
