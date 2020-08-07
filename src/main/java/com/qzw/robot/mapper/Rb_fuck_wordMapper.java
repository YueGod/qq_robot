package com.qzw.robot.mapper;

import com.qzw.robot.entity.Rb_fuck_word;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
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
public interface Rb_fuck_wordMapper extends BaseMapper<Rb_fuck_word> {

    Rb_fuck_word findByFuckWord(String word);

    List<String> findAllWords();

}
