package com.qzw.robot.event;

import cn.hutool.db.ds.DataSourceWrapper;
import com.mysql.cj.jdbc.CallableStatementWrapper;
import com.qzw.robot.entity.Rb_group;
import com.qzw.robot.model.Group;
import com.qzw.robot.service.IRb_groupService;
import com.qzw.robot.service.IRb_group_historyService;
import com.qzw.robot.service.IRb_group_userService;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.message.GroupMessageEvent;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import java.sql.Wrapper;
import java.util.List;

/**
 * @author ：quziwei
 * @date ：06/08/2020 20:11
 * @description：群消息事件
 */
public class GroupMessagesEvent extends AbstractEvent{

    private IRb_groupService groupService;
    private IRb_group_userService userService;
    private IRb_group_historyService historyService;
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void init(){
        this.groupService = (IRb_groupService) super.getBean(IRb_groupService.class);
        this.userService = (IRb_group_userService) super.getBean(IRb_group_userService.class);
        this.historyService = (IRb_group_historyService) super.getBean(IRb_group_historyService.class);
        this.redisTemplate = (RedisTemplate) super.getBean(RedisTemplate.class);
    }

    @EventHandler
    public void message(GroupMessageEvent event){
        String msg = event.getMessage().toString().replaceAll("\\[.*\\]","");
        Rb_group group = new Rb_group();
        group.setName(event.getGroup().getName());
        group.setNumber(String.valueOf(event.getGroup().getId()));
        if (groupService.findByNumber(String.valueOf(event.getGroup().getId())) == null){
            groupService.save(group);
            group = groupService.findByNumber(group.getNumber());
            List<Rb_group> list = (List<Rb_group>) redisTemplate.opsForValue().get("groups");
            list.add(group);
            redisTemplate.opsForValue().set("groups",list);
        }

    }
}
