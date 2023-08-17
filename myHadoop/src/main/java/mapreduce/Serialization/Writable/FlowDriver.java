package mapreduce.Serialization.Writable;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FlowDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        // 创建一个配置对象
        Configuration conf = new Configuration();

        // 获取一个Job对象
        Job job = Job.getInstance(conf);

        // 关联本Driver类
        job.setJarByClass(FlowDriver.class);

        // 关联Mapper和Reducer
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);

        // 设置Map端输出KV类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);

        // 设置程序最终输出的KV类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        // 设置程序的输入输出路径
        FileInputFormat.setInputPaths(job, new Path("/Users/lixiaofeng/myGitProjects/myJava/myHadoop/src/test/java/mapreduce/Serialization/Writable/inputflow2.txt"));    // 设置输入路径
        // 获取当前时间
        LocalDateTime currentTime = LocalDateTime.now();
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 将当前时间格式化为指定格式的字符串
        String formattedTime = currentTime.format(formatter);
        // 打印当前格式化后的时间
        System.out.println("Current time: " + formattedTime);

        FileOutputFormat.setOutputPath(job, new Path("/Users/lixiaofeng/myGitProjects/myJava/myHadoop/src/main/java/mapreduce/Serialization/Writable/output/outputflow"+formattedTime));


        // 提交Job并等待任务完成
        boolean isSuccess = job.waitForCompletion(true);

        // 根据任务执行结果决定退出状态码
        System.exit(isSuccess ? 0 : 1);
    }
}
