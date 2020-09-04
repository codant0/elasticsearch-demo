package com.bgtech.elasticsearchdemo;

import bboss.org.apache.velocity.runtime.parser.ParseException;
import com.bgtech.elasticsearchdemo.entity.DemoEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.frameworkset.elasticsearch.ElasticSearchException;
import org.frameworkset.elasticsearch.ElasticSearchHelper;
import org.frameworkset.elasticsearch.boot.BBossESStarter;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.frameworkset.elasticsearch.entity.ESDatas;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchDemoEntityApplicationTests {

    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Autowired
    private BBossESStarter bbossESStarter;

    private String mappath = "esmapper/demo.xml";

    @Test
    public void contextLoads() {
        ClientInterface configClientUtil = bbossESStarter.getConfigRestClient(mappath);
        ClientInterface restClient = bbossESStarter.getRestClient();
    }

    @Test
    public void dropAndCreateAndGetIndice(){
        //Create a client tool to load configuration files, single instance multithreaded security
        ClientInterface clientUtil = bbossESStarter.getConfigRestClient(mappath);
        try {
            //To determine whether the indice demo exists, it returns true if it exists and false if it does not
            boolean exist = clientUtil.existIndice("demo");

            //Delete mapping if the indice demo already exists
            if(exist) {
                String r = clientUtil.dropIndice("demo");
                logger.debug("clientUtil.dropIndice(\"demo\") response:"+r);

            }
            //Create index demo
            clientUtil.createIndiceMapping("demo",//The indice name
                    "createDemoIndice");//Index mapping DSL script name, defined createDemoIndice in esmapper/demo.xml

            String demoIndice = clientUtil.getIndice("demo");//Gets the newly created indice structure
            logger.info("after createIndiceMapping clientUtil.getIndice(\"demo\") response:"+demoIndice);
        } catch (ElasticSearchException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    public void addAndUpdateDocument()  {
        //Build a create/modify/get/delete document client object, single instance multi-thread security
        ClientInterface clientUtil = bbossESStarter.getRestClient();
        //Build an object as index document
        DemoEntity demo = new DemoEntity();
        demo.setDemoId(2L);//Specify the document id, the unique identity, and mark with the @ESId annotation. If the demoId already exists, modify the document; otherwise, add the document
        demo.setAgentStarttime(new Date());
        demo.setAgentStarttimezh(new Date());
        demo.setApplicationName("blackcatdemo2");
        demo.setContentbody("this is content body2");
        demo.setName("liudehua");
        demo.setOrderId("NFZF15045871807281445364228");
        demo.setContrastStatus(2);


        //Add the document and force refresh
        String response = clientUtil.addDocument("demo",//indice name
                "demo",//idnex type
                demo,"refresh=true");



        logger.debug("Print the result：addDocument-------------------------");
        logger.debug(response);

        demo = new DemoEntity();
        demo.setDemoId(3l);//Specify the document id, the unique identity, and mark with the @ESId annotation. If the demoId already exists, modify the document; otherwise, add the document
        demo.setAgentStarttime(new Date());
        demo.setApplicationName("blackcatdemo3");
        demo.setContentbody("this is content body3");
        demo.setName("zhangxueyou");
        demo.setOrderId("NFZF15045871807281445364228");
        demo.setContrastStatus(3);
        demo.setAgentStarttime(new Date());
        demo.setAgentStarttimezh(new Date());

        //Add the document and force refresh
        response = clientUtil.addDocument("demo",//indice name
                "demo",//idnex type
                demo,"refresh=true");

        //Get the document object according to the document id, and return the DemoEntity object
        demo = clientUtil.getDocument("demo",//indice name
                "demo",//idnex type
                "2",//document id
                DemoEntity.class);

        //update document
        demo = new DemoEntity();
        demo.setDemoId(2l);//Specify the document id, the unique identity, and mark with the @ESId annotation. If the demoId already exists, modify the document; otherwise, add the document
        demo.setAgentStarttime(new Date());
        demo.setApplicationName("blackcatdemo2");
        demo.setContentbody("this is modify content body2");
        demo.setName("刘德华modify\t");
        demo.setOrderId("NFZF15045871807281445364228");
        demo.setContrastStatus(2);
        demo.setAgentStarttimezh(new Date());
        //Execute update and force refresh
        response = clientUtil.addDocument("demo",//index name
                "demo",//idnex type
                demo,"refresh=true");


        //Get the modified document object according to the document id and return the json message string
        response = clientUtil.getDocument("demo",//indice name
                "demo",//idnex type
                "2");//document id
        logger.debug("Print the modified result:getDocument-------------------------");
        logger.debug(response);




        logger.debug("Print the modified result：getDocument-------------------------");
        logger.debug(response);


    }

    @Test
    public void addDateDocumentTest() {
        //一个完整的批量添加和修改索引文档的案例
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        String date = format.format(new Date());
        ClientInterface clientUtil = ElasticSearchHelper.getRestClientUtil();
        List<DemoEntity> demos = new ArrayList<>();
        DemoEntity demo = new DemoEntity();
        demo.setDemoId(2L);
        demo.setAgentStarttime(new Date());
        demo.setApplicationName("blackcatdemo2");
        demo.setContentbody("this is content body2");
        demos.add(demo);
        demo = new DemoEntity();
        demo.setDemoId(3L);
        demo.setAgentStarttime(new Date());
        demo.setApplicationName("blackcatdemo3");
        demo.setContentbody("this is content body3");
        demos.add(demo);
        //批量创建文档
        String response = clientUtil.addDateDocuments("demo",//索引表
                "demo",//索引类型
                demos);
        System.out.println("addDateDocument-------------------------");
        System.out.println(response);
/*        //批量更新文档
        demo.setContentbody("updated");
        response = clientUtil.updateDocuments("demo-"+date,"demo",demos);
        System.out.println("updateDateDocument-------------------------");
        System.out.println(response);
        //获取索引文档，json格式
        response = clientUtil.getDocument("demo-"+date,//索引表
                "demo",//索引类型
                "2");//文档id
        System.out.println("getDocument-------------------------");
        System.out.println(response);
        //获取索引文档，返回Demo对象类型
        demo = clientUtil.getDocument("demo-"+date,//索引表
                "demo",//索引类型
                "3",//文档id
                DemoEntity.class);*/
    }

    public void deleteDocuments(){
        //Build a create/modify/get/delete document client object, single instance multi-thread security
        ClientInterface clientUtil = bbossESStarter.getRestClient();
        //Batch delete documents
        clientUtil.deleteDocuments("demo",//indice name
                "demo",//idnex type
                new String[]{"2","3"});//Batch delete document ids
    }

    /**
     * Use slice parallel scoll query all documents of indice demo by 2 thread tasks. DEFAULT_FETCHSIZE is 5000
     */
    public void searchAllPararrel(){
        ClientInterface clientUtil = bbossESStarter.getRestClient();
        ESDatas<DemoEntity> esDatas = clientUtil.searchAllParallel("demo", DemoEntity.class,2);
    }



    /**
     * Search the documents
     */
    /*public DemoSearchResult search()   {
        //Create a load DSL file client instance to retrieve documents, single instance multithread security
        ClientInterface clientUtil = bbossESStarter.getConfigRestClient(mappath);
        //Set query conditions, pass variable parameter values via map,key for variable names in DSL
        //There are four variables in the DSL:
        //        applicationName1
        //        applicationName2
        //        startTime
        //        endTime
        Map<String,Object> params = new HashMap<String,Object>();
        //Set the values of applicationName1 and applicationName2 variables
        params.put("applicationName1","blackcatdemo2");
        params.put("applicationName2","blackcatdemo3");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Set the time range, and accept the long value as the time parameter
        try {
            params.put("startTime",dateFormat.parse("2017-09-02 00:00:00").getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        params.put("endTime",new Date().getTime());


        //Execute the query
        ESDatas<DemoEntity> esDatas =  //ESDatas contains a collection of currently retrieved records, up to 1000 records, specified by the size attribute in the DSL
                clientUtil.searchList("demo/_search",//demo as the indice, _search as the search action
                        "searchDatas",//DSL statement name defined in esmapper/demo.xml
                        params,//Query parameters
                        DemoEntity.class);//Data object type DemoEntity returned


        //Gets a list of result objects and returns max up to 1000 records (specified in DSL)
        List<DemoEntity> demos = esDatas.getDatas();

//        String json = clientUtil.executeRequest("demo/_search",//demo as the index table, _search as the search action
//                "searchDatas",//DSL statement name defined in esmapper/demo.xml
//                params);//Query parameters

//        String json = com.frameworkset.util.SimpleStringUtil.object2json(demos);
        //Gets the total number of records
        long totalSize = esDatas.getTotalSize();
        DemoSearchResult demoSearchResult = new DemoSearchResult();
        demoSearchResult.setDemos(demos);
        demoSearchResult.setTotalSize(totalSize);
        return demoSearchResult;
    }*/
}
