# 大数据知识面试题-Sqoop（2022版）
|序列号|内容|链接|
| :-----| :-----| :-----|
|1|大数据知识面试题-通用（2022版）|https://blog.csdn.net/qq_43061290/article/details/124819089|
|2|大数据知识面试题-Hadoop（2022版）|https://blog.csdn.net/qq_43061290/article/details/124822293|
|3|大数据知识面试题-MapReduce和yarn（2022版）|https://blog.csdn.net/qq_43061290/article/details/124841929|
|4|大数据知识面试题-Zookeepr （2022版）|https://blog.csdn.net/qq_43061290/article/details/124548428|
|5|大数据知识面试题-Hive （2022版）|https://blog.csdn.net/qq_43061290/article/details/125105485|
|6|大数据知识面试题-Flume（2022版）|https://blog.csdn.net/qq_43061290/article/details/125132610|
|7|大数据知识面试题-Hbase（2022版）|https://blog.csdn.net/qq_43061290/article/details/125145399|
|8|大数据知识面试题-sqoop（2022版）|https://blog.csdn.net/qq_43061290/article/details/125145736|
|9|大数据知识面试题-Kafka（2022版）|https://blog.csdn.net/qq_43061290/article/details/125145841|
|10|大数据知识面试题-Azkaban（2022版）|https://blog.csdn.net/qq_43061290/article/details/125146859|
|11|大数据知识面试题-Scala （2022版）|https://blog.csdn.net/qq_43061290/article/details/125145976|
|12|大数据知识面试题-Spark （2022版）|https://blog.csdn.net/qq_43061290/article/details/125146030|
|13|大数据知识面试题-Flink（2022版）|https://blog.csdn.net/qq_43061290/article/details/125182137|


 # 文章目录
1、sqoop
1.1、sqoop介绍
1.2、sqoop常见问题
1.2.1、sqoop中文数据乱码问题
1.2.2、sqoop中文注释乱码问题
1.2.3、datax常见问题

## 1、sqoop

#### 1.1、sqoop介绍

<img src="https://img-blog.csdnimg.cn/271d0fca6f804a72b7a32cebb67f5e66.png" alt="在这里插入图片描述"/>

<img src="https://img-blog.csdnimg.cn/1fb907a91b454cbe9cf34aefccc316b3.png#pic_center" alt="在这里插入图片描述"/>

sqoop是apache旗下一款**“Hadoop和关系数据库服务器之间传送数据”**的工具。

**导入数据**：MySQL，Oracle导入数据到Hadoop的HDFS、HIVE、HBASE等数据存储系统；

**导出数据：**从Hadoop的文件系统中导出数据到关系数据库mysql等

将导入或导出命令翻译成mapreduce程序来实现

在翻译出的mapreduce中主要是对inputformat和outputformat进行定制

#### 1.2、sqoop常见问题

##### 1.2.1、sqoop中文数据乱码问题
- mysql导入到hdfs
```
bin/sqoop import \
--connect jdbc:mysql://node03:3306/A  \
--username root \
--password 123456 \
--target-dir /A2 \
--table B --m 1


```
- mysql导入到hive
```
bin/sqoop import \
--connect "jdbc:mysql://node03:3306/A?useUnicode=true&amp;characterEncoding=utf-8" \
--username root \
--password 123456 \
--table B \
--hive-import \
--m 1 \
--hive-database default;


```
- 从hdfs导出到mysql
```
bin/sqoop export \
--connect "jdbc:mysql://node03:3306/A?useUnicode=true&amp;characterEncoding=utf-8" \
--username root \
--password 123456 \
--table B \
--export-dir /user/hive/warehouse/b


```

##### 1.2.2、sqoop中文注释乱码问题

参考资料：https://www.cnblogs.com/qingyunzong/p/8724155.html

##### 1.2.3、datax常见问题
<li> oom <pre><code class="prism language-shell">在datax 中导数据使用过程中往往会因为，目标数据过大导致datax oom，那么可以调大datax的jvm参数来防止oom,在python命令后，使用 -jvm=”-Xms5G -Xmx 5G”来调大

python datax.py  --jvm="-Xms5G -Xmx5G" ../job/test.json
</code></pre> </li><li> 字段长度过长 <pre><code class="prism language-json">如果报java.io.IOException: Maximum column length of 100,000 exceeded in column...异常信息，说明数据源column字段长度超过了100000字符。
需要在json的reader里增加如下配置
 "csvReaderConfig":{<!-- -->
 "safetySwitch": false,
  "skipEmptyRecords": false,
  "useTextQualifier": false
     }
   safetySwitch = false;//单列长度不限制100000字符
</code></pre> </li># **文章地址： **https://blog.csdn.net/qq_43061290/article/details/125145736