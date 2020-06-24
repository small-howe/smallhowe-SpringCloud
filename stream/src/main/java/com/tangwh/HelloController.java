package com.tangwh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 自定义消息发送
 */
@RestController
public class HelloController {

private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    MyChannerl myChannerl;

    @GetMapping("/hello")
    public void hellO(){

        logger.info("send msg:"+new Date());
        myChannerl.output().send(MessageBuilder.withPayload("Hello SpringCloud stream!").setHeader("x-delay", 3000).build());


    }




}
