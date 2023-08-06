package MyHbase.HbaseAPI3;

import java.io.IOException;

import static MyHbase.HbaseAPI3.HbaseDDL.*;
import static MyHbase.HbaseAPI3.HbaseDML.*;

public class HbaseMain {
    public static void main(String[] args) throws IOException {
        createNamespace("bigdata1");
        //测试表格是否存在
        System.out.println(isTableExists("bigdata1", "student"));

        //测试创建表格
        createTable("bigdata1","student","info1");

        //测试修改表格
        modifyTable("bigdata1","student","info",6);


        putCell("bigdata1","student","1001","info","name","lisi");
        getCell("bigdata1","student","1001","info","name");
        scanRows("bigdata1","student","1001","2000");
        deleteColumn("bigdata1","student","1001","info","name");

        // 其他代码
        //关闭HBase连接
        HbaseConnection.closeConnection();
    }
}
