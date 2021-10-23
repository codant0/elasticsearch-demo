package com.bgtech.elasticsearchdemo;

import org.frameworkset.elasticsearch.client.ClientInterface;
import org.frameworkset.elasticsearch.boot.BBossESStarter;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author 黄杰峰
 * @Date 2020/9/4 0004 10:00
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsSpringBootTest {

    @Autowired
    BBossESStarter bBossESStarter;



}
