package com.tangwh;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients// 开启Feign支持
public class OpenfeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenfeignApplication.class, args);
    }

    // OpenFeign 日志 打印所有的日志

    @Bean
    Logger.Level loggerLevel(){

        return Logger.Level.FULL;
    }

}
