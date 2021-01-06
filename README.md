# lkengine-db

#### 介绍
独立的存储引擎jar，满足日常基本使用(MySQL/MSSQL/Oracle/PgSQL)



#### 项目引入

```xml
<!-- https://mvnrepository.com/artifact/cn.linkey/lkengine-db -->
<dependency>
    <groupId>cn.linkey</groupId>
    <artifactId>lkengine-db</artifactId>
    <version>1.0</version>
</dependency>
```



#### 简单DEMO

jdbctest是一个简单测试类，主要是获取连接，然后初始化连接。增删改查在Rdb类中，可以通过BeanCtx.getRdb()获得。

```java

jdbctest testObj = new jdbctest();
// 获取conn对象
Connection conn = testObj.getConn();

// 初始化连接
Rdb rdb = new RdbImpl(conn);
BeanCtx.setConnection(conn);
BeanCtx.setRdb(rdb);

Rdb r2 = BeanCtx.getRdb();
// 简单查询测试
Document doc = r2.getDocumentBySql("select * from bpm_maindata where WF_OrUnid = '04899ff608b7004ca0081c003f3abe8506a4'");
System.out.println(doc.toJson());
```



#### 详细文档

见联科开源官网：[lkengine_db_1_0](http://open.linkey.cn/osbpm/r?wf_num=P_openLinkey_N002&treeid=T_openLinkey_N004&docVn=lkengine_db_1_0) 帮助文档；

QQ交流群：823545910



