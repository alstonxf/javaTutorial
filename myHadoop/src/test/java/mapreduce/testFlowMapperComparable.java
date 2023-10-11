package mapreduce;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class testFlowMapperComparable extends Mapper<LongWritable, Text, testFlowBeanComparable,LongWritable> {
    private HashMap<String, String> pdMap = new HashMap<>();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        // 初始化  order  pd
//        FileSplit split = (FileSplit) context.getInputSplit();
//        String fileName = split.getPath().getName();
//        System.out.println("读取的文件名称为："+fileName);

        // 获取缓存的文件，并把文件内容封装到集合 pd.txt
        URI[] cacheFiles = context.getCacheFiles();
        FileSystem fs = FileSystem.get(context.getConfiguration());
        FSDataInputStream fis = fs.open(new Path(cacheFiles[0]));

        // 从流中读取数据
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));

        String line;
        while (StringUtils.isNotEmpty(line = reader.readLine())) {
            // 切割
            String[] fields = line.split(" ");
            System.out.println(Arrays.toString(fields));
            // 赋值
            pdMap.put(fields[0], fields[fields.length-1]);
        }

        for (Map.Entry<String, String> stringStringEntry : pdMap.entrySet()) {
            System.out.println(stringStringEntry.getKey()+" "+stringStringEntry.getValue());
        }
        // 关流
        IOUtils.closeStream(reader);
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
//        1	13736230513	192.196.100.1	www.atguigu.com	2481	24681	200
//        2	13846544121	192.196.100.2			264	0	200
        System.out.println("******************map***********"+value);
        testFlowBeanComparable testFlowBeanComparable = new testFlowBeanComparable();
        String[] s = value.toString().split("\t");
//        System.out.println(s.toString());
        testFlowBeanComparable.setPhoneNumber(Long.parseLong(s[1]));
        testFlowBeanComparable.setDownCount(Long.parseLong(s[s.length-3]));
        testFlowBeanComparable.setUpCount(Long.parseLong(s[s.length-2]));
        testFlowBeanComparable.setSumCount(Long.parseLong(s[s.length-1]));
        //从加载的字典表查到手机号前三位对应的省份名称
        String phoneNum3 = s[1].substring(0, 3);
        System.out.println(s[1]+"的前三位是"+phoneNum3);
        testFlowBeanComparable.setProvince(pdMap.getOrDefault(phoneNum3,"未在字典表找到对应省份"));
        System.out.println(testFlowBeanComparable);

        context.write(testFlowBeanComparable,new LongWritable(Long.parseLong(s[1])));
    }

    @Override
    public void run(Mapper<LongWritable, Text, testFlowBeanComparable, LongWritable>.Context context) throws IOException, InterruptedException {
        this.setup(context);
        try {
            while(context.nextKeyValue()) {
                LongWritable currentKey = context.getCurrentKey();
                Text currentValue = context.getCurrentValue();
                System.out.println(currentKey+"\t"+currentValue);
                this.map(context.getCurrentKey(), context.getCurrentValue(), context);
            }
        } finally {
            this.cleanup(context);
        }
    }
}
