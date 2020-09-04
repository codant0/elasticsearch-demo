# Elasticsearch学习笔记

## Elasticsearch

* 安装

  * 略

* 概念

  * index
  * type
  * 

* 常见问题：

  > 外网访问问题：
  >
  > ​	修改 Elastic 安装目录的`config/elasticsearch.yml`文件，去掉`network.host`的注释，将它的值改成`0.0.0.0`，然后重新启动 Elastic。
  >
  > 
  >
  > "Content-Type header [application/x-www-form-urlencoded] is not supported"
  >
  > 指定类型：
  >
  > curl -H "Content-Type: application/json" -XPOST http://localhost:9200/tracy/fulltext/_mapping -d'{...}'

* 常用命令

  > ```shell
  > # *重要！ES7之后取消Type！
  > # 	* 以下内容中的type在ES7中去除（保留也能正常使用，但一个index下最多一个Type）
  > # Elasticsearch的CRUD操作都是依照Rest API风格实现
  > # curl -X 指定请求方法
  > 
  > # 查看状态
  > $ curl -X PUT 'localhost:9200'
  > 
  > # 新建和删除Index-weather
  > $ curl -X PUT 'localhost:9200/weather'
  > 
  > # 删除Index
  > $ curl -X DELETE 'localhost:9200/weather'
  > 
  > 
  > # 新增记录(指定id)，新增操作会自动创建index
  > $ curl -X PUT -H "Content-Type: application/json" 'localhost:9200/index/type/id?pretty=true' -d '
  > {
  >   "user": "张三",
  >   "title": "工程师",
  >   "desc": "数据库管理"
  > }' 
  > 
  > 
  > # 新增记录也可不指定id，这时需要更改为POST请求，此时_id为随机生成的字符串
  > $ curl -X POST -H "Content-Type: application/json" 'localhost:9200/index2/type' -d '
  > {
  >   "user": "李四",
  >   "title": "工程师",
  >   "desc": "系统管理"
  > }'
  > 
  > 
  > # 查看记录，pretty=true->结果美化，若id不正确则查不到记录
  > $ curl 'localhost:9200/index/type/id?pretty=true'
  > 
  > 
  > # 删除记录
  > $ curl -X DELETE 'localhost:9200/index/type/id'
  > 
  > 
  > # 更新记录，更新操作会使字段version增加
  > $ curl -X PUT -H "Content-Type: application/json"  'localhost:9200/index/type/id' -d '
  > {
  >     "user" : "张三",
  >     "title" : "工程师",
  >     "desc" : "数据库管理，软件开发"
  > }' 
  > 
  > 
  > # 查找所有记录，即id对应位置替换成_search
  > # 	took字段表示该操作耗时，hits字段表示命中的记录的信息
  > $ curl 'localhost:9200/index/type/_search?pretty=true'
  > 
  > # 查看所有索引
  > curl 'localhost:9200/_cat/indices?v'
  > 
  > # 获得所有Type
  > curl -XGET 'http://localhost:9200/_mapping?pretty=true'
  > 
  > ==========================================================
  > 
  > # 全文搜索
  > # 	Elastic的查询非常特别，使用自己的查询语法，要求GET请求带有数据体。
  > # 使用的是"Match查询"(?)
  > # 默认一次返回10条结果，可以使用size字段改变这个设置
  > # 可以使用from字段指定位移
  > # *如果有多个关键词，Elastic认为是or的关系
  > $ curl -H "Content-Type: application/json" 'localhost:9200/index/type/_search?pretty=true' -d'
  > {
  > 	"query": {"match" : {"desc" : "软件"}},
  > 	"from": 10,
  > 	"size": 2
  > }'
  > ```
  >
  > ------

## Elasticsearch Bboss

* 主要功能特色

1. bboss es底层直接基于es 的http restful协议，因此支持所有的es的restful功能，采用连接池技术管理http连接，高效；
2. 支持x-pack安全认证；
3. 支持集群负载和容灾以及节点自动发现；
4. 提供了丰富的orm api（增删改查、批量增删改，聚合统计等），api简洁易用；
5. 基于xml配置文件管理query dsl脚本，在query dsl的基础上，提供了简单强大的动态控制语法结构，支持if/else,if/elseif/else,foreach循环控制结构，语法风格非常类似于mybatis管理sql语句的语法风格，但是更加简洁高效；
6. 将query dsl脚本从java代码剥离，提供query dsl热加载功能，实时修改实时生效，开发调试效率高，可以与es head和kibana的deptool配合使用；
7. 开发和配置也非常简单，只需要引入bboss es的maven坐标或者gradle坐标，无需依赖es官方的jar包，几乎兼容es的各个版本(向前、向后兼容，前提是编写query dsl脚本要兼容)；
8. bboss es即提供高阶的orm api，也提供了低阶原生restful以及java transport的支持，可以方便地根据自己的实际需要选用。
9.  原生的restful的使用，bboss es直接发http restful请求，可以指定http post，get，delete，put方法，返回json报文，有问题直接抛异常。相关示例在新闻《[高性能Elasticsearch ORM开发库bboss es 5.0.3.7.8发布](https://www.oschina.net/news/90641/bboss-es-5-0-3-7-8-released)》中都有介绍，而且除了返回string类型的json报文，还可以指定ResponseHandler回调处理接口，自行封装成自己想要的对象结构，例如：

```
Map<String,Object> state = clientUtil.executeHttp("_cluster/state",ClientInterface.HTTP_GET,                                       new MapResponseHandler());//返回map结构
```

 10.对于响应的异常处理，如果restful返回异常报文，都会以ElasticsearchException抛出到应用端。

1. https://esdoc.bbossgroups.com/#/spring-booter-with-bboss)