package com.qzw.robot.event;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.qzw.robot.entity.Rb_group;
import com.qzw.robot.entity.Rb_group_user;
import com.qzw.robot.service.IRb_groupService;
import com.qzw.robot.service.IRb_group_userService;
import com.qzw.robot.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;

/**
 * @author ：quziwei
 * @date ：06/08/2020 16:22
 * @description：群管理相关
 */

@Slf4j
public class GroupAdminEvent extends AbstractEvent{
    private IRb_groupService groupService;
    private IRb_group_userService group_userService;

    @PostConstruct
    public void init(){
        this.groupService = SpringUtil.getBean(IRb_groupService.class);
        this.group_userService = SpringUtil.getBean(IRb_group_userService.class);
    }

    /**
     * 新成员加入群组
     */
    @EventHandler
    public void join(MemberJoinEvent event){
        MessageChainBuilder builder = new MessageChainBuilder(){{
            add("👏 欢迎第" + (event.getGroup().getMembers().size() + 1) + "名群员。" + "\n");
            add(new At(event.getMember()));
            add("\n" + "记得阅读群公告（如果有的话）哦！");
            Rb_group group = groupService.findByNumber(String.valueOf(event.getGroup().getId()));
            if (ObjectUtils.isEmpty(group)){
                group = new Rb_group();
                group.setNumber(String.valueOf(event.getGroup().getId()));
                group.setName(event.getGroup().getName());
                groupService.save(group);
                group = groupService.findByNumber(group.getNumber());
            }
            Rb_group_user groupUser = new Rb_group_user();
            groupUser.setGroup_id(group.getId());
            groupUser.setNickname(event.getMember().getNick());
            groupUser.setQq(String.valueOf(event.getMember().getId()));
            group_userService.save(groupUser);
        }};
    }

    /**
     * 成员离开群组
     */
    @EventHandler
    public void leave(MemberLeaveEvent event){
        MessageChainBuilder messages = new MessageChainBuilder() {{
            add("⚠ 群员减少提醒\n" + "群员" +
                    event.getMember().getId() +
                    "已退出群聊。");
            Rb_group_user user = group_userService.findUserByQQAndGroupNumber(GroupAdminEvent.super.getUserId(event),GroupAdminEvent.super.getGourpId(event));
            group_userService.removeById(user.getId());
        }};
        event.getGroup().sendMessage(messages.asMessageChain());
    }

    /**
     * 成员被禁言
     */
    @EventHandler
    public void mute(MemberMuteEvent event){
        MessageChainBuilder messages = new MessageChainBuilder() {{
            add("⚠ 群员被禁言提醒\n" + "群员" +
                    event.getGroup().get(event.getMember().getId()).getNameCard() + " (" + event.getMember().getId() + ") " +
                    "已被管理员" + event.getGroup().get(event.getOperator().getId()).getNameCard() + " (" + event.getOperator().getId() + ") " +
                    "禁言，解封时间：" + DateUtil.offsetSecond(new DateTime(), event.getDurationSeconds()));
            add(String.valueOf(event.getGroup().getId()));
        }};
        event.getGroup().sendMessage(messages.asMessageChain());
    }

    /**
     * 解除禁言
     */
    @EventHandler
    public void unMute(MemberUnmuteEvent event) {
        MessageChainBuilder messages = new MessageChainBuilder() {{
            add("⚠ 群员被解除禁言提醒\n" + "群员" +
                    event.getMember().getId() +
                    "已被管理员" + event.getGroup().get(event.getOperator().getId()).getNameCard() + " (" + event.getOperator().getId() + ") " +
                    "解除禁言。");
        }};
        event.getGroup().sendMessage(messages.asMessageChain());
    }

    /**
     * 消息撤回处理
     */
    @EventHandler
    public void onBotRecalled(MessageRecallEvent.GroupRecall event) {
        MessageChainBuilder messages = new MessageChainBuilder() {{
            add("👀群员撤回消息提醒\n" + event.getGroup().get(event.getOperator().getId()).getNameCard() + " (" + event.getOperator().getId() + ") " +
                    "撤回了" +
                    event.getGroup().get(event.getAuthorId()).getNameCard() + " (" + event.getAuthorId() + ") " +
                    "在 " +
                    TimeUtil.reformatDateTimeOfTimestamp(event.getMessageTime()) +
                    " 发的一条消息。");
        }};
        event.getGroup().sendMessage(messages.asMessageChain());
    }

}
