package com.tangwh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }



    /**
     * Spring 中 调用服务接口的方式
     * @return
     */
    @Bean
    RestTemplate restTemplateOne(){

        return new RestTemplate();
    }


    @Bean
    @LoadBalanced // 开启负载均衡
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
