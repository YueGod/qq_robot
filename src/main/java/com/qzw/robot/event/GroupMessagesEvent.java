package com.qzw.robot.event;

import com.RobotApplication;
import com.qzw.robot.entity.Rb_group;
import com.qzw.robot.entity.Rb_group_history;
import com.qzw.robot.entity.Rb_group_user;
import com.qzw.robot.service.IRb_fuck_wordService;
import com.qzw.robot.service.IRb_groupService;
import com.qzw.robot.service.IRb_group_historyService;
import com.qzw.robot.service.IRb_group_userService;
import com.qzw.robot.util.ServiceUtils;
import com.qzw.robot.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import org.springframework.data.redis.core.StringRedisTemplate;

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

    @EventHandler
    public ListeningStatus message(GroupMessageEvent event) {
        //将收到的消息写入数据库
        String msg = event.getMessage().toString().replaceAll("\\[.*\\]", "");
        Rb_group group = new Rb_group();
        group.setName(event.getGroup().getName());
        group.setNumber(String.valueOf(event.getGroup().getId()));
        if (groupService.findByNumber(String.valueOf(event.getGroup().getId())) == null) {
            groupService.save(group);
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
//            List<Rb_group> list = (List<Rb_group>) redisTemplate.opsForValue().get("groups");
//            list.add(group);
//            redisTemplate.opsForValue().set("groups",list);

        //判断是否为脏话
        if (fuckWordService.isFuckWord(msg)) {
            try {
                RobotApplication.bot.recall(event.getMessage());
            } catch (Exception e) {
                MessageChainBuilder messages = new MessageChainBuilder() {{
                    StringBuilder sb = new StringBuilder();
                    sb.append("⚠ 群员发脏话提醒\n" + "群员")
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
        if (event.getMessage().toString().matches(".*\\[mirai:at:" + event.getBot().getId() + ",.*\\].*") &&
                !event.getMessage().toString().matches(".*\\[mirai:quote:\\d*,\\d*\\].*")) {
            System.out.println(event.getMessage().toString());
            event.getGroup().sendMessage("@我是没用的\n发送 \"功能列表\" 获取功能列表");
            return ListeningStatus.LISTENING;
        }
        if (msg.equals("功能列表")){

        }

        return ListeningStatus.LISTENING;
    }
}
