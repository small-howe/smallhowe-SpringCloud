package com.tangwh;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 自定义通道
 */
public interface MyChannerl {


    // 输入通道
    String INPUT = "javaboy-input";

    // 输出通道
    String OUTPUT = "javaboy-output";


    /** 输出消息通道
     * 发消息
     * 消息生产者
     * @return
     */
    @Output(OUTPUT)
    MessageChannel output();

    /**
     * 消息输入通道
     * 收消息
     * 消息消费者
     * @return
     */
    @Input(INPUT)
    SubscribableChannel input();




}
