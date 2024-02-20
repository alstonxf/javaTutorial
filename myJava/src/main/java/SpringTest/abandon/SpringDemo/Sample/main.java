package SpringTest.abandon.SpringDemo.Sample;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class main {
    /**
     * 基于xml的装配
     * 通过读取配置文件创建对象调用方法
     * @param args
     */
    public static void main(String[] args) {
//        1/*获取文件流*/
        ApplicationContext context = new ClassPathXmlApplicationContext("SpringTest/Sample/sample1.xml");

//        2.FileSystemXmlApplicationContext：文件 系统 文件类型 应用 上下文——磁盘绝对路径
//        它是从磁盘路径上加载配置文件，配置文件可以在磁盘的任何位置。
//        ApplicationContext context2 = new FileSystemXmlApplicationContext("/Users/lixiaofeng/Library/Mobile Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava/src/main/resources/sample1.xml");


        //获取Student实例
        Student student2 = (Student) context.getBean("student2");
        //调用方法
        System.out.println("开始调用方法student2.studentInfo()");
        student2.studentInfo();
        System.out.println("student2.habit"+student2.habit);
        System.out.println("student2.age"+student2.age);

        System.out.println("**********************************************");
        //获取Collection实例
        Collection collect1=(Collection) context.getBean("myCollect");
        //调用方法
        String[] myArray = collect1.getmyArray();
        for (String i: myArray) {
            System.out.println("myArray:"+i);
        }
        //调用方法
        List<String> myList = collect1.getmyList();
        for (String i: myList) {
            System.out.println("myList:"+i);
        }
        //调用方法
        Map<String,Object> myMap = collect1.getmyMap();
        for (String i: myMap.keySet()) {
            System.out.println("myMap:"+i+" "+myMap.get(i));
        }
        //调用方法
        Properties myProps = collect1.getMyProps();
        for (String i: myProps.stringPropertyNames()) {
            System.out.println("myProps:"+i);
        }
    }
}
