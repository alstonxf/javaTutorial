package MyHbase.HbaseAPI3;

import java.io.IOException;

import static MyHbase.HbaseAPI3.HbaseDDL.createNamespace;
import static MyHbase.HbaseAPI3.HbaseDDL.createTable;
import static MyHbase.HbaseAPI3.HbaseDML.*;

public class HbaseMain {
    public static void main(String[] args) throws IOException {
        createNamespace("bigdata1");
        createTable("bigdata1","student","info");
        putCell("bigdata1","student","1001","info","name","lisi");
        getCell("bigdata1","student","1001","info","name");
        scanRows("bigdata1","student","1001","2000");
        deleteColumn("bigdata1","student","1001","info","name");
    }
}
