package MyKafka.myProducer;

import MyKafka.MyKafkaConsumer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;


public class CustomProducer {

    public static void main(String[] args) {
        final Logger LOGGER = LoggerFactory.getLogger(CustomProducer.class);

//         0 配置
        Properties properties = new Properties();

//         连接集群 bootstrap.servers
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
//         指定对应的key和value的序列化类型 key.serializer
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

//         1 创建kafka生产者对象
//         "" hello
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);


        // 2 发送数据
        for (int i = 0; i < 5; i++) {
            kafkaProducer.send(new ProducerRecord<>("first","Precision Products","atguigu"+i));
        }

        // 3 关闭资源
        kafkaProducer.close();
    }
}
