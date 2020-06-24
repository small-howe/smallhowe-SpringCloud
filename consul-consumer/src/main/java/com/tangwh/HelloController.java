package com.tangwh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @PROJECT_NAME: smallhowecloud
 * @DESCRIPTION:
 * @USER: Howe
 * @DATE: 2020/6/24 10:37
 */
@RestController
public class HelloController {


    @Autowired
    LoadBalancerClient loadBalancerClient;


    @Autowired
    RestTemplate restTemplate;

    /**
     * 获取服务的信息
     */
    @GetMapping("/hello")
    public void hello(){

        ServiceInstance choose = loadBalancerClient.choose("consul-provider");
        System.out.println("服务地址:"+choose.getUri());
        String serviceId = choose.getServiceId();
        System.out.println("服务名称:"+serviceId);
        // 调用 consul-provider 提供的服务

        String forObject = restTemplate.getForObject(choose.getUri(), String.class);
        System.out.println(forObject);

    }
}
