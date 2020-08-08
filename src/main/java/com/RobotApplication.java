package com;

import com.qzw.robot.event.GroupAdminEvent;
import com.qzw.robot.event.GroupMessagesEvent;
import com.qzw.robot.service.*;
import com.qzw.robot.util.RobotUtils;
import com.qzw.robot.util.ServiceUtils;
import com.qzw.robot.util.TimeUtil;
import kotlinx.io.core.Input;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.ExternalImage;
import net.mamoe.mirai.utils.FileCacheStrategy;
import net.mamoe.mirai.utils.SystemDeviceInfoKt;
import org.jetbrains.annotations.NotNull;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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

    private Long qq = 1773979533L;

    private String password = "quziwei123";

    @Autowired
    private IRb_groupService groupService;

    @Autowired
    private IRb_group_userService userService;

    @Autowired
    private IRb_group_historyService historyService;

    @Autowired
    private IRb_menu_listService menuListService;

    @Autowired
    private IRb_fuck_wordService fuckWordService;

    @Autowired
    private IRb_func_listService funcListService;

    @Autowired
    private RedisTemplate redisTemplate;


    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(RobotApplication.class, args);

    }


    @Override
    public void run(String... args) throws Exception {
        ServiceUtils instance = ServiceUtils.getInstance();
        instance.setFuckWordService(fuckWordService);
        instance.setFuncListService(funcListService);
        instance.setGroupService(groupService);
        instance.setMenuListService(menuListService);
        instance.setHistoryService(historyService);
        instance.setUserService(userService);
        instance.setRedisTemplate(redisTemplate);
        //初始化机器人
        bot = BotFactoryJvm.newBot(
                qq,
                password,
                new BotConfiguration() {{
                    // 设备缓存信息
                    //setProtocol(MiraiProtocol.ANDROID_PHONE);
                    setDeviceInfo(context -> SystemDeviceInfoKt.loadAsDeviceInfo(new File("deviceInfo.json"), getJson(), context));
                    setFileCacheStrategy(FileCacheStrategy.MemoryCache.INSTANCE);
                    RobotUtils.memoryCache = FileCacheStrategy.MemoryCache.INSTANCE;

                }}
        );
        // 登录
        bot.login();

        //监听时间
        Events.registerEvents(bot, new GroupAdminEvent());
        Events.registerEvents(bot, new GroupMessagesEvent());
        // 发送启动成功提示消息
        String endTime = TimeUtil.getDateTimeString();
//        for (Long groupId : RobotUtils.adminGroups) {
//            bot.getGroup(groupId).sendMessage("[INFO] " + " 启动成功" + "\n" +
//                    "开始启动时间: " + startTime + "\n" +
//                    "完成启动时间: " + endTime + "\n" +
//                    "启动耗时: " + DateTime.of(startTime, "YYYY年MM月dd日 HH:mm:ss").between(DateTime.of(endTime, "YYYY年MM月dd日 HH:mm:ss"), DateUnit.SECOND) + "s"
//            );
//        }

        // 挂载该机器人的线程
        bot.join();

        // Spring线程阻塞
        Thread.currentThread().join();
    }
}
