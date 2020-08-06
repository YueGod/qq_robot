package com.qzw.robot.service;

import com.qzw.robot.entity.Rb_group_user;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YueGod
 * @since 2020-08-06
 */
public interface IRb_group_userService extends IService<Rb_group_user> {
    /**
     * 通过用户QQ和群号获取User实体
     */
    Rb_group_user findUserByQQAndGroupNumber(String qq,String number);
}
