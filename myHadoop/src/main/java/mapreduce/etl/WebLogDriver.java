package mapreduce.etl;

import mapreduce.outputformat.LogDriver;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WebLogDriver {

    public static void main(String[] args) throws Exception {
        // 获取当前时间
        LocalDateTime currentTime = LocalDateTime.now();
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 将当前时间格式化为指定格式的字符串
        String formattedTime = currentTime.format(formatter);
        // 打印当前格式化后的时间
        System.out.println("Current time: " + formattedTime);
        // 输入输出路径需要根据自己电脑上实际的输入输出路径设置
        args = new String[]{"/Users/lixiaofeng/myGitProjects/myJava/myHadoop/src/main/java/mapreduce/etl/web.log", "/Users/lixiaofeng/myGitProjects/myJava/myHadoop/src/main/java/mapreduce/etl/output/"+formattedTime};

        // 1 获取job信息
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 2 加载jar包
        job.setJarByClass(LogDriver.class);

        // 3 关联map
        job.setMapperClass(WebLogMapper.class);

        // 4 设置最终输出类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        // 设置reducetask个数为0
        job.setNumReduceTasks(0);

        // 5 设置输入和输出路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 6 提交
        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : 1);
    }

}
