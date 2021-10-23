package com.bgtech.elasticsearchdemo;

import org.frameworkset.elasticsearch.ElasticSearchHelper;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.frameworkset.elasticsearch.entity.ESDatas;
import org.frameworkset.elasticsearch.scroll.HandlerInfo;
import org.frameworkset.elasticsearch.scroll.ScrollHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @Author 黄杰峰
 * @Date 2020/9/4 0004 9:22
 * @Description 原生ElasticSearch测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsTest {

    @Test
    public void esInsertAndSearchTest() {
        ClientInterface clientUtil = ElasticSearchHelper.getRestClientUtil();

        // 获取es集群的状态，原生RestApi风格
        String result = clientUtil.executeHttp("_cluster/state?pretty",ClientInterface.HTTP_GET);

        // 判断指定索引(twitter)下的类型(tweet)是否存在
        boolean exist1 = clientUtil.existIndiceType("twitter","tweet");
        System.out.println("twitter  tweet type exist：" + exist1);
        // 判断指定索引(twitter)是否存在
        exist1 = clientUtil.existIndice("twitter");
        System.out.println("Index twitter exist: " + exist1);
        // 获取index下所有document数量
        long count = clientUtil.countAll("demo");
        System.out.println("documents of index-demo：" + count);

        // 获取指定index下所有document，默认数量为5k
        ESDatas<Map> esDatas = clientUtil.searchAll("demo", Map.class);
        // 获取index下所有的document，设置获取数量，使用ScrollHandler来处理数据
        clientUtil.searchAll("demo", 10000, new ScrollHandler<Map>() {

            @Override
            public void handle(ESDatas<Map> esDatas, HandlerInfo handlerInfo) throws Exception {
                List<Map> dataList = esDatas.getDatas();
                System.out.println("TotalSize:"+esDatas.getTotalSize());
                if(dataList != null) {
                    System.out.println("dataList.size:" + dataList.size());
                }
                else
                {
                    System.out.println("dataList.size:0");
                }
                //do something other such as do a db query.
                //SQLExecutor.queryList(Map.class,"select * from td_sm_user");
            }
        }, Map.class);
        // 多线程获取索引下所有document，此处线程数为2
        // You can also use ScrollHandler to process each batch of datas on your own.
        esDatas = clientUtil.searchAllParallel("demo", Map.class,2);
        System.out.println("searchAllParallel:ok");
    }

    @Test
    public void esDeleteTest() {
        ClientInterface clientUtil = ElasticSearchHelper.getRestClientUtil();
        clientUtil.deleteDocument("index", "type", "id");
        clientUtil.deleteDocuments("index", "type", new String[]{"id1", "id2", "id3"});
    }

}
