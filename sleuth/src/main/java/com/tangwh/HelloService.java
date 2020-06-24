package com.tangwh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloService.class);


    /**
     * 异步方法
     * @return
     */
    @Async
    public String backgroudFun(){

        logger.info("backgroudFun");
        return "backgroudFun";
    }

    @Scheduled(cron = "0/10 * * * * ?")
    public void sche1(){

        logger.info("start:");

        backgroudFun();
        logger.info("end");
    }


}
