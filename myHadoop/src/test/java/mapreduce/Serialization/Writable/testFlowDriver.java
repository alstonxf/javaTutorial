package mapreduce.Serialization.Writable;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
//import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

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

        FileInputFormat.setInputPaths(job,new Path("/Users/lixiaofeng/myGitProjects/myJava/myHadoop/src/test/java/mapreduce/Serialization/Writable/inputflow2.txt"));
        // 获取当前时间
        LocalDateTime currentTime = LocalDateTime.now();
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 将当前时间格式化为指定格式的字符串
        String formattedTime = currentTime.format(formatter);
        // 打印当前格式化后的时间
        System.out.println("Current time: " + formattedTime);

        FileOutputFormat.setOutputPath(job,new Path("/Users/lixiaofeng/myGitProjects/myJava/myHadoop/src/test/java/mapreduce/Serialization/Writable/outputflow/"+ formattedTime));

        boolean b = job.waitForCompletion(true);
        System.exit(b?0:1);
    }

}
