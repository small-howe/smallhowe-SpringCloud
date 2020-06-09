package com.tangwh;

import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.client.RestTemplate;

/**
 * 通过 继承的方式 来  实现 断容
 */
public class HelloCommand extends HystrixCommand<String> {


    RestTemplate restTemplate;
    String name;


    public HelloCommand(Setter setter, RestTemplate restTemplate, String name) {
        super(setter);
        this.name = name;

        this.restTemplate = restTemplate;
    }

    @Override
    protected String run() throws Exception {
        return restTemplate.getForObject("http://provider/hello2?name={1}", String.class,name);
    }

    /**
     * 请求失败的一个回调
     *
     * @return
     */
    @Override
    protected String getFallback() {

        return "error-extends" + getExecutionException().getMessage();
    }

    /**
     *   继承的方式 缓存key
     * @return
     */
  @Override
  public String getCacheKey(){
        return name;
  }

}


