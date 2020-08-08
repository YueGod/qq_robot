package com.qzw.robot.menu;

import com.qzw.robot.event.GroupMessagesEvent;
import com.qzw.robot.handler.MusicHandler;
import net.mamoe.mirai.message.GroupMessageEvent;
import org.springframework.util.StringUtils;

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
        if (!StringUtils.isEmpty(music)){
            event.getGroup().sendMessage(music);
        }else {
            event.getGroup().sendMessage("对不起，没有找到\""+msg.replaceAll("听音乐 ","")+"\"这首歌");
        }
    }

}