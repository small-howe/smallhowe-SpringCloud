package com.tangwh;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class UserService {

    @Autowired
    RestTemplate restTemplate;


    /**
     * 请求合并 注解方式
     * @param id
     * @return
     */
    @HystrixCollapser(batchMethod = "getUsersByIds",collapserProperties = {@HystrixProperty(name = "timerDelayInMilliseconds", value =
            "200")})
    public Future<User> getUserByid(Integer id){

        return null;
    }

    /**
     * 请求合并 继承方式
     *
     * @param ids
     * @return
     */
    @HystrixCommand
    public List<User> getUsersByIds(List<Integer> ids) {
        User[] users = restTemplate.getForObject("http://provider/user/{1}", User[].class,StringUtils.join(ids,","));

        return Arrays.asList(users);
    }
}
