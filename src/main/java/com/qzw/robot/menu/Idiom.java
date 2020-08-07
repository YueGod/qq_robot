package com.qzw.robot.menu;

import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;

/**
 * @author ：quziwei
 * @date ：08/08/2020 05:49
 * @description：成语接龙
 */
public class Idiom {
    public Idiom(String msg, GroupMessageEvent event) {
        if (msg.indexOf("成语接龙") == 0) {
            start(msg, event);
            return;
        }
        new DailyTasks(msg, event);
    }

    public void start(String msg,GroupMessageEvent event) {

        event.getGroup().sendMessage("接你妈");
    }
}
