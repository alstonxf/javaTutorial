package HbaseAPI;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.ColumnValueFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class HbaseDML {


    //添加静态属性connection指向单例连接
    public static Connection connnection = HbaseConnection.connection;

    /**
     * 插入数据
     *
     * @param namespace    命名空间名称
     * @param tableName    表格名称
     * @param rowKey       主键
     * @param columnFamily 列族名称
     * @param columnName   列名
     * @param value        值
     */
    public static void putCell(String namespace, String tableName, String rowKey, String columnFamily, String columnName, String value) throws IOException {
        // 1. 获取table
        Table table = connnection.getTable(TableName.valueOf(namespace, tableName));

        // 2. 调用相关方法插入数据
        // 2.1 创建put对象
        Put put = new Put(Bytes.toBytes(rowKey));

        // 2.2 给put对象添加数据
        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(columnName), Bytes.toBytes(value));

        // 2.3 将对象写入对应的方法
        try {
            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 3. 关闭table
        table.close();

    }

    /**
     * 读取数据 读取对应的一行中的某一列
     *
     * @param namespace    命名空间名称
     * @param tableName    表格名称
     * @param rowKey       主键
     * @param columnFamily 列族名称
     * @param columnName   列名
     */
    public static void getCell(String namespace, String tableName, String rowKey, String columnFamily, String columnName) throws IOException {
        // 1. 获取table
        Table table = connnection.getTable(TableName.valueOf(namespace, tableName));

        // 2. 调用相关方法读取数据
        // 2.1 创建get对象
        Get get = new Get(Bytes.toBytes(rowKey));

        // 如果直接调用get方法读取数据 此时读一整行数据
        // 如果想读取某一列的数据 需要添加相应的参数
        get.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(columnName));

        // 设置读取数据的版本
        get.readAllVersions();

        try {
            //读取数据 得到result对象
            Result result = table.get(get);
            //处理数据
            Cell[] cells = result.rawCells();

            //测试方法：直接把读取的数据打印在控制台
            //如果是实际开发 需要再额外写方法 对应处理数据
            for (Cell cell : cells) {
                // cell 存储数据比较底层
                String value = new String(CellUtil.cloneValue(cell));
                System.out.println(value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 3. 关闭table
        table.close();
    }

    /**
     * 扫描数据
     *
     * @param namespace 命名空间名称
     * @param tableName 表格名称
     * @param startRow  开始的row 包含的
     * @param stopRow   结束的row 不包含
     */
    public static void scanRows(String namespace, String tableName, String startRow, String stopRow) throws IOException {

        //1. 获取table
        Table table = connnection.getTable(TableName.valueOf(namespace, tableName));

        //2. 创建scan对象
        Scan scan = new Scan();
        //如果此时直接调用会直接扫描整张表

        //添加参数来控制扫描的数据
        //默认包含
        scan.withStartRow(Bytes.toBytes(startRow));
        //默认不包含
        scan.withStopRow(Bytes.toBytes(stopRow));

        try {
            //读取多行数据 获取scanner
            ResultScanner scanner = table.getScanner(scan);
            //result 来记录一行数据    cell数组
            //ResultScanner来记录多行数据 result的数组
            for (Result result : scanner) {
                Cell[] cells = result.rawCells();
                for (Cell cell : cells) {
                    System.out.println(new String(CellUtil.cloneRow(cell))
                            + "-" + new String(CellUtil.cloneFamily(cell))
                            + "-" + new String(CellUtil.cloneQualifier(cell))
                            + "-" + new String(CellUtil.cloneValue(cell)));
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 3. 关闭table
        table.close();
    }

    /**
     * 带过滤的扫描
     *
     * @param namespace    命名空间名称
     * @param tableName    表格名称
     * @param startRow     开始的row 包含的
     * @param stopRow      结束的row 不包含
     * @param columnFamily 列族名称
     * @param columnName   列名
     * @param value        value 值
     */
    public static void filterScan(String namespace, String tableName, String startRow, String stopRow, String columnFamily, String columnName, String value) throws IOException {

        //1. 获取table
        Table table = connnection.getTable(TableName.valueOf(namespace, tableName));

        //2. 创建scan对象
        Scan scan = new Scan();
        //如果此时直接调用会直接扫描整张表

        //添加参数来控制扫描的数据
        //默认包含
        scan.withStartRow(Bytes.toBytes(startRow));
        //默认不包含
        scan.withStopRow(Bytes.toBytes(stopRow));

        //可以添加多个过滤
        FilterList filterList = new FilterList();

        //创建过滤器
        //（1）结果只保留当前列的数据
        ColumnValueFilter columnValueFilter = new ColumnValueFilter(
                //列族名称
                Bytes.toBytes(columnFamily),
                // 列名
                Bytes.toBytes(columnName),
                // 比较关系
                CompareOperator.EQUAL,
                // 值
                Bytes.toBytes(value)
        );

        //(2) 结果保留整行数据
        //结果同时会保留没有当前列的数据
        SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter(
                //列族名称
                Bytes.toBytes(columnFamily),
                // 列名
                Bytes.toBytes(columnName),
                // 比较关系
                CompareOperator.EQUAL,
                // 值
                Bytes.toBytes(value)
        );

        // 本身可以添加多个过滤器
        filterList.addFilter(singleColumnValueFilter);

        // 添加过滤
        scan.setFilter(filterList);

        try {
            //读取多行数据 获得scanner
            ResultScanner scanner = table.getScanner(scan);
            //result 来记录一行数据 cell数组
            //ResultScanner 来记录多行数据 result的数组
            for (Result result : scanner) {
                Cell[] cells = result.rawCells();

                for (Cell cell : cells) {
                    System.out.println(new String(CellUtil.cloneRow(cell))
                            + "-" + new String(CellUtil.cloneFamily(cell))
                            + "-" + new String(CellUtil.cloneQualifier(cell))
                            + "-" + new String(CellUtil.cloneValue(cell)));
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 3. 关闭table
        table.close();
    }

    /**
     * 删除数据
     *
     * @param namespace 命名空间名称
     * @param tableName 表格名称
     * @param rowKey
     * @param family
     * @param column
     * @throws IOException
     */
    public static void deleteColumn(String namespace, String tableName, String rowKey, String family, String column) throws IOException {
        //1. 获取table
        Table table = connnection.getTable(TableName.valueOf(namespace, tableName));

        //2. 创建Delete 对象
        Delete delete = new Delete(Bytes.toBytes(rowKey));

        //3. 添加删除信息
        //3.1 删除单个版本
        delete.addColumn(Bytes.toBytes(family),Bytes.toBytes(column));
        //3.2 删除所有版本
        delete.addColumns(Bytes.toBytes(family),Bytes.toBytes(column));
        //3.3 删除列族
//        delete.addFamily(Bytes.toBytes(family));

        //4. 删除数据
        table.delete(delete);

        //5. 关闭资源
        table.close();
    }

}