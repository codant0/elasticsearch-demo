package com.bgtech.elasticsearchdemo.entity;

import com.frameworkset.orm.annotation.ESId;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author 黄杰峰
 * @Date 2020/9/4 0004 11:19
 * @Description
 */
public class TAgentInfo implements Serializable {
    private String hostname;
    //ip属性作为文档唯一标识，根据ip值对应的索引文档存在与否来决定添加或者修改操作
    @ESId
    private String ip;
    private String ports;
    private String agentId;
    private String location;
    private String applicationName;
    private int serviceType;
    private int pid;
    private String agentVersion;
    private String vmVersion;
    //日期类型
    private Date startTimestampDate;
    private Date endTimestampDate;
    private long startTimestamp;
    private long endTimestamp;
    private int endStatus;
    private String serverMetaData;
    private String jvmInfo;
}
