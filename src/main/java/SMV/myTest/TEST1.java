package SMV.myTest;

import org.apache.log4j.Logger;

import java.io.IOException;


public class TEST1 {
    //    然后创建 Logger写入器：
    private static Logger logger = Logger.getLogger(TEST1.class.getName());

    public TEST1() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        logger.info("info：启动test1成功");
        logger.debug("debug：启动test1成功");
        logger.error("error：启动test1成功");
    }
}