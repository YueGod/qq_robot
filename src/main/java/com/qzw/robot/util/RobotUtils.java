package com.qzw.robot.util;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.qzw.robot.entity.Rb_group;
import com.qzw.robot.entity.Rb_group_history;
import com.qzw.robot.entity.Rb_group_user;
import com.qzw.robot.model.CheckIn;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.utils.FileCacheStrategy;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

/**
 * @author ：quziwei
 * @date ：06/08/2020 19:37
 * @description：
 */

public class RobotUtils {
    private static RobotUtils instance;

    public static final ConcurrentHashSet<Long> adminGroups = new ConcurrentHashSet<>();

    public static FileCacheStrategy.MemoryCache memoryCache;

    public static ConcurrentHashSet<CheckIn> checkIns = new ConcurrentHashSet<>();

    private RobotUtils(){}

    public static RobotUtils getInstance() {
        if (instance == null){
            synchronized (RobotUtils.class){
                if (instance == null){
                    instance = new RobotUtils();
                    return instance;
                }
            }
        }
        return instance;
    }

    public void a(){

    }
}
