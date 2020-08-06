package com.qzw.robot.service.impl;

import com.qzw.robot.entity.Rb_group_user;
import com.qzw.robot.mapper.Rb_group_userMapper;
import com.qzw.robot.service.IRb_group_userService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YueGod
 * @since 2020-08-06
 */
@Service
public class Rb_group_userServiceImpl extends ServiceImpl<Rb_group_userMapper, Rb_group_user> implements IRb_group_userService {
    @Resource
    private Rb_group_userMapper userMapper;

    @Override
    public Rb_group_user findUserByQQAndGroupNumber(String qq, String number) {
        return userMapper.findUserByQQAndGroupNumber(qq, number);
    }
}
