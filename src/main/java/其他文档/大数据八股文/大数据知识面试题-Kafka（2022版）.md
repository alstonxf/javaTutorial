# 大数据知识面试题-Kafka（2022版）
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
1、kafka
1.1、kafka介绍
1.2、kafka相比其他消息队列的优势
1.3、kafka的术语
1.3.1、kafka中的术语名词
1.4、kafka的架构
1.5、kafka能做到消费的有序性吗
1.5.1、为什么topic下多个分区不能保证有序
1.6、分区与消费者组间的关系
1.7、生产者分区策略
1.8、数据丢失
1.8.1、生产者保证数据不丢失
**1.8.2、broker保证数据不丢失**
1.8.3、customer保证数据不丢失
1.9、数据重复
1.10、kafka当中数据的查找过程
1.11、Kafka auto.offset.reset值详解
1.12、Kafka 其他
1. 为什么要使用 kafka？
2. Kafka消费过的消息如何再消费？
3. kafka的数据是放在磁盘上还是内存上，为什么速度会快？
4. Kafka数据怎么保障不丢失？
5. 采集数据为什么选择kafka？
6. kafka 重启是否会导致数据丢失？
7. kafka 宕机了如何解决？
8. 为什么Kafka不支持读写分离？
9. kafka数据分区和消费者的关系？
10. kafka的数据offset读取流程
11. kafka内部如何保证顺序，结合外部组件如何保证消费者的顺序？
12. Kafka消息数据积压，Kafka消费能力不足怎么处理？
13. Kafka单条日志传输大小

<img src="https://img-blog.csdnimg.cn/491f70ad9ce242e4834e440d80700871.png" alt="在这里插入图片描述"/>

## 1、kafka

### 1.1、kafka介绍

 kafka是最初由linkedin公司开发的，使用scala语言编写，kafka是一个分布式，分区的，多副本的，多订阅者的消息队列系统。

### 1.2、kafka相比其他消息队列的优势

 常见的消息队列：RabbitMQ，Redis ，zeroMQ ,ActiveMQ

**kafka的优势**：
- 可靠性：分布式的，分区，复制和容错的。- 可扩展性：kafka消息传递系统轻松缩放，无需停机。- 耐用性：kafka使用分布式提交日志，这意味着消息会尽可能快速的保存在磁盘上，因此它是持久的。- 性能：kafka对于发布和定于消息都具有高吞吐量。即使存储了许多TB的消息，他也爆出稳定的性能。- kafka非常快：保证零停机和零数据丢失。
### 1.3、kafka的术语

<img src="https://img-blog.csdnimg.cn/1634903cde1b4e5f88f98a35ca137d07.png#pic_center" alt="在这里插入图片描述"/>

#### 1.3.1、kafka中的术语名词

**Broker**：kafka集群中包含一个或者多个服务实例，这种服务实例被称为Broker

**Topic**：每条发布到kafka集群的消息都有一个类别，这个类别就叫做Topic

**Partition**：Partition是一个物理上的概念，每个Topic包含一个或者多个Partition

**Producer**：负责发布消息到kafka的Broker中。

**Consumer**：消息消费者,向kafka的broker中读取消息的客户端

**Consumer Group**：每一个Consumer属于一个特定的Consumer Group（可以为每个Consumer指定 groupName）

### 1.4、kafka的架构

<img src="https://img-blog.csdnimg.cn/89c88b8b0b0d4083a4db2f67b6739b80.png#pic_center" alt="在这里插入图片描述"/>

### 1.5、kafka能做到消费的有序性吗
- 一个主题（topic）下面有一个分区（partition）即可
#### 1.5.1、为什么topic下多个分区不能保证有序
- 生产者生产数据到borker的多个分区，每个分区的数据是相对有序的，但整体的数据就无序了。因为消费者在消费的时候是一个个的分区进行消费的，所以不能保证全局有序。
### 1.6、分区与消费者组间的关系
- 消费组： 由一个或者多个消费者组成，同一个组中的消费者对于同一条消息只消费一次。- 某一个主题下的分区数，对于消费组来说，应该小于等于该主题下的分区数。
<img src="https://img-blog.csdnimg.cn/c12609fad388452695415fd8afb7cd45.png#pic_center" alt="在这里插入图片描述"/>

### 1.7、生产者分区策略
- 没有指定分区号、没指定key根据轮询的方式发送到不同的分区- 没有指定分区号、指定了key，根据key.hashcode%numPartition- 指定了分区号，则直接将数据写到指定的分区里面去- 自定义分区策略
```
//可根据主题和内容发送
public ProducerRecord(String topic, V value)
//根据主题，key、内容发送
public ProducerRecord(String topic, K key, V value)
//根据主题、分区、key、内容发送
public ProducerRecord(String topic, Integer partition, K key, V value)
//根据主题、分区、时间戳、key，内容发送
public ProducerRecord(String topic, Integer partition, Long timestamp, K key, V value)



如果没有指定分区号，也没有指定具体的key（轮询）
如果没有指定分区号，指定了具体的key（hash）
	前缀+date.getTime()   fixlog_1564388581914
如果指定了具体的分区号，（按照分区号）
自定义分区
	

```

### 1.8、数据丢失

#### 1.8.1、生产者保证数据不丢失
1.  **同步模式**：配置=1 （只有Leader收到，-1 所有副本成功，0 不等待）Leader Partition挂了，数据就会丢失 解决：设置 -1 保证produce 写入所有副本算成功 producer.type = sync request.required.acks=-1 1.  **异步模式**，当缓冲区满了，如果配置为0（没有收到确认，一满就丢弃），数据立刻丢弃 解决：不限制阻塞超时时间。就是一满生产者就阻 ​ 
#### **1.8.2、broker保证数据不丢失**

 broker采用分片副本机制，保证数据高可用。

#### 1.8.3、customer保证数据不丢失
-  拿到数据后，存储到hbase中或者mysql中，如果hbase或者mysql在这个时候连接不上，就会抛出异常，如果在处理数据的时候已经进行了提交，那么kafka上的oﬀset值已经进行了修改了，但是hbase或者mysql中没有数据，这个时候就会出现**数据丢失**。 主要是因为offset提交使用了异步提交。 <li> 解决方案 
  
  <ul>- Consumer将数据处理完成之后，再来进行oﬀset的修改提交。默认情况下oﬀset是 自动提交，需要修改为手动提交oﬀset值。- 流式计算。高级数据源以kafka为例，由2种方式：receiver (开启WAL，失败可恢复) director （checkpoint保证）
### 1.9、数据重复
- 落表（主键或者唯一索引的方式，避免重复数据） 业务逻辑处理（选择唯一主键存储到Redis或者mongdb中，先查询是否存在，若存在则不处理；若不存在，先插入Redis或Mongdb,再进行业务逻辑处理）
### 1.10、kafka当中数据的查找过程

<img src="https://img-blog.csdnimg.cn/230c173cbfe84ba0916c1265d071053a.png#pic_center" alt="在这里插入图片描述"/>

**第一步**：通过offset确定数据保存在哪一个segment里面了，

**第二步**：查找对应的segment里面的index文件 。index文件都是key/value对的。key表示数据在log文件里面的顺序是第几条。value记录了这一条数据在全局的标号。如果能够直接找到对应的offset直接去获取对应的数据即可

 如果index文件里面没有存储offset，就会查找offset最近的那一个offset，例如查找offset为7的数据找不到，那么就会去查找offset为6对应的数据，找到之后，再取下一条数据就是offset为7的数据

### 1.11、Kafka auto.offset.reset值详解

**earliest**
- 当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
**latest**
- 当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据
**none**
-  topic各分区都存在已提交的offset时，从offset后开始消费；只要有一个分区不存在已提交的offset，则抛出异常 ​**latest 这个设置容易丢失消息**，假如kafka出现问题，还有数据往topic中写，这个时候重启kafka，这个设置会从最新的offset开始消费,中间出问题的哪些就不管了。 
### 1.12、Kafka 其他

### 1. 为什么要使用 kafka？
1. 缓冲和削峰：上游数据时有突发流量，下游可能扛不住，或者下游没有足够多的机器来保证冗余，kafka在中间可以起到一个缓冲的作用，把消息暂存在kafka中，下游服务就可以按照自己的节奏进行慢慢处理。1. 解耦和扩展性：项目开始的时候，并不能确定具体需求。消息队列可以作为一个接口层，解耦重要的业务流程。只需要遵守约定，针对数据编程即可获取扩展能力。1. 冗余：可以采用一对多的方式，一个生产者发布消息，可以被多个订阅 topic的服务消费到，供多个毫无关联的业务使用。1. 健壮性：消息队列可以堆积请求，所以消费端业务即使短时间死掉，也不会影响主要业务的正常进行。1. 异步通信：很多时候，用户不想也不需要立即处理消息。消息队列提供了异步处理机制，允许用户把一个消息放入队列，但并不立即处理它。想向队列中放入多少消息就放多少，然后在需要的时候再去处理它们。
### 2. Kafka消费过的消息如何再消费？

kafka消费消息的offset是定义在zookeeper中的， 如果想重复消费kafka的消息，可以在redis中自己记录offset的checkpoint点（n个），当想重复消费消息时，通过读取redis中的checkpoint点进行zookeeper的offset重设，这样就可以达到重复消费消息的目的了

### 3. kafka的数据是放在磁盘上还是内存上，为什么速度会快？

kafka使用的是磁盘存储。 速度快是因为：
1. 顺序写入：因为硬盘是机械结构，每次读写都会寻址->写入，其中寻址是一个“机械动作”，它是耗时的。所以硬盘 “讨厌”随机I/O， 喜欢顺序I/O。为了提高读写硬盘的速度，Kafka就是使用顺序I/O。1. Memory Mapped Files（内存映射文件）：64位操作系统中一般可以表示 20G的数据文件，它的工作原理是直接利用操作系统的Page来实现文件到物理内存的直接映射。完成映射之后你对物理内存的操作会被同步到硬盘上。1. Kafka高效文件存储设计： Kafka把topic中一个parition大文件分成多个小文件段，通过多个小文件段，就容易定期清除或删除已经消费完文件，减少磁盘占用。通过索引信息可以快速定位 message和确定response 的 大 小。通过index元数据全部映射到memory（内存映射文件）， 可以避免segmentfile的IO磁盘操作。通过索引文件稀疏存储，可以大幅降低index文件元数据占用空间大小。 注：1. Kafka解决查询效率的手段之一是将数据文件分段，比如有100条Message，它们的offset是从0到99。假设将数据文件分成5段，第一段为0-19，第二段为20-39，以此类推，每段放在一个单独的数据文件里面，数据文件以该段中 小的offset命名。这样在查找指定offset的 Message的时候，用二分查找就可以定位到该Message在哪个段中。1. 为数据文件建 索引数据文件分段 使得可以在一个较小的数据文件中查找对应offset的Message 了，但是这依然需要顺序扫描才能找到对应 offset的Message。 为了进一步提高查找的效率，Kafka为每个分段后的数据文件建立了索引文件，文件名与数据文件的名字是一样的，只是文件扩展名为.index。
### 4. Kafka数据怎么保障不丢失？

分三个点说，一个是生产者端，一个消费者端，一个broker端。
1. 生产者数据的不丢失 kafka的ack机制：在kafka发送数据的时候，每次发送消息都会有一个确认反馈机制，确保消息正常的能够被收到，其中状态有0，1，-1。 如果是同步模式： ack设置为0，风险很大，一般不建议设置为0。即使设置为1，也会随着leader 宕机丢失数据。所以如果要严格保证生产端数据不丢失，可设置为-1。 如果是异步模式： 也会考虑ack的状态，除此之外，异步模式下的有个buffer，通过buffer来进行控制数据的发送，有两个值来进行控制，时间阈值与消息的数量阈值，如果 buffer满了数据还没有发送出去，有个选项是配置是否立即清空buffer。可以设置为-1，永久阻塞，也就数据不再生产。异步模式下，即使设置为-1。也可能因为程序员的不科学操作，操作数据丢失，比如kill -9，但这是特别的例外情况。 注： ack=0：producer不等待broker同步完成的确认，继续发送下一条(批)信息。 ack=1（默认）：producer要等待leader成功收到数据并得到确认，才发送下一条 message。 ack=-1：producer得到follwer确认，才发送下一条数据。1. 消费者数据的不丢失 通过offsetcommit 来保证数据的不丢失，kafka自己记录了每次消费的offset 数值，下次继续消费的时候，会接着上次的offset进行消费。而offset的信息在kafka0.8版本之前保存在zookeeper中，在0.8版本之后保存到topic中，即使消费者在运行过程中挂掉了，再次启动的时候会找到offset 的值，找到之前消费消息的位置，接着消费，由于 offset 的信息写入的时候并不是每条消息消费完成后都写入的，所以这种情况有可能会造成重复消费，但是不会丢失消息。 唯一例外的情况是，我们在程序中给原本做不同功能的两个consumer组设置 KafkaSpoutConfig.bulider.setGroupid的时候设置成了一样的groupid，这种情况会导致这两个组共享同一份数据，就会产生组A消费partition1， partition2中的消息，组B消费partition3的消息，这样每个组消费的消息都会丢失，都是不完整的。 为了保证每个组都独享一份消息数据，groupid一定不要重复才行。1. kafka集群中的broker的数据不丢失 每个broker中的partition我们一般都会设置有replication（副本）的个数，生产者写入的时候首先根据分发策略（有partition按partition，有key按key，都没有轮询）写入到leader中，follower（副本）再跟leader同步数据，这样有了备份，也可以保证消息数据的不丢失。
### 5. 采集数据为什么选择kafka？

采集层 主要可以使用Flume, Kafka等技术。 Flume：Flume 是管道流方式，提供了很多的默认实现，让用户通过参数部署，及扩展API. Kafka：Kafka是一个可持久化的分布式的消息队列。 Kafka 是一个非常通用的系统。你可以有许多生产者和很多的消费者共享多个主题Topics。 相比之下,Flume是一个专用工具被设计为旨在往HDFS，HBase发送数据。它对 HDFS有特殊的优化，并且集成了Hadoop的安全特性。 所以，Cloudera 建议如果数据被多个系统消费的话，使用kafka；如果数据被设计给Hadoop使用，使用Flume。

### 6. kafka 重启是否会导致数据丢失？
1. kafka是将数据写到磁盘的，一般数据不会丢失。1. 但是在重启kafka过程中，如果有消费者消费消息，那么kafka如果来不及提交offset，可能会造成数据的不准确（丢失或者重复消费）。
### 7. kafka 宕机了如何解决？
1. 先考虑业务是否受到影响 kafka 宕机了，首先我们考虑的问题应该是所提供的服务是否因为宕机的机器而受到影响，如果服务提供没问题，如果实现做好了集群的容灾机制，那么这块就不用担心了。1. 节点排错与恢复 想要恢复集群的节点，主要的步骤就是通过日志分析来查看节点宕机的原因，从而解决，重新恢复节点。
### 8. 为什么Kafka不支持读写分离？

在 Kafka 中，生产者写入消息、消费者读取消息的操作都是与 leader 副本进行交互的，从 而实现的是一种主写主读的生产消费模型。 Kafka 并不支持主写从读，因为主写从读有 2 个很明显的缺点:
1. 数据一致性问题：数据从主节点转到从节点必然会有一个延时的时间窗口，这个时间 窗口会导致主从节点之间的数据不一致。某一时刻，在主节点和从节点中 A 数据的值都为 X， 之后将主节点中 A 的值修改为 Y，那么在这个变更通知到从节点之前，应用读取从节点中的 A 数据的值并不为最新的 Y，由此便产生了数据不一致的问题。1. 延时问题：类似 Redis 这种组件，数据从写入主节点到同步至从节点中的过程需要经历 网络→主节点内存→网络→从节点内存 这几个阶段，整个过程会耗费一定的时间。而在 Kafka 中，主从同步会比 Redis 更加耗时，它需要经历 网络→主节点内存→主节点磁盘→网络→从节 点内存→ 从节点磁盘 这几个阶段。对延时敏感的应用而言，主写从读的功能并不太适用。 而kafka的主写主读的优点就很多了：1. 可以简化代码的实现逻辑，减少出错的可能;1. 将负载粒度细化均摊，与主写从读相比，不仅负载效能更好，而且对用户可控;1. 没有延时的影响;1. 在副本稳定的情况下，不会出现数据不一致的情况。
### 9. kafka数据分区和消费者的关系？

每个分区只能由同一个消费组内的一个消费者(consumer)来消费，可以由不同的消费组的消费者来消费，同组的消费者则起到并发的效果。

### 10. kafka的数据offset读取流程
1. 连接ZK集群，从ZK中拿到对应topic的partition信息和partition 的Leader的相关信息1. 连接到对应Leader对应的broker1. consumer将⾃自⼰己保存的offset发送给Leader1. Leader根据offset等信息定位到segment（索引⽂文件和⽇日志⽂文件）1. 根据索引⽂文件中的内容，定位到⽇日志⽂文件中该偏移量量对应的开始位置读取相应⻓长度的数据并返回给consumer
### 11. kafka内部如何保证顺序，结合外部组件如何保证消费者的顺序？

kafka只能保证partition内是有序的，但是partition间的有序是没办法的。 爱奇艺的搜索架构，是从业务上把需要有序的打到同⼀个partition。

### 12. Kafka消息数据积压，Kafka消费能力不足怎么处理？
1. 如果是Kafka消费能力不足，则可以考虑增加Topic的分区数，并且同时提升消费组的消费者数量，消费者数=分区数。（两者缺一不可）1. 如果是下游的数据处理不及时：提高每批次拉取的数量。批次拉取数据过少（拉取数据/处理时间<生产速度），使处理的数据小于生产的数据，也会造成数据积压。
### 13. Kafka单条日志传输大小

kafka对于消息体的大小默认为单条最大值是1M但是在我们应用场景中, 常常会出现一条消息大于1M，如果不对kafka进行配置。则会出现生产者无法将消息推送到kafka或消费者无法去消费kafka里面的数据, 这时我们就要对kafka 进行以下配置：server.properties replica.fetch.max.bytes: 1048576 broker可复制的消息的最大字节数, 默认为 1M message.max.bytes: 1000012 kafka 会接收单个消息size的最大限制，默认为1M左右 注意：message.max.bytes必须小于等于replica.fetch.max.bytes，否则就会导致replica之间数据同步失败。

# **文章地址： **https://blog.csdn.net/qq_43061290/article/details/125145841



