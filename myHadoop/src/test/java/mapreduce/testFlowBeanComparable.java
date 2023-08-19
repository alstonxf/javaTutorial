package mapreduce;

import org.apache.hadoop.io.WritableComparable;

public class testFlowBeanComparable extends testFlowBean implements WritableComparable<testFlowBeanComparable> {
    //按以下顺序比较，总流量，上行流量，下行流量
    @Override
    public int compareTo(testFlowBeanComparable o) {
        if (this.getSumCount()>o.getSumCount()){
            return 1;
        } else if (this.getSumCount()<o.getSumCount()) {
            return -1;
        }else{
            if (this.getUpCount()>o.getUpCount()){
                return 1;
            } else if (this.getUpCount()<o.getUpCount()) {
                return -1;
            }else{
                if (this.getDownCount()>o.getDownCount()){
                    return 1;
                } else if (this.getDownCount()<o.getDownCount()) {
                    return -1;
                }else{
                    return 0;
                }
            }
        }
//
//        return (this.getSumCount()>o.getSumCount()? 1:(this.getSumCount()==o.getSumCount()?0:-1));



    }
}
