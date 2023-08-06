package hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Properties;
/***
 * HDFS文件操作： 上传文件、下载文件、删除文件
 */
public class HdfsClient {
    private FileSystem fs;


    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        //创建一个配置对象
        Configuration conf = new Configuration();

        //指定HDFS地址
        // 读取 properties 文件
        try{
            Properties properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream("/Users/lixiaofeng/myGitProjects/myHadoop/myHadoop/src/main/resources/myHadoop.properties");
            properties.load(fileInputStream);
            fileInputStream.close();

            // 获取属性值
            String host = properties.getProperty("hdfs.port");
            String port = properties.getProperty("hdfs.node");
            String host_port = "hdfs://"+host+":"+port;
            System.out.println(host_port);
            conf.set("fs.defaultFS", host_port);
        } catch (Exception e){
            e.printStackTrace();
        }

        String workingDir = System.getProperty("user.dir");
        System.out.println("当前工作目录：" + workingDir);

        //获取HDFS操作对象
        FileSystem fs = FileSystem.get(conf);

        // 创建目录
        testmkdir(fs);

        //上传文件
        testPut(fs);
        testPut2(fs);
        testPut3(fs);

        //下载文件
        testGet(fs);
        testGet2(fs);

        //删除文件
        testRm(fs);

        //文件的更名和移动
        testmv(fs);

        //获取文件详细信息
        fileDetail(fs);

        //关闭fs
        close(fs);
    }


    public static void close(FileSystem fs) throws IOException {
        // 3 关闭资源
        fs.close();
    }

    // 创建目录
    @Test
    public static void testmkdir(FileSystem fs) throws URISyntaxException, IOException, InterruptedException {
        // 2 创建一个文件夹
        try {
            fs.mkdirs(new Path("/xiyou3"));
            System.out.println("创建一个文件夹成功");
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    // 上传
    /**
     * 参数优先级
     * hdfs-default.xml => hdfs-site.xml=> 在项目资源目录下的配置文件 =》代码里面的配置
     *
     * @throws IOException
     */
    @Test
    public static void testPut(FileSystem fs) throws IOException {
        // 参数解读：参数一：表示删除原数据； 参数二：是否允许覆盖；参数三：原数据路径； 参数四：目的地路径
        String path1 = "src/main/java/hdfs/test/local2hdfs/testupload.txt";
        String path2 = "/xiyou3";
        fs.copyFromLocalFile(false, true, new Path(path1), new Path(path2));
    }

    @Test
    public static void testPut2(FileSystem fs) throws IOException {
        //创建文件input3
        FSDataOutputStream fos = fs.create(new Path("/input3.txt"));
        //写入内容
        fos.write("hello world".getBytes());
    }

    // 文件下载
    @Test
    public static void testGet(FileSystem fs) throws IOException {
        // 参数的解读：参数一：原文件是否删除；参数二：原文件路径HDFS； 参数三：目标地址路径Win ; 参数四：
        //fs.copyToLocalFile(true, new Path("hdfs://hadoop102/xiyou/huaguoshan/"), new Path("D:\\"), true);
        String path1 = "src/main/java/hdfs/test/hdfs2local/testupload20230807.txt";
        String path2 = "/xiyou/testupload.txt";
        fs.copyToLocalFile(false, new Path(path2), new Path(path1), false);
    }

    // 删除
    @Test
    public static void testRm(FileSystem fs) throws IOException {

        // 参数解读：参数1：要删除的路径； 参数2 ： 是否递归删除
        // 删除文件
        fs.delete(new Path("/input2"),false);

        // 删除空目录
//        fs.delete(new Path("/xiyou"), false);

        // 删除非空目录
        boolean flag = fs.delete(new Path("/xiyou"), true);

        if(flag){
            System.out.println("删除成功");
        }else{
            System.out.println("删除失败");
        }
    }

    // 文件的更名和移动
    @Test
    public static void testmv(FileSystem fs) throws IOException {
        // 参数解读：参数1 ：原文件路径； 参数2 ：目标文件路径
        // 对文件名称的修改
        //fs.rename(new Path("/input/word.txt"), new Path("/input/ss.txt"));

        // 文件的移动和更名
        //fs.rename(new Path("/input/ss.txt"),new Path("/cls.txt"));

        // 目录更名
        fs.rename(new Path("/output"), new Path("/input"));

    }

    // 获取文件详细信息
    @Test
    public static void fileDetail(FileSystem fs) throws IOException {

        // 获取所有文件信息
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);

        // 遍历文件
        while (listFiles.hasNext()) {
            LocatedFileStatus fileStatus = listFiles.next();

            System.out.println("==========" + fileStatus.getPath() + "=========");
            System.out.println(fileStatus.getPermission());
            System.out.println(fileStatus.getOwner());
            System.out.println(fileStatus.getGroup());
            System.out.println(fileStatus.getLen());
            System.out.println(fileStatus.getModificationTime());
            System.out.println(fileStatus.getReplication());
            System.out.println(fileStatus.getBlockSize());
            System.out.println(fileStatus.getPath().getName());

            // 获取块信息
            BlockLocation[] blockLocations = fileStatus.getBlockLocations();

            System.out.println(Arrays.toString(blockLocations));

        }
    }

    // 判断是文件夹还是文件
    @Test
    public void testFile(FileSystem fs) throws IOException {

        FileStatus[] listStatus = fs.listStatus(new Path("/"));

        for (FileStatus status : listStatus) {

            if (status.isFile()) {
                System.out.println("文件：" + status.getPath().getName());
            } else {
                System.out.println("目录：" + status.getPath().getName());
            }
        }
    }

    /**
     * 上传文件
     * @param fileSystem
     * @throws IOException
     */
    private static void testPut3(FileSystem fileSystem) throws IOException {
        //文件本地文件的输入流
        FileInputStream fis = new FileInputStream("src/main/java/hdfs/test/local2hdfs/testupload2.txt");
        //获取HDFS文件系统的输出流
        FSDataOutputStream fos = fileSystem.create(new Path("/testupload2.txt"));

        //通过工具类把输入流拷贝到输出流里面，实现本地文件上传到HDFS
        IOUtils.copyBytes(fis, fos, 1024, true);
    }

    /**
     * 下载文件
     * @param fileSystem
     * @throws IOException
     */
    private static void testGet2(FileSystem fileSystem) throws IOException {
        //获取HDFS文件系统中的输入流
        FSDataInputStream fis = fileSystem.open(new Path("/testupload2.txt"));
        //获取本地文件输出流
        FileOutputStream fos = new FileOutputStream("src/main/java/hdfs/test/hdfs2local/testupload2_20230807.txt");
        //下载文件
        IOUtils.copyBytes(fis, fos, 1024, true);
    }




}