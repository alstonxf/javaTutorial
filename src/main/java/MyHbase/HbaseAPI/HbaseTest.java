package MyHbase.HbaseAPI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;

import java.io.IOException;

public class HbaseTest {
    public static void main(String[] args) throws IOException {

        /** hadoop 连接 */
        Configuration conf = null;
        /** hbase 连接 */
        Connection conn = null;
        /** 会话 */
        Admin admin = null;



        // 初始化连接
        //1、创建配置对象，获取HBase连接
        System.out.println("1、创建配置对象，获取HBase连接");
        conf = HBaseConfiguration.create();
        // 设置配置参数
//        private static String ip ="master";
//        private static String port ="2181";
//        private static String port1 ="9001";
//            conf.set("hbase.zookeeper.quorum", ip);
//            conf.set("hbase.zookeeper.property.clientPort", port);
        //如果hbase是集群，这个必须加上
        //这个ip和端口是在hadoop/mapred-site.xml配置文件配置的
//            conf.set("hbase.master", ip+":"+port1);

        //2、获取HBase连接对象
        System.out.println("2、获取HBase连接对象...");
        conn = HbaseUtil.getConnection(conn,conf);
        System.out.println("获取到HBase连接对象"+conn);

        //3、获取操作对象
        System.out.println("3、获取操作对象...");
        admin = conn.getAdmin();
        System.out.println("获取到操作对象");


        //4、操作数据库
        //4.0 定义一些变量
        String nameSpace = "FNMKT_REALTIME";
        String tableName = "ODS_SLRE";
        String columnFamily1 = "CF";
        String columnFamily2 = "CF1";
        String rowKey1 = "00001-CN000";
        String rowKey2 = "00002-CN000";
        String qualifier1 = "pk";
        String value1 = "v1";
        String value2 = "v2";

        // 判断命名空间是否存在，不存在的话创建命名空间
        System.out.println("4.1 判断命名空间是否存在，不存在的话创建命名空间");
        HbaseUtil.isNamespaceExist(nameSpace, admin);

        // 判断HBase中是否存在某张表
        System.out.println("4.2 判断HBase中是否存在某张表");
        boolean exists = HbaseUtil.isTableExist(nameSpace,tableName, admin);
        System.out.println("HBase中是否存在" + nameSpace + ":" + tableName + "表：" + exists);

        // 创建表
        System.out.println("4.3 创建表");
        HbaseUtil.createTable(nameSpace, tableName, admin, columnFamily1,columnFamily2);

        // 禁用表
        HbaseUtil.DisableTable(admin,nameSpace,tableName);

        // 启用表
        HbaseUtil.EnableTable(admin,nameSpace,tableName);

        // 列出所有表名
        System.out.println("列出所有表");
        HbaseUtil.getAllTables(admin);

        // 简单测试下功能
        System.out.println("4.4 简单测试下功能");
        HbaseUtil.testExecTable(conn, nameSpace + ":" + tableName);

        // 获取某张表的所有列簇
        HbaseUtil.getRowName(conn,nameSpace,tableName);

        // 新增列簇
        HbaseUtil.addColoumnFamily(conn,admin,nameSpace,tableName,"CF5");
        HbaseUtil.getRowName(conn,nameSpace,tableName);

        // 删除列簇
        HbaseUtil.deleteColoumnFamily(conn,admin,nameSpace,tableName,"CF9");
        HbaseUtil.getRowName(conn,nameSpace,tableName);

        // insert数据
        HbaseUtil.putData( conn, nameSpace,  tableName, rowKey1, columnFamily1, qualifier1, value1);

        // upsert数据
        System.out.println("4.4.0 向表中插入数据");
        HbaseUtil.addRowData(conn,nameSpace,tableName, rowKey1, columnFamily1,  qualifier1,  value1);
        HbaseUtil.addRowData(conn,nameSpace,tableName, rowKey1, columnFamily1,  qualifier1,  value1);

        // 获取所有数据
        System.out.println("获取所有数据");
        HbaseUtil.getAllRows(conn,nameSpace,tableName);

        // 获取某一行数据
        System.out.println("获取某一行数据");
        HbaseUtil.getRow(conn,nameSpace,tableName,rowKey1);

        // 获取某一列数据
        System.out.println("获取某一列数据");
        HbaseUtil.scanTable(conn, nameSpace, tableName, columnFamily1, qualifier1);

        // 获取某一行指定 "列族:列" 的数据
        System.out.println("获取某一行指定 \"列族:列\" 的数据");
        HbaseUtil.getRowQualifier(conn,nameSpace,tableName,columnFamily1,qualifier1,rowKey2);

        // 删除表多行数据
        System.out.println("4.5 删除表多行数据");
        HbaseUtil.deleteMultiRow(conn,nameSpace,tableName,rowKey1,rowKey2);

        // 删除表
        System.out.println("4.6 删除表");
//        HbaseAPI.HbaseUtil.delTable(nameSpace,tableName, admin);

        //关闭资源
        System.out.println("关闭资源");
//        HbaseAPI.HbaseUtil.closeConn(conn,admin);

        // Shutting down HBase
//        System.out.println("Shutting down hbase");
//        admin.shutdown();

    }


}
