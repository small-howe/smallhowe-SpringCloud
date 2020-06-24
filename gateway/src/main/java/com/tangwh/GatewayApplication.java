package com.tangwh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    /**
     * 代码配置
     */

//    @Bean
//    RouteLocator routeLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                // 配置规则的名字   配置规则 ——>
//                .route("javaboy",r->r.path("/get").uri("http://httpbin.org/get"))
//
//                .build();
//    }
}
