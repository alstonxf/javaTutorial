package test.propertiesExercise;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class proper {
    public static void main(String[] args) throws IOException {

        //获取所有系统属性
        Properties pro2 = System.getProperties();
        System.out.println("all:"+pro2);
        //获取给定键名对应的系统属性
        System.out.println("1:"+pro2.getProperty("java.vm.specification.vendor"));
        System.out.println("2:"+pro2.getProperty("java.runtime.name"));

        //创建一个空属性映射
        Properties pro = new Properties();

        //添加属性
//        pro.setProperty("width","600.0");
//        pro.setProperty("filename","/home/cay/books/cj11/code/v1ch11/raven.html");

        //将属性保存到文件
//        FileOutputStream out = new FileOutputStream("program.propertirs");
//        pro.store(out,"-comment-");

        //从文件读取属性
        FileInputStream in = new FileInputStream("program.propertirs");
        pro.load(in);
        String filename = pro.getProperty("filename");
        System.out.println(filename);

        //手动在program2.propertirs添加了新属性，测试读取
        Properties pro3 = new Properties();
        FileInputStream in3 = new FileInputStream("program2.propertirs");
        pro3.load(in3);
        String test = pro3.getProperty("test");
        System.out.println("测试成功  "+test);

    }
}
