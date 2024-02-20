package SpringTest.abandon.SpringDemo.Sample4;

/**
 * GirlFriend实体
 */
public class GirlFriend {
    private String girlName;
    private int girlAge;
    private String girlHeight;
    //getter、setter、toString方法省略......




    public String getGirlName() {
        return girlName;
    }

    public void setGirlName(String girlName) {
        this.girlName = girlName;
    }

    public int getGirlAge() {
        return girlAge;
    }

    public void setGirlAge(int girlAge) {
        this.girlAge = girlAge;
    }

    public String getGirlHeight() {
        return girlHeight;
    }

    public void setGirlHeight(String girlHeight) {
        this.girlHeight = girlHeight;
    }

    @Override
    public String toString() {
        return "GirlFriend{" +
                "girlName='" + girlName + '\'' +
                ", girlAge=" + girlAge +
                ", girlHeight='" + girlHeight + '\'' +
                '}';
    }
}