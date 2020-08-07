package com.qzw.robot.mapper;

import com.qzw.robot.entity.Rb_group_history;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Date;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author YueGod
 * @since 2020-08-06
 */
public interface Rb_group_historyMapper extends BaseMapper<Rb_group_history> {
    Rb_group_history findByQqAndGroupIdAndCreateDate(String qq, String number, Date createDate);
}
