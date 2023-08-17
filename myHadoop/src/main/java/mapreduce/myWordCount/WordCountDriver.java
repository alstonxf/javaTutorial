package mapreduce.myWordCount;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WordCountDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        // 1 获取配置信息，获取job对象实例
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 2 指定本程序的jar包所在的本地路径
        job.setJarByClass(WordCountDriver.class);

        // 3 关联mapper和reducer业务类
        job.setMapperClass(myMapper.class);
        job.setReducerClass(myReducer.class);

        // 4 设置map输出的kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 5 设置最终输出的kV类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // 6 设置输入路径和输出路径
        String inputPath = null;
        if (args.length == 0){
            inputPath = "/Users/lixiaofeng/myGitProjects/myJava/myHadoop/src/main/java/mapreduce/myWordCount/inputword";
        }else{
            inputPath = args[0];
        }

        FileInputFormat.setInputPaths(job, new Path(inputPath));

        // 获取当前时间
        LocalDateTime currentTime = LocalDateTime.now();
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 将当前时间格式化为指定格式的字符串
        String formattedTime = currentTime.format(formatter);
        // 打印当前格式化后的时间
        System.out.println("Current time: " + formattedTime);
        String outputPath = null;
        if (args.length == 0){
            outputPath = "/Users/lixiaofeng/myGitProjects/myJava/myHadoop/src/main/java/mapreduce/myWordCount/outputword/"+formattedTime;
        }else{
            outputPath = args[1];
        }

        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        // 7 提交job
        boolean result = job.waitForCompletion(true); //参数 true 是一个布尔值，用来指定是否要打印作业的详细信息和进度信息。

//        if (result) {
//            // 获取Map任务的数量（切片的数量）
//    //        int sliceCount = job.getNumMapTasks();//在Hadoop 2.x及之后的版本中废弃了这个方法
//            // 获取作业计数器
//            Counters counters = job.getCounters();
//            // 获取Map任务的数量（切片的数量）
//            long mapTaskCount = counters.findCounter("org.apache.hadoop.mapred.Task$Counter", "MAP_TASKS").getValue();
//            System.out.println("Number of slices (Map tasks): " + mapTaskCount);
//        } else {
//            System.out.println("Job failed or was not successful.");
//        }

        System.out.println("result="+result);
        System.exit(result ? 0 : 1);
    }
}
