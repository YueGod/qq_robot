package com.qzw.robot.mapper;

import com.qzw.robot.entity.Rb_group_user;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author YueGod
 * @since 2020-08-06
 */
@Mapper
public interface Rb_group_userMapper extends BaseMapper<Rb_group_user> {
    /**
     * 通过用户QQ和群号获取User实体
     */
    Rb_group_user findUserByQQAndGroupNumber(String qq,String number);
}
