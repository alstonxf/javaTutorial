package HbaseAPI;

import java.io.IOException;

import static HbaseAPI.HbaseDML.*;

public class HbaseMain {
    public static void main(String[] args) throws IOException {
        HbaseDDL.createNamespace("bigdata1");
        //测试表格是否存在
        System.out.println(HbaseDDL.isTableExists("bigdata1", "student"));

        //测试创建表格
        HbaseDDL.createTable("bigdata1","student","info1");

        //测试修改表格
        HbaseDDL.modifyTable("bigdata1","student","info",6);


        putCell("bigdata1","student","1001","info","name","lisi");
        getCell("bigdata1","student","1001","info","name");
        scanRows("bigdata1","student","1001","2000");
        deleteColumn("bigdata1","student","1001","info","name");

        // 其他代码
        //关闭HBase连接
        HbaseConnection.closeConnection();
    }
}
