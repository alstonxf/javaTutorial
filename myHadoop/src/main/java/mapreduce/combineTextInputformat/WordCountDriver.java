package mapreduce.combineTextInputformat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WordCountDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        // 1 获取job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 2 设置jar包路径
        job.setJarByClass(WordCountDriver.class);

        // 3 关联mapper和reducer
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        // 4 设置map输出的kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 5 设置最终输出的kV类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // 如果不设置InputFormat，它默认用的是TextInputFormat.class
//        job.setInputFormatClass(TextInputFormat.class); //Shuffled Maps =4
        job.setInputFormatClass(CombineTextInputFormat.class);

        //虚拟存储切片最大值设置4m
//        CombineTextInputFormat.setMaxInputSplitSize(job, 1048576*3); //Shuffled Maps =3
        //虚拟存储切片最大值设置4m
        CombineTextInputFormat.setMaxInputSplitSize(job, 1048576*4); //Shuffled Maps =3
        //虚拟存储切片最大值设置20m
//        CombineTextInputFormat.setMaxInputSplitSize(job, 1048576*20); //Shuffled Maps =1


        // 6 设置输入路径和输出路径
        FileInputFormat.setInputPaths(job, new Path("/Users/lixiaofeng/myGitProjects/myJava/myHadoop/src/main/java/mapreduce/combineTextInputformat/input2"));
        // 获取当前时间
        LocalDateTime currentTime = LocalDateTime.now();
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 将当前时间格式化为指定格式的字符串
        String formattedTime = currentTime.format(formatter);
        // 打印当前格式化后的时间
        System.out.println("Current time: " + formattedTime);
        FileOutputFormat.setOutputPath(job, new Path("/Users/lixiaofeng/myGitProjects/myJava/myHadoop/src/main/java/mapreduce/combineTextInputformat/output/"+formattedTime));

        // 从日志查看切片数量
//        您提供的信息看起来像是一个MapReduce作业的执行日志中的部分内容，其中包含了各种不同的计数器（Counters）和统计信息，以及作业的执行结果。
//
//        这些计数器提供了有关作业执行过程中各种操作的信息，例如文件读写、记录数、内存使用等。以下是其中一些计数器的含义：
//
//        Map input records: Map任务处理的输入记录数。
//        Map output records: Map任务产生的输出记录数。
//        Map output bytes: Map任务产生的输出字节数。
//        Reduce input groups: Reduce任务的输入键值对的分组数。
//        Reduce shuffle bytes: Reduce任务的Shuffle阶段传输的字节数。
//        Reduce input records: Reduce任务的输入记录数。
//        Reduce output records: Reduce任务产生的输出记录数。
//        Spilled Records: 数据溢出到磁盘的记录数，通常表示内存不足，数据需要写入磁盘。
//        Shuffled Maps: 发送数据到每个Reduce任务的Map任务数量。
//        GC time elapsed (ms): 垃圾回收所花费的时间，以毫秒为单位。
//        Total committed heap usage (bytes): JVM堆内存的总使用量。
//        此外，还有一些文件输入和输出的统计信息，例如输入字节数、输出字节数等。

        // 7 提交job
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
