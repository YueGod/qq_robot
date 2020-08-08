package com.qzw.robot.event;

import cn.hutool.core.img.Img;
import com.RobotApplication;
import com.qzw.robot.entity.Rb_group;
import com.qzw.robot.entity.Rb_group_history;
import com.qzw.robot.entity.Rb_group_user;
import com.qzw.robot.handler.MenuListHandler;
import com.qzw.robot.handler.MusicHandler;
import com.qzw.robot.menu.Music;
import com.qzw.robot.service.IRb_fuck_wordService;
import com.qzw.robot.service.IRb_groupService;
import com.qzw.robot.service.IRb_group_historyService;
import com.qzw.robot.service.IRb_group_userService;
import com.qzw.robot.util.ServiceUtils;
import com.qzw.robot.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.GroupImage;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.qqandroid.message.OnlineGroupImageImpl;
import net.mamoe.mirai.qqandroid.network.protocol.data.proto.ImMsgBody;
import net.mamoe.mirai.utils.ExternalImage;
import net.mamoe.mirai.utils.FileCacheStrategy;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ：quziwei
 * @date ：06/08/2020 20:11
 * @description：群消息事件
 */
@Slf4j

public class GroupMessagesEvent extends AbstractEvent {

    private IRb_groupService groupService = ServiceUtils.getInstance().getGroupService();
    private IRb_group_userService userService = ServiceUtils.getInstance().getUserService();
    private IRb_group_historyService historyService = ServiceUtils.getInstance().getHistoryService();
    private StringRedisTemplate stringRedisTemplate = SpringUtil.getBean(StringRedisTemplate.class);
    private IRb_fuck_wordService fuckWordService = ServiceUtils.getInstance().getFuckWordService();

    @EventHandler(priority = Listener.EventPriority.HIGHEST)
    public ListeningStatus message(GroupMessageEvent event) {

        /**
         * 消息处理
         */
        String msg = event.getMessage().toString().replaceAll("\\[.*\\]", "");
        if (event.getSender().getNameCard().indexOf("鸡王") > -1 || msg.indexOf("鸡王") > -1) {
            event.getGroup().sendMessage("你也不看看谁才是鸡王？");
            return ListeningStatus.LISTENING;
        }
        if (event.getSender().getNameCard().indexOf("杀鸡") > -1 || msg.indexOf("杀鸡") > -1) {
            event.getGroup().sendMessage("杀鸡之王才是最会杀鸡的？");
            return ListeningStatus.LISTENING;
        }
        /**
         * 将消息存放至数据库
         */
        Rb_group group = new Rb_group();
        group.setName(event.getGroup().getName());
        group.setNumber(String.valueOf(event.getGroup().getId()));
        if (groupService.findByNumber(String.valueOf(event.getGroup().getId())) == null) {
            try {
                groupService.save(group);
            } catch (Exception e) {
                group.setName(URLEncoder.encode(group.getName()));
                groupService.save(group);
            }
        } else {
            group = groupService.findByNumber(group.getNumber());
        }
        Rb_group_user user = new Rb_group_user();
        if (userService.findUserByQQAndGroupNumber(String.valueOf(event.getSender().getId()), group.getNumber()) == null) {
            user.setGroup_id(group.getId());
            user.setQq(String.valueOf(event.getSender().getId()));
            user.setNickname(event.getSender().getNick());
            userService.save(user);
        } else {
            user = userService.findUserByQQAndGroupNumber(String.valueOf(event.getSender().getId()), group.getNumber());
        }
        Rb_group_history history = new Rb_group_history();
        history.setContent(msg);
        Long time = event.getTime() * 1000L;
        history.setCreate_date(new Date(time));
        history.setGroup_id(group.getId());
        history.setUser_id(user.getId());
        historyService.save(history);
        log.info(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(history.getCreate_date()) + " : " + history.getContent());

        /**
         * 敏感词判断
         */
        if (fuckWordService.isFuckWord(msg)) {
            try {
                RobotApplication.bot.recall(event.getMessage());
            } catch (Exception e) {
                MessageChainBuilder messages = new MessageChainBuilder() {{
                    StringBuilder sb = new StringBuilder();
                    sb.append("👀 群员发脏话提醒\n" + "群员")
                            .append(event.getGroup().get(event.getSender().getId()).getNameCard() + " (" + event.getSender().getId() + ") ")
                            .append("在")
                            .append(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(time)))
                            .append("发了一句脏话：")
                            .append(msg + "\n")
                            .append("但是机器人无禁言权限");
                    add(sb.toString());
                }};
                event.getGroup().sendMessage(messages.asMessageChain());
            }
            return ListeningStatus.LISTENING;
        }
        /**
         * 功能处理
         */
        if (event.getMessage().toString().matches(".*\\[mirai:at:" + event.getBot().getId() + ",.*\\].*") &&
                !event.getMessage().toString().matches(".*\\[mirai:quote:\\d*,\\d*\\].*")) {
            if (!StringUtils.isEmpty(msg)){
                event.getGroup().sendMessage(msg);

                return ListeningStatus.LISTENING;
            }
            event.getGroup().sendMessage("@我是没用的\n发送 \"功能列表\" 获取功能列表");
            return ListeningStatus.LISTENING;
        }

        if (msg.equals("菜单列表")) {
            MenuListHandler menuListHandler = new MenuListHandler();
            String list = menuListHandler.list();
            event.getGroup().sendMessage(list);
            return ListeningStatus.LISTENING;
        }
        new Music(msg,event);
        return ListeningStatus.LISTENING;
    }


}
