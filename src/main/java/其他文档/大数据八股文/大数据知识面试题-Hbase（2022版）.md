# 大数据知识面试题-Hbase（2022版）
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
1.1、HBASE的架构
1、HMaster
2、RegionServer
1.2、常用HBASE shell
1、进入HBase客户端命令操作界面
2、查看帮助命令
3、查看当前数据库中有哪些表
4、创建一张表
5、添加数据操作
6、查询数据操作
1、通过rowkey进行查询
2、查看rowkey下面的某个列族的信息
3、查看rowkey指定列族指定字段的值
4、查询所有数据
5、列族查询
7、统计
8.显示所有表
1.3、HBASE表数据模型
Row Key
列族Column Family
列 Column
时间戳
Cell
1.4、HBASE读请求过程
1.5、HBASE写请求过程
1.6、hbase的预分区
1.6.1、预分区有什么用
1.6.2、如何预分区
1.6.3、设定预分区
1.7、HBASE的rowkey设计
1.7.1、rowkey设计三原则
1、长度原则
2、散列原则
3、唯一原则
1.7.2、热点问题
加盐
哈希
反转
时间戳反转

### 1.1、HBASE的架构

<img src="https://img-blog.csdnimg.cn/d1c1ee7555b94cd3807f853e09cd2eab.png#pic_center" alt="在这里插入图片描述"/>

#### 1、HMaster

功能：

```
1) 监控RegionServer

2) 处理RegionServer故障转移

3) 处理元数据的变更

4) 处理region的分配或移除

5) 在空闲时间进行数据的负载均衡

6) 通过Zookeeper发布自己的位置给客户端

```

#### 2、RegionServer

功能：

```
1) 负责存储HBase的实际数据

2) 处理分配给它的Region

3) 刷新缓存到HDFS

4) 维护HLog

5) 执行压缩

6) 负责处理Region分片

```

### 1.2、常用HBASE shell

#### 1、进入HBase客户端命令操作界面

$ bin/hbase shell

#### 2、查看帮助命令

hbase(main):001:0> help

#### 3、查看当前数据库中有哪些表

hbase(main):002:0> list

#### 4、创建一张表

创建user表，包含info、data两个列族

```
hbase(main):010:0> create 'user', 'info', 'data'

或者
hbase(main):010:0> create 'user', {<!-- -->NAME => 'info', VERSIONS => '3'}，{<!-- -->NAME => 'data'}

```

#### 5、添加数据操作
-  向user表中插入信息，row key为rk0001，列族info中添加name列标示符，值为zhangsan hbase(main):011:0> put ‘user’, ‘rk0001’, ‘info:name’, ‘zhangsan’ ​ -  向user表中插入信息，row key为rk0001，列族info中添加gender列标示符，值为female hbase(main):012:0> put ‘user’, ‘rk0001’, ‘info:gender’, ‘female’ -  向user表中插入信息，row key为rk0001，列族info中添加age列标示符，值为20 hbase(main):013:0> put ‘user’, ‘rk0001’, ‘info:age’, 20 -  向user表中插入信息，row key为rk0001，列族data中添加pic列标示符，值为picture hbase(main):014:0> put ‘user’, ‘rk0001’, ‘data:pic’, ‘picture’ 
#### 6、查询数据操作

##### 1、通过rowkey进行查询

获取user表中row key为rk0001的所有信息

hbase(main):015:0> get ‘user’, ‘rk0001’

##### 2、查看rowkey下面的某个列族的信息

获取user表中row key为rk0001，info列族的所有信息

hbase(main):016:0> get ‘user’, ‘rk0001’, ‘info’

##### 3、查看rowkey指定列族指定字段的值

获取user表中row key为rk0001，info列族的name、age列标示符的信息

hbase(main):017:0> get ‘user’, ‘rk0001’, ‘info:name’, ‘info:age’

##### 4、查询所有数据

查询user表中的所有信息

scan ‘user’

##### 5、列族查询

查询user表中列族为info的信息

scan ‘user’, {COLUMNS => ‘info’}

#### 7、统计

hbase(main):053:0> count ‘user’

#### 8.显示所有表

list

### 1.3、HBASE表数据模型

<img src="https://img-blog.csdnimg.cn/f6f14039142d433c9856be7bb139bd96.png#pic_center" alt="在这里插入图片描述"/>

#### Row Key

与nosql数据库们一样,row key是用来检索记录的主键。访问hbase table中的行，只有三种方式：

1 通过单个row key访问

2 通过row key的range

3 全表扫描

Row key行键 (Row key)可以是任意字符串(最大长度是 64KB，实际应用中长度一般为 10-100bytes)，在hbase内部，row key保存为字节数组。

**Hbase会对表中的数据按照rowkey排序(字典顺序)**

#### 列族Column Family

 hbase表中的每个列，都归属与某个列族。列族是表的schema的一部分(而列不是)，必须在使用表之前定义。列名都以列族作为前缀。例如courses:history ， courses:math 都属于 courses 这个列族。

 列族越多，在取一行数据时所要参与IO、搜寻的文件就越多，所以，如果没有必要，不要设置太多的列族

#### 列 Column

 列族下面的具体列，属于某一个ColumnFamily,类似于我们mysql当中创建的具体的列

#### 时间戳

 HBase中通过row和columns确定的为一个存贮单元称为cell。每个 cell都保存着同一份数据的多个版本。版本通过时间戳来索引。时间戳的类型是 64位整型。时间戳可以由hbase(在数据写入时自动 )赋值，此时时间戳是精确到毫秒的当前系统时间。

##### Cell

由{row key, column( = + <label>), version} 唯一确定的单元。</label>

cell中的数据是没有类型的，全部是字节码形式存贮。

### 1.4、HBASE读请求过程

get ‘user’ ‘rk0001’

java api

<img src="https://img-blog.csdnimg.cn/5d4e51ca220347328fe698eceb855675.png#pic_center" alt="在这里插入图片描述"/>
- HRegionServer保存着meta表以及表数据，要访问表数据，首先Client先去访问zookeeper，从zookeeper里面获取meta表所在的位置信息，即找到这个meta表在哪个HRegionServer上保存着。- 接着Client通过刚才获取到的HRegionServer的IP来访问Meta表所在的HRegionServer，从而读取到Meta，进而获取到Meta表中存放的元数据。- Client通过元数据中存储的信息，访问对应的HRegionServer，然后扫描所在HRegionServer的Memstore和Storefile来查询数据。- 最后HRegionServer把查询到的数据响应给Client。
### 1.5、HBASE写请求过程

<img src="https://img-blog.csdnimg.cn/f51a0a7573db416db32789f87c60683f.png#pic_center" alt="在这里插入图片描述"/>
-  Client也是先访问zookeeper，找到Meta表，并获取Meta表元数据。确定当前将要写入的数据所对应的HRegion和HRegionServer服务器。 -  Client向该HRegionServer服务器发起写入数据请求，然后HRegionServer收到请求并响应。 -  Client先把数据写入到HLog，以防止数据丢失。然后将数据写入到Memstore。 -  如果HLog和Memstore均写入成功，则这条数据写入成功 -  如果Memstore达到阈值，会把Memstore中的数据flush到Storefile中。 -  当Storefile越来越多，会触发Compact合并操作，把过多的Storefile合并成一个大的Storefile。 -  当Storefile越来越大，Region也会越来越大，达到阈值后，会触发Split操作，将Region一分为二。 
### 1.6、hbase的预分区

#### 1.6.1、预分区有什么用
- 增加数据读写效率- 负载均衡，防止数据倾斜- 方便集群容灾调度region- 优化Map数量
#### 1.6.2、如何预分区

 每一个region维护着startRowkey与endRowKey，如果加入的数据符合某个region维护的rowKey范围，则该数据交给这个region维护。

#### 1.6.3、设定预分区
<li> **手动设定预分区** <pre><code>create 'staff','info','partition1',SPLITS => ['1000','2000','3000','4000']
</code></pre> </li>
<img src="https://img-blog.csdnimg.cn/7bd366cf5e4b452a94b41bc37b7d7e04.png#pic_center" alt="在这里插入图片描述"/>

2.**使用16进制算法生成预分区**

```
create 'staff2','info','partition2',{NUMREGIONS => 15, SPLITALGO => 'HexStringSplit'}


```

<img src="https://img-blog.csdnimg.cn/dacaa977515b4a2f994ffeae20f194b4.png#pic_center" alt="在这里插入图片描述"/>

### 1.7、HBASE的rowkey设计

#### 1.7.1、rowkey设计三原则

##### 1、长度原则

 Rowkey 是一个二进制码流，Rowkey 的长度被很多开发者建议说设计在 10~100 个字节，不过建议是越短越好，不要超过 16 个字节，存为byte[]字节数组，**一般设计成定长的**。

数据的持久化文件 HFile 中是按照 KeyValue 存储的，如果 Rowkey 过长比如 100 个字 节，1000 万列数据光 Rowkey 就要占用 100*1000 万=10 亿个字节，将近 1G 数据，这会极大 影响 HFile 的存储效率；

##### 2、散列原则

 如果 Rowkey 是按时间戳的方式递增，不要将时间放在二进制码的前面，建议将 Rowkey 的高位作为散列字段，由程序循环生成，低位放时间字段，这样将提高数据均衡分布在每个 Regionserver 实现负载均衡的几率。如果没有散列字段，首字段直接是时间信息将产生所有 新数据都在一个 RegionServer 上堆积的热点现象，这样在做数据检索的时候负载将会集中 在个别 RegionServer，降低查询效率。

 row key是按照**字典序**存储，因此，设计row key时，要充分利用这个排序特点，将经常一起读取的数据存储到一块，将最近可能会被访问的数据放在一块。

 举个例子：如果最近写入HBase表中的数据是最可能被访问的，可以考虑将时间戳作为row key的一部分，由于是字典序排序，所以可以使用Long.MAX_VALUE - timestamp作为row key，这样能保证新写入的数据在读取时可以被快速命中。

##### 3、唯一原则

必须在设计上保证其唯一性。rowkey 是按照字典顺序排序存储的，因此，设计 rowkey 的时候，要充分利用这个排序的特点，将经常读取的数据存储到一块，将最近可能会被访问 的数据放到一块。

#### 1.7.2、热点问题

热点发生在大量的 client 直接访问集群的一个或极少数个节点（访问可能是读， 写或者其他操作）。大量访问会使热点 region 所在的单个机器超出自身承受能力，引起性能 下降甚至 region 不可用，这也会影响同一个 RegionServer 上的其他 region，由于主机无法服 务其他 region 的请求。 设计良好的数据访问模式以使集群被充分，均衡的利用。 为了避免写热点，设计 rowkey 使得不同行在同一个 region，但是在更多数据情况下，数据 应该被写入集群的多个 region，而不是一个。

**解决方案：**

##### 加盐

 这里所说的加盐不是密码学中的加盐，而是在 rowkey 的前面增加随机数，具体就是给 rowkey 分配一个随机前缀以使得它和之前的 rowkey 的开头不同。分配的前缀种类数量应该 和你想使用数据分散到不同的 region 的数量一致。加盐之后的 rowkey 就会根据随机生成的 前缀分散到各个 region 上，以避免热点。

```
原本 rowKey 为 1001 的，SHA1 后变成：dd01903921ea24941c26a48f2cec24e0bb0e8cc7 

原本 rowKey 为 3001 的，SHA1 后变成：49042c54de64a1e9bf0b33e00245660ef92dc7bd 

原本 rowKey 为 5001 的，SHA1 后变成：7b61dec07e02c188790670af43e717f0f46e8913

在做此操作之前，一般我们会选择从数据集中抽取样本，来决定什么样的 rowKey 来 Hash
后作为每个分区的临界值。

```

##### 哈希

 哈希会使同一行永远用一个前缀加盐。哈希也可以使负载分散到整个集群，但是读却是 可以预测的。使用确定的哈希可以让客户端重构完整的 rowkey，可以使用 get 操作准确获取 某一个行数据

##### 反转

反转 rowkey 的例子以手机号为 rowkey，可以将手机号反转后的字符串作为 rowkey，这 样的就避免了以手机号那样比较固定开头导致热点问题。

130xxx 155xx 156xx 180x x 189xx

##### 时间戳反转

一个常见的数据处理问题是快速获取数据的最近版本，使用反转的时间戳作为 rowkey 的一部分对这个问题十分有用，可以用 Long.Max_Value - timestamp 追加到 key 的末尾

---


---


|序列号|内容|链接|
| :-----| :-----| :-----|
|1|Java基础知识面试题（2022版）|https://blog.csdn.net/qq_43061290/article/details/124023797|
|2|Java集合容器面试题（2022版）|https://blog.csdn.net/qq_43061290/article/details/124043363|
|3|Java异常面试题（2022版）|https://blog.csdn.net/qq_43061290/article/details/124078378|
|4|并发编程面试题 （2022版）|https://blog.csdn.net/qq_43061290/article/details/124104563|
|5|JVM面试题（2022版）|https://blog.csdn.net/qq_43061290/article/details/124104514|
|6|Spring面试题（2022版）|https://blog.csdn.net/qq_43061290/article/details/124227864|
|7|Spring MVC面试题（2022版）|https://blog.csdn.net/qq_43061290/article/details/124337927|
|8|Spring Boot面试题（2022版）|https://blog.csdn.net/qq_43061290/article/details/124339493|
|9|Spring Cloud面试题（2022版）|https://blog.csdn.net/qq_43061290/article/details/124341152|
|10|MyBatis面试题（2022版）|https://blog.csdn.net/qq_43061290/article/details/124542376|
|11|Redis面试题（2022版）|https://blog.csdn.net/qq_43061290/article/details/124427311|
|12|MySQL数据库面试题（2022版）|https://blog.csdn.net/qq_43061290/article/details/124427311|
|13|消息中间件MQ知识点（2022版）|https://blog.csdn.net/qq_43061290/article/details/124542376|
|14|ZooKeeper面试题（2022版）|https://blog.csdn.net/qq_43061290/article/details/124548428|
|15|架构设计&分布式&数据结构与算法面试题（2022版）|https://blog.csdn.net/qq_43061290/article/details/124624540|
|16|计算机网络编程面试题（2022版）|https://blog.csdn.net/qq_43061290/article/details/124041420|

# **文章地址： **https://blog.csdn.net/qq_43061290/article/details/125145399