package mapreduce.Serialization.Writable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

// Reducer类，将相同键的数据进行聚合和计算
public class FlowReducer extends Reducer<Text, FlowBean, Text, FlowBean> {
    private FlowBean outV = new FlowBean(); // 输出值，包含流量信息

    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {

        // 遍历输入值集合，累加上行流量和下行流量
        long totalUp = 0;
        long totalDown = 0;

        for (FlowBean value : values) {
            totalUp += value.getUpFlow();     // 累加上行流量
            totalDown += value.getDownFlow(); // 累加下行流量
        }

        // 封装输出值
        outV.setUpFlow(totalUp);         // 设置上行流量
        outV.setDownFlow(totalDown);     // 设置下行流量
        outV.setSumFlow();               // 计算总流量

        // 写出键值对，键是手机号，值是包含合并后流量信息的FlowBean对象
        context.write(key, outV);
    }
}
