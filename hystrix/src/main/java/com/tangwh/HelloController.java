package com.tangwh;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class HelloController {

    @Autowired
    HelloService helloService;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/hello")
    public String hello() {

        return helloService.hello();
    }


    /**
     * 访问 自己写的 继承断容
     */
    @GetMapping("/hello2")
    public void hello2() {
        // 继承方式  开启缓存
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
        HelloCommand helloCommand =
                new HelloCommand(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("javaboy"))
                        , restTemplate, "javaboy");
        // 直接执行
        String execute = helloCommand.execute();
        System.out.println(execute);

        // 或者 入队执行
        HelloCommand helloCommand2 =
                new HelloCommand(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(
                        "javaboy")),
                        restTemplate, "javaboy");
        try {
            // 或者 先入队 后执行
            Future<String> queue = helloCommand2.queue();
            String s = queue.get();
            System.out.println(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ctx.close();
    }

    @GetMapping("/hello3")
    public void hello3() {

        Future<String> stringFuture = helloService.hello2();
        try {
            String s = stringFuture.get();
            System.out.println(s);
        } catch (InterruptedException e) {


        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    /**
     * 测试缓存
     */

    @GetMapping("/hello4")
    public void hello4() {

        HystrixRequestContext cx = HystrixRequestContext.initializeContext();
        // 第一次请求 数据 缓存下来
        String javaboy = helloService.hello3("javaboy");
        //删除数据 同事 缓存中的数据也会被删除
        helloService.deleteUserByName("javaboy");
        // 第三次请求 虽然参数 是javaboy  但是缓存中的数据已经没了 所以这一次 provider还是会收到请求
        javaboy = helloService.hello3("javaboy");
        // 在 close 之前 缓存有效  重新请求 就重新缓存
        cx.close();

    }

    @Autowired
    UserService userService;

    /**
     * 请求合并
     */
    @GetMapping("/hello5")
    public void hello5() throws ExecutionException, InterruptedException {
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
        UserCollapseCommand cmd1 = new UserCollapseCommand(userService, 99);
        UserCollapseCommand cmd2 = new UserCollapseCommand(userService, 98);
        UserCollapseCommand cmd3 = new UserCollapseCommand(userService, 97);
        UserCollapseCommand cmd4 = new UserCollapseCommand(userService, 96);
  ///请求入队
        Future<User> q1 = cmd1.queue();
        Future<User> q2 = cmd2.queue();
        Future<User> q3 = cmd3.queue();
        Future<User> q4 = cmd4.queue();

        User u1 = q1.get();
        User u2 = q2.get();
        User u3 = q3.get();
        User u4 = q4.get();

        System.out.println(u1);
        System.out.println(u2);
        System.out.println(u3);
        System.out.println(u4);
        ctx.close();

    }

    /**
     * 请求合并 注解方式
     */
    @GetMapping("/hello6")
    public void hello6() throws ExecutionException, InterruptedException {
        HystrixRequestContext ctx = HystrixRequestContext.initializeContext();
        Future<User> q1 = userService.getUserByid(99);
        Future<User> q2 = userService.getUserByid(98);
        Future<User> q3 = userService.getUserByid(97);


        User u1 = q1.get();
        User u2 = q2.get();
        User u3 = q3.get();
        System.out.println(u1);
        System.out.println(u2);
        System.out.println(u3);
        Thread.sleep(2000);
        Future<User> q4 = userService.getUserByid(96);
        User user = q4.get();
        System.out.println(user);
        ctx.close();

    }
}
