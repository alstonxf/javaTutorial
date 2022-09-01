# 大数据知识面试题-MapReduce和YARN（2022版）
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
MapReduce
1、介绍MapReduce
2、会写Wordcount
3、Combiner
4、partitioner
5、MapReduce的执行流程
6、MapReduce的shuffle阶段
7、MapReduce优化
7.1、资源相关参数
7.2、容错相关参数
7.3、效率跟稳定性参数
8、mapreduce程序在yarn上的执行流程
9、执行MapReduce常见的问题
10、其他MapReduce常见的问题
10.1、使用MapReduce实现两表的join
10.2、使用MapReduce的优化
10.3、MapReduce中排序发生在哪几个阶段？
10.4、MapReduce的序列化
10.45、MapReduce的combiner
yarn
1、介绍yarn
2、yarn的基本架构
3、yarn三大组件
3.1．ResourceManager
3.2． NodeManager
3.3． ApplicationMaster
4、Yarn 调度器Scheduler
4.1.FIFO Scheduler
4.2． Capacity Scheduler
4.3．Fair Scheduler
4.4．YARN作业调度流程
5、hadoop为什么有yarn

## MapReduce

### 1、介绍MapReduce

​ MapReduce的思想核心是“**分而治之**”，适用于大量复杂的任务处理场景（大规模数据处理场景）。

​ Map负责“分”，即把复杂的任务分解为若干个“简单的任务”来并行处理。可以进行拆分的前提是这些小任务可以并行计算，彼此间几乎没有依赖关系。

​ Reduce负责“合”，即对map阶段的结果进行全局汇总。

<img src="https://img-blog.csdnimg.cn/16e79aa940664387bc5d5eeae3f06019.png#pic_center" alt="在这里插入图片描述"/>

​ 图：MapReduce思想模型

### 2、会写Wordcount
- **定义一个mapper类**
```
//首先要定义四个泛型的类型
//keyin:  LongWritable    valuein: Text
//keyout: Text            valueout:IntWritable

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	//map方法的生命周期：  框架每传一行数据就被调用一次
	//key :  这一行的起始点在文件中的偏移量
	//value: 这一行的内容
	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		//拿到一行数据转换为string
		String line = value.toString();
		//将这一行切分出各个单词
		String[] words = line.split(" ");
		//遍历数组，输出<单词，1>
		for(String word:words){
			context.write(new Text(word), new IntWritable(1));
		}
	}
}

```
- **定义一个reducer类**
```
//生命周期：框架每传递进来一个kv 组，reduce方法被调用一次  
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		//定义一个计数器
		int count = 0;
		//遍历这一组kv的所有v，累加到count中
		for(IntWritable value:values){
			count += value.get();
		}
		context.write(key, new IntWritable(count));
	}
}

```
- **定义一个主类，用来描述job并提交job**
```
public class WordCountRunner {
	//把业务逻辑相关的信息（哪个是mapper，哪个是reducer，要处理的数据在哪里，输出的结果放哪里……）描述成一个job对象
	//把这个描述好的job提交给集群去运行
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job wcjob = Job.getInstance(conf);
		//指定我这个job所在的jar包
//		wcjob.setJar("/home/hadoop/wordcount.jar");
		wcjob.setJarByClass(WordCountRunner.class);
		
		wcjob.setMapperClass(WordCountMapper.class);
		wcjob.setReducerClass(WordCountReducer.class);
		//设置我们的业务逻辑Mapper类的输出key和value的数据类型
		wcjob.setMapOutputKeyClass(Text.class);
		wcjob.setMapOutputValueClass(IntWritable.class);
		//设置我们的业务逻辑Reducer类的输出key和value的数据类型
		wcjob.setOutputKeyClass(Text.class);
		wcjob.setOutputValueClass(IntWritable.class);
		
		//指定要处理的数据所在的位置
		FileInputFormat.setInputPaths(wcjob, "hdfs://hdp-server01:9000/wordcount/data/big.txt");
		//指定处理完成之后的结果所保存的位置
		FileOutputFormat.setOutputPath(wcjob, new Path("hdfs://hdp-server01:9000/wordcount/output/"));
		
		//向yarn集群提交这个job
		boolean res = wcjob.waitForCompletion(true);
		System.exit(res?0:1);
	}

```

### 3、Combiner

​ 每一个map都可能会产生大量的本地输出，Combiner的作用就是对map端的输出先做一次合并，以减少在map和reduce节点之间的数据传输量，以提高网络IO性能。

​ 例如：对于hadoop自带的wordcount的例子，value就是一个叠加的数字， ​ 所以map一结束就可以进行reduce的value叠加，而不必要等到所有的map结束再去进行reduce的value叠加。

<img src="https://img-blog.csdnimg.cn/c6e8ccf8678746f382358dfb5e16f7fd.png#pic_center" alt="在这里插入图片描述"/>
- 具体使用
```
自定义Combiner：

public static class MyCombiner extends  Reducer<Text, LongWritable, Text, LongWritable> {
        protected void reduce(
                Text key, Iterable<LongWritable> values,Context context)throws IOException, InterruptedException {

            long count = 0L;
            for (LongWritable value : values) {
                count += value.get();
            }
            context.write(key, new LongWritable(count));
        };
    }


```
- 在主类中添加
```
Combiner设置
    // 设置Map规约Combiner
    job.setCombinerClass(MyCombiner.class);

执行后看到map的输出和combine的输入统计是一致的，而combine的输出与reduce的输入统计是一样的。

```

### 4、partitioner

​ 在进行MapReduce计算时，有时候需要把最终的输出数据分到不同的文件中，比如按照省份划分的话，需要把同一省份的数据放到一个文件中；按照性别划分的话，需要把同一性别的数据放到一个文件中。负责实现划分数据的类称作Partitioner。
- HashPartitioner源码如下
```
package org.apache.hadoop.mapreduce.lib.partition;

import org.apache.hadoop.mapreduce.Partitioner;

/** Partition keys by their {@link Object#hashCode()}. */
public class HashPartitioner<K, V> extends Partitioner<K, V> {

  /** Use {@link Object#hashCode()} to partition. */
  public int getPartition(K key, V value,
                          int numReduceTasks) {
    //默认使用key的hash值与上int的最大值，避免出现数据溢出 的情况
    return (key.hashCode() &amp; Integer.MAX_VALUE) % numReduceTasks;
  }

}

```
- **key、value分别指的是Mapper任务的输出，numReduceTasks指的是设置的Reducer任务数量，默认值是1**。那么任何整数与1相除的余数肯定是0。也就是说getPartition(…)方法的返回值总是0。也就是Mapper任务的输出总是送给一个Reducer任务，最终只能输出到一个文件中。
**具体实现**：

```
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class FivePartitioner extends Partitioner<IntWritable, IntWritable>{

    /**
     * 我们的需求：按照能否被5除尽去分区
     * 
     * 1、如果除以5的余数是0，  放在0号分区
     * 2、如果除以5的余数不是0，  放在1分区
     */
    @Override
    public int getPartition(IntWritable key, IntWritable value, int numPartitions) {
        
        int intValue = key.get();
        
        if(intValue % 5 == 0){
            return 0;
        }else{
           return 1;
        }    
    }
}

```
- 在主函数里加入如下两行代码即可：
```
job.setPartitionerClass(FivePartitioner.class);
job.setNumReduceTasks(2);//设置为2

```

### 5、MapReduce的执行流程

<img src="https://img-blog.csdnimg.cn/99a36e619e7641b6a914943661f48146.png#pic_center" alt="在这里插入图片描述"/>
-  详细流程 <li> Map阶段 
  <ul>-  l **第一阶段**是把输入目录下文件按照一定的标准逐个进行逻辑切片，形成切片规划。默认情况下，Split size = Block size。每一个切片由一个MapTask处理。（getSplits） l **第二阶段**是对切片中的数据按照一定的规则解析成<key,value>对。默认规则是把每一行文本内容解析成键值对。key是每一行的起始位置(单位是字节)，value是本行的文本内容。（TextInputFormat） l **第三阶段**是调用Mapper类中的map方法。上阶段中每解析出来的一个<k,v>，调用一次map方法。每次调用map方法会输出零个或多个键值对。 l **第四阶段**是按照一定的规则对第三阶段输出的键值对进行分区。默认是只有一个区。分区的数量就是Reducer任务运行的数量。默认只有一个Reducer任务。 l **第五阶段**是对每个分区中的键值对进行排序。首先，按照键进行排序，对于键相同的键值对，按照值进行排序。比如三个键值对<2,2>、<1,3>、<2,1>，键和值分别是整数。那么排序后的结果是<1,3>、<2,1>、<2,2>。如果有第六阶段，那么进入第六阶段；如果没有，直接输出到文件中。 l **第六阶段**是对数据进行局部聚合处理，也就是combiner处理。键相等的键值对会调用一次reduce方法。经过这一阶段，数据量会减少。**本阶段默认是没有的。** 
reduce阶段
-  l **第一阶段**是Reducer任务会主动从Mapper任务复制其输出的键值对。Mapper任务可能会有很多，因此Reducer会复制多个Mapper的输出。 l **第二阶段**是把复制到Reducer本地数据，全部进行合并，即把分散的数据合并成一个大的数据。再对合并后的数据排序。 l **第三阶段**是对排序后的键值对调用reduce方法。键相等的键值对调用一次reduce方法，每次调用会产生零个或者多个键值对。最后把这些输出的键值对写入到HDFS文件中。 
### 6、MapReduce的shuffle阶段

<img src="https://img-blog.csdnimg.cn/08d67545078846caba69ad1a022dab10.png#pic_center" alt="在这里插入图片描述"/>
-  shuffle被称作MapReduce的心脏，是MapReduce的核心。 -  由上图看出，每个数据切片由一个Mapper进程处理，也就是说mappper只是处理文件的一部分。 -  每一个Mapper进程都有一个环形的内存缓冲区，用来存储Map的输出数据，这个内存缓冲区的默认大小是100MB，当数据达到阙值0.8，也就是80MB的时候，一个后台的程序就会把数据溢写到磁盘中。在将数据溢写到磁盘的过程中要经过复杂的过程，首先要将数据进行分区排序（按照分区号如0，1,2），分区完以后为了避免Map输出数据的内存溢出，可以将Map的输出数据分为各个小文件再进行分区，这样map的输出数据就会被分为了具有多个小文件的分区已排序过的数据。然后将各个小文件分区数据进行合并成为一个大的文件（将各个小文件中分区号相同的进行合并）。 -  这个时候Reducer启动了三个分别为0,1,2。0号Reducer会取得0号分区 的数据；1号Reducer会取得1号分区的数据；2号Reducer会取得2号分区的数据。 ​ 
### 7、MapReduce优化

#### 7.1、资源相关参数

//以下参数是在用户自己的MapReduce应用程序中配置就可以生效

(1) mapreduce.map.memory.mb: 一个Map Task可使用的内存上限（单位:MB），默认为1024。如果Map Task实际使用的资源量超过该值，则会被强制杀死。

(2) mapreduce.reduce.memory.mb: 一个Reduce Task可使用的资源上限（单位:MB），默认为1024。如果Reduce Task实际使用的资源量超过该值，则会被强制杀死。

(3) mapreduce.map.cpu.vcores: 每个Maptask可用的最多cpu core数目, 默认值: 1

(4) mapreduce.reduce.cpu.vcores: 每个Reducetask可用最多cpu core数目默认值: 1

(5) mapreduce.map.java.opts: Map Task的JVM参数，你可以在此配置默认的java heap

size等参数, 例如：“-Xmx1024m -verbose:gc -Xloggc:/tmp/@taskid@.gc”

（@taskid@会被Hadoop框架自动换为相应的taskid）, 默认值: “”

(6) mapreduce.reduce.java.opts: Reduce Task的JVM参数，你可以在此配置默认的java

heap size等参数, 例如：“-Xmx1024m -verbose:gc -Xloggc:/tmp/@taskid@.gc”, 默认值: “”

//应该在yarn启动之前就配置在服务器的配置文件中才能生效

(1) yarn.scheduler.minimum-allocation-mb RM中每个容器请求的最小配置，以MB为单位，默认1024。

(2) yarn.scheduler.maximum-allocation-mb RM中每个容器请求的最大分配，以MB为单位，默认8192。

(3) yarn.scheduler.minimum-allocation-vcores 1

(4)yarn.scheduler.maximum-allocation-vcores 32

(5) yarn.nodemanager.resource.memory-mb 表示该节点上YARN可使用的物理内存总量，默认是8192（MB），注意，如果你的节点内存资源不够8GB，则需要调减小这个值，而YARN不会智能的探测节点的物理内存总量。

//shuffle性能优化的关键参数，应在yarn启动之前就配置好

(1) mapreduce.task.io.sort.mb 100 shuffle的环形缓冲区大小，默认100m

(2) mapreduce.map.sort.spill.percent 0.8 环形缓冲区溢出的阈值，默认80%

#### 7.2、容错相关参数

(1) mapreduce.map.maxattempts: 每个Map Task最大重试次数，一旦重试参数超过该值，则认为Map Task运行失败，默认值：4。

(2) mapreduce.reduce.maxattempts: 每个Reduce Task最大重试次数，一旦重试参数超过该值，则认为Map Task运行失败，默认值：4。

(3) mapreduce.map.failures.maxpercent: 当失败的Map Task失败比例超过该值，整个作业则失败，默认值为0. 如果你的应用程序允许丢弃部分输入数据，则该该值设为一个大于0的值，比如5，表示如果有低于5%的Map Task失败（如果一个Map Task重试次数超过mapreduce.map.maxattempts，则认为这个Map Task失败，其对应的输入数据将不会产生任何结果），整个作业扔认为成功。

(4) mapreduce.reduce.failures.maxpercent: 当失败的Reduce Task失败比例超过该值为，整个作业则失败，默认值为0.

(5) mapreduce.task.timeout:如果一个task在一定时间内没有任何进入，即不会读取新的数据，也没有输出数据，则认为该task处于block状态，可能是临时卡住，也许永远会卡住。为了防止因为用户程序永远block不退出，则强制设置了一个超时时间（单位毫秒），默认是600000，值为0将禁用超时。

#### 7.3、效率跟稳定性参数

(1) mapreduce.map.speculative: 是否为Map Task打开推测执行机制，默认为true, 如果为true，则可以并行执行一些Map任务的多个实例。

(2) mapreduce.reduce.speculative: 是否为Reduce Task打开推测执行机制，默认为true

(3)mapreduce.input.fileinputformat.split.minsize: FileInputFormat做切片时最小切片大小，默认1。

(5)mapreduce.input.fileinputformat.split.maxsize: FileInputFormat做切片时最大切片大小

### 8、mapreduce程序在yarn上的执行流程

Hadoop jar xxx.jar

[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-Cz8bHxg9-1651672351492)(assert/1582125526987.png)]

<img src="https://img-blog.csdnimg.cn/ff72b2c1634a40f5a2f6b6610deed519.png#pic_center" alt="在这里插入图片描述"/>

**详细流程**：
- 一：客户端向集群提交一个任务，该任务首先到ResourceManager中的ApplicationManager;- 二：ApplicationManager收到任务之后，会在集群中找一个NodeManager，并在该NodeManager所在DataNode上启动一个AppMaster进程，该进程用于进行任务的划分和任务的监控；- 三：AppMaster启动起来之后，会向ResourceManager中的ApplicationManager注册其信息（目的是与之通信）；- 四：AppMaster向ResourceManager下的ResourceScheduler申请计算任务所需的资源；- 五：AppMaster申请到资源之后，会与所有的NodeManager通信要求它们启动计算任务所需的任务（Map和Reduce）；- 六：各个NodeManager启动对应的容器用来执行Map和Reduce任务；- 七：各个任务会向AppMaster汇报自己的执行进度和执行状况，以便让AppMaster随时掌握各个任务的运行状态，在某个任务出了问题之后重启执行该任务；- 八：在任务执行完之后，AppMaster向ApplicationManager汇报，以便让ApplicationManager注销并关闭自己，使得资源得以回收；
### 9、执行MapReduce常见的问题
1. client对集群中HDFS的操作没有权限
```
在集群配置文件hdfs-site.xml
property>
     <name>dfs.permissions</name>
    <value>false</value>
</property>
然后重启

```
1.  mapreduce的输出路径已存在，必须先删除掉那个路径 1.  提交集群运行，运行失败 
```
job.setJar("/home/hadoop/wordcount.jar");

```
<li> 日志打不出来，报警告信息 <pre><code class="prism language-verilog">log4j:WARN No appenders could be found for logger (org.apache.hadoop.metrics2.lib.MutableMetricsFactory).  
log4j:WARN Please initialize the log4j system properly.  
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.  
</code></pre> 需要在项目的src下面新建file名为log4j.properties的文件 </li>
### 10、其他MapReduce常见的问题

#### 10.1、使用MapReduce实现两表的join
- **map join** map side join 是针对一下场景进行的优化。两个待连接的表中，有一个表非常大，而另一个非常小，以至于小表可以直接存放到内存中。这样，我们可以将小表复制多份，让每一个map task内存中存在一份（比如放在hash table中），然后只扫描大表：对于大表中的每一条记录key/value，在hash table中查找是否具有相同key的记录，入股有，则连接后输出即可。
**场景：MapJoin 适用于有一份数据较小的连接情况。**

做法：直接将较小的数据加载到内存中，按照连接的关键字建立索引，大份数据作为MapTask的输入数据对 map()方法的每次输入都去内存当中直接去匹配连接。然后把连接结果按 key 输出，这种方法要使用 hadoop中的 DistributedCache 把小份数据分布到各个计算节点，每个 maptask 执行任务的节点都需要加载该数据到内存，并且按连接关键字建立索引。
- **reduce join** 在map阶段，map函数同时读取两个文件File1和File2，为了区分两种来源key/value数据对，没条数据打一个标签（tag），比如：tag=0表示来自文件File1，tag=2表示来自文件File2。 在map阶段, 把关键字作为key输出，并在value中标记出数据是来自data1还是data2。因为在shuffle阶段已经自然按key分组，reduce阶段，判断每一个value是来自data1还是data2,在内部分成2组，做集合的乘积。
**问题** map阶段没有对数据瘦身，shuffle的网络传输和排序性能很低。 reduce端对2个集合做乘积计算，很耗内存，容易导致OOM。

#### 10.2、使用MapReduce的优化

优化前我们需要知道hadoop适合干什么活，适合什么场景，在工作中，我们要知道业务是怎样的，能才结合平台资源达到最有优化。除了这些我们当然还要知道mapreduce的执行过程，比如从文件的读取，map处理，shuffle过程，reduce处理，文件的输出或者存储。在工作中，往往平台的参数都是固定的，不可能为了某一个作业去修改整个平台的参数，所以在作业的执行过程中，需要对作业进行单独的设定，这样既不会对其他作业产生影响，也能很好的提高作业的性能，提高优化的灵活性。

**回顾下hadoop的优势**

1、可构建在廉价机器上，设备成本相对低 2、高容错性，HDFS将数据自动保存多个副本，副本丢失后，自动恢复，防止数据丢失或损坏 3、适合批处理，HDFS适合一次写入、多次查询（读取）的情况，适合在已有的数据进行多次分析，稳定性好 4、适合存储大文件，其中的大表示可以存储单个大文件，因为是分块存储，以及表示存储大量的数据

**二、小文件优化**

从概述中我们知道，很明显hadoop适合大文件的处理和存储，那为什么不适合小文件呢？

1、从存储方面来说：hadoop的存储每个文件都会在NameNode上记录元数据，如果同样大小的文件，文件很小的话，就会产生很多文件，造成NameNode的压力。 2、从读取方面来说：同样大小的文件分为很多小文件的话，会增加磁盘寻址次数，降低性能 3、从计算方面来说：我们知道一个map默认处理一个分片或者一个小文件，如果map的启动时间都比数据处理的时间还要长，那么就会造成性能低，而且在map端溢写磁盘的时候每一个map最终会产生reduce数量个数的中间结果，如果map数量特别多，就会造成临时文件很多，而且在reduce拉取数据的时候增加磁盘的IO。

好，我们明白小文件造成的弊端之后，那我们应该怎么处理这些小文件呢？

1、从源头干掉，也就是在hdfs上我们不存储小文件，也就是数据上传hdfs的时候我们就合并小文件 2、在FileInputFormat读取入数据的时候我们使用实现类CombineFileInputFormat读取数据，在读取数据的时候进行合并。

**三、数据倾斜问题优化**

我们都知道mapreduce是一个并行处理，那么处理的时间肯定是作业中所有任务最慢的那个了，可谓木桶效应？为什么会这样呢？

1、数据倾斜，每个reduce处理的数据量不是同一个级别的，所有导致有些已经跑完了，而有些跑的很慢。 2、还有可能就是某些作业所在的NodeManager有问题或者container有问题，导致作业执行缓慢。

**那么为什么会产生数据倾斜呢？**

数据本身就不平衡，所以在默认的hashpartition时造成分区数据不一致问题，还有就是代码设计不合理等。

**那如何解决数据倾斜的问题呢？**

1、既然默认的是hash算法进行分区，那我们自定义分区，修改分区实现逻辑，结合业务特点，使得每个分区数据基本平衡 2、既然有默认的分区算法，那么我们可以修改分区的键，让其符合hash分区，并且使得最后的分区平衡，比如在key前加随机数n-key。 3、既然reduce处理慢，我们可以增加reduce的内存和vcore呀，这样挺高性能就快了，虽然没从根本上解决问题，但是还有效果 4、既然一个reduce处理慢，那我们可以增加reduce的个数来分摊一些压力呀，也不能根本解决问题，还是有一定的效果。

**那么如果不是数据倾斜带来的问题，而是节点服务有问题造成某些map和reduce执行缓慢呢？**

那么我们可以使用推测执行呀，你跑的慢，我们可以找个其他的节点重启一样的任务竞争，谁快谁为准。推测执行时以空间换时间的优化。会带来集群资源的浪费，会给集群增加压力，所以我司集群的推测执行都是关闭的。其实在作业执行的时候可以偷偷开启的呀

运行时间：启动一个MapReduce任务，map阶段和reduce阶段都会有并行的task共同处理任务，这些task都需要开jvm，然后初始化，而这些jvm很花费空间的，如果运行一个20-30s的任务需要进行开启，初始化，停止jvm操作很是浪费，所以我们应该尽量吧数据量控制在能让每个task运行1分钟以上。

**1.如何能够让Map执行效率最高** 尽量减少环形缓冲区flush的次数（减少IO 的使用）

1、调大环形缓冲区的大小，将100M调更大。 2、调大环形缓冲区阈值大的大小。 3、对Map输出的数据进行压缩。（数据在压缩和解压的过程中会消耗CPU）

**2.如何能够让Reduce执行效率最高** 尽量减少环形缓冲区flush的次数 1尽量将所有的数据写入内存，在内存中进行计算。 3.集群调优核心思路

`在网络带宽、磁盘IO是瓶颈的前提下:`

能不使用io 和网络，就不使用。在必须使用的情况下，能少用IO 网络就少用， 所有的能够减少网络开销的、减少IO使用的可选项，都可以作为集群调优的可选项。（软件层面（操作系统----集群层面），硬件层面，网络层面）

`数据倾斜：可以通过对原始数据进行抽样得到结果集来预设分区。`

#### 10.3、MapReduce中排序发生在哪几个阶段？
-  一个MapReduce作业由Map阶段和Reduce阶段两部分组成，这两阶段会对数据排序，从这个意义上说，MapReduce框架本质就是一个Distributed Sort。 -  在Map阶段，Map Task会在本地磁盘输出一个按照key排序(采用的是快速排序)的文件(中间可能产生多个文件，但最终会合并成一个)，在Reduce阶段，每个ReduceTask会对收到的数据排序，这样数据便按照key分成了若干组，之后以组为单位交给reduce方法处理。 -  很多人的误解在Map阶段，如果不使用Combiner便不会排序，这是错误的，不管你用不用Combiner，MapTask均会对产生的数据排序(如果没有ReduceTask，则不会排序，实际上Map阶段的排序就是为了减轻Reduce端排序负载)。 -  由于这些排序是MapReduce自动完成的，用户无法控制，因此，在hadoop1.x中无法避免，也不可以关闭，但hadoop2.x是可以关闭的(将reducetask设置为0)。 
#### 10.4、MapReduce的序列化

序列化（Serialization）是指把结构化对象转化为字节流。 反序列化（Deserialization）是序列化的逆过程。把字节流转为结构化对象。 当要在进程间传递对象或持久化对象的时候，就需要序列化对象成字节流，反之当要将接收到或从磁盘读取的字节流转换为对象，就要进行反序列化。 Java的序列化（Serializable）是一个重量级序列化框架，一个对象被序列化后，会附带很多额外的信息（各种校验信息，header，继承体系…），不便于在网络中高效传输；所以，hadoop自己开发了一套序列化机制（Writable），精简，高效。不用像java对象类一样传输多层的父子关系，需要哪个属性就传输哪个属性值，大大的减少网络传输的开销。 Writable是Hadoop的序列化格式，hadoop定义了这样一个Writable接口。 一个类要支持可序列化只需实现这个接口即可。

**Writable序列化接口**

如需要将自定义的bean放在key中传输，则还需要实现comparable接口，因为mapreduce框中的shuffle过程一定会对key进行排序,此时，自定义的bean实现的接口应该是：

```
public  class  FlowBean  implements  WritableComparable<FlowBean>{<!-- -->
	/**
	 * 反序列化的方法，反序列化时，从流中读取到的各个字段的顺序应该与序列化时写出去的顺序保持一致
	 */
     private long upFlow;
    private long downFlow;
    private long sumFlow;
    
    //这里反序列的时候会用到
    public FlowBean() {<!-- -->
    }
	 /这里是序列化方法
    @Override
    public void write(DataOutput out) throws IOException {<!-- -->
          out.writeLong(upFlow);
          out.writeLong(downFlow);
          out.writeLong(sumFlow);
    }
    /这里是反序列化方法
    @Override
    public void readFields(DataInput in) throws IOException {<!-- -->
        //注意反序列化的顺序跟序列化的顺序一致
       this.upFlow = in.readLong();
       this.downFlow = in.readLong();
       this.sumFlow = in.readLong();
        
    }
    //这里进行bean的自定义比较大小
    @Override
    public int compareTo(FlowBean o) {<!-- -->
        //实现按照 sumflow 的大小倒序排序
        return this.sumFlow>o.getSumFlow()?-1:1;
    }
    }
    
}

```

compareTo方法用于将当前对象与方法的参数进行比较。 如果指定的数与参数相等返回0。

如果指定的数小于参数返回 -1。 如果指定的数大于参数返回 1。 例如：o1.compareTo(o2); 返回正数的话，当前对象（调用compareTo方法的对象o1）要排在比较对象（compareTo传参对象o2）后面，返回负数的话，放在前面

#### 10.45、MapReduce的combiner

每一个map都可能会产生大量的本地输出，Combiner的作用就是对map端的输出先做一次合并，以减少在map和reduce节点之间的数据传输量，以提高网络IO性能，是MapReduce的一种优化手段之一。

combiner是MR程序中Mapper和Reducer之外的一种组件

combiner组件的父类就是Reducer

combiner和reducer的区别在于运行的位置：
-  Combiner是在每一个maptask所在的节点运行 -  Reducer是接收全局所有Mapper的输出结果； 
combiner的意义就是对每一个maptask的输出进行局部汇总，以减小网络传输量

具体实现步骤：

1、自定义一个combiner继承Reducer，重写reduce方法

2、在job中设置：job.setCombinerClass(CustomCombiner.class)

combiner能够应用的前提是不能影响最终的业务逻辑，而且，combiner的输出kv应该跟reducer的输入kv类型要对应起来

## yarn

### 1、介绍yarn

​ 通用资源管理系统和调度平台，可为上层应用提供统一的资源管理和调度。可以把yarn理解为相当于一个分布式的操作系统平台，而mapreduce等运算程序则相当于运行于操作系统之上的应用程序，Yarn为这些程序提供运算所需的资源（内存、cpu）。

### 2、yarn的基本架构

<img src="https://img-blog.csdnimg.cn/21eb61bd55714de4a1fb295d7cf0f77f.png#pic_center" alt="在这里插入图片描述"/>

​ YARN是一个资源管理、任务调度的框架，主要包含三大模块：ResourceManager（RM）、NodeManager（NM）、ApplicationMaster（AM）。

​ **ResourceManager负责所有资源的监控、分配和管理；**

​ **ApplicationMaster负责每一个具体应用程序的调度和协调**；

​ **NodeManager负责每一个节点的维护。**

​ 对于所有的applications，RM拥有绝对的控制权和对资源的分配权。而每个AM则会和RM协商资源，同时和NodeManager通信来执行和监控task。

### 3、yarn三大组件

#### 3.1．ResourceManager
- ResourceManager负责整个集群的资源管理和分配，是一个全局的资源管理系统。- NodeManager以心跳的方式向ResourceManager汇报资源使用情况（目前主要是CPU和内存的使用情况）。RM只接受NM的资源回报信息，对于具体的资源处理则交给NM自己处理。- YARN Scheduler根据application的请求为其分配资源，不负责application job的监控、追踪、运行状态反馈、启动等工作。
#### 3.2． NodeManager
- NodeManager是每个节点上的资源和任务管理器，它是管理这台机器的代理，负责该节点程序的运行，以及该节点资源的管理和监控。YARN集群每个节点都运行一个NodeManager。- NodeManager定时向ResourceManager汇报本节点资源（CPU、内存）的使用情况和Container的运行状态。当ResourceManager宕机时NodeManager自动连接RM备用节点。- NodeManager接收并处理来自ApplicationMaster的Container启动、停止等各种请求。
#### 3.3． ApplicationMaster
- 用户提交的每个应用程序均包含一个ApplicationMaster，它可以运行在ResourceManager以外的机器上。- 负责与RM调度器协商以获取资源（用Container表示）。- 将得到的任务进一步分配给内部的任务(资源的二次分配)。- 与NM通信以启动/停止任务。- 监控所有任务运行状态，并在任务运行失败时重新为任务申请资源以重启任务。- 当前YARN自带了两个ApplicationMaster实现，一个是用于演示AM编写方法的实例程序DistributedShell，它可以申请一定数目的Container以并行运行一个Shell命令或者Shell脚本；另一个是运行MapReduce应用程序的AM—MRAppMaster。
注：RM只负责监控AM，并在AM运行失败时候启动它。RM不负责AM内部任务的容错，任务的容错由AM完成。

### 4、Yarn 调度器Scheduler

​ 在**Yarn中，负责给应用分配资源的就是Scheduler**。在Yarn中有三种调度器可以选择：FIFO Scheduler ，Capacity Scheduler，Fair Scheduler。

#### 4.1.FIFO Scheduler

**FIFO** Scheduler把应用按提交的顺序排成一个队列，这是一个**先进先出**队列，在进行资源分配的时候，先给队列中最头上的应用进行分配资源，待最头上的应用需求满足后再给下一个分配，以此类推。

<img src="https://img-blog.csdnimg.cn/304a0592401b49768b25a18ff37d6d18.png#pic_center" alt="在这里插入图片描述"/>

​ FIFO Scheduler是最简单也是最容易理解的调度器，也不需要任何配置，但它并不适用于共享集群。大的应用可能会占用所有集群资源，这就导致其它应用被阻塞。在共享集群中，更适合采用Capacity Scheduler或Fair Scheduler，这两个调度器都允许大任务和小任务在提交的同时获得一定的系统资源。

#### 4.2． Capacity Scheduler

​ Capacity 调度器允许多个组织共享整个集群，每个组织可以获得集群的一部分计算能力。通过为每个组织分配专门的队列，然后再为每个队列分配一定的集群资源，这样整个集群就可以通过设置多个队列的方式给多个组织提供服务了。除此之外，队列内部又可以垂直划分，这样一个组织内部的多个成员就可以共享这个队列资源了，在一个队列内部，资源的调度是采用的是先进先出(FIFO)策略。

<img src="https://img-blog.csdnimg.cn/cc742ce63a854293ba013ce9fcd0b2ac.png#pic_center" alt="在这里插入图片描述"/>

​ 容量调度器 Capacity Scheduler 最初是由 Yahoo 最初开发设计使得 Hadoop 应用能够被多用户使用，且最大化整个集群资源的吞吐量，现被 IBM BigInsights 和 Hortonworks HDP 所采用。

​ Capacity Scheduler 被设计为允许应用程序在一个可预见的和简单的方式共享集群资源，即"作业队列"。Capacity Scheduler 是根据租户的需要和要求把现有的资源分配给运行的应用程序。Capacity Scheduler 同时允许应用程序访问还没有被使用的资源，以确保队列之间共享其它队列被允许的使用资源。管理员可以控制每个队列的容量，Capacity Scheduler 负责把作业提交到队列中。

#### 4.3．Fair Scheduler

​ 在Fair调度器中，我们不需要预先占用一定的系统资源，Fair调度器会为所有运行的job动态的调整系统资源。如下图所示，当第一个大job提交时，只有这一个job在运行，此时它获得了所有集群资源；当第二个小任务提交后，Fair调度器会分配一半资源给这个小任务，让这两个任务公平的共享集群资源。

需要注意的是，在下图Fair调度器中，从第二个任务提交到获得资源会有一定的延迟，因为它需要等待第一个任务释放占用的Container。小任务执行完成之后也会释放自己占用的资源，大任务又获得了全部的系统资源。最终效果就是Fair调度器即得到了高的资源利用率又能保证小任务及时完成。

<img src="https://img-blog.csdnimg.cn/df1b81c8803947869d99ad049fffbfab.png#pic_center" alt="在这里插入图片描述"/>

​ 公平调度器 Fair Scheduler 最初是由 Facebook 开发设计使得 Hadoop 应用能够被多用户公平地共享整个集群资源，现被 Cloudera CDH 所采用。

Fair Scheduler 不需要保留集群的资源，因为它会动态在所有正在运行的作业之间平衡资源。

#### 4.4．YARN作业调度流程

1、客户端程序向ResourceManager提交应用并请求一个ApplicationMaster实例，ResourceManager在应答中给出一个applicationID以及有助于客户端请求资源的资源容量信息。

2、ResourceManager找到可以运行一个Container的NodeManager，并在这个Container中启动ApplicationMaster实例

Application Submission Context发出响应，其中包含有：ApplicationID，用户名，队列以及其他启动ApplicationMaster的信息，Container Launch Context（CLC）也会发给ResourceManager，CLC提供了资源的需求，作业文件，安全令牌以及在节点启动ApplicationMaster所需要的其他信息。   当ResourceManager接收到客户端提交的上下文，就会给ApplicationMaster调度一个可用的container（通常称为container0）。然后ResourceManager就会联系NodeManager启动ApplicationMaster，并建立ApplicationMaster的RPC端口和用于跟踪的URL，用来监控应用程序的状态。

3、ApplicationMaster向ResourceManager进行注册，注册之后客户端就可以查询ResourceManager获得自己ApplicationMaster的详细信息，以后就可以和自己的ApplicationMaster直接交互了。在注册响应中，ResourceManager会发送关于集群最大和最小容量信息，

4、在平常的操作过程中，ApplicationMaster根据resource-request协议向ResourceManager发送resource-request请求，ResourceManager会根据调度策略尽可能最优的为ApplicationMaster分配container资源，作为资源请求的应答发个ApplicationMaster

5、当Container被成功分配之后，ApplicationMaster通过向NodeManager发送container-launch-specification信息来启动Container， container-launch-specification信息包含了能够让Container和ApplicationMaster交流所需要的资料，一旦container启动成功之后，ApplicationMaster就可以检查他们的状态，Resourcemanager不在参与程序的执行，只处理调度和监控其他资源，Resourcemanager可以命令NodeManager杀死container，

6、应用程序的代码在启动的Container中运行，并把运行的进度、状态等信息通过application-specific协议发送给ApplicationMaster，随着作业的执行，ApplicationMaster将心跳和进度信息发给ResourceManager，在这些心跳信息中，ApplicationMaster还可以请求和释放一些container。

7、在应用程序运行期间，提交应用的客户端主动和ApplicationMaster交流获得应用的运行状态、进度更新等信息，交流的协议也是application-specific协议

8、一但应用程序执行完成并且所有相关工作也已经完成，ApplicationMaster向ResourceManager取消注册然后关闭，用到所有的Container也归还给系统，当container被杀死或者回收，Resourcemanager都会通知NodeManager聚合日志并清理container专用的文件。

### 5、hadoop为什么有yarn

Hadoop2.x较Hadoop1.x来说，变化非常大，主要主要体现在Hadoop2.x引入了“Yarn”这个核心部件。

hadoop1.x有两大部件，HDFS和MadpReduce，其中HDFS（Hadoop Distributed Files System）用于分布式存储文件，由一个NameNode和多个DateNode组成，便于集群中各机器从上面读取和写入文件（数据），MadpReduce由一个JobTracker和多个TaskTracker组成，两个核心任务，Map负责对数据块的分片，Reduce对Map进程出来的结果进行聚合，排序，写入到HDFS中。从上图中，对于hadoo1.x来说，HDFS和MapReduce是两个相互依赖的关系，而对于hadoop2.x，在HDFS和MapReduce之间增加了Yarn部件，这个暂且可以理解为是一个“管理平台”，MapReduce就变成了一个可插拔的“插件”，它不仅仅允许hadoo自身的部件使用，还允许其他的数据处理插件接入到hadoop的生态上，如spark。

**为什么要引入yarn**

首先，简单了解hadoop1.x的MapReduce工作原理：

为了更好的理解，我们就需要跟hadoop1.x比较：

我们看到JobTracker的功能被分散到各个进程中包括ResourceManager和NodeManager：

比如监控功能，分给了NodeManager，和Application Master。

ResourceManager里面又分为了两个组件：调度器及应用程序管理器。

也就是说Yarn重构后，JobTracker的功能，被分散到了各个进程中。同时由于这些进程可以被单独部署所以这样就大大减轻了单点故障，及压力。

同时我们还看到Yarn使用了Container，而hadoop1.x中使用了slot。slot存在的缺点比如只能map或则reduce用。Container则不存在这个问题。

---


Java·面试知识点

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

# **文章地址： **https://blog.csdn.net/qq_43061290/article/details/124841929