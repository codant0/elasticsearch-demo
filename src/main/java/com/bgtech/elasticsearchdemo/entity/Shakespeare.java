package com.bgtech.elasticsearchdemo.entity;

import com.frameworkset.orm.annotation.ESId;
import lombok.Data;

/**
 * @author HuangJiefeng
 * @date 2020/9/4 0004 15:34
 * @description: 对应Es库中莎士比亚数据集的实体类
 *  莎士比亚数据集格式：
 *      {
 *          "line_id": INT,
 *          "play_name": "String",
 *          "speech_number": INT,
 *          "line_number": "String",
 *          "speaker": "String",
 *          "text_entry": "String",
 *      }
 *
 */
@Data
public class Shakespeare {

    @ESId
    private int lineId;

    private String playName;

    private int speechNumber;

    private String lineNumber;

    private String speaker;

    private String textEntry;
}
