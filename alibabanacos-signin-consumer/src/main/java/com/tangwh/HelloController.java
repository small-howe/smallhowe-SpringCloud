package com.tangwh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @PROJECT_NAME: smallhowecloud
 * @DESCRIPTION:
 * @USER: Howe
 * @DATE: 2020/6/24 13:19
 */
@RestController
public class HelloController {


    @Autowired
    RestTemplate restTemplate;


    @GetMapping("/hello")
    public String hello(){
        return restTemplate.getForObject("http://nacos01/hello", String.class);
    }




}
