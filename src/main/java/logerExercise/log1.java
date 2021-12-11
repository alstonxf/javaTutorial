package logerExercise;

import java.util.logging.Level;
import java.util.logging.Logger;

public class log1 {
    public static void main(String[] args) {
        Logger.getGlobal().info("日志关闭前");
        Logger.getGlobal().setLevel(Level.OFF);
        Logger.getGlobal().info("日志关闭后");//这行被关闭，不会打印出来

        final Logger myLogger =  Logger.getLogger("java.logerExercese.log1");
        myLogger.warning("warning....");
        myLogger.info("info....");

    }
}
