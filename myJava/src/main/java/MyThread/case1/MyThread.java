package MyThread.case1;

public class MyThread extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            //getName可以获取线程名称，如果没有设置，默认是Thread-0,1,2,3...
            System.out.println(getId() + getName() +"运行  "+ i);
        }
    }
}
