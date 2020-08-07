package com.qzw.robot.service;

import com.qzw.robot.entity.Rb_group_history;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YueGod
 * @since 2020-08-06
 */
public interface IRb_group_historyService extends IService<Rb_group_history> {
    Rb_group_history findByQqAndGroupIdAndCreateDate(String qq, String number, Date createDate);
}
