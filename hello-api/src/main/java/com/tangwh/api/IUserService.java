package com.tangwh.api;

import com.tangwh.User;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

/**
 * openfeign 和 provider中 调用的时候 工共的部分
 */
public interface IUserService {



    @GetMapping("/hello")
    String hello(); //这里的方法名随意


    @GetMapping("/hello2")
    String hello2(@RequestParam("name") String name);


    @PostMapping("/user2")
    User addUser2(@RequestBody User user);


    @DeleteMapping("/user2/{id}")
    void deleteUser2(@PathVariable("id") Integer id);

    @GetMapping("/user3")
    void getUserByName(@RequestHeader("name") String name) throws UnsupportedEncodingException;






}
