package MyHadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

/***
 * HDFS文件操作： 上传文件、下载文件、删除文件
 */
public class HdfsOp {
    private FileSystem fs;


    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        //创建一个配置对象
        Configuration conf = new Configuration();
        //指定HDFS地址
        conf.set("fs.defaultFS", "hdfs://172.16.22.154:9000");
        //获取HDFS操作对象
        FileSystem fs = FileSystem.get(conf);

        // 创建目录
//        testmkdir(fs);

        //上传文件
//        testPut(fs);
//        testPut2(fs);
//        put(fileSystem);

        //下载文件
//        testGet(fs);
        //get(fileSystem);

        //删除文件
//        testRm(fs);
//        delete(fileSystem);

        //文件的更名和移动
//        testmv(fs);

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
        fs.mkdirs(new Path("/xiyou/huaguoshan3"));
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
        String path1 = "/Users/lixiaofeng/Library/Mobile Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava2/myJava/src/main/java/MyHadoop/hdfs/testupload1228.txt";
        String path2 = "/xiyou";
        fs.copyFromLocalFile(false, true, new Path(path1), new Path(path2));
    }

    @Test
    public static void testPut2(FileSystem fs) throws IOException {
        //创建文件input3
        FSDataOutputStream fos = fs.create(new Path("/input3"));
        //写入内容
        fos.write("hello world".getBytes());
    }

    // 文件下载
    @Test
    public static void testGet(FileSystem fs) throws IOException {
        // 参数的解读：参数一：原文件是否删除；参数二：原文件路径HDFS； 参数三：目标地址路径Win ; 参数四：
        //fs.copyToLocalFile(true, new Path("hdfs://hadoop102/xiyou/huaguoshan/"), new Path("D:\\"), true);
        String path1 = "/Users/lixiaofeng/Library/Mobile Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava2/myJava/src/main/java/MyHadoop/hdfs/output/testupload1228.txt";
        String path2 = "/xiyou/testupload1228.txt";
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
        fs.rename(new Path("/input"), new Path("/output"));

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
     * 下载文件
     * @param fileSystem
     * @throws IOException
     */
    private static void get(FileSystem fileSystem) throws IOException {
        //获取HDFS文件系统中的输入流
        FSDataInputStream fis = fileSystem.open(new Path("/user/root/output/part-r-00000"));
        //获取本地文件输出流
        FileOutputStream fos = new FileOutputStream("D:\\data\\part-00000");
        //下载文件
        IOUtils.copyBytes(fis, fos, 1024, true);
    }

    /**
     * 上传文件
     * @param fileSystem
     * @throws IOException
     */
    private static void put(FileSystem fileSystem) throws IOException {
        //文件本地文件的输入流
        FileInputStream fis = new FileInputStream("/Users/lixiaofeng/Library/Mobile Documents/com~apple~CloudDocs/Documents/study/myGItProject/myJava2/myJava/src/main/java/MyHadoop/hdfs/testupload1228.txt");
        //获取HDFS文件系统的输出流
        FSDataOutputStream fos = fileSystem.create(new Path("/testupload1228.txt"));

        //通过工具类把输入流拷贝到输出流里面，实现本地文件上传到HDFS
        IOUtils.copyBytes(fis, fos, 1024, true);
    }


}