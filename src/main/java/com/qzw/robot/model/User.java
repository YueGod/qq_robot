package com.qzw.robot.model;

import lombok.Data;

import java.util.List;

/**
 * @author ：quziwei
 * @date ：06/08/2020 20:37
 * @description：
 */
@Data
public class User {
    private Integer id;
    private String qq;
    private String nickname;
    private List<Group> groups;
    private List<Message> messages;
}
