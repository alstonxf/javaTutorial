package com.itheima.factory;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 降低耦合方法2：不直接new实例化，改用bean工厂。
 * 如果用spring管理，就不用这个了。
 */
public class BeanFactory {
    /**
     * 从bean.properties 读取参数，然后返回一个hashmap，key是properties的key，value是properties的value的实例
     */
    //定义一个Properties对象
    private static Properties props;

    //定义一个map，用于存放我们要创建的对象  ，即容器。
    private static Map<String, Object> beans;

    //使用静态代码块为Properties对象赋值
    static {
        try {
            //实例化对象
            props = new Properties();
            //获取properties文件流对象
            InputStream in = BeanFactory.class.getClassLoader().getResourceAsStream("com/bean.properties");
            props.load(in);
            //实例化容器
            beans = new HashMap<String, Object>();
            //取出配置文件中所有的key
            Enumeration keys = props.keys();
            //遍历枚举
            while(keys.hasMoreElements()){
                //取出每个key
                String key = keys.nextElement().toString();
                //根据key获取value
                String beanPath = props.getProperty(key);
                //反射创建对象
                Object value = Class.forName(beanPath).newInstance();
                //把key和value存入容器
                beans.put(key, value);
            }

        } catch (Exception e) {
            throw new ExceptionInInitializerError("初始化properties失败");
        }
    }

    /**
     * 根据 bean名称获取 bean单例对象
     */
    /*public static Object getBean(String beanName){
        Object bean = null;
        try{
            String beanPath = props.getProperty(beanName);
            bean = Class.forName(beanPath).newInstance();//反射方式创建对象
        }catch (Exception e){
            e.printStackTrace();
        }
        return bean;
    }*/
    public static Object getBean(String beanName){
        return beans.get(beanName);
    }
}

