package com.qzw.robot.util;

import com.qzw.robot.service.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author ：quziwei
 * @date ：06/08/2020 21:16
 * @description：获取service
 */
public class ServiceUtils {
    private volatile static ServiceUtils instance;

    @Getter
    @Setter
    private IRb_group_historyService historyService;

    @Getter
    @Setter
    private IRb_fuck_wordService fuckWordService;

    @Getter
    @Setter
    private IRb_group_userService userService;

    @Getter
    @Setter
    private IRb_groupService groupService;

    @Getter
    @Setter
    private IRb_func_listService funcListService;

    @Getter
    @Setter
    private IRb_menu_listService menuListService;

    @Getter
    @Setter
    private RedisTemplate redisTemplate;



    private ServiceUtils(){}

    public static ServiceUtils getInstance() {
        if (instance == null){
            synchronized (ServiceUtils.class){}
            if (instance == null){
                instance = new ServiceUtils();
                return instance;
            }
        }
        return instance;
    }


}
