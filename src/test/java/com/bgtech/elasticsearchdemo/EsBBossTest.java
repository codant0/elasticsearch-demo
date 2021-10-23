package com.bgtech.elasticsearchdemo;

import org.frameworkset.elasticsearch.ElasticSearchHelper;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author 黄杰峰
 * @Date 2020/9/4 0004 10:13
 * @Description BBoss Elasticsearch Test
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class EsBBossTest {

    @Test
    public void bbsCRUDTest() {

    }

    @Test
    public void insertTest() {
        ClientInterface clientUtil = ElasticSearchHelper.getRestClientUtil();

//        clientUtil
    }
}
