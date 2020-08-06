package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ：quziwei
 * @date ：06/08/2020 15:30
 * @description：机器人启动
 */
@SpringBootApplication
@MapperScan("com.qzw.robot.mapper")
public class RobotApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(RobotApplication.class,args);
        Thread.currentThread().join();
    }
}
