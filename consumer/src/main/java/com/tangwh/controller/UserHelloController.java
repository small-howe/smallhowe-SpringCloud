package com.tangwh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@RestController
public class UserHelloController {

    /**
     * 定死的：
     *
     * @return
     */

    @Autowired
    DiscoveryClient discoveryClient;


    @GetMapping("/hello1")
    public String hello1() {

        // 调用提供者的接口 使用Http
        HttpURLConnection con = null;
        try {
            URL url = new URL("http://localhost:1113/hello");


            con = (HttpURLConnection) url.openConnection();

            if (con.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String s = br.readLine();

                br.close();
                return s;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "error";
    }


    /**
     * 灵活调用
     * @return
     */
    @GetMapping("/hello2")
    public String hello2() {

        // 服务调用的名字  查询到的服务列表是一个集合
        List<ServiceInstance> list = discoveryClient.getInstances("provider");
         // 获取第一个服务名
        ServiceInstance instance = list.get(0);

        // 获取第一个服务名的第一个地址
        String host = instance.getHost();
        // 获取服务的端口
        int port = instance.getPort();

// 拼接地址
        StringBuffer sb = new StringBuffer();
        sb.append("http://").append(host).append(":").append(port).append("/hello");

        // 调用提供者的接口 使用Http
        HttpURLConnection con = null;
        try {

            URL url = new URL(sb.toString());


            con = (HttpURLConnection) url.openConnection();

            if (con.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String s = br.readLine();

                br.close();
                return s;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "error";
    }

    /**
     * 多个服务之间的调用
     * @return
     */


    int count = 0;
    @GetMapping("/hello3")
    public String hello3() {

        // 服务调用的名字  查询到的服务列表是一个集合
        List<ServiceInstance> list = discoveryClient.getInstances("provider");
        // 获取第一个服务名
        ServiceInstance instance = list.get((count++)%list.size());

        // 获取第一个服务名的第一个地址
        String host = instance.getHost();
        // 获取服务的端口
        int port = instance.getPort();

// 拼接地址
        StringBuffer sb = new StringBuffer();
        sb.append("http://").append(host).append(":").append(port).append("/hello");

        // 调用提供者的接口 使用Http
        HttpURLConnection con = null;
        try {

            URL url = new URL(sb.toString());


            con = (HttpURLConnection) url.openConnection();

            if (con.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String s = br.readLine();

                br.close();
                return s;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "error";
    }

}
