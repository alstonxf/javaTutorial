package SpringTest.Sample;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Collection {

    public String[] myArray;

    public List<String> myList;

    public Map<String,Object> myMap;

    public Properties myProps;

    public String[] getMyArray() {
        return myArray;
    }

    public void setMyArray(String[] myArray) {
        this.myArray = myArray;
    }

    public List<String> getMyList() {
        return myList;
    }

    public void setMyList(List<String> myList) {
        this.myList = myList;
    }

    public Map<String, Object> getMyMap() {
        return myMap;
    }

    public void setMyMap(Map<String, Object> myMap) {
        this.myMap = myMap;
    }

    public Properties getMyProps() {
        return myProps;
    }

    public void setMyProps(Properties myProps) {
        this.myProps = myProps;
    }

    public String[] getmyArray() {
        return myArray;
    }

    public void setmyArray(String[] myArray) {
        this.myArray = myArray;
    }

    public List<String> getmyList() {
        return myList;
    }

    public void setmyList(List<String> myList) {
        this.myList = myList;
    }

    public Map<String, Object> getmyMap() {
        return myMap;
    }

    public void setmyMap(Map<String, Object> myMap) {
        this.myMap = myMap;
    }

    public Properties getmyProps() {
        return myProps;
    }

    public void setmyProps(Properties myProps) {
        this.myProps = myProps;
    }

    @Override
    public String toString() {
        return "Collection [myArray=" + Arrays.toString(myArray) + ", myList=" + myList + ", myMap=" + myMap + ", myProps=" + myProps + "]";
    }

}