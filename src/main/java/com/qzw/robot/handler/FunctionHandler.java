package com.qzw.robot.handler;

import com.qzw.robot.service.IRb_func_listService;
import com.qzw.robot.util.ServiceUtils;

import java.util.List;

/**
 * @author ：quziwei
 * @date ：07/08/2020 10:21
 * @description：功能列表
 */
public class FunctionHandler {
    private IRb_func_listService funcListService = ServiceUtils.getInstance().getFuncListService();

    public String list(){
        List<String> list = funcListService.findList();
        StringBuilder sb = new StringBuilder();
        sb.append("=====功能列表=====");
        int i=1;
        for (String s : list) {
            sb.append(i+s+"\n");
            i++;
        }
        sb.append("=====功能列表=====");
        return sb.toString();
    }
}
