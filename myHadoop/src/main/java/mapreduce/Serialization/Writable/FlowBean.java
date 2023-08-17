package mapreduce.Serialization.Writable;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 1、定义类实现writable接口
 * 2、重写序列化和反序列化方法
 * 3、重写空参构造
 * 4、toString方法
 */
// 实现Writable接口，使得对象可以在MapReduce中序列化和反序列化传递
//    （1）必须实现Writable接口
public class FlowBean implements Writable {

    private long upFlow; // 上行流量
    private long downFlow; // 下行流量
    private long sumFlow; // 总流量

//    （2）反序列化时，需要反射调用空参构造函数，所以必须有空参构造
    // 无参构造函数
//    public FlowBean() {
//    }

    // 获取上行流量
    public long getUpFlow() {
        return upFlow;
    }

    // 设置上行流量
    public void setUpFlow(long upFlow) {
        this.upFlow = upFlow;
    }

    // 获取下行流量
    public long getDownFlow() {
        return downFlow;
    }

    // 设置下行流量
    public void setDownFlow(long downFlow) {
        this.downFlow = downFlow;
    }

    // 获取总流量
    public long getSumFlow() {
        return sumFlow;
    }

    // 设置总流量
    public void setSumFlow(long sumFlow) {
        this.sumFlow = sumFlow;
    }

    // 根据上行和下行流量计算总流量
    public void setSumFlow() {
        this.sumFlow = this.upFlow + this.downFlow;
    }

    // 将对象序列化到输出流
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(upFlow);
        dataOutput.writeLong(downFlow);
        dataOutput.writeLong(sumFlow);
    }

    // 从输入流反序列化对象
    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.upFlow = dataInput.readLong();
        this.downFlow = dataInput.readLong();
        this.sumFlow = dataInput.readLong();
    }

    // 转换对象为字符串，以便在输出中显示
    @Override
    public String toString() {
        return "上行流量:"+upFlow + "\t" + "下行流量:"+downFlow + "\t" + "总流量:"+sumFlow;
    }
}