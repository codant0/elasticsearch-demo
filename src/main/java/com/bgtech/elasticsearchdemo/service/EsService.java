package com.bgtech.elasticsearchdemo.service;

/**
 * @author HuangJiefeng
 * @date 2020/9/4 0004 15:38
 * @description
 */
public interface EsService {

    /**
     * es插入
     */
    public void esInsert();

    /**
     * es批量插入
     */
    public void esBatchInsert();

    /**
     * es删除
     */
    public void esDelete();

    /**
     * es分页查询
     */
    public void esPaginationQuery();
}
