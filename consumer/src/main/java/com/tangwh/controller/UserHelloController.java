package com.tangwh.controller;

import com.tangwh.User;
import javafx.beans.binding.ObjectExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.naming.Name;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 服务消费者
 */
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
     *  使用RestTempt 调用
     * @return
     */
    // 两个 restTemplate  我们使用 restTemplateOne
    @Autowired
    @Qualifier("restTemplateOne")
    RestTemplate restTemplateOne;

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
        sb.append("http://")
                .append(host)
                .append(":")
                .append(port)
                .append("/hello");

        // 使用RrestTemplate ； 方式调用
        String s = restTemplateOne.getForObject(sb.toString(), String.class);
        return s;

    }

    /**
     * 多个服务之间的调用  负载均衡
     * @return
     */
    // 两个 restTemplate  我们使用 restTemplate  具备负载均衡的restTemplate
    @Autowired
    @Qualifier("restTemplate")
    RestTemplate restTemplate;


    @GetMapping("/hello3")
    public String hello3() {


        return restTemplate.getForObject("http://provider/hello", String.class);
    }


    /**
     * 测试 restTemplate.getForEntity  get请求
     *
     * @return
     */
    @GetMapping("/hello4")
    public void hello4() {


        String s1 = restTemplate.getForObject("http://provider/hello2?name={1}", String.class, "javaboy");

        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://provider/hello", String.class);
        String body = responseEntity.getBody();

        System.out.println("body"+body);
        //http 响应的状态码
        HttpStatus status = responseEntity.getStatusCode();

        System.out.println("状态码:"+status);

        int statusCodeValue = responseEntity.getStatusCodeValue();
        System.out.println("statusCodeValue:"+statusCodeValue);

        // http 响应头信息
        HttpHeaders headers = responseEntity.getHeaders();
        Set<String> keySet = headers.keySet();
        System.out.println("------------headers---------------");
        keySet.forEach(key->{
            System.out.println(key+":"+headers.get(key));
        });
    }

    /**
     * 测试 restTemplate.getForEntity  get请求 方式二
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/hello5")
    public void hello5() throws UnsupportedEncodingException {


        String s1 = restTemplate.getForObject("http://provider/hello2?name={1}", String.class, "javaboy");
        System.out.println(s1);

        Map<String,Object> map = new HashMap<>();
        map.put("name","zhangsan");
        s1 = restTemplate.getForObject("http://provider/hello2?name={name}",String.class,map);


        String url = "http://provider/hello2?name="+ URLEncoder.encode( "张三", "UTF-8");

        URI uri = URI.create(url);

    s1 =  restTemplate.getForObject(uri, String.class);

        System.out.println(s1);


    }


    /**
     * 测试 restTemplate.getForEntity  post
     */
    @GetMapping("/hello6")
    public void hello6(){

        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();

        map.add("username","javaboy");
        map.add("password","123");
        map.add("id", 99);

        User user = restTemplate.postForObject("http://provider/user1", map, User.class);

        System.out.println(user);




        user.setId(98);
        user = restTemplate.postForObject("http://provider/user2", user, User.class);

        System.out.println(user);
    }


    /**
     * postForLocation post 测试  这里的响应一定是 302 否则不生效
     */
    @GetMapping("/hello7")
    public void hello7(){
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();

        map.add("username","javaboy");
        map.add("password","123");
        map.add("id", 99);
        URI uri = restTemplate.postForLocation("http://provider/register",map);

        System.out.println(uri);
        String s = restTemplate.getForObject(uri, String.class);
        System.out.println(s);

    }

    /**
     * postForLocation put 测试  这里的响应一定是 302 否则不生效
     */
    @GetMapping("/hello8")
    public void hello8(){

        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();

        map.add("username","javaboy");
        map.add("password","123");
        map.add("id", 99);
        restTemplate.put("http://provider/user1", map);


        // 修改操作
        User user = new User();
        user.setId(98);
        user.setPassword("456");
        user.setUsername("zhangsan");
        restTemplate.put("http://provider/user2", user);

    }


    /**
     * postForLocation delete 测试  这里的响应一定是 302 否则不生效
     */
    @GetMapping("/hello9")
    public void hello9(){

        restTemplate.delete("http://provider/user1?id={1}",99);


        restTemplate.delete("http://provider/user2/{1}",99);
    }
}
