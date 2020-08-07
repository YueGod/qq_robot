package com.qzw.robot.menu;

import com.qzw.robot.entity.Rb_group_user;
import com.qzw.robot.event.GroupMessagesEvent;
import com.qzw.robot.service.IRb_group_userService;
import com.qzw.robot.util.ServiceUtils;
import net.mamoe.mirai.message.GroupMessageEvent;

import java.math.BigDecimal;

/**
 * @author ：quziwei
 * @date ：08/08/2020 05:54
 * @description：日常任务
 */
public class DailyTasks{

    private IRb_group_userService groupUserService = ServiceUtils.getInstance().getUserService();

    public DailyTasks(String msg, GroupMessageEvent event){
        if (msg.indexOf("签到") == 0){
            checkIn(msg, event);
        }
    }

    /**
     * 签到
     */
    public void checkIn(String msg,GroupMessageEvent event){
        String qq = String.valueOf(event.getSender().getId());
        String group = String.valueOf(event.getGroup().getId());
        Rb_group_user user = groupUserService.findUserByQQAndGroupNumber(qq, group);
        BigDecimal score = user.getScore();
        user.setScore(user.getScore().add(new BigDecimal(1)));
        groupUserService.updateById(user);
        StringBuilder sb = new StringBuilder();
        sb.append("签到成功！\n")
        .append(event.getSender().getNick()+"(")
        .append(String.valueOf(event.getSender().getId())+")\n")
        .append("原有积分："+score.toString()+"\n")
        .append("现有积分："+user.getScore());
        event.getGroup().sendMessage(sb.toString());

    }

}
