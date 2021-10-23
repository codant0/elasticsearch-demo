package com.bgtech.elasticsearchdemo;

import com.bgtech.elasticsearchdemo.entity.DemoEntity;
import org.frameworkset.elasticsearch.ElasticSearchHelper;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.frameworkset.elasticsearch.entity.ESDatas;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 黄杰峰
 * @Date 2020/9/4 0004 11:25
 * @Description
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class EsDslTest {

    @Test
    public void testDsl() throws ParseException {
        // 创建加载配置文件的客户端工具，用来检索文档，单实例多线程安全
        ClientInterface clientUtil = ElasticSearchHelper.getConfigRestClientUtil("esmapper/demo.xml");

        //设定查询条件,通过map传递变量参数值,key对于dsl中的变量名称
        //dsl中有四个变量
        //        applicationName1
        //        applicationName2
        //        startTime
        //        endTime
        Map<String,Object> params = new HashMap<String,Object>();
        //设置applicationName1和applicationName2两个变量的值
        params.put("applicationName1","blackcatdemo2");
        params.put("applicationName2","blackcatdemo3");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //设置时间范围,时间参数接受long值
        params.put("startTime",dateFormat.parse("2017-09-02 00:00:00"));
        params.put("endTime",new Date());
        //返回的文档封装对象类型
        ESDatas<DemoEntity> esDatas = null;
        //保存总记录数
        long totalSize = 0;
        //保存每页结果对象列表，最多返回1000条记录
        List<DemoEntity> demos = null;
        int i = 0; //页码
        do{//遍历获取每页的记录
            //设置分页参数
            params.put("from",i * 1000);//分页起点
            params.put("size",1000);//每页返回1000条
            i ++;//往前加页码
            //执行查询，demo为索引表，_search为检索操作action
            esDatas =  //ESDatas包含当前检索的记录集合，最多1000条记录，由dsl中的size属性指定
                    clientUtil.searchList("demo/_search",//demo为索引表，_search为检索操作action
                            "searchPagineDatas",//esmapper/demo.xml中定义的dsl语句
                            params,//变量参数
                            DemoEntity.class);//返回的文档封装对象类型
            demos = esDatas.getDatas();//每页结果对象列表，最多返回1000条记录
            totalSize = esDatas.getTotalSize();//总记录数
            if(i * 1000 > totalSize)
                break;
        }while(true);
//        String json = clientUtil.executeRequest("demo/_search",//demo为索引表，_search为检索操作action
//                "searchDatas",//esmapper/demo.xml中定义的dsl语句
//                params);
//        String json = com.frameworkset.util.SimpleStringUtil.object2json(demos);
        System.out.println(totalSize);
    }
}
