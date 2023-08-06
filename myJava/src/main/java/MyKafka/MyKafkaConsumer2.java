package MyKafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MyKafkaConsumer2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyKafkaConsumer2.class);

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


        Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();
        int count = 0;

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("获取到kafka消息了:" + record.value());

                    //提交特定偏移量
                    currentOffsets.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1, "no metadata"));
                    if (count % 1000 == 0){
                        //每处理 1000 条记录就提交一次偏移量
                        consumer.commitAsync(currentOffsets, null);
                        count++;
                    }
                }
            }
        }catch (CommitFailedException e) {
            LOGGER.error("commit failed", e);
        }
    }
}
