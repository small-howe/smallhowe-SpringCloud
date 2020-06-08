package com.tangwh.controller;

import com.tangwh.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 注册 代码
 */
@Controller
public class RegisterController {

    /**
     * 测试 estTemplate.postForLocation 请求
     * @param user
     * @return
     */

    @PostMapping("/register")
    public String register(User user){

        return "redirect:http://provider/loginPage?username="+user.getUsername();
    }

    @GetMapping("/loginPage")
    @ResponseBody
    public String loginPage(String username){

        return "loginPage:"+username;

    }

}
