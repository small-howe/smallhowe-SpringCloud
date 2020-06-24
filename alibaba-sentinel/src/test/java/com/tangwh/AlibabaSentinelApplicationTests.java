package com.tangwh;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@SpringBootTest
class AlibabaSentinelApplicationTests {




    @Test
    void contextLoads() {
        
        RestTemplate restTemplate = new RestTemplate();


        for (int i = 0; i < 15; i++) {
            String forObject = restTemplate.getForObject("http://localhost:8081/hello", String.class);

            System.out.println(forObject+":"+new Date() );

        }
    }

}
