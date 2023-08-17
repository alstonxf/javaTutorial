package mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;

import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class testFlowDriver {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        Configuration entries = new Configuration();
        Job job = Job.getInstance(entries);

        job.setJarByClass(testFlowDriver.class);
        job.setMapperClass(testFlowMapper.class);
        job.setReducerClass(testReducer.class);

        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(testFlowBean.class);

        job.setOutputKeyClass(LongWritable.class);
        job.setOutputValueClass(testFlowBean.class);

        //默认使用TextInputFormat 类读取数据
//        job.setInputFormatClass(TextInputFormat.class);
        //改用combine
        job.setInputFormatClass(CombineTextInputFormat.class);
        // 设置分片的最大大小，影响虚拟内存的使用 1 MB（兆字节）等于 1024 KB（千字节），而每个 KB 等于 1024 字节（byte）。
        CombineFileInputFormat.setMaxInputSplitSize(job,1024*1024*4);//字节

        //指定reducer个数
        job.setNumReduceTasks(3);

        //使用自定义分区
        job.setPartitionerClass(myPartitioner.class);

        FileInputFormat.setInputPaths(job,new Path("/Users/lixiaofeng/myGitProjects/myJava/myHadoop/src/test/java/mapreduce/inputflow2.txt"));
        // 获取当前时间
        LocalDateTime currentTime = LocalDateTime.now();
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 将当前时间格式化为指定格式的字符串
        String formattedTime = currentTime.format(formatter);
        // 打印当前格式化后的时间
        System.out.println("Current time: " + formattedTime);

        FileOutputFormat.setOutputPath(job,new Path("/Users/lixiaofeng/myGitProjects/myJava/myHadoop/src/test/java/mapreduce/outputflow/"+ formattedTime));

        boolean b = job.waitForCompletion(true);
        System.exit(b?0:1);
    }

}
