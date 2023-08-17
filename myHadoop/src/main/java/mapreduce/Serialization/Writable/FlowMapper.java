package mapreduce.Serialization.Writable;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

// Mapper类，将输入数据转换为键值对
public class FlowMapper extends Mapper<LongWritable, Text, Text, FlowBean> {

    private Text outK = new Text();     // 输出键
    private FlowBean outV = new FlowBean(); // 输出值

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        // 获取一行数据，例如：1\t13736230513\t192.196.100.1\twww.atguigu.com\t2481\t24681\t200
        String line = value.toString();

        // 切割数据，使用制表符进行分隔
        String[] split = line.split("\t");

        // 提取所需数据
        String phone = split[1];        // 手机号
        String up = split[split.length - 3]; // 上行流量
        String down = split[split.length - 2]; // 下行流量

        // 封装输出键和值
        outK.set(phone);                 // 设置输出键为手机号
        outV.setUpFlow(Long.parseLong(up));     // 设置上行流量
        outV.setDownFlow(Long.parseLong(down)); // 设置下行流量
        outV.setSumFlow();              // 计算总流量

        // 写出键值对，键是手机号，值是封装了流量信息的FlowBean对象
        context.write(outK, outV);
    }
}
