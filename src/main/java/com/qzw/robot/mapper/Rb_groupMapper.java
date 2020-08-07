package com.qzw.robot.mapper;

import com.qzw.robot.entity.Rb_group;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author YueGod
 * @since 2020-08-06
 */
@Mapper
public interface Rb_groupMapper extends BaseMapper<Rb_group> {
    Rb_group findByNumber(String number);

}
