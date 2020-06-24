package com.tangwh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.Date;

/**
 * 自定义消息接收器
 */
@EnableBinding(MyChannerl.class)
public class MyReceiver2 {

    private static final Logger logger = LoggerFactory.getLogger(MyReceiver2.class);

    /**
     * 接收消息
     * @param payload
     */
    @StreamListener(MyChannerl.INPUT)
    public void receive(Object payload){

        logger.info("received2:"+payload+new Date());
        System.err.println(payload);

    }
}
