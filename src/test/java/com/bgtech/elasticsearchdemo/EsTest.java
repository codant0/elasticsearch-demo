package com.bgtech.elasticsearchdemo;

import org.frameworkset.elasticsearch.ElasticSearchHelper;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author 黄杰峰
 * @Date 2020/9/4 0004 9:22
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsTest {

    @Test
    void esInsertTest() {
        ClientInterface clientUtil = ElasticSearchHelper.getRestClientUtil();
        // 获取es集群的状态，原生RestApi风格
        String result = clientUtil.executeHttp("_cluster/state?pretty",ClientInterface.HTTP_GET);
    }
}
