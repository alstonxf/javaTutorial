package MyHbase.HbaseAPI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:
 * @create_date:
 * @desc: 测试HBase API
 * @modifier:
 * @modified_date:
 * @desc:
 */
public class HbaseUtil {

    /**
     * 判断HBase中是否存在命名空间
     *
     * @param namespace 命名空间的名称
     * @param admin     操作对象
     * @throws IOException
     */
    public static void isNamespaceExist(String namespace, Admin admin) throws IOException {
        try {
            //判断表空间是否存在，不存在会抛出异常
            admin.getNamespaceDescriptor(namespace);
        } catch (NamespaceNotFoundException e) {
            //创建表空间
            NamespaceDescriptor nd = NamespaceDescriptor.create(namespace).build();
            admin.createNamespace(nd);
        }
    }

    /**
     * 判断HBase中是否存在某张表
     *
     * @param tableName 表名
     * @param admin     操作对象
     * @return 返回true or false
     * @throws IOException
     */
    public static boolean isTableExist(String nameSpace,String tableName, Admin admin) throws IOException {
        TableName tn = TableName.valueOf(nameSpace + ":" + tableName);
        boolean b = admin.tableExists(tn);
        return b;
    }

    /**
     * 创建HBase表
     *
     * @param tableName    表名
     * @param admin        操作对象
     * @param columnFamily 列族
     * @throws IOException
     */
    public static void createTable(String nameSpace,String tableName, Admin admin, String... columnFamily) throws IOException {
        tableName = nameSpace + ":" + tableName;
        //判断表是否存在
        boolean b = admin.tableExists(TableName.valueOf(tableName));
        if (b) {
            System.out.println(tableName + "已经存在！");
        } else {
            //创建表描述对象
            HTableDescriptor td = new HTableDescriptor(TableName.valueOf(tableName));

            //增加列族
            for (String cf : columnFamily) {
                HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(cf);
                td.addFamily(hColumnDescriptor);
            }

            admin.createTable(td);
            System.out.println(tableName + "表创建成功！");
        }
    }


    /**
     * 操作表
     *
     * @param connection 连接对象
     * @param tableName  表名
     * @throws IOException
     */
    public static void testExecTable(Connection connection, String tableName) throws IOException {
        TableName tn = TableName.valueOf(tableName);
        Table table = connection.getTable(tn);

        //查询数据
        String rowkey = "1001";
        Get get = new Get(Bytes.toBytes(rowkey));
        Result result = table.get(get);
        boolean empty = result.isEmpty();
        System.out.println(tableName + "表中是否存在该条数据：" + !empty);

        if (empty) {
            //插入数据
            Put put = new Put(Bytes.toBytes(rowkey));

            String family = "CF";
            String qualifier = "pk";
            String value = "00001-CN000";

            put.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value));

            table.put(put);
            System.out.println("插入数据成功！");
        } else {
            //展示数据
            for (Cell cell :
                    result.rawCells()) {
                System.out.println("family = " + Bytes.toString(CellUtil.cloneFamily(cell)));
                System.out.println("rowkey = " + Bytes.toString(CellUtil.cloneRow(cell)));
                System.out.println("value = " + Bytes.toString(CellUtil.cloneValue(cell)));
                System.out.println("qualifier = " + Bytes.toString(CellUtil.cloneQualifier(cell)));
            }
        }

    }

    /**
     * 删除表
     *
     * @param tableName 表名
     * @param admin     操作对象
     * @throws IOException
     */
    public static void delTable(String nameSpace, String tableName, Admin admin) throws IOException {
        tableName = nameSpace + ":" + tableName;
        TableName tn = TableName.valueOf(tableName);

        if (admin.tableExists(tn)) {
            //禁用表
            admin.disableTable(tn);

            //删除表
            admin.deleteTable(tn);

            System.out.println("表" + tn + "删除成功");
        }else{
            System.out.println("表" + tableName + "不存在! 无法执行删除命令！");
        }
    }


    // 更新数据
    public static void addRowData(Connection conn, String nameSpace, String tableName, String rowKey, String columnFamily, String qualifier, String value) throws IOException {

        try {
            HTable hTable=(HTable) conn.getTable(TableName.valueOf(nameSpace + ":" + tableName));
            // 向表中插入数据
            // 像Put 对象中封装数据
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier), Bytes.toBytes(value));
            hTable.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("插入数据成功");
    }

    //插入数据
    public static void putData(Connection conn, String nameSpace, String tableName, String rowKey, String columnFamily, String qualifier, String value) throws IOException {

        try {
            // Instantiating Configuration class
            Configuration config = HBaseConfiguration.create();

            // Instantiating HTable class
//            HTable hTable = new HTable(config, nameSpace + ":" + tableName);
            HTable hTable=(HTable) conn.getTable(TableName.valueOf(nameSpace + ":" + tableName));
            // Instantiating Put class
            // accepts a row name.
            Put p = new Put(Bytes.toBytes(rowKey));

            // adding values using add() method
            // accepts column family name, qualifier/row name ,value
            p.addColumn(Bytes.toBytes(columnFamily),Bytes.toBytes(qualifier),Bytes.toBytes(value));

            // Saving the put Instance to the HTable.
            hTable.put(p);
            System.out.println("data inserted");

            // closing HTable
            hTable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("插入数据成功");
    }

    // 删除多行数据
    public static void deleteMultiRow(Connection conn, String nameSpace, String tableName, String... rows) throws IOException {
        //HTable hTable = new HTable(configuration, tableName);

        List<Delete> deleteList = new ArrayList<Delete>();
        for (String row : rows) {
            Delete delete = new Delete(Bytes.toBytes(row));
            deleteList.add(delete);
        }
        try {
            HTable hTable=(HTable) conn.getTable(TableName.valueOf(nameSpace + ":" + tableName));
            hTable.delete(deleteList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void getAllTables(Admin admin) throws IOException{
        // Getting all the list of tables using HBaseAdmin object
        HTableDescriptor[] tableDescriptor = admin.listTables();

        // printing all the table names.
        for (int i=0; i<tableDescriptor.length;i++ ){
            System.out.println(tableDescriptor[i].getNameAsString());
        }
    }

    public static void DisableTable(Admin admin,String nameSpace,String tableName) throws IOException{


        // Verifying weather the table is disabled
        Boolean bool = admin.isTableDisabled(TableName.valueOf(nameSpace + ":" + tableName));
        System.out.println(bool);

        // Disabling the table using HBaseAdmin object
        if(!bool){
            admin.disableTable(TableName.valueOf(nameSpace + ":" + tableName));
            System.out.println("Table disabled");
        }

    }
    public static void EnableTable(Admin admin,String nameSpace,String tableName) throws IOException{

        // Verifying weather the table is disabled
        Boolean bool = admin.isTableEnabled(TableName.valueOf(nameSpace + ":" + tableName));
        System.out.println(bool);

        // Disabling the table using HBaseAdmin object
        if(!bool){
            admin.enableTable(TableName.valueOf(nameSpace + ":" + tableName));
            System.out.println("Table Enabled");
        }

    }


    // 获取所有数据
    public static void getAllRows(Connection conn, String nameSpace,String tableName) throws IOException{
        //HTable hTable = new HTable(configuration, tableName);
        // 得到用于扫描region的对象
        Scan scan = new Scan();
        // 使用HTable 得到 resultcanner实现类的对象
        try {
            HTable hTable=(HTable) conn.getTable(TableName.valueOf(nameSpace + ":" + tableName));
            ResultScanner resultScanner = hTable.getScanner(scan);

            for (Result result : resultScanner) {
                Cell[] cells = result.rawCells();
                for (Cell cell : cells){
                    // 得到rowkey
                    System.out.println("行键:" + Bytes.toString(CellUtil.cloneRow(cell)));
                    // 得到列族
                    System.out.println("列族:" + Bytes.toString(CellUtil.cloneFamily(cell)));
                    System.out.println("列:" + Bytes.toString(CellUtil.cloneQualifier(cell)));
                    System.out.println("值:" + Bytes.toString(CellUtil.cloneValue(cell)));

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 获取某一列数据
    static void scanTable(Connection conn, String nameSpace, String tableName, String familyName, String col) throws IOException {
        // Instantiating Configuration class
//        Configuration config = HBaseConfiguration.create();
        // Instantiating HTable class
//        HTable table = new HTable(config, nameSpace + ":" +tableName);
        HTable table=(HTable) conn.getTable(TableName.valueOf(nameSpace + ":" + tableName));

        // Instantiating the Scan class
        Scan scan = new Scan();

        // Scanning the required columns
        scan.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(col));

        // Getting the scan result
        ResultScanner scanner = table.getScanner(scan);

        // Reading values from scan result
        for (Result result = scanner.next(); result != null; result = scanner.next()) {
            System.out.println("Found row : " + result);
        }
        //closing the scanner
        scanner.close();
    }

    // 获取某一行数据
    public static void getRow(Connection conn, String nameSpace, String tableName, String rowKey) throws IOException{
        //HTable table = new HTable(configuration, tableName);
        Get get = new Get(Bytes.toBytes(rowKey));
        // get.setMaxVersions(); 显示所有版本
        // get.setTimeStamp(); 显示指定时间的版本

        try {
            HTable hTable=(HTable) conn.getTable(TableName.valueOf(nameSpace + ":" + tableName));
            Result result = hTable.get(get);
            for (Cell cell : result.rawCells()) {
                System.out.println("行键:" + Bytes.toString(result.getRow()));
                System.out.println("列族:" + Bytes.toString(CellUtil.cloneFamily(cell)));
                System.out.println("列:" + Bytes.toString(CellUtil.cloneQualifier(cell)));
                System.out.println("值:" + Bytes.toString(CellUtil.cloneValue(cell)));
                System.out.println("时间戳:" + cell.getTimestamp());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 获取某一行指定 "列族:列" 的数据
    static void getRowQualifier(Connection conn, String nameSpace, String tableName, String family, String col, String rowKey) throws IOException{
        //HTable table = new HTable(configuration, tableName);
        Get get = new Get(Bytes.toBytes(rowKey));
        get.setMaxVersions();
        //get.setTimestamp();

        get.addColumn(Bytes.toBytes(family), Bytes.toBytes(col));

        try {
            HTable hTable=(HTable) conn.getTable(TableName.valueOf(nameSpace + ":" + tableName));
            Result result = hTable.get(get);
            for (Cell cell : result.rawCells()) {
                System.out.println("行键:" + Bytes.toString(result.getRow()));
                System.out.println("列族:" + Bytes.toString(CellUtil.cloneFamily(cell)));
                System.out.println("列:" + Bytes.toString(CellUtil.cloneQualifier(cell)));
                System.out.println("值:" + Bytes.toString(CellUtil.cloneValue(cell)));
                //System.out.println("时间戳:" + cell.getTimestamp());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取某张表的所有列簇
    public static List<String> getRowName(Connection conn,String nameSpace,String tableName)throws IOException{
        Table table = conn.getTable(TableName.valueOf(nameSpace + ":" +tableName));
        List<String> list = new ArrayList<>();
        HTableDescriptor hTableDescriptor=table.getTableDescriptor();
        for(HColumnDescriptor fdescriptor : hTableDescriptor.getColumnFamilies()){
            list.add(fdescriptor.getNameAsString());
        }
        System.out.println(nameSpace + ":" +tableName+"列簇：");
        for(int i=0;i<list.size();i++) {
            System.out.println(list.get(i));
        }
        return list;
    }


    static void addColoumnFamily(Connection conn, Admin admin, String nameSpace, String tableName, String familyName) throws IOException {
        List<String> list = getRowName(conn,nameSpace,tableName);
        if(list.size() > 0 && list.contains(familyName)){
            System.out.println("已存在列簇"+familyName+" 无法新增！");
            return;
        }
        // Instantiating columnDescriptor class
        HColumnDescriptor columnDescriptor = new HColumnDescriptor(familyName);

        // Adding column family
        admin.addColumn(TableName.valueOf(nameSpace + ":" + tableName), columnDescriptor);
        System.out.println(familyName + " added");
    }

    static void deleteColoumnFamily(Connection conn, Admin admin, String nameSpace, String tableName, String familyName) throws IOException {
        List<String> list = getRowName(conn,nameSpace,tableName);
        if(list.size() > 0 && list.contains(familyName)==false){
            System.out.println("不存在列簇"+familyName+" 无法删除！");
            return;
        }
        // Deleting a column family
        admin.deleteColumn(TableName.valueOf(nameSpace + ":" + tableName), familyName.getBytes());
        System.out.println("coloumn deleted");
    }


    /**
     * 获取连接
     *
     * @return
     */
    public synchronized static Connection getConnection(Connection conn,Configuration conf) {
        try {
            if (null == conn || conn.isClosed()) {
                // 获得连接对象
                conn = ConnectionFactory.createConnection(conf);
            }
        } catch (IOException e) {
            System.out.println("获取连接失败!");
            e.printStackTrace();
        }

        return conn;
    }


    /**
     * 连接关闭
     */
    public static void closeConn(Connection conn,Admin admin) {
        try {
            if (admin != null) {
                admin.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (IOException e) {
            System.out.println("连接关闭失败！");
            e.printStackTrace();
        }
    }


}