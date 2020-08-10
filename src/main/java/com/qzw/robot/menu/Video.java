package com.qzw.robot.menu;

import com.qzw.robot.util.VideoSearchUtil;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.LightApp;

import java.io.IOException;

/**
 * @author ：quziwei
 * @date ：09/08/2020 12:08
 * @description：视频分享类
 */
public class Video {

    public Video(String msg, GroupMessageEvent event){
        if (msg.indexOf("看视频 ") == 0){
            findBilibili(msg, event);
            return;
        }
    }

    public void findBilibili(String msg, GroupMessageEvent event){
        try {
            String json = VideoSearchUtil.bilibiliVideo(msg.replaceAll("看视频 ", ""));
            event.getGroup().sendMessage(new LightApp(json));
        } catch (IOException e) {
            event.getGroup().sendMessage("啊哦~~视频搜索失败~~");
        }
    }
}
