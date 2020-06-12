package com.tangwh.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.vavr.CheckedFunction0;
import io.vavr.CheckedRunnable;
import io.vavr.control.Try;
import org.junit.Test;

import java.time.Duration;
import java.util.Date;

/**
 * 测试 Resilience4j的断路器 服务断容
 */
public class Resilience4jTest {


    /**
     * 断路器创建
     */
    @Test
    public void test1() {
        // 获取一个 CircuitBreakerRegistry 实例 可以调用 ofDefaults
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.ofDefaults();


        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                // 故障率百分比 超过这个阈值 断路器会打开
                .failureRateThreshold(50)
                // 断路器打开的时间 在到达设置 的时间之后 断路器会进入到half open状态
                .waitDurationInOpenState(Duration.ofMillis(1000))
                // 当断路器 处于half(一半) opne状态 环形缓冲区的大小
                .ringBufferSizeInHalfOpenState(2)
                .ringBufferSizeInClosedState(2)
                .build();

        CircuitBreakerRegistry r1 = CircuitBreakerRegistry.of(config);

        // 断路器名字
        CircuitBreaker cb1 = r1.circuitBreaker("javaboy");
        CircuitBreaker cb2 = r1.circuitBreaker("javaboy2", config);


        CheckedFunction0<String> supplier = CircuitBreaker.decorateCheckedSupplier(cb1, () -> "hello resilience4j");

        Try<String> result = Try.of(supplier)
                .map(v -> v + " Hello word");

        System.out.println(result.isSuccess());

        System.out.println(result.get());


    }


    /**
     * 短路测试
     */
    @Test
    public void test2() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                // 故障率百分比 超过这个阈值 断路器会打开
                .failureRateThreshold(50)
                // 断路器打开的时间 在到达设置 的时间之后 断路器会进入到half open状态
                .waitDurationInOpenState(Duration.ofMillis(1000))
                // 当断路器 处于half(一半) opne状态 环形缓冲区的大小
                .ringBufferSizeInClosedState(2)
                .build();
        CircuitBreakerRegistry r1 = CircuitBreakerRegistry.of(config);

        // 断路器名字
        CircuitBreaker cb1 = r1.circuitBreaker("javaboy");
        // 获取断路器的状态
        System.out.println(cb1.getState());

        // 模拟犯错
        cb1.onError(0, new RuntimeException());
        // 获取断路器的状态
        System.out.println(cb1.getState());
        // 模拟犯错
        cb1.onError(0, new RuntimeException());
        // 获取断路器的状态
        System.out.println(cb1.getState());


        CheckedFunction0<String> supplier = CircuitBreaker.decorateCheckedSupplier(cb1, () -> "hello resilience4j");

        Try<String> result = Try.of(supplier)
                .map(v -> v + " Hello word");

        System.out.println(result.isSuccess());

        System.out.println(result.get());
    }


    /**
     * 限流测试
     */
    @Test
    public void test3() {

        RateLimiterConfig config = RateLimiterConfig.custom()
                // 阈值刷新的时间
                .limitRefreshPeriod(Duration.ofMillis(1000))
                // 阈值刷新的频次 //相当于 每处理多少请求
                .limitForPeriod(4)
                //限流之后的冷却时间
                .timeoutDuration(Duration.ofMillis(1000)).build();

        RateLimiter rateLimiter = RateLimiter.of("javaboy", config);

        //测试限流
        CheckedRunnable checkedRunnable = RateLimiter.decorateCheckedRunnable(rateLimiter, () -> {
            System.out.println(new Date());
        });
        /**
         * 4个任务分两批执行
         */
        Try.run(checkedRunnable)
                .andThenTry(checkedRunnable)
                .andThenTry(checkedRunnable)
                .andThenTry(checkedRunnable)
                .onFailure(t -> {
                    System.out.println(t.getMessage());
                });
    }

    /**
     *  请求重试
     */
    @Test
    public void test4(){
        RetryConfig config = RetryConfig.custom()
                //重试次数
                .maxAttempts(2)
                // 重试间隔时间
                .waitDuration(Duration.ofMillis(500))
                // 执行中抛出异常 重试异常
                .retryExceptions(RuntimeException.class)
                .build();


        Retry retry = Retry.of("javaboy", config);
        Retry.decorateRunnable(retry,new Runnable(){
            int count = 0;
            // 开启了重试功能之后 run方法执行时 如果抛出异常 会自动出发重试功能
            @Override
            public void run() {
                if (count++ < 3){
                    System.out.println(count);
                    throw new RuntimeException();
                }
            }
        }).run();

    }

}
