package com.tangwh.controller;

import com.tangwh.User;
import com.tangwh.api.IUserService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.util.Date;

@RestController
public class HelloController implements IUserService {


    @Value("${server.port}")
    Integer port;

    @Override
    @RateLimiter(name = "rlA")//服务端限流操作 配置在配置文件中
    public String hello() {
        String s = "Hello Javaboy" + port;
        System.out.println(new Date());
        return s;
    }

    /**
     * 测试RestTemplate 中的Get 请求
     *
     * @param name
     * @return
     */

    @Override
    public String hello2(String name) {

        LocalDate date = LocalDate.now();
        System.out.println(date+">>>"+name);


        return "hello" + name;
    }


    /**
     * 测试RestTemplate 中的Post请求 key-value 形式传递  添加操作
     * @param user
     * @return
     */
    @PostMapping("/user1")
    public User addUser1(User user) {

        return user;
    }
    /**
     * 测试RestTemplate 中的Post请求  JSON形式传递。  添加操作
     * @param user
     * @return
     */
    @Override
    public User addUser2(@RequestBody User user) {

        return user;
    }


    /**
     * 测试RestTemplate 中的Put请求 key-value 形式传递 修改操作
     * @param user
     */
    @PutMapping("/user1")
    public void updateUser(User user){

        System.out.println(user);
    }

    /**
     * 测试RestTemplate 中的Put请求  JSON形式传递。 修改操作
     * @param user
     */
    @PutMapping("/user2")
    public void updateUser2(@RequestBody User user) {

        System.out.println(user);

    }


    /**
     *测试RestTemplate 中的Delete请求 参数key - value 形递。 删除操作
     * @param id
     */
    @DeleteMapping("/user1")
    public void deleteUser1(Integer id){
        System.out.println(id);

    }

    /**
     * 测试RestTemplate 中的Delete请求 参数  路径的形式 递。 删除操作
     */
    @Override
    public void deleteUser2(@PathVariable Integer id){
        System.out.println(id);

    }


    /**
     * 测试 OpenFeign 参数传递
     *
     * @param name
     */
    @Override
    public void getUserByName(@RequestHeader String name) throws UnsupportedEncodingException {
        System.out.println(URLDecoder.decode(name, "UTF-8"));
    }

}
