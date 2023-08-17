package mapreduce;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class testFlowMapper extends Mapper<LongWritable, Text, LongWritable, testFlowBean> {

    @Override
    protected void setup(Mapper<LongWritable, Text, LongWritable, testFlowBean>.Context context) throws IOException, InterruptedException {
        System.out.println("map初始化");
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
//        1	13736230513	192.196.100.1	www.atguigu.com	2481	24681	200
//        2	13846544121	192.196.100.2			264	0	200
        System.out.println("******************map***********"+value);
        testFlowBean testFlowBean = new testFlowBean();
        String[] s = value.toString().split("\t");
//        System.out.println(s.toString());
        testFlowBean.setPhoneNumber(Long.parseLong(s[1]));
        testFlowBean.setDownCount(Long.parseLong(s[s.length-3]));
        testFlowBean.setUpCount(Long.parseLong(s[s.length-2]));
        testFlowBean.setSumCount(Long.parseLong(s[s.length-1]));
        System.out.println(testFlowBean);
        context.write(new LongWritable(Long.parseLong(s[1])),testFlowBean);
    }

    @Override
    protected void cleanup(Mapper<LongWritable, Text, LongWritable, testFlowBean>.Context context) throws IOException, InterruptedException {
        System.out.println("map结束cleanup");
    }


    @Override
    public void run(Mapper<LongWritable, Text, LongWritable, testFlowBean>.Context context) throws IOException, InterruptedException {
        this.setup(context);

        try {
            while(context.nextKeyValue()) {
                LongWritable currentKey = context.getCurrentKey();
                Text currentValue = context.getCurrentValue();
//                System.out.println(currentKey);
//                System.out.println("map======================================"+currentValue);
                this.map(context.getCurrentKey(), context.getCurrentValue(), context);
            }
        } finally {
            this.cleanup(context);
        }
    }
}
