package com.qzw.robot.menu;

import com.RobotApplication;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.ContactJavaFriendlyAPIKt;
import net.mamoe.mirai.event.events.ImageUploadEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.ExternalImage;
import org.jetbrains.annotations.NotNull;

import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

/**
 * @author ：quziwei
 * @date ：08/08/2020 13:51
 * @description：图片发送
 */
public class Images{

    public Images(String msg, GroupMessageEvent event){
        try {
            flushImage(msg, event);
        }catch (Exception e){
            if (msg.indexOf("看美女") == 0){
                girl(msg, event);
                return;
            }
            new Video(msg, event);
        }
    }

    /**
     * 捕获闪照并转发到群里
     */
    public void flushImage(String msg, GroupMessageEvent event){
        Image image = ((FlashImage)event.getMessage().get(1)).getImage();
        MessageChainBuilder messages = new MessageChainBuilder(){{
            add("来康康闪照啦~\n");
            add(RobotApplication.bot.queryImageUrl(image));
        }};
        event.getGroup().sendMessage(messages.asMessageChain());
    }

    /**
     * 美女写真
     */
    public void girl(String msg, GroupMessageEvent event){
        String path = "F:\\java-code\\qq_robot\\target\\classes\\image";
        File file = new File(path);
        File[] files = file.listFiles();
        Random random = new Random();
        int i = random.nextInt(6);
        File image = files[i];
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Image uploadImage = event.getGroup().uploadImage(inputStream);
        MessageChainBuilder messages = new MessageChainBuilder(){{
            add(RobotApplication.bot.queryImageUrl(uploadImage));
            Face face = new Face(1);
        }};
        event.getGroup().sendMessage(messages.asMessageChain());

    }

}
