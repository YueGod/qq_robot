package com.qzw.robot.menu;

import com.qzw.robot.event.GroupMessagesEvent;
import com.qzw.robot.handler.MusicHandler;
import net.mamoe.mirai.message.GroupMessageEvent;

/**
 * @author ：quziwei
 * @date ：08/08/2020 05:41
 * @description：音乐相关
 */
public class Music {

    public Music(String msg, GroupMessageEvent event){
        if (msg.indexOf("听音乐 " ) == 0){
            findMusic(msg,event);
            return;
        }
        new Idiom(msg, event);
    }

    public void findMusic(String msg, GroupMessageEvent event){
        String music = new MusicHandler().findMusic(msg);
        event.getGroup().sendMessage(music);
    }

}
