package mapreduce.myWordCount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
/**
 * KEYIN, reduce阶段输入的key的类型：Text
 * VALUEIN,reduce阶段输入value类型：IntWritable
 * KEYOUT,reduce阶段输出的Key类型：Text
 * VALUEOUT,reduce阶段输出的value类型：IntWritable
 */
public class myReducer extends Reducer<Text, IntWritable, Text, IntWritable>{

    // * KEYIN, reduce阶段输入的key的类型：Text
    // * VALUEIN,reduce阶段输入value类型：IntWritable
    // Context 上下文
    // 注意： 每个不同的key调用一次。
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        while (values.iterator().hasNext()){
            sum += values.iterator().next().get();
        }
        context.write(key,new IntWritable(sum));
    }
}

