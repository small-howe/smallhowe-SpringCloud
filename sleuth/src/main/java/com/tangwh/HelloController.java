package com.tangwh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    HelloService helloService;


    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    public String hello() {

        logger.info("hello spring cloud sleuth");

        return "hello spring cloud sleuth";
    }



    @GetMapping("/hello2")
    public String heelo2() throws InterruptedException {

        logger.info("hello2");
        Thread.sleep(5000);

        return restTemplate.getForObject("http://localhost:8080/hello3", String.class);

    }

    @GetMapping("/hello3")
    public String hello3(){

        logger.info("hello3");

        return "Hello3";


    }


    @GetMapping("/hello4")
    public String hello4(){

        logger.info("Hello4");

        return helloService.backgroudFun();
    }

}
