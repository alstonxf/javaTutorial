package jvm;
import java.io.*;

/**
 * 自定义ClassLoader:使用自定义的ClassLoader到指定的目录下面的文件中加载。
 */
public class PKClassLoader extends ClassLoader{
    private String path;
    private String classLoaderName;
    public PKClassLoader(){
    }
    public PKClassLoader(String path,String classLoaderName){
        this.path = path;
        this.classLoaderName = classLoaderName;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
//        return super.findClass(name);
        //替换super的方法，自定义一个
        byte[] b = loadClassData(name);
        return defineClass(name,b,0,b.length);
    }

    private byte[] loadClassData(String name) {
        String fileName = path+name+".class";
        System.out.println("在寻找"+fileName);
        InputStream  in = null;
        ByteArrayOutputStream out = null;
        try{
            in = new FileInputStream(new File(fileName));
            out = new ByteArrayOutputStream();
            int i = 0;
            while (( i = in.read()) != -1){
                out.write(i);
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return out.toByteArray();
    }

}
