package listExercise;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class listExercise2 {
    public static void main(String[] args) {
        // 注意ayyaylist 是继承的 list。
        //这句创建了一个ArrayList的对象后把上溯到了List。此时它是一个List对象了，有些ArrayList有但是List没有的属性和方法，它就不能再用了。
        List list = new ArrayList();
        //创建一对象则保留了ArrayList的所有属性。
        ArrayList list1 = new ArrayList();

        //例子
//        list.trimToSize(); //错误，没有该方法。
        list1.trimToSize();   //ArrayList里有该方法。

        //技巧
        /*
        为什么要用 List list = new ArrayList() ,而不用 ArrayList alist = new ArrayList()呢？
        问题就在于List有多个实现类，现在你用的是ArrayList，也许哪一天你需要换成其它的实现类，如 LinkedList或者Vector等等，这时你只要改变这一行就行了：
        List list = new LinkedList(); 其它使用了list地方的代码根本不需要改动。
        假设你开始用 ArrayList alist = new ArrayList(), 这下你有的改了，特别是如果你使用了 ArrayList特有的方法和属性。

        地区用 List arr = new ArrayList();定义;行业用 ArrayListarr = new ArrayList();定义;则说明,行业里用到了ArrayList的特殊的方法.
        另外的例子就是,在类的方法中,如下声明:
        private void doMyAction(List list){}
        这样这个方法能处理所有实现了List接口的类,一定程度上实现了泛型函数.
        如果开发的时候觉得ArrayList,HashMap的性能不能满足你的需要,可以通过实现List,Map(或者Collection)来定制你的自定义类.

         */


    }
}
