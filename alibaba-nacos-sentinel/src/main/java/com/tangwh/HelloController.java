package com.tangwh;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PROJECT_NAME: smallhowecloud
 * @DESCRIPTION:
 * @USER: Howe
 * @DATE: 2020/6/24 16:56
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){

        return "Hello Sentinel";

    }
}
