package com;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import com.qzw.robot.event.GroupAdminEvent;
import com.qzw.robot.util.RobotUtils;
import com.qzw.robot.util.TimeUtil;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.SystemDeviceInfoKt;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

/**
 * @author ：quziwei
 * @date ：06/08/2020 15:30
 * @description：机器人启动
 */
@SpringBootApplication
@MapperScan("com.qzw.robot.mapper")
public class RobotApplication implements CommandLineRunner {
    public static Bot bot;
    public static String startTime = TimeUtil.getDateTimeString();

    @Value("${qzw.qq.number}")
    private Long qq;

    @Value("${qzw.qq.password}")
    private String password;

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(RobotApplication.class, args);
        Thread.currentThread().join();
    }


    @Override
    public void run(String... args) throws Exception {
        //初始化机器人
        bot = BotFactoryJvm.newBot(
                qq,
                password,
                new BotConfiguration() {{
                    // 设备缓存信息
                    //setProtocol(MiraiProtocol.ANDROID_PHONE);
                    setDeviceInfo(context -> SystemDeviceInfoKt.loadAsDeviceInfo(new File("deviceInfo.json"), getJson(), context));
                }}
        );
        // 登录
        bot.login();

        //监听时间
        Events.registerEvents(bot, new GroupAdminEvent());
        // 发送启动成功提示消息
        String endTime = TimeUtil.getDateTimeString();
        for (Long groupId : RobotUtils.adminGroups) {
            bot.getGroup(groupId).sendMessage("[INFO] " + " 启动成功" + "\n" +
                    "开始启动时间: " + startTime + "\n" +
                    "完成启动时间: " + endTime + "\n" +
                    "启动耗时: " + DateTime.of(startTime, "YYYY年MM月dd日 HH:mm:ss").between(DateTime.of(endTime, "YYYY年MM月dd日 HH:mm:ss"), DateUnit.SECOND) + "s"
            );
        }

        // 挂载该机器人的线程
        bot.join();

        // Spring线程阻塞
        Thread.currentThread().join();
    }
}
