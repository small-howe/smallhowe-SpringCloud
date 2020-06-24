package com.tangwh;

import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PROJECT_NAME: smallhowecloud
 * @DESCRIPTION:
 * @USER: Howe
 * @DATE: 2020/6/24 11:18
 */
@RestController
@Reference //动态刷新
public class HelloController {


    @Value("${name}")
    String name;


    @GetMapping("/hello")
    public String hello(){

        return name;
    }
}
