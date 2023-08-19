package mapreduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Partitioner;


//Partitioner<testFlowBeanComparable,LongWritable> 与map方法输出类型保持一致
public class myPartitionerComparable extends Partitioner<testFlowBeanComparable,LongWritable> {

    @Override
    public int getPartition(testFlowBeanComparable testFlowBeanComparable, LongWritable longWritable, int i) {
        if (longWritable.get()<100000000){
            return 0;
        }else {
            return 1;
        }
    }
}
