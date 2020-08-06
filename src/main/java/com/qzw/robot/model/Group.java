package com.qzw.robot.model;

import lombok.Data;

import java.util.List;

/**
 * @author ：quziwei
 * @date ：06/08/2020 20:36
 * @description：
 */
@Data
public class Group {
    private Integer id;
    private String name;
    private String number;
    private List<Message> messages;
}
