package com.qzw.robot.service.impl;

import com.qzw.robot.entity.Rb_group_history;
import com.qzw.robot.mapper.Rb_group_historyMapper;
import com.qzw.robot.service.IRb_group_historyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YueGod
 * @since 2020-08-06
 */
@Service
public class Rb_group_historyServiceImpl extends ServiceImpl<Rb_group_historyMapper, Rb_group_history> implements IRb_group_historyService {
    @Resource
    private Rb_group_historyMapper historyMapper;

    @Override
    public Rb_group_history findByQqAndGroupIdAndCreateDate(String qq, String number, Date createDate) {
        return historyMapper.findByQqAndGroupIdAndCreateDate(qq, number, createDate);
    }
}
