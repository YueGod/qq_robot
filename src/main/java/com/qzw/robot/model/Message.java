package com.qzw.robot.model;

import lombok.Data;

import java.util.Date;

/**
 * @author ：quziwei
 * @date ：06/08/2020 20:37
 * @description：
 */
@Data
public class Message {
    private Integer id;
    private String content;
    private Date create_date;
    private User user;
}
