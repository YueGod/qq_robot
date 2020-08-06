package com.qzw.robot.event;

import kotlin.coroutines.experimental.CoroutineContext;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMemberEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author ：quziwei
 * @date ：06/08/2020 16:11
 * @description：群管理员监听事件
 */
public abstract class AbstractEvent extends SimpleListenerHost {

    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        System.out.println(context + " " + exception);
    }

    /**
     * 获取群ID
     */
    public String getGourpId(GroupMemberEvent event){
        return String.valueOf(event.getGroup().getId());
    }

    /**
     * 获取群名称
     */
    public String getGroupName(GroupMemberEvent event){
        return String.valueOf(event.getGroup().getName());
    }

    /**
     * 获取用户ID
     */
    public String getUserId(GroupMemberEvent event){
        return String.valueOf(event.getMember().getId());
    }

    /**
     * 获取用户昵称
     */
    public String getUserNick(GroupMemberEvent event){
        return String.valueOf(event.getMember().getNick());
    }
}
