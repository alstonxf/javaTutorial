package MyHbase.HbaseAPI3;

import lombok.val;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.AsyncConnection;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

public class HbaseConnection {

    /**
     *
     * @param args
     * @throws IOException
     */
    //设置静态属性hbase连接
    public static Connection connection = null;

    static {
        //创建hbase连接
        //默认使用同步连接
        try {
            //使用配置文件的方法
            connection = ConnectionFactory.createConnection();
            //使用连接
            System.out.println("使用连接"+connection);
        }catch (IOException e){
            System.out.println("连接获取失败");
        }
    }


    //连接关闭方法，用于进程关闭时调用
    public static void closeConnection() throws IOException{
        if (connection != null){
            connection.close();
        }
    }
}
