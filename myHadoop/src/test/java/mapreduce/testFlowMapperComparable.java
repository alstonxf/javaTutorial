package mapreduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class testFlowMapperComparable extends Mapper<LongWritable, Text, testFlowBeanComparable,LongWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
//        1	13736230513	192.196.100.1	www.atguigu.com	2481	24681	200
//        2	13846544121	192.196.100.2			264	0	200
        System.out.println("******************map***********"+value);
        testFlowBeanComparable testFlowBeanComparable = new testFlowBeanComparable();
        String[] s = value.toString().split("\t");
//        System.out.println(s.toString());
        testFlowBeanComparable.setPhoneNumber(Long.parseLong(s[1]));
        testFlowBeanComparable.setDownCount(Long.parseLong(s[s.length-3]));
        testFlowBeanComparable.setUpCount(Long.parseLong(s[s.length-2]));
        testFlowBeanComparable.setSumCount(Long.parseLong(s[s.length-1]));
        System.out.println(testFlowBeanComparable);

        context.write(testFlowBeanComparable,new LongWritable(Long.parseLong(s[1])));
    }
    
}
