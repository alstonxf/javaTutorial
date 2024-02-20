package jvm;

public class ClassLoaderApp {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        PKClassLoader classLoader = new PKClassLoader("/Users/lixiaofeng/","PK");
        Class clazz = classLoader.loadClass("PK");
        System.out.println(clazz.getClassLoader());
        System.out.println(clazz.getClassLoader().getParent());
        System.out.println(clazz.getClassLoader().getParent().getParent());
        System.out.println(clazz.getClassLoader().getParent().getParent().getParent());
        clazz.newInstance();

        Class<?> aClass = Class.forName("jvm.PK2");
        System.out.println("在class.forName后就会执行类变量赋值和执行static代码块");

        // 创建实例
        PK2 instance = (PK2) aClass.newInstance();

        // 输出实例变量的值
        System.out.println(instance.test);

    }
}
