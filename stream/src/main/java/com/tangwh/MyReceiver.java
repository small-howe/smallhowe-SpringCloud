package com.tangwh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * 消息接收器
 */
// 绑定某一个消息通道
@EnableBinding(Sink.class)//绑定消息通道 Sink
public class MyReceiver {

    private static final Logger logger = LoggerFactory.getLogger(MyReceiver.class);

    /**
     * 有人发消息 能监听到消息
     * Sink.INPUT:消息通道
     * @param paylod
     */
    @StreamListener(Sink.INPUT)
    public void  receive(Object paylod){
        logger.info("Receive:"+paylod);

    }
}
