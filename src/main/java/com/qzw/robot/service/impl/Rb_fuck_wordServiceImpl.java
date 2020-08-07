package com.qzw.robot.service.impl;

import com.qzw.robot.entity.Rb_fuck_word;
import com.qzw.robot.entity.Rb_group_user;
import com.qzw.robot.mapper.Rb_fuck_wordMapper;
import com.qzw.robot.service.IRb_fuck_wordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YueGod
 * @since 2020-08-06
 */
@Service
public class Rb_fuck_wordServiceImpl extends ServiceImpl<Rb_fuck_wordMapper, Rb_fuck_word> implements IRb_fuck_wordService {

    @Resource
    Rb_fuck_wordMapper fuckWordMapper;

    @Override
    public boolean isFuckWord(String word) {

        List<String> wordList = fuckWordMapper.findAllWords();
        for (String s : wordList) {
            if (word.indexOf(s)>-1){
                return true;
            }

        }
        return false;

    }
}
