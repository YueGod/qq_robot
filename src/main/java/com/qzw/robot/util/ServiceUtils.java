package com.qzw.robot.util;

import com.qzw.robot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author ：quziwei
 * @date ：06/08/2020 21:16
 * @description：获取service
 */
public class ServiceUtils {
    private volatile static ServiceUtils instance;

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

    public IRb_groupService groupService(){
        return SpringUtil.getBean(IRb_groupService.class);
    }

    public IRb_group_userService userService(){
        return SpringUtil.getBean(IRb_group_userService.class);
    }

    public IRb_group_historyService historyService(){
        return SpringUtil.getBean(IRb_group_historyService.class);
    }

    public IRb_menu_listService menuListService(){
        return SpringUtil.getBean(IRb_menu_listService.class);
    }

    public IRb_fuck_wordService fuckWordService(){
        return SpringUtil.getBean(IRb_fuck_wordService.class);
    }

    public IRb_func_listService funcListService(){
        return SpringUtil.getBean(IRb_func_listService.class);
    }
}
