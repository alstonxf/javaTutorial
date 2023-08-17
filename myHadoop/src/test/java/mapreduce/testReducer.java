package mapreduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class testReducer extends Reducer<LongWritable, testFlowBean, LongWritable, testFlowBean> {
    testFlowBean testFlowBean = new testFlowBean();

    @Override
    protected void setup(Reducer<LongWritable, testFlowBean, LongWritable, testFlowBean>.Context context) throws IOException, InterruptedException {
        System.out.println("reducer初始化");
    }

    @Override
    protected void reduce(LongWritable key, Iterable<testFlowBean> values, Reducer<LongWritable, testFlowBean, LongWritable, testFlowBean>.Context context) throws IOException, InterruptedException {
        long upCount = 0;
        long downCount = 0;
        long sumCount = 0;
//        for (mapreduce.Serialization.Writable.testFlowBean value : values) {
//            System.out.println("-------------------------------------------"+value.toString());
//        }
        for (testFlowBean value : values) {
            upCount += value.getUpCount();
            downCount += value.getDownCount();
            sumCount += value.getSumCount();
        }
        testFlowBean.setPhoneNumber(new Long(key.get()));
        testFlowBean.setDownCount(downCount);
        testFlowBean.setUpCount(upCount);
        testFlowBean.setSumCount(sumCount);
        context.write(key,testFlowBean);
    }

    @Override
    protected void cleanup(Reducer<LongWritable, testFlowBean, LongWritable, testFlowBean>.Context context) throws IOException, InterruptedException {
        System.out.println("reducer结束cleanup");
    }
}
