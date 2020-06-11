package com.tangwh.serivce;

import com.tangwh.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

// 和那个服务绑定
@FeignClient("provider")
public interface HelloService {


    /**
     * 这里的hello 是 provider中的  hello接口 访问
     * 方法名 无所谓
     * 注解 中的 接口请求 和provider 所请求的一致
     * 返回类型 也必须一致
     * 参数  必须一致
     *
     * @return
     */
    @GetMapping("/hello")
    String hello(); //这里的方法名随意


    @GetMapping("/hello2")
    String hello2(@RequestParam("name") String name);


    @PostMapping("/user2")
    User addUser(@RequestBody User user);


    @DeleteMapping("/user2/{id}")
    void deleteUserById(@PathVariable("id") Integer id);

    @GetMapping("/user3")
    void getUserByName(@RequestHeader("name") String name);


}
