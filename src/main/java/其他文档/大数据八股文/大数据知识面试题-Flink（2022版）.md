# 大数据知识面试题-Flink（2022版）
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
1. 简单介绍一下Flink
2. Flink的运行必须依赖Hadoop组件吗
3. Flink集群运行时角色
4. Flink相比Spark Streaming有什么区别
5. 介绍下Flink的容错机制（checkpoint）
6. Flink checkpoint与Spark Streaming的有什么区别或优势吗
7. Flink是如何保证Exactly-once语义的
8. 如果下级存储不支持事务，Flink怎么保证exactly-once
9. Flink常用的算子有哪些
10. Flink任务延时高，如何入手
11. Flink是如何处理反压的
12. 如何排查生产环境中的反压问题
13. Flink中的状态存储
14. Operator Chains（算子链）这个概念你了解吗
15. Flink的内存管理是如何做的
16. 如何处理生产环境中的数据倾斜问题
17. Flink中的Time有哪几种
18. Flink对于迟到数据是怎么处理的
19. Flink中window出现数据倾斜怎么解决
20. Flink CEP编程中当状态没有到达的时候会将数据保存在哪里
21. Flink设置并行度的方式
22. Flink中Task如何做到数据交换
23. Flink的内存管理是如何做的
24. 介绍下Flink的序列化
25. Flink海量数据高效去重
26. Flink SQL的是如何实现的
业务方面

<img src="https://img-blog.csdnimg.cn/31681020827f4338ad67da789040710c.png" alt="在这里插入图片描述"/>

## 简单介绍一下Flink

Flink是一个面向流处理和批处理的分布式数据计算引擎，能够基于同一个Flink 运行，可以提供流处理和批处理两种类型的功能。 在 Flink 的世界观中，一切都是由流组成的，离线数据是有界的流；实时数据是一个没有界限的流：这就是所谓的有界流和无界流。

## 2. Flink的运行必须依赖Hadoop组件吗

Flink可以完全独立于Hadoop，在不依赖Hadoop组件下运行。但是做为大数据的基础设施，Hadoop体系是任何大数据框架都绕不过去的。Flink可以集成众多 Hadooop 组件，例如Yarn、Hbase、HDFS等等。例如，Flink可以和Yarn集成做资源调度，也可以读写HDFS，或者利用HDFS做检查点。

## 3. Flink集群运行时角色

Flink 运行时由两种类型的进程组成：**一个 JobManager 和一个或者多个 TaskManager**。 Client 不是运行时和程序执行的一部分，而是用于准备数据流并将其发送给 JobManager。之后，客户端可以断开连接（分离模式），或保持连接来接收进程报告（附加模式）。客户端可以作为触发执行 Java/Scala 程序的一部分运行， 也可以在命令行进程 ./bin/flink run … 中运行。

可以通过多种方式启动 JobManager 和 TaskManager：直接在机器上作为 standalone 集群启动、在容器中启动、或者通过YARN等资源框架管理并启动。 TaskManager 连接到 JobManagers，宣布自己可用，并被分配工作。

**JobManager：** JobManager 具有许多与协调 Flink 应用程序的分布式执行有关的职责：它决定何时调度下一个 task（或一组 task）、对完成的 task 或执行失败做出反应、协调 checkpoint、并且协调从失败中恢复等等。这个进程由三个不同的组件组成：

+ ResourceManager 

  ResourceManager 负责 Flink 集群中的资源提供、回收、分配，管理 taskslots。

- Dispatcher 

  Dispatcher 提供了一个 REST 接口，用来提交 Flink 应用程序执行，并为每个提交的作业启动一个新的 JobMaster。它还运行 Flink WebUI 用来提供作业执行信息。

- JobMaster 

  JobMaster 负责管理单个JobGraph的执行。Flink 集群中可以同时运行多个作业，每个作业都有自己的 JobMaster。

**TaskManagers：** TaskManager（也称为 worker）执行作业流的 task，并且缓存和交换数据流。必须始终至少有一个 TaskManager。在 TaskManager 中资源调度的最小单位是 taskslot。TaskManager 中 taskslot 的数量表示并发处理 task 的数量。请注意一个 task slot 中可以执行多个算子。

## 4(1). Flink集群有哪些角色？各自有什么作用

有以下三个角色：

**JobManager处理器：**

也称之为Master，用于协调分布式执行，它们用来调度task，协调检查点，协调失败时恢复等。Flink运行时至少存在一个master处理器，如果配置高可用模式则会存在多个master处理器，它们其中有一个是leader，而其他的都是standby。

**TaskManager处理器：**

也称之为Worker，用于执行一个dataflow的task(或者特殊的subtask)、数据缓冲和data stream的交换，Flink运行时至少会存在一个worker处理器。

**Clint客户端：**

Client是Flink程序提交的客户端，当用户提交一个Flink程序时，会首先创建一个Client，该Client首先会对用户提交的Flink程序进行预处理，并提交到Flink集群中处理，所以Client需要从用户提交的Flink程序配置中获取JobManager的地址，并建立到JobManager的连接，将Flink Job提交给JobManager

## 4. Flink相比Spark Streaming有什么区别
1、**架构模型** 

SparkStreaming 在运行时的主要角色包括：Master、Worker、Driver、Executor， 

Flink 在运行时主要包含：Jobmanager、Taskmanager 和 Slot。

2、**任务调度**

SparkStreaming 连续不断的生成微小的数据批次，构建有向无环图 DAG，Spark Streaming 会依次创建 DStreamGraph、JobGenerator、JobScheduler。 

Flink 根据用户提交的代码生成 StreamGraph，经过优化生成 JobGraph，然后提交给 JobManager 进行处理，JobManager 会根据 JobGraph 生成 ExecutionGraph，ExecutionGraph 是 Flink 调度最核心的数据结构， JobManager 根据 ExecutionGraph 对 Job 进行调度。

3、**时间机制** 

SparkStreaming 支持的时间机制有限，只支持处理时间。

Flink 支持了流处理程序在时间上的三个定义：处理时间、事件时间、注入时间。同时也支持 watermark 机制来处理滞后数据。

4、**容错机制** 

对于 SparkStreaming 任务，我们可以设置 checkpoint，然后假如发生故障并重启，我们可以从上次 checkpoint 之处恢复，但是这个行为只能使得数据不丢失，可能会重复处理，不能做到恰一次处理语义。 

Flink 则使用两阶段提交协议来解决这个问题。

## 5. 介绍下Flink的容错机制（checkpoint）

Checkpoint机制是Flink可靠性的基石，可以保证Flink集群在某个算子因为某些原因(如异常退出)出现故障时，能够将整个应用流图的状态恢复到故障之前的某一状态，保证应用流图状态的一致性。Flink的Checkpoint机制原理来自“Chandy-Lamport algorithm”算法。 每个需要Checkpoint的应用在启动时，Flink的JobManager为其创建一个 CheckpointCoordinator(检查点协调器)，CheckpointCoordinator全权负责本应用的快照制作。  

 <img src="https://img-blog.csdnimg.cn/3355486099374f33a1f63c1c0672edbd.png" alt="在这里插入图片描述"/>

1. CheckpointCoordinator(检查点协调器) 周期性的向该流应用的所有 source算子发送 barrier(屏障)。

2. 当某个source算子收到一个barrier时，便暂停数据处理过程，然后将自己的当前状态制作成快照，并保存到指定的持久化存储中，最后向CheckpointCoordinator报告自己快照制作情况，同时向自身所有下游算子广播该barrier，恢复数据处理

3. 下游算子收到barrier之后，会暂停自己的数据处理过程，然后将自身的相关状态制作成快照，并保存到指定的持久化存储中，最后向

   CheckpointCoordinator报告自身快照情况，同时向自身所有下游算子广播该barrier，恢复数据处理。

4. 每个算子按照步骤3不断制作快照并向下游广播，直到最后barrier传递到sink算子，快照制作完成。

5. 当CheckpointCoordinator收到所有算子的报告之后，认为该周期的快照制作成功; 否则，如果在规定的时间内没有收到所有算子的报告，则认为本周期快照制作失败。

   

   **文章推荐：** Flink可靠性的基石-checkpoint机制详细解析

   

## 6. Flink checkpoint与Spark Streaming的有什么区别或优势吗

spark streaming 的 checkpoint 仅仅是针对 driver 的故障恢复做了数据和元数据的 checkpoint。而 flink 的 checkpoint 机制要复杂了很多，它采用的是轻量级的分布式快照，实现了每个算子的快照，及流动中的数据的快照。

## 7. Flink是如何保证Exactly-once语义的

Flink通过实现两阶段提交和状态保存来实现端到端的一致性语义。分为以下几个步骤：

+ 开始事务（beginTransaction）创建一个临时文件夹，来写把数据写入到这个文件夹里面
+ 预提交（preCommit）将内存中缓存的数据写入文件并关闭
+ 正式提交（commit）将之前写完的临时文件放入目标目录下。这代表着最终的数据会有一些延迟
+ 丢弃（abort）丢弃临时文件若失败发生在预提交成功后，正式提交前。可以根据状态来提交预提交的数据，也可删除预提交的数据。
  

两阶段提交协议详解：八张图搞懂Flink的Exactly-once

## 8. 如果下级存储不支持事务，Flink怎么保证exactly-once

端到端的 exactly-once 对 sink 要求比较高，具体实现主要有**幂等写入**和**事务性写入**两种方式。

幂等写入的场景依赖于业务逻辑，更常见的是用事务性写入。而事务性写入又有预写日志（WAL）和两阶段提交（2PC）两种方式。

如果外部系统不支持事务，那么可以用预写日志的方式，把结果数据先当成状态保存，然后在收到 checkpoint 完成的通知时，一次性写入 sink 系统。

## 9. Flink常用的算子有哪些

分两部分：

1、数据读取，这是Flink流计算应用的起点，常用算子有：
• 从内存读：fromElements
• 从文件读：readTextFile
• Socket 接入 ：socketTextStream
• 自定义读取：createInput
2、处理数据的算子，常用的算子包括：Map（单输入单输出）、FlatMap（单输入、多输出）、Filter（过滤）、KeyBy（分组）、Reduce（聚合）、Window（窗口）、Connect（连接）、Split（分割）等。

## 10. Flink任务延时高，如何入手

在 Flink 的后台任务管理中，我们可以看到 Flink 的哪个算子和 task 出现了反压。最主要的手段是资源调优和算子调优。资源调优即是对作业中的 Operator 的并发数（parallelism）、CPU（core）、堆内存（heap_memory）等参数进行调优。作业参数调优包括：并行度的设置，State 的设置，checkpoint 的设置。

## 11. Flink是如何处理反压的

Flink 内部是基于 producer-consumer 模型来进行消息传递的，Flink的反压设计也是基于这个模型。Flink 使用了高效有界的分布式阻塞队列，就像 Java 通用的阻塞队列（BlockingQueue）一样。下游消费者消费变慢，上游就会受到阻塞。

## 12. 如何排查生产环境中的反压问题
1、**反压出现的场景**
反压经常出现在促销、热门活动等场景。短时间内流量陡增造成数据的堆积或者消费速度变慢。
它们有一个共同的特点：数据的消费速度小于数据的生产速度。

2、**反压监控方法**
通过Flink Web UI发现反压问题。
Flink 的 TaskManager 会每隔 50ms 触发一次反压状态监测，共监测 100 次，并将计算结果反馈给 JobManager，最后由 JobManager 进行计算反压的比例，然后进行展示。
这个比例展示逻辑如下：
OK: 0 <= Ratio <= 0.10，表示状态良好正； LOW: 0.10 < Ratio <= 0.5，表示有待观察；
HIGH: 0.5 < Ratio <= 1，表示要处理了（增加并行度/subTask/检查是否有数据倾斜/增加内存）。
0.01，代表100次中有一次阻塞在内部调用。

3、**flink反压的实现方式**
Flink任务的组成由基本的“流”和“算子”构成，“流”中的数据在“算子” 间进行计算和转换时，会被放入分布式的阻塞队列中。当消费者的阻塞队列满时，则会降低生产者的数据生产速度

4、**反压问题定位和处理**
Flink会因为数据堆积和处理速度变慢导致checkpoint超时，而checkpoint是Flink保证数据一致性的关键所在，最终会导致数据的不一致发生。
**数据倾斜**：可以在 Flink 的后台管理页面看到每个 Task 处理数据的大小。当数据倾斜出现时，通常是简单地使用类似 KeyBy 等分组聚合函数导致的，需要用户将热点 Key 进行预处理，降低或者消除热点 Key 的影响。
**GC**：不合理的设置 TaskManager 的垃圾回收参数会导致严重的 GC 问题，我们可以通过 -XX:+PrintGCDetails 参数查看 GC 的日志。
**代码本身**：开发者错误地使用 Flink 算子，没有深入了解算子的实现机制导致性能问题。我们可以通过查看运行机器节点的 CPU 和内存情况定位问题。

## 13. Flink中的状态存储

Flink在做计算的过程中经常需要存储中间状态，来避免数据丢失和状态恢复。选择的状态存储策略不同，会影响状态持久化如何和 checkpoint 交互。Flink 提供了三种状态存储方式：MemoryStateBackend、FsStateBackend、RocksDBStateBackend。

## 14. Operator Chains（算子链）这个概念你了解吗

为了更高效地分布式执行，Flink 会尽可能地将 operator 的 subtask 链接 （chain）在一起形成 task。每个 task 在一个线程中执行。将 operators 链接成 task 是非常有效的优化：它能减少线程之间的切换，减少消息的序列化/ 反序列化，减少数据在缓冲区的交换，减少了延迟的同时提高整体的吞吐量。这就是我们所说的算子链。

## 15. Flink的内存管理是如何做的

Flink 并不是将大量对象存在堆上，而是将对象都序列化到一个预分配的内存块上。此外，Flink大量的使用了堆外内存。如果需要处理的数据超出了内存限制，则会将部分数据存储到硬盘上。Flink 为了直接操作二进制数据实现了自己的序列化框架。

## 16. 如何处理生产环境中的数据倾斜问题
+ flink数据倾斜的表现：
  任务节点频繁出现反压，增加并行度也不能解决问题；
  部分节点出现OOM异常，是因为大量的数据集中在某个节点上，导致该节点内存被爆，任务失败重启。
+ 数据倾斜产生的原因：
  业务上有严重的数据热点，比如滴滴打车的订单数据中北京、上海等几个城市的订单量远远超过其他地区；
  技术上大量使用了 KeyBy、GroupBy 等操作，错误的使用了分组 Key，人为产生数据热点。
+ 解决问题的思路：
  业务上要尽量避免热点 key 的设计，例如我们可以把北京、上海等热点城市分成不同的区域，并进行单独处理；
  技术上出现热点时，要调整方案打散原来的 key，避免直接聚合；此外 Flink 还提供了大量的功能可以避免数据倾斜。

## 17. Flink中的Time有哪几种

- Flink中的时间有三种类型，如下图所示：

  ![Image](https://mmbiz.qpic.cn/sz_mmbiz_png/ZubDbBye0zFC5n0MavwFE6hrqerBJUGzOoZd4pcpPdibjJrFdA5Y2vKzGgnvz1JOicuCwAtVXsONeLUdxib9qxhicA/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1) 

  - **Event Time**：是事件创建的时间。它通常由事件中的时间戳描述，例如采集的日志数据中，每一条日志都会记录自己的生成时间，Flink通过时间戳分配器访问事件时间戳。
  - **Ingestion Time**：是数据进入Flink的时间。
  - **Processing Time**：是每一个执行基于时间操作的算子的本地系统时间，与机器相关，默认的时间属性就是Processing Time。

  例如，一条日志进入Flink的时间为`2021-01-22 10:00:00.123`，到达Window的系统时间为`2021-01-22 10:00:01.234`，日志的内容如下：
  `2021-01-06 18:37:15.624 INFO Fail over to rm2`

  对于业务来说，要统计1min内的故障日志个数，哪个时间是最有意义的？—— eventTime，因为我们要根据日志的生成时间进行统计。

## 18. Flink对于迟到数据是怎么处理的

Flink中 WaterMark 和 Window 机制解决了流式数据的乱序问题，对于因为延迟而顺序有误的数据，可以根据eventTime进行业务处理，对于延迟的数据Flink也有自己的解决办法，主要的办法是给定一个允许延迟的时间，在该时间范围内仍可以接受处理延迟数据：

- 设置允许延迟的时间是通过allowedLateness(lateness: Time)设置
- 保存延迟数据则是通过sideOutputLateData(outputTag: OutputTag[T])保存
- 获取延迟数据是通过DataStream.getSideOutput(tag: OutputTag[X])获取

**文章推荐**：

[Flink 中极其重要的 Time 与 Window 详细解析]

## 19. Flink中window出现数据倾斜怎么解决

window 产生数据倾斜指的是数据在不同的窗口内堆积的数据量相差过多。本质上产生这种情况的原因是数据源头发送的数据量速度不同导致的。出现这种情况一般通过两种方式来解决：
- 在数据进入窗口前做预聚合
- 重新设计窗口聚合的 key
## 20. Flink CEP编程中当状态没有到达的时候会将数据保存在哪里

在流式处理中，CEP 当然是要支持 EventTime 的，那么相对应的也要支持数据的迟到现象，也就是watermark的处理逻辑。CEP对未匹配成功的事件序列的处理，和迟到数据是类似的。在 Flink CEP的处理逻辑中，状态没有满足的和迟到的数据，都会存储在一个Map数据结构中，也就是说，如果我们限定判断事件序列的时长为5分钟，那么内存中就会存储5分钟的数据，这在我看来，也是对内存的极大损伤之一。

推荐阅读：一文学会Flink CEP

## 21. Flink设置并行度的方式

在实际生产环境中可以从四个不同层面设置并行度：

1、**操作算子层面**(Operator Level)
.map(new RollingAdditionMapper()).setParallelism(10) //将操作算子设置并行度
2、**执行环境层面**(Execution Environment Level)
$FLINK_HOME/bin/flink 的-p参数修改并行度
3、**客户端层面**(Client Level)
env.setParallelism(10)
4、**系统层面**(System Level)
全局配置在flink-conf.yaml文件中，parallelism.default，默认是1：可以设置默认值大一点

**需要注意的优先级**：算子层面>环境层面>客户端层面>系统层面。

## 22. Flink中Task如何做到数据交换

在一个 Flink Job 中，数据需要在不同的 task 中进行交换，整个数据交换是有 TaskManager 负责的，TaskManager 的网络组件首先从缓冲 buffer 中收集 records，然后再发送。Records 并不是一个一个被发送的，是积累一个批次再发送，batch 技术可以更加高效的利用网络资源。

## 23. Flink的内存管理是如何做的

Flink 并不是将大量对象存在堆上，而是将对象都序列化到一个预分配的内存块上。此外，Flink大量的使用了堆外内存。如果需要处理的数据超出了内存限制，则会将部分数据存储到硬盘上。Flink 为了直接操作二进制数据实现了自己的序列化框架。

## 24. 介绍下Flink的序列化

Flink 摒弃了 Java 原生的序列化方法，以独特的方式处理数据类型和序列化，包含自己的类型描述符，泛型类型提取和类型序列化框架。 TypeInformation 是所有类型描述符的基类。它揭示了该类型的一些基本属性，并且可以生成序列化器。 TypeInformation 支持以下几种类型：
- BasicTypeInfo: 任意 Java 基本类型或 String 类型

- BasicArrayTypeInfo: 任意 Java 基本类型数组或 String 数组
- WritableTypeInfo: 任意 Hadoop Writable 接口的实现类
- TupleTypeInfo: 任意的 Flink Tuple 类型(支持 Tuple1 to Tuple25)。
- Flink tuples 是固定长度固定类型的 Java Tuple 实现
- CaseClassTypeInfo: 任意的 Scala CaseClass(包括 Scala tuples)
- PojoTypeInfo: 任意的 POJO (Java or Scala)，例如，Java 对象的所有成员变量，要么是 public 修饰符定义，要么有 getter/setter 方法
- GenericTypeInfo: 任意无法匹配之前几种类型的类

## 25. Flink海量数据高效去重
1. 基于状态后端。

2. 基于HyperLog：不是精准的去重。
3. 基于布隆过滤器（BloomFilter）；快速判断一个key是否存在于某容器，不存在就直接返回。
4. 基于BitMap；用一个bit位来标记某个元素对应的Value，而Key即是该元素。由于采用了Bit为单位来存储数据，因此可以大大节省存储空间。
5. 基于外部数据库；选择使用Redis或者HBase存储数据，我们只需要设计好存储的Key即可，不需要关心Flink任务重启造成的状态丢失问题。



## 26. Flink SQL的是如何实现的

<img src="https://img-blog.csdnimg.cn/40c1238a45ad4be1bc52b36b324c34d4.png" alt="在这里插入图片描述" width="100%" height="100%"/>

构建抽象语法树的事情交给了 Calcite 去做。SQLquery 会经过 Calcite 解析器转变成 SQL 节点树，通过验证后构建成 Calcite 的抽象语法树（也就是图中的 Logical Plan）。另一边，Table API 上的调用会构建成 Table API 的抽象语法树，并通过 Calcite 提供的 RelBuilder 转变成 Calcite 的抽象语法树。

然后依次被转换成逻辑执行计划和物理执行计划。 在提交任务后会分发到各个 TaskManager 中运行，在运行时会使用 Janino 编译器编译代码后运行。

## 27. Flink 资源管理中 Task Slot 的概念

在Flink中每个worker(也就是 TaskManager)是一个JVM的进程, 可以在不同的线程中执行一个或多个子任务。为了控制一个worker能接收多少个task。worker通过task slot（任务槽）来进行控制（一个worker至少有一个task slot）。

## 28. Flink的重启策略了解吗

Flink支持不同的重启策略，这些重启策略控制着job失败后如何重启：

1. **固定延迟重启策略**

固定延迟重启策略会尝试一个给定的次数来重启Job，如果超过了最大的重启次数，Job最终将失败。在连续的两次重启尝试之间，重启策略会等待一个固定的时间。

1. **失败率重启策略**

失败率重启策略在Job失败后会重启，但是超过失败率后，Job会最终被认定失败。在两个连续的重启尝试之间，重启策略会等待一个固定的时间。

1. **无重启策略**

Job直接失败，不会尝试进行重启。

https://mp.weixin.qq.com/s?__biz=Mzg2MzU2MDYzOA==&mid=2247484113&idx=2&sn=dceb0b19c31dedaa33ce0d59ea830bf0&scene=21#wechat_redirect)

## 29. Flink是如何支持流批一体的

这道题问的比较开阔，如果知道Flink底层原理，可以详细说说，如果不是很了解，就直接简单一句话：**Flink的开发者认为批处理是流处理的一种特殊情况。批处理是有限的流处理。Flink 使用一个引擎支持了 DataSet API 和 DataStream API**。

## 30. 业务方面

1. 在处理大数据过程中，如何保证得到期望值 保证在数据采集的时候不丢失数据，这个尤为重要，如果在数据采集的时候就已经不准确，后面很难达到期望值在数据处理的时候不丢失数据，例如sparkstreaming处理kafka数据的时候，要保证数据不丢失，这个尤为重要

前两步中，如果无法保证数据的完整性，那么就要通过离线计算进行数据的校对，这样才能保证我们能够得到期望值

1.  你感觉数仓建设中最重要的是什么 数仓建设中，最重要的是数据准确性，数据的真正价值在于数据驱动决策，通过数据指导运营，在一个不准确的数据驱动下，得到的一定是错误的数据分析，影响的是公司的业务发展决策，最终导致公司的策略调控失败。 <br /> 数据仓库建模怎么做的 数仓建设中最常用模型–Kimball维度建模详解 <br /> 数据质量怎么监控 单表数据量监控 
    一张表的记录数在一个已知的范围内，或者上下浮动不会超过某个阈值

SQL结果：var 数据量 = select count（*）from 表 where 时间等过滤条件

报警触发条件设置：如果数据量不在[数值下限, 数值上限]， 则触发报警

同比增加：如果((本周的数据量 -上周的数据量)/上周的数据量*100)不在 [比例下线，比例上限]，则触发报警

环比增加：如果((今天的数据量 - 昨天的数据量)/昨天的数据量*100)不在 [比例下线，比例上限]，则触发报警

报警触发条件设置一定要有。如果没有配置的阈值，不能做监控 日活、周活、月活、留存（日周月）、转化率（日、周、月）GMV（日、周、月） 复购率（日周月）

单表空值检测

某个字段为空的记录数在一个范围内，或者占总量的百分比在某个阈值范围内

目标字段：选择要监控的字段，不能选“无”

SQL结果：var 异常数据量 = select count(*) from 表 where 目标字段 is null

单次检测：如果(异常数据量)不在[数值下限, 数值上限]，则触发报警

单表重复值检测

一个或多个字段是否满足某些规则

目标字段：第一步先正常统计条数；select count(*) form 表；

第二步，去重统计；select count(*) from 表 group by 某个字段

第一步的值和第二步不的值做减法，看是否在上下线阀值之内

单次检测：如果(异常数据量)不在[数值下限, 数值上限]， 则触发报警

跨表数据量对比

主要针对同步流程，监控两张表的数据量是否一致

SQL结果：count(本表) - count(关联表)

阈值配置与“空值检测”相同

1. 数据分析方法论了解过哪些？ 数据商业分析的目标是利用大数据为所有职场人员做出迅捷，高质，高效的决策提供可规模化的解决方案。商业分析是创造价值的数据科学。
   数据商业分析中会存在很多判断：

观察数据当前发生了什么？ 比如想知道线上渠道A、B各自带来了多少流量，新上线的产品有多少用户喜欢，新注册流中注册的人数有多少。这些都需要通过数据来展示结果。

理解为什么发生？ 我们需要知道渠道A为什么比渠道B好，这些是要通过数据去发现的。也许某个关键字带来的流量转化率比其他都要低，这时可以通过信息、知识、数据沉淀出发生的原因是什么。

预测未来会发生什么？ 在对渠道A、B有了判断之后，根据以往的知识预测未来会发生什么。在投放渠道C、D的时候，猜测渠道C比渠道D好，当上线新的注册流、新的优化，可以知道哪一个节点比较容易出问题，这些都是通过数据进行预测的过程。

商业决策 所有工作中最有意义的还是商业决策，通过数据来判断应该做什么。这是商业分析最终的目的。

# **文章地址： **https://blog.csdn.net/qq_43061290/article/details/125182137



