package com.qzw.robot.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：quziwei
 * @date ：08/08/2020 15:31
 * @description：签到
 */
@Data
public class CheckIn implements Serializable {
    private String qq;
    private String number;
}
