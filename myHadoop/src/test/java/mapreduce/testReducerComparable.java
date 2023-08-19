package mapreduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class testReducerComparable extends Reducer<testFlowBeanComparable,LongWritable, LongWritable, testFlowBeanComparable> {
    @Override
    protected void reduce(testFlowBeanComparable key, Iterable<LongWritable> values, Reducer<testFlowBeanComparable, LongWritable, LongWritable, testFlowBeanComparable>.Context context) throws IOException, InterruptedException {
        //values 是电话号码
        for (LongWritable value : values) {
            context.write(value,key);
        }
    }
}
