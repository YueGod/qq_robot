package com.qzw.robot.service.impl;

import com.qzw.robot.entity.Rb_group;
import com.qzw.robot.mapper.Rb_groupMapper;
import com.qzw.robot.service.IRb_groupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class Rb_groupServiceImpl extends ServiceImpl<Rb_groupMapper, Rb_group> implements IRb_groupService {
    @Resource
    private Rb_groupMapper groupMapper;

    @Override
    public Rb_group findByNumber(String number) {
        return groupMapper.findByNumber(number);
    }
}
