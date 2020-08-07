package com.qzw.robot.handler;

import com.qzw.robot.entity.Rb_menu_list;
import com.qzw.robot.service.IRb_menu_listService;
import com.qzw.robot.util.ServiceUtils;

import java.util.List;

/**
 * @author ：quziwei
 * @date ：07/08/2020 10:31
 * @description：菜单列表
 */
public class MenuListHandler {
    private IRb_menu_listService menuListService = ServiceUtils.getInstance().getMenuListService();

    public String list(){
        List<Rb_menu_list> list = menuListService.list();
        StringBuilder sb = new StringBuilder();
        sb.append("=====菜单列表=====\n");
        int i=1;
        for (Rb_menu_list menu_list : list) {
            sb.append(i+": "+menu_list.getName()+"\n");
            i++;
        }
        sb.append("=====菜单列表=====");
        return sb.toString();
    }
}
