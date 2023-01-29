package MyHbase.HbaseAPI3;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.AsyncConnection;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

public class HbaseConnectionSingle {

    /**
     * 单例模式
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        //1：创建连接配置对象
        Configuration conf = new Configuration();

        //2:添加配置参数
        conf.set("hbase.zookeeper.quorum","172.16.22.154");

        //3：创建连接
        //默认使用同步连接
        Connection connection = ConnectionFactory.createConnection();
        Admin admin = connection.getAdmin();
        System.out.println(admin.tableExists(TableName.valueOf("bigdata","student")));
        //可以使用异步连接,不推荐使用
        CompletableFuture<AsyncConnection> asynConnection = ConnectionFactory.createAsyncConnection(conf);

        //4：使用连接
        System.out.println(connection);

        //5：关闭连接
        connection.close();
    }
}
