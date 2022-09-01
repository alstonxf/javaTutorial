# 大数据技术之Kafka
## kafka由来

```
Kafka是由Apache软件基金会开发的一个开源流处理平台，由Scala和Java编写。Kafka是一种高吞吐
量的分布式发布订阅消息系统，它可以处理消费者在网站中的所有动作流数据。 这种动作（网页浏览，
搜索和其他用户的行动）是在现代网络上的许多社会功能的一个关键因素。 这些数据通常是由于吞吐量
的要求而通过处理日志和日志聚合来解决。 对于像Hadoop一样的日志数据和离线分析系统，但又要求实
时处理的限制，这是一个可行的解决方案。Kafka的目的是通过Hadoop的并行加载机制来统一线上和离
线的消息处理，也是为了通过集群来提供实时的消息。


```

## 消息队列

<img src="https://img-blog.csdnimg.cn/2e68d404e37e49719b485f660b96dfd0.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA5Y2X5pa55pyJ6ZuoWWE=,size_20,color_FFFFFF,t_70,g_se,x_16" alt="在这里插入图片描述"/>

## 点对点模式

```
消息生产者生产消息发送到Queue中，然后消息消费者从Queue中取出并且消费消息。消息被消费以后， queue 中不再有存储，所以消息消费者不可能消费到已经被消费的消息。Queue 支持存在多个消费者，但是对一个消息而言，只会有一个消费者可以消费。

```

<img src="https://img-blog.csdnimg.cn/0939f227d64f443e9c3df5467cb1457f.png" alt="在这里插入图片描述"/>

## 发布/订阅模式

<img src="https://img-blog.csdnimg.cn/addc46905c8c4bde8872cfccc5e36c26.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA5Y2X5pa55pyJ6ZuoWWE=,size_20,color_FFFFFF,t_70,g_se,x_16" alt="在这里插入图片描述"/>

```
一对多，消费者消费数据之后不会清除消息

消息生产者（发布）将消息发布到 topic 中，同时有多个消息消费者（订阅）消费该消息。和点对点方式不同，发布到 topic 的消息会被所有订阅者消费。

```

## 为什么需要消息队列

```
1）解耦：
　　允许你独立的扩展或修改两边的处理过程，只要确保它们遵守同样的接口约束。
2）冗余：
消息队列把数据进行持久化直到它们已经被完全处理，通过这一方式规避了数据丢失风险。许多消息队列所采用的"插入-获取-删除"范式中，在把一个消息从队列中删除之前，需要你的处理系统明确的指出该消息已经被处理完毕，从而确保你的数据被安全的保存直到你使用完毕。
3）扩展性：
因为消息队列解耦了你的处理过程，所以增大消息入队和处理的频率是很容易的，只要另外增加处理过程即可。
4）灵活性 &amp; 峰值处理能力：
在访问量剧增的情况下，应用仍然需要继续发挥作用，但是这样的突发流量并不常见。如果为以能处理这类峰值访问为标准来投入资源随时待命无疑是巨大的浪费。使用消息队列能够使关键组件顶住突发的访问压力，而不会因为突发的超负荷的请求而完全崩溃。
5）可恢复性：
系统的一部分组件失效时，不会影响到整个系统。消息队列降低了进程间的耦合度，所以即使一个处理消息的进程挂掉，加入队列中的消息仍然可以在系统恢复后被处理。
6）顺序保证：
在大多使用场景下，数据处理的顺序都很重要。大部分消息队列本来就是排序的，并且能保证数据会按照特定的顺序来处理。（Kafka保证一个Partition内的消息的有序性）
7）缓冲：
有助于控制和优化数据流经过系统的速度，解决生产消息和消费消息的处理速度不一致的情况。
8）异步通信：
很多时候，用户不想也不需要立即处理消息。消息队列提供了异步处理机制，允许用户把一个消息放入队列，但并不立即处理它。想向队列中放入多少消息就放多少，然后在需要的时候再去处理它们。


```

## Kafka入门_基础架构

<img src="https://img-blog.csdnimg.cn/da927a42d4704832a8dcd7db776038bd.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA5Y2X5pa55pyJ6ZuoWWE=,size_20,color_FFFFFF,t_70,g_se,x_16" alt="在这里插入图片描述"/>

```
Producer ： 消息生产者，就是向 Kafka ；
Consumer ： 消息消费者，向 Kafka broker 取消息的客户端；
Consumer Group （CG）： 消费者组，由多个 consumer 组成。 消费者组内每个消费者负责消费不同分区的数据，一个分区只能由一个组内消费者消费；消费者组之间互不影响。 所有的消费者都属于某个消费者组，即消费者组是逻辑上的一个订阅者。
Broker ：经纪人 一台 Kafka 服务器就是一个 broker。一个集群由多个 broker 组成。一个 broker可以容纳多个 topic。
Topic ： 话题，可以理解为一个队列， 生产者和消费者面向的都是一个 topic；
Partition： 为了实现扩展性，一个非常大的 topic 可以分布到多个 broker（即服务器）上，一个 topic 可以分为多个 partition，每个 partition 是一个有序的队列；
Replica： 副本（Replication），为保证集群中的某个节点发生故障时， 该节点上的 partition 数据不丢失，且 Kafka仍然能够继续工作， Kafka 提供了副本机制，一个 topic 的每个分区都有若干个副本，一个 leader 和若干个 follower。
Leader： 每个分区多个副本的“主”，生产者发送数据的对象，以及消费者消费数据的对象都是 leader。
Follower： 每个分区多个副本中的“从”，实时从 leader 中同步数据，保持和 leader 数据的同步。 leader 发生故障时，某个 Follower 会成为新的 leader。

```

## Kafka集群部署

```
1）解压安装包
[atguigu@hadoop102 software]$ tar -zxvf kafka_2.11-0.11.0.0.tgz -C /opt/module/
2）修改解压后的文件名称
[atguigu@hadoop102 module]$ mv kafka_2.11-0.11.0.0/ kafka
3）在/opt/module/kafka目录下创建logs文件夹
[atguigu@hadoop102 kafka]$ mkdir logs
4）修改配置文件
[atguigu@hadoop102 kafka]$ cd config/
[atguigu@hadoop102 config]$ vi server.properties
输入以下内容：
#broker的全局唯一编号，不能重复
broker.id=0
#删除topic功能使能
delete.topic.enable=true
#处理网络请求的线程数量
num.network.threads=3
#用来处理磁盘IO的现成数量
num.io.threads=8
#发送套接字的缓冲区大小
socket.send.buffer.bytes=102400
#接收套接字的缓冲区大小
socket.receive.buffer.bytes=102400
#请求套接字的缓冲区大小
socket.request.max.bytes=104857600
#kafka运行日志存放的路径	
log.dirs=/opt/module/kafka/logs
#topic在当前broker上的分区个数
num.partitions=1
#用来恢复和清理data下数据的线程数量
num.recovery.threads.per.data.dir=1
#segment文件保留的最长时间，超时将被删除
log.retention.hours=168
#配置连接Zookeeper集群地址
zookeeper.connect=hadoop102:2181,hadoop103:2181,hadoop104:2181
5）配置环境变量
[atguigu@hadoop102 module]$ sudo vi /etc/profile

#KAFKA_HOME
export KAFKA_HOME=/opt/module/kafka
export PATH=$PATH:$KAFKA_HOME/bin

[atguigu@hadoop102 module]$ source /etc/profile
6）分发安装包
[atguigu@hadoop102 module]$ xsync kafka/
	注意：分发之后记得配置其他机器的环境变量
7）分别在hadoop103和hadoop104上修改配置文件/opt/module/kafka/config/server.properties中的broker.id=1、broker.id=2
	注：broker.id不得重复
8）启动集群
依次在hadoop102、hadoop103、hadoop104节点上启动kafka
[atguigu@hadoop102 kafka]$ bin/kafka-server-start.sh config/server.properties &amp;
[atguigu@hadoop103 kafka]$ bin/kafka-server-start.sh config/server.properties &amp;
[atguigu@hadoop104 kafka]$ bin/kafka-server-start.sh config/server.properties &amp;
9）关闭集群
[atguigu@hadoop102 kafka]$ bin/kafka-server-stop.sh stop
[atguigu@hadoop103 kafka]$ bin/kafka-server-stop.sh stop
[atguigu@hadoop104 kafka]$ bin/kafka-server-stop.sh stop


```

## Kafka命令行操作

```
1）查看当前服务器中的所有topic
[atguigu@hadoop102 kafka]$ bin/kafka-topics.sh --zookeeper hadoop102:2181 --list
2）创建topic
[atguigu@hadoop102 kafka]$ bin/kafka-topics.sh --zookeeper hadoop102:2181 \
--create --replication-factor 3 --partitions 1 --topic first
选项说明：
--topic 定义topic名
--replication-factor  定义副本数
--partitions  定义分区数
3）删除topic
[atguigu@hadoop102 kafka]$ bin/kafka-topics.sh --zookeeper hadoop102:2181 \
--delete --topic first
需要server.properties中设置delete.topic.enable=true否则只是标记删除或者直接重启。
4）发送消息
[atguigu@hadoop102 kafka]$ bin/kafka-console-producer.sh \
--broker-list hadoop102:9092 --topic first
>hello world
>atguigu  atguigu
5）消费消息
[atguigu@hadoop103 kafka]$ bin/kafka-console-consumer.sh \
--zookeeper hadoop102:2181 --from-beginning --topic first
--from-beginning：会把first主题中以往所有的数据都读取出来。根据业务场景选择是否增加该配置。
6）查看某个Topic的详情
[atguigu@hadoop102 kafka]$ bin/kafka-topics.sh --zookeeper hadoop102:2181 \
--describe --topic first


```

## Kafka工作流程分析

<img src="https://img-blog.csdnimg.cn/916b1f2b5a93452d82c4f19704d91f25.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA5Y2X5pa55pyJ6ZuoWWE=,size_20,color_FFFFFF,t_70,g_se,x_16" alt="在这里插入图片描述"/>

## Kafka生产过程分析

## 写入方式

producer采用推（push）模式将消息发布到broker，每条消息都被追加（append）到分区（patition）中，属于顺序写磁盘（顺序写磁盘效率比随机写内存要高，保障kafka吞吐率）。

## 分区（Partition）

消息发送时都被发送到一个topic，其本质就是一个目录，而topic是由一些Partition Logs(分区日志)组成，其组织结构如下图所示： <img src="https://img-blog.csdnimg.cn/86aa5a01dc3244f5b7861434d4e948c8.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA5Y2X5pa55pyJ6ZuoWWE=,size_11,color_FFFFFF,t_70,g_se,x_16" alt="在这里插入图片描述"/> <img src="https://img-blog.csdnimg.cn/72d6295acaf54f20980da138bba0f106.png" alt="在这里插入图片描述"/> 我们可以看到，每个Partition中的消息都是有序的，生产的消息被不断追加到Partition log上，其中的每一个消息都被赋予了一个唯一的offset值。

## 分区的原因

（1）方便在集群中扩展，每个Partition可以通过调整以适应它所在的机器，而一个topic又可以有多个Partition组成，因此整个集群就可以适应任意大小的数据了； （2）可以提高并发，因为可以以Partition为单位读写了。

## 分区的原则

（1）指定了patition，则直接使用； （2）未指定patition但指定key，通过对key的value进行hash出一个patition； （3）patition和key都未指定，使用轮询选出一个patition。

```
DefaultPartitioner类
public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {<!-- -->
        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        int numPartitions = partitions.size();
        if (keyBytes == null) {<!-- -->
            int nextValue = nextValue(topic);
            List<PartitionInfo> availablePartitions = cluster.availablePartitionsForTopic(topic);
            if (availablePartitions.size() > 0) {<!-- -->
                int part = Utils.toPositive(nextValue) % availablePartitions.size();
                return availablePartitions.get(part).partition();
            } else {<!-- -->
                // no partitions are available, give a non-available partition
                return Utils.toPositive(nextValue) % numPartitions;
            }
        } else {<!-- -->
            // hash the keyBytes to choose a partition
            return Utils.toPositive(Utils.murmur2(keyBytes)) % numPartitions;
        }
    }


```

## 副本（Replication）

同一个partition可能会有多个replication（对应 server.properties 配置中的 default.replication.factor=N）。没有replication的情况下，一旦broker 宕机，其上所有 patition 的数据都不可被消费，同时producer也不能再将数据存于其上的patition。引入replication之后，同一个partition可能会有多个replication，而这时需要在这些replication之间选出一个leader，producer和consumer只与这个leader交互，其它replication作为follower从leader 中复制数据。

## 写入流程

### producer写入消息流程如下：

<img src="https://img-blog.csdnimg.cn/6de53d504ccc42948e4d11b4b9b1b270.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA5Y2X5pa55pyJ6ZuoWWE=,size_20,color_FFFFFF,t_70,g_se,x_16" alt="在这里插入图片描述"/> 1）producer先从zookeeper的 "/brokers/…/state"节点找到该partition的leader 2）producer将消息发送给该leader 3）leader将消息写入本地log 4）followers从leader pull消息，写入本地log后向leader发送ACK 5）leader收到所有ISR中的replication的ACK后，增加HW（high watermark，最后commit 的offset）并向producer发送ACK

## Broker 保存消息

## 存储方式

物理上把topic分成一个或多个patition（对应 server.properties 中的num.partitions=3配置），每个patition物理上对应一个文件夹（该文件夹存储该patition的所有消息和索引文件），如下：

```
[atguigu@hadoop102 logs]$ ll
drwxrwxr-x. 2 atguigu atguigu  4096 8月   6 14:37 first-0
drwxrwxr-x. 2 atguigu atguigu  4096 8月   6 14:35 first-1
drwxrwxr-x. 2 atguigu atguigu  4096 8月   6 14:37 first-2
[atguigu@hadoop102 logs]$ cd first-0
[atguigu@hadoop102 first-0]$ ll
-rw-rw-r--. 1 atguigu atguigu 10485760 8月   6 14:33 00000000000000000000.index
-rw-rw-r--. 1 atguigu atguigu      219 8月   6 15:07 00000000000000000000.log
-rw-rw-r--. 1 atguigu atguigu 10485756 8月   6 14:33 00000000000000000000.timeindex
-rw-rw-r--. 1 atguigu atguigu        8 8月   6 14:37 leader-epoch-checkpoint


```

## 存储策略

无论消息是否被消费，kafka都会保留所有消息。有两种策略可以删除旧数据： 1）基于时间：log.retention.hours=168 2）基于大小：log.retention.bytes=1073741824 需要注意的是，因为Kafka读取特定消息的时间复杂度为O(1)，即与文件大小无关，所以这里删除过期文件与提高 Kafka 性能无关。

## Zookeeper存储结构

<img src="https://img-blog.csdnimg.cn/bb443b85916c41689be344a5848c9cbb.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA5Y2X5pa55pyJ6ZuoWWE=,size_16,color_FFFFFF,t_70,g_se,x_16" alt="在这里插入图片描述"/> 注意：producer不在zk中注册，消费者在zk中注册。

## Kafka消费过程分析

kafka提供了两套consumer API：高级Consumer API和低级Consumer API。

## 高级API

1）高级API优点 高级API 写起来简单 不需要自行去管理offset，系统通过zookeeper自行管理。 不需要管理分区，副本等情况，.系统自动管理。 消费者断线会自动根据上一次记录在zookeeper中的offset去接着获取数据（默认设置1分钟更新一下zookeeper中存的offset） 可以使用group来区分对同一个topic 的不同程序访问分离开来（不同的group记录不同的offset，这样不同程序读取同一个topic才不会因为offset互相影响） 2）高级API缺点 不能自行控制offset（对于某些特殊需求来说） 不能细化控制如分区、副本、zk等

## 低级API

1）低级 API 优点 能够让开发者自己控制offset，想从哪里读取就从哪里读取。 自行控制连接分区，对分区自定义进行负载均衡 对zookeeper的依赖性降低（如：offset不一定非要靠zk存储，自行存储offset即可，比如存在文件或者内存中） 2）低级API缺点 太过复杂，需要自行控制offset，连接哪个分区，找到分区leader 等。

## 消费者组

<img src="https://img-blog.csdnimg.cn/3d31fcb433624de982b9a9bdcb525957.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA5Y2X5pa55pyJ6ZuoWWE=,size_20,color_FFFFFF,t_70,g_se,x_16" alt="在这里插入图片描述"/> 消费者是以consumer group消费者组的方式工作，由一个或者多个消费者组成一个组，共同消费一个topic。每个分区在同一时间只能由group中的一个消费者读取，但是多个group可以同时消费这个partition。在图中，有一个由三个消费者组成的group，有一个消费者读取主题中的两个分区，另外两个分别读取一个分区。某个消费者读取某个分区，也可以叫做某个消费者是某个分区的拥有者。 在这种情况下，消费者可以通过水平扩展的方式同时读取大量的消息。另外，如果一个消费者失败了，那么其他的group成员会自动负载均衡读取之前失败的消费者读取的分区。

## 消费方式

consumer采用pull（拉）模式从broker中读取数据。 push（推）模式很难适应消费速率不同的消费者，因为消息发送速率是由broker决定的。它的目标是尽可能以最快速度传递消息，但是这样很容易造成consumer来不及处理消息，典型的表现就是拒绝服务以及网络拥塞。而pull模式则可以根据consumer的消费能力以适当的速率消费消息。 对于Kafka而言，pull模式更合适，它可简化broker的设计，consumer可自主控制消费消息的速率，同时consumer可以自己控制消费方式——即可批量消费也可逐条消费，同时还能选择不同的提交方式从而实现不同的传输语义。 pull模式不足之处是，如果kafka没有数据，消费者可能会陷入循环中，一直等待数据到达。为了避免这种情况，我们在我们的拉请求中有参数，允许消费者请求在等待数据到达的“长轮询”中进行阻塞（并且可选地等待到给定的字节数，以确保大的传输大小）。

## 消费者组案例

1）需求：测试同一个消费者组中的消费者，同一时刻只能有一个消费者消费。 2）案例实操

```
（1）在hadoop102、hadoop103上修改/opt/module/kafka/config/consumer.properties配置文件中的group.id属性为任意组名。
[atguigu@hadoop103 config]$ vi consumer.properties
group.id=atguigu
	（2）在hadoop102、hadoop103上分别启动消费者
[atguigu@hadoop102 kafka]$ bin/kafka-console-consumer.sh \
--zookeeper hadoop102:2181 --topic first --consumer.config config/consumer.properties
[atguigu@hadoop103 kafka]$ bin/kafka-console-consumer.sh --zookeeper hadoop102:2181 --topic first --consumer.config config/consumer.properties
	（3）在hadoop104上启动生产者
[atguigu@hadoop104 kafka]$ bin/kafka-console-producer.sh \
--broker-list hadoop102:9092 --topic first
>hello world
	（4）查看hadoop102和hadoop103的接收者。
		同一时刻只有一个消费者接收到消息。


```

## Kafka API实战

。。。。。

## 参考：参考文档</a>

https://my.oschina.net/jallenkwong/blog/4449224#h2_52
# **文章地址： **https://blog.csdn.net/qq_43061290/article/details/121182788