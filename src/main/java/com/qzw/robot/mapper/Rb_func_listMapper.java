package com.qzw.robot.mapper;

import com.qzw.robot.entity.Rb_func_list;
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
public interface Rb_func_listMapper extends BaseMapper<Rb_func_list> {
    List<String> findList();
}
