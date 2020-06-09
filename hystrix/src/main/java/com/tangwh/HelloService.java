package com.tangwh;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;


    /**
     * 在这个方法中 我们将发起一个远程调用 调用provider中提供的 /hello 接口
     * 但是 这个 调用 可能会失败
     * <p>
     * 在这个方法上 添加上 @HystrixCommand(fallbackMethod = "error") eror方法 是 调用 该方法时失败 会调用方法
     * ignoreExceptions 不会服务降价 直接报错信息
     *
     * @return
     */
    @HystrixCommand(fallbackMethod = "error",ignoreExceptions = ArithmeticException.class)
    public String hello() {
        int i = 2/0;
        return restTemplate.getForObject("http://provider/hello", String.class);
    }

    @HystrixCommand(fallbackMethod = "error")
    public Future<String> hello2(){

        return new AsyncResult<String>(){

            @Override
            public String invoke() {
                return restTemplate.getForObject("http://provider/hello", String.class);
            }
        };
    }
    /**
     * 注意这个 方法 名字 和 fallbackMethod 一致  方法返回值 也必须一致
     *
     * @return
     */
    public String error(Throwable throwable) {
        String message = throwable.getMessage();
        System.out.println(message);
        return "error"+message;
    }


    /**
     * 测试缓存
     * @param name
     * @return
     */
    @HystrixCommand(fallbackMethod = "error2")
    @CacheResult // 这个注解标是 该方法的请求结果会被缓存起来  默认 缓存key 就是 方法的参数  缓存的value是 结果
    public String hello3(String name){
        return restTemplate.getForObject("http://provider/hello2?name={1}", String.class,name);

    }
    public String error2(String name) {

        return "error:javaboy";
    }
    @HystrixCommand
    @CacheRemove(commandKey = "hello3")
    public String deleteUserByName(String name){

        return null;
    }
}
