package mapreduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Partitioner;


//Partitioner<LongWritable, testFlowBean> 与map方法输出类型保持一致
public class myPartitioner extends Partitioner<LongWritable, testFlowBean> {

    @Override
    public int getPartition(LongWritable longWritable, testFlowBean testFlowBean, int i) {
        if (longWritable.get()<100000000){
            return 0;
        }else {
            return 1;
        }
    }
}
