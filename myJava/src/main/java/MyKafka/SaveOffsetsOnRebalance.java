//package MyKafka;
//
//
//import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
//import org.apache.kafka.common.TopicPartition;
//
//import java.util.Collection;
//
//public class SaveOffsetsOnRebalance implements ConsumerRebalanceListener {
//
//    @Override
//    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
//        commitDBTransaction();
//
//    }
//
//    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
//        for(TopicPartition partition: partitions)
//            consumer.seek(partition, getOffsetFromDB(partition));
//    }
//
//}
//
//
//
