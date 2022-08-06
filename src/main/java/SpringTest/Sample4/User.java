package SpringTest.Sample4;

import java.util.*;

/**
 * 用户实体类
 */
public class User {
    private int userId;
    private String userName;
    private int userAge;
    private String userPwd;
    private String userAddress;
    //女朋友
    private GirlFriend girlFriend;

    private List<GirlFriend> lists;
    private Set<GirlFriend> sets;
    private Map<String, GirlFriend> maps;
    private Properties properties;
    private String[] array;




    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public GirlFriend getGirlFriend() {
        return girlFriend;
    }

    public void setGirlFriend(GirlFriend girlFriend) {
        this.girlFriend = girlFriend;
    }

    public List<GirlFriend> getLists() {
        return lists;
    }

    public void setLists(List<GirlFriend> lists) {
        this.lists = lists;
    }

    public Set<GirlFriend> getSets() {
        return sets;
    }

    public void setSets(Set<GirlFriend> sets) {
        this.sets = sets;
    }

    public Map<String, GirlFriend> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, GirlFriend> maps) {
        this.maps = maps;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String[] getArray() {
        return array;
    }

    public void setArray(String[] array) {
        this.array = array;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userAge=" + userAge +
                ", userPwd='" + userPwd + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", girlFriend=" + girlFriend +
                ", lists=" + lists +
                ", sets=" + sets +
                ", maps=" + maps +
                ", properties=" + properties +
                ", array=" + Arrays.toString(array) +
                '}';
    }
}