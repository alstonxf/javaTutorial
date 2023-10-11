package mapreduce;
import org.apache.hadoop.io.Writable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class testFlowBean implements Writable {

    private String province;
    private long phoneNumber;
    private long upCount;
    private long downCount;
    private long sumCount;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getUpCount() {
        return upCount;
    }

    public void setUpCount(long upCount) {
        this.upCount = upCount;
    }

    public long getDownCount() {
        return downCount;
    }

    public void setDownCount(long downCount) {
        this.downCount = downCount;
    }

    public long getSumCount() {
        return sumCount;
    }

    public void setSumCount(long sumCount) {
        this.sumCount = sumCount;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(province);
        dataOutput.writeLong(phoneNumber);
        dataOutput.writeLong(upCount);
        dataOutput.writeLong(downCount);
        dataOutput.writeLong(sumCount);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.province = dataInput.readUTF();
        this.phoneNumber = dataInput.readLong();
        this.upCount = dataInput.readLong();
        this.downCount = dataInput.readLong();
        this.sumCount = dataInput.readLong();

    }

    @Override
    public String toString() {
        return  "省份= " + province +
                ", 手机号=" + phoneNumber +
                ", 上行流量=" + upCount +
                ", 上行流量=" + downCount +
                ", 总流量=" + sumCount;
    }
}
