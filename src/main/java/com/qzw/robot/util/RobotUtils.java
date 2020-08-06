package com.qzw.robot.util;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.qzw.robot.entity.Rb_group;
import com.qzw.robot.entity.Rb_group_history;
import com.qzw.robot.entity.Rb_group_user;
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
    private static RedisTemplate redisTemplate = SpringUtil.getBean(RedisTemplate.class);

    public static final ConcurrentHashSet<Long> adminGroups = new ConcurrentHashSet<>();

    public static List<Rb_group> groupList(){
        return (List<Rb_group>) redisTemplate.opsForValue().get("groups");
    }

    public static List<Rb_group_history> historyList(){
        return (List<Rb_group_history>) redisTemplate.opsForValue().get("histories");
    }

    public static List<Rb_group_user> userList(){
        return (List<Rb_group_user>) redisTemplate.opsForValue().get("users");
    }



}
