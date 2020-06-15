package com.tangwh;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
//@Retry(name = "retryA") //配置文件中的 重试策略名  重试
@CircuitBreaker(name = "cbA",fallbackMethod = "error") // 服务熔断机制
public class HelloService {


    @Autowired
    RestTemplate restTemplate;


    public String hello(){
        for (int i = 0; i < 5; i++) {
             restTemplate.getForObject("http://localhost:1113/hello", String.class);
        }
        return "success";
    }


    /**
     * 服务熔断 指定的错误方法
     * @param throwable
     * @return
     */
    public String error(Throwable throwable){

        return "error";
    }
}
