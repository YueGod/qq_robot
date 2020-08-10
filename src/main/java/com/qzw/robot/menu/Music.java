package com.qzw.robot.menu;

import com.qzw.robot.event.GroupMessagesEvent;
import com.qzw.robot.handler.MusicHandler;
import com.qzw.robot.model.MusicInfo;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;
import org.springframework.util.ObjectUtils;
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
        String music = msg.replaceAll("听音乐 ","");
        MusicInfo musicInfo = new MusicHandler().findMusicInfo(music);
        if (!ObjectUtils.isEmpty(musicInfo)){
            MessageChainBuilder builder = new MessageChainBuilder(){{
                String json = "{\"app\":\"com.tencent.structmsg\",\"config\":{\"autosize\":true,\"ctime\":1596942253,\"forward\":true,\"token\":\"c185986430d9602afb5f6f474e1ecbc4\",\"type\":\"normal\"},\"desc\":\"音乐\",\"extra\":{\"app_type\":1,\"appid\":100495085},\"meta\":{\"music\":{\"action\":\"\",\"android_pkg_name\":\"\",\"app_type\":1,\"appid\":100495085,\"desc\":\""+musicInfo.getDesc()+"\",\"jumpUrl\":\""+musicInfo.getJumpUrl()+"\",\"musicUrl\":\""+musicInfo.getMusicUrl()+"\",\"preview\":\""+musicInfo.getPreview()+"\",\"sourceMsgId\":\"0\",\"source_icon\":\"\",\"source_url\":\"\",\"tag\":\"网易云音乐\",\"title\":\""+musicInfo.getMusicName()+"\"}},\"prompt\":\"[分享]"+musicInfo.getMusicName()+"\",\"ver\":\"0.0.0.1\",\"view\":\"music\"}";
                add(new LightApp(json));

            }};
            event.getGroup().sendMessage(builder.asMessageChain());
        }else {
            event.getGroup().sendMessage("对不起，没有找到\""+msg.replaceAll("听音乐 ","")+"\"这首歌");
        }
    }

    public void shared(String msg, GroupMessageEvent event){
        MessageChainBuilder builder = new MessageChainBuilder(){{
            String json = "{\"app\":\"com.tencent.structmsg\",\"config\":{\"autosize\":true,\"ctime\":1596942253,\"forward\":true,\"token\":\"c185986430d9602afb5f6f474e1ecbc4\",\"type\":\"normal\"},\"desc\":\"音乐\",\"extra\":{\"app_type\":1,\"appid\":100495085},\"meta\":{\"music\":{\"action\":\"\",\"android_pkg_name\":\"\",\"app_type\":1,\"appid\":100495085,\"desc\":\"林宥嘉\",\"jumpUrl\":\"https://y.music.163.com/m/song?id=417250911&userid=83974353\",\"musicUrl\":\"http://music.163.com/song/media/outer/url?id=417250911\",\"preview\":\"https://cpic.url.cn/v1/aa6eoboc53bk9h457h1tm8rueun48lqmf2lnlol4kmqbhjv9hh0aachi3itcv41kjla6uifuj2m2u8ivulhefeqgqcuo4911lrm2hcl9tgm3qlcufr1bgdio5aiichjr7f82j4gr13rvvo5mqasda78repmbu32e02tah7ilmmop5t53oreg/bulps27rab4hblqf1sch7rk5o3fe6t09r9mt9r99cocnc79v7v00\",\"sourceMsgId\":\"0\",\"source_icon\":\"\",\"source_url\":\"\",\"tag\":\"网易云音乐\",\"title\":\"我梦见你梦见我\"}},\"prompt\":\"[分享]我梦见你梦见我\",\"ver\":\"0.0.0.1\",\"view\":\"music\"}";
            add(new LightApp(json));


        }};

        event.getGroup().sendMessage(builder.asMessageChain());

    }

}
