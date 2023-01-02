package MyKafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.KafkaProducer;
import java.util.Properties;

public class MyKafkaProducer {

    public static void main(String[] args) {
        //参数设置
        Properties kafkaProps = new Properties();
//        kafkaProps.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        kafkaProps.put("bootstrap.servers", "localhost:9092");
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer(kafkaProps);


        //创建消息
        ProducerRecord<String, String> record = new ProducerRecord<>("test1", "Precision Products", "France");

        // 简单的发送消息
        try {
            producer.send(record);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 同步发送消息
        try {
            //producer.send() 方法先返回一个 Future 对象，然后调用 Future 对象的 get() 方法等待 Kafka 响应。如果服务器返回错误，get() 方法会抛出异常。如果没有发生错误，我们会得到一个 RecordMetadata 对象，可以用它获取消息的偏移量。
            producer.send(record).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 异步发送消息
        class DemoProducerCallback implements Callback {

            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e != null) { e.printStackTrace();
                }
            }
        }
        producer.send(record, new DemoProducerCallback());

        System.out.println("发送成功");
    }
}
