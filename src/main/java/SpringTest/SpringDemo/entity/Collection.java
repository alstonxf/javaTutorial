package SpringTest.SpringDemo.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Collection {

    public String[] arr;

    public List<String> list;

    public Map<String,Object> map;

    public Properties props;

    public String[] getArr() {
        return arr;
    }

    public void setArr(String[] arr) {
        this.arr = arr;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public Properties getProps() {
        return props;
    }

    public void setProps(Properties props) {
        this.props = props;
    }

    @Override
    public String toString() {
        return "Collection [arr=" + Arrays.toString(arr) + ", list=" + list + ", map=" + map + ", props=" + props + "]";
    }

}