package com.tangwh.controller;

import com.tangwh.User;
import com.tangwh.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

import static java.net.URLEncoder.encode;

@RestController
public class HelloController {


    @Autowired
    HelloService helloService;


    /**
     * 调用这个 /hello  先去 helloService
     * helloService 然后去 服务命 为 provider中 调用 名字为 hello的接口
     * @return
     */
    @GetMapping("/hello")
    public String hello() throws UnsupportedEncodingException {
        String hello2 = helloService.hello2("唐同学");
        System.out.println(hello2);
        User user = helloService.addUser2(new User(1, "javaboy", "123"));
        System.out.println(user);
        helloService.deleteUser2(1);
        helloService.getUserByName(encode("唐同学", "UTF-8"));
        return helloService.hello();
    }

}
