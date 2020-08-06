package com.qzw.robot.service;

import com.qzw.robot.entity.Rb_group;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YueGod
 * @since 2020-08-06
 */
public interface IRb_groupService extends IService<Rb_group> {
    Rb_group findByNumber(String number);
}
