package com.tangwh;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PROJECT_NAME: smallhowecloud
 * @DESCRIPTION:
 * @USER: Howe
 * @DATE: 2020/6/24 12:19
 */
@RestController
public class HelloController {


    @Value("${server.port}")
    Integer prot;


    @GetMapping("/hello")
    public String hello(){
        return "hello:"+prot;
    }


}
