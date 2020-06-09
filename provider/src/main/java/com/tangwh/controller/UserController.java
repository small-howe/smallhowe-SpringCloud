package com.tangwh.controller;

import com.tangwh.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    /**
     * 请求合并
     * @param ids
     * @return
     */
    @GetMapping("/user/{ids}")  //假设consumer传过来的多个id的格式是 1,2,3,4,5.....
    public List<User> getUserByIds(@PathVariable String ids){

        System.out.println(ids);
        String[] split = ids.split(",");
        List<User> users = new ArrayList<>();
        for (String s : split) {
            User user = new User();
            user.setId(Integer.parseInt(s));
            users.add(user);
        }
        return users;
    }
}
