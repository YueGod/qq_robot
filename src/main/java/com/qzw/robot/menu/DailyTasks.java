package com.qzw.robot.menu;

import com.qzw.robot.entity.Rb_group_user;
import com.qzw.robot.event.GroupMessagesEvent;
import com.qzw.robot.model.CheckIn;
import com.qzw.robot.service.IRb_group_userService;
import com.qzw.robot.util.RobotUtils;
import com.qzw.robot.util.ServiceUtils;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：quziwei
 * @date ：08/08/2020 05:54
 * @description：日常任务
 */
public class DailyTasks{

    private RedisTemplate redisTemplate = ServiceUtils.getInstance().getRedisTemplate();

    private IRb_group_userService groupUserService = ServiceUtils.getInstance().getUserService();

    public DailyTasks(String msg, GroupMessageEvent event){
        if (msg.indexOf("签到") == 0){
            checkIn(msg, event);
        }
        new Images(msg, event);
    }

    /**
     * 签到
     */
    public void checkIn(String msg,GroupMessageEvent event){
        String qq = String.valueOf(event.getSender().getId());
        String group = String.valueOf(event.getGroup().getId());
        CheckIn checkIn = new CheckIn();
        checkIn.setNumber(group);
        checkIn.setQq(qq);
        if (isCheckInOnRedis(checkIn)){
            MessageChainBuilder builder = new MessageChainBuilder(){{
                add(new At(event.getSender()));
                add("您今天已经签到过~");
            }};
            event.getGroup().sendMessage(builder.asMessageChain());
            return;
        }
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

        RobotUtils.checkIns.add(checkIn);
        event.getGroup().sendMessage(sb.toString());
    }

    public boolean isCheckInOnRedis(CheckIn checkIn){

        try {

            return RobotUtils.checkIns.contains(checkIn);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

}
