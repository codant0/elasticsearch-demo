package com.bgtech.elasticsearchdemo.entity;

import com.frameworkset.orm.annotation.ESId;
import lombok.Data;

import java.util.Date;

/**
 * @Author 黄杰峰
 * @Date 2020/9/3 0003 17:23
 * @Description
 */
@Data
public class DemoEntity {
    @ESId
    private long demoId;
    private Date agentStarttime;
    private Date agentStarttimezh;
    private String applicationName;
    private String contentbody;
    private String name;
    private String orderId;
    private int contrastStatus;
}
