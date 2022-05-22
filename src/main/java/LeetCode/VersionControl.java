package LeetCode;

/**
 * @author lixiaofeng
 */
public abstract class VersionControl {
    boolean isBadVersion(int version) {
        if (version >= 1702766719){
            return true;
        }else{
            return false;
        }

    }
}
