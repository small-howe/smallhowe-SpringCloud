package com.tangwh.controller;

import com.tangwh.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class HelloController {


    @Value("${server.port}")
    Integer port;

    @GetMapping("/hello")
    public String hello() {

        return "Hello Javaboy"+port;
    }

    /**
     * 测试RestTemplate 中的Get 请求
     *
     * @param name
     * @return
     */

    @GetMapping("/hello2")
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
    @PostMapping("/user2")
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
    @DeleteMapping("/user2/{id}")
    public void deleteUser2(@PathVariable Integer id){
        System.out.println(id);

    }

}
