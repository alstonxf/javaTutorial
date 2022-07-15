package test.sqlliteExercise;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class sqle2 {
    public static void main(String[] args) {
        try {
            // 加载驱动,连接sqlite的jdbc
            Class.forName("org.sqlite.JDBC");
            // 连接数据库how2j.db,不用手动创建。。。
            Connection connection = DriverManager.getConnection("jdbc:sqlite:testsql2");//库名
            // 创建连接对象，是Java的一个操作数据库的重要接口
            Statement statement = connection.createStatement();
            // 判断是否有表tables的存在。有则删除
//            statement.executeUpdate("drop table if exists hero");
            // 创建表
            File file = new File("src/main/resources/test1.txt");
            String sql = new txt2String().txt2String2(file);
//            statement.execute(sql);
//            statement.executeUpdate("create table if not exists mytable (id integer primary key,name varchar, sex varchar,address varchar);");
            //插入数据
//            statement.executeUpdate("replace into mytable1 values (1, 'wxb', 'male', '010-22222222')");
//            statement.executeUpdate("replace into mytable1 values (2, 'll', 'male', '010-11111111')");
            // 搜索数据库，将搜索的放入数据集ResultSet中

//            select * from "stu_info"
            ResultSet rSet = statement.executeQuery("select * from mytable1;");
            while (rSet.next()) { // 遍历这个数据集
                System.out.println("id：" + rSet.getInt(1));
                System.out.println("name：" + rSet.getString(2));
                System.out.println("sex：" + rSet.getString(3));
                System.out.println("address：" + rSet.getString(3));
            }
            rSet.close();// 关闭数据集
            connection.close();// 关闭数据库连接
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
