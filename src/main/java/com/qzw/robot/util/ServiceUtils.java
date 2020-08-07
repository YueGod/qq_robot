package com.qzw.robot.util;

import com.qzw.robot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

/**
 * @author ：quziwei
 * @date ：06/08/2020 21:16
 * @description：获取service
 */
public class ServiceUtils {
    private volatile static ServiceUtils instance;

    private IRb_group_historyService historyService;

    private IRb_fuck_wordService fuckWordService;

    private IRb_group_userService userService;

    private IRb_groupService groupService;

    private IRb_func_listService funcListService;

    private IRb_menu_listService menuListService;

    private RestTemplate restTemplate;

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

    public IRb_group_historyService getHistoryService() {
        return historyService;
    }

    public void setHistoryService(IRb_group_historyService historyService) {
        this.historyService = historyService;
    }

    public IRb_fuck_wordService getFuckWordService() {
        return fuckWordService;
    }

    public void setFuckWordService(IRb_fuck_wordService fuckWordService) {
        this.fuckWordService = fuckWordService;
    }

    public IRb_group_userService getUserService() {
        return userService;
    }

    public void setUserService(IRb_group_userService userService) {
        this.userService = userService;
    }

    public IRb_groupService getGroupService() {
        return groupService;
    }

    public void setGroupService(IRb_groupService groupService) {
        this.groupService = groupService;
    }

    public IRb_func_listService getFuncListService() {
        return funcListService;
    }

    public void setFuncListService(IRb_func_listService funcListService) {
        this.funcListService = funcListService;
    }

    public IRb_menu_listService getMenuListService() {
        return menuListService;
    }

    public void setMenuListService(IRb_menu_listService menuListService) {
        this.menuListService = menuListService;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
