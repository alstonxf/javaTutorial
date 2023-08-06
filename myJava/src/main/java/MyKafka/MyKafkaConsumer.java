package MyKafka;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MyKafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyKafkaConsumer.class);

    public static void main(String[] args) {
        //配置消费者组参数
        Properties props = new Properties();
        //第 1 个属性 bootstrap.servers 指定了 Kafka 集群的连接字符串。它 的用途与在 KafkaProducer 中的用途是一样的，
        props.put("bootstrap.servers", "localhost:9092");
        //另外两个属性 key.deserializer 和 value.deserializer 与生产者的 serializer 定义也很类似，不过 它们不是使用指定的类把 Java 对象转成字节数组，而是使用指定的类把字 节数组转成 Java 对象。
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //第 4 个属性 group.id 不是必需的，不过我们现在姑且认为它是必需的。 它指定了 KafkaConsumer 属于哪一个消费者群组。创建不属于任何一个 群组的消费者也是可以的，只是这样做不太常见
        props.put("group.id", "MyConsumerGroup1");

        //创建消费者
        KafkaConsumer consumer = new KafkaConsumer(props);
        System.out.println(consumer.toString());

        //订阅topic
        consumer.subscribe(Collections.singletonList("test1"));
        //consumer.subscribe("test.*");//要订阅所有与 test 相关的主题，可以这样做：

        Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();
        int count = 0;
        try {
            //消费者实际上是一个长期运行的应用程序，它通过 持续轮询向 Kafka 请求数据。
            while (true) {
                //传给 poll() 方法的参数是一个超时时间，用于控制 poll() 方法的阻塞时间
                //poll() 方法返回一个记录列表。每条记录都包含了记录所属主题的信息、记录所在分区的信息、记录在分区里的偏移量，以及记录的键值对。 我们一般会遍历这个列表，逐条处理这些记录。
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("获取到kafka消息了:"+record.value());
//                    LOGGER.info("topic = %s, partition = %s, offset = %d, customer = %s, country = %s\n",
//                    record.topic(), record.partition(), record.offset(), record.key(), record.value());

                    //消息处理：把结果保存起来或者对已有的记录进行更新，处理过程也随之结束。
//                    int updatedCount = 1;
//                    if (custCountryMap.countainsValue(record.value())) {
//                        updatedCount = custCountryMap.get(record.value()) + 1;
//                    }
//                    custCountryMap.put(record.value(), updatedCount);
//                    JSONObject json = new JSONObject(custCountryMap);
//                    System.out.println(json.toString(4));

                    //第一种方法 手动提交偏移量 处理完当前批次的消息，在轮询更多的消息之前，调用 commitSync() 方法提交当前批次最新的偏移量。
                    //把 auto.commit.offset 设为 false，让应用程序决定何时提交偏移 量。使用 commitSync() 提交偏移量最简单也最可靠。这个 API 会提交 由 poll() 方法返回的最新偏移量，提交成功后马上返回，如果提交失败 就抛出异常。
                    try {
                        currentOffsets.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset()+1, "no metadata"));
                        if (count % 1000 == 0)
                            consumer.commitAsync(currentOffsets,null);

                        consumer.commitSync();
                    }
                    catch (CommitFailedException e) {
                        LOGGER.error("commit failed", e);
                    }
                }
                //第二种方法 异步提交
                //如果一切正常，我们使用 commitAsync() 方法来提交。这样速度更快，而且即使这次提交失败，下一次提交很可能会成功。
                consumer.commitAsync();

            }
        } catch (Exception e) {
            LOGGER.error("Unexpected error", e);
        }finally {
            try {
                //第一，第二种方法可以独立使用，也可以尝试同步和异步组合提交
                //第三种方法，把第一种方法放到这里，然后和第二种方法组合提交。
                consumer.commitSync();
            }finally{
                //在退出应用程序之前使用 close() 方法关闭消费者。网络连接和 socket 也会随之关闭，并立即触发一次再均衡，而不是等待群组协调器发 现它不再发送心跳并认定它已死亡，因为那样需要更长的时间，导致整个 群组在一段时间内无法读取消息。
                consumer.close();
            }
        }
    }
}
