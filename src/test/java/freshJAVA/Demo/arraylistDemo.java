package freshJAVA.Demo;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class arraylistDemo {

    public static void main(String[] args) {
        ArrayList<String> myArrayList = new ArrayList();

//打印ArrayList的方法
//        1：将ArrayList转为String
        System.out.println(myArrayList.toString());
//        2：将ArrayList先转为Array,然后把array转为String
        System.out.println(Arrays.toString(myArrayList.toArray(new String[myArrayList.size()])));


//    方法	描述
//    add()	将元素插入到指定位置的 arraylist 中
        myArrayList.add("v1");
        myArrayList.add("v2");
        myArrayList.add("v3");
        myArrayList.add("v6");
        myArrayList.add("v7");
        myArrayList.add("v8");
        System.out.println(Arrays.toString(myArrayList.toArray(new String[myArrayList.size()])));

//    toArray()	将 arraylist 转换为数组
        String[] toA = myArrayList.toArray(new String[myArrayList.size()]);
        System.out.println(Arrays.toString(toA));

//    addAll()	添加集合中的所有元素到 arraylist 中
        String[] myArray = new String[]{"v4", "v5"};
        List<String> myList = Arrays.asList(myArray);
        myArrayList.addAll(myList);
        ArrayList myArrayList0 = new ArrayList(myList);
        System.out.println(Arrays.toString(myArrayList.toArray(new String[myArrayList.size()])));

//    clone()	复制一份 arraylist
        ArrayList myArrayList2 = (ArrayList) myArrayList.clone();
        System.out.println(Arrays.toString(myArrayList2.toArray(new String[myArrayList2.size()])));

//    contains()	判断元素是否在 arraylist
        Boolean containsV1 = myArrayList.contains("v1");
        System.out.println(containsV1);

//    get()	通过索引值获取 arraylist 中的元素
        String v0 = myArrayList.get(0);
        System.out.println("v0->"+v0);

//    indexOf()	返回 arraylist 中元素的索引值
        int i0 = myArrayList.indexOf("v2");
        System.out.println("index of V2 ->"+i0);

//    removeAll()	删除存在于指定集合中的 arraylist 里的所有元素
        List<String> l1 = Arrays.asList(new String[]{"v3","v4"});
        ArrayList<String> removeArrayList = new ArrayList(l1) ;
        myArrayList.removeAll(removeArrayList);
        System.out.println(Arrays.toString(myArrayList.toArray(new String[myArrayList.size()])));

//    remove()	删除 arraylist 里的单个元素
        myArrayList.remove("v2");
        System.out.println(Arrays.toString(myArrayList.toArray(new String[myArrayList.size()])));

//    size()	返回 arraylist 里元素数量
        System.out.println(myArrayList.size());

//    isEmpty()	判断 arraylist 是否为空
        Boolean isEmpty = myArrayList.isEmpty();
        System.out.println("isEmpty "+isEmpty);

//    subList()	截取部分 arraylist 的元素
        List<String> mySubList = myArrayList.subList(1, 4);//注意：索引从0开始，左闭右开。
        myArrayList = new ArrayList(mySubList);
        System.out.println("sublist-> "+Arrays.toString(myArrayList.toArray(new String[myArrayList.size()])));

//    set()	替换 arraylist 中指定索引的元素
        myArrayList.set(0,"vnew");
        System.out.println("set-> "+Arrays.toString(myArrayList.toArray(new String[myArrayList.size()])));

//    lastIndexOf()	返回指定元素在 arraylist 中最后一次出现的位置:没有返回-1，有的话返回索引
        int lastV2 = myArrayList.lastIndexOf("v2");
        System.out.println("lastV2->"+lastV2);
        int lastV7 = myArrayList.lastIndexOf("v7");
        System.out.println("lastV7->"+lastV7);

//    toString()	将 arraylist 转换为字符串
        String myArrayListToString = myArrayList.toString();
        System.out.println(myArrayListToString);

//    retainAll()	保留 arraylist 中在指定集合中也存在的那些元素
        System.out.println("before retainALL "+myArrayList.toString());
        myArrayList.retainAll(new ArrayList<String>(Arrays.asList(new String[]{"v7","v8"})));
        System.out.println("after retainALL "+myArrayList.toString());

//    containsAll()	查看 arraylist 是否包含指定集合中的所有元素
        boolean containsAll = myArrayList.containsAll(new ArrayList<String>(Arrays.asList(new String[]{"v7","v8"})));
        System.out.println("containsAll:"+containsAll);

//    trimToSize()	将 arraylist 中的容量调整为数组中的元素个数 此方法的目的是为了节约内存空间设置的，相当于手动回收内存，对JAVA语言来说意义不大，了解即可。
        System.out.println("before trimToSize:"+myArrayList.size());
        myArrayList.trimToSize();
        System.out.println("after trimToSize:"+myArrayList.size());

//    removeRange()	删除 arraylist 中指定索引之间存在的元素
//        removeRange() 方法是受保护的，所以如果要使用需要继承 ArrayList 类，继承后我们就可以使用 Main 类来创建一个动态数组。
//        removeRange() 方法不常用，我们通常可以使用 ArrayList subList() 和 ArrayList clear() 方法来实现删除元素。
/*
        import java.util.*;
        class Main extends ArrayList<String> {
            public static void main(String[] args) {
                // 创建一个动态数组
                Main sites = new Main();
                sites.add("Google");
                sites.add("Runoob");
                sites.add("Taobao");
                sites.add("Wiki");
                sites.add("Weibo");

                System.out.println("ArrayList : " + sites);

                // 删除从索引值1到3的元素
                sites.removeRange(1, 3);
                System.out.println("删除后的 ArrayList: " + sites);
            }
        }
*/

//    replaceAll()	将给定的操作内容替换掉数组中每一个元素
        myArrayList.replaceAll(e -> e.toUpperCase());
        System.out.println("改用大写"+myArrayList.toString());

//    removeIf()	删除所有满足特定条件的 arraylist 元素
        // 删除名称中带有 Tao 的元素
        myArrayList.removeIf(e -> e.contains("7"));;
        System.out.println("删除后的 ArrayList: " + myArrayList);

//    forEach()	遍历 arraylist 中每一个元素并执行特定操作
        // 将 lambda 表达式传递给 forEach
        myArrayList.forEach(e -> {
            System.out.print(" forEach:"+e);
        });

//    sort()	对 arraylist 元素进行排序
//        myArrayList = ArrayList.sort(myArrayList); //不能放在static方法中
//        System.out.println(Arrays.toString(myArrayList.toArray(new String[myArrayList.size()])));

//    ensureCapacity()	设置指定容量大小的 arraylist
//        我们使用 ensureCapacity() 方法将动态数组大小调整为可以存放 3 个元素。
//        Java 中的 ArrayList 可动态调整大小。也就是说，如果我们在 arraylist 中添加 3 个以上的元素，它将自动调整自身大小，例如：
        // 创建一个动态数组
        ArrayList<String> sites = new ArrayList<>();

        // 设置 arraylist的容量大小
        sites.ensureCapacity(3);

        sites.add("Google");
        sites.add("Runoob");
        sites.add("Taobao");

        // 添加第四个元素
        sites.add("Wiki");

        System.out.println("网站列表: " + sites);

//    clear()	删除 arraylist 中的所有元素
        myArrayList.clear();
        System.out.println(Arrays.toString(myArrayList.toArray(new String[myArrayList.size()])));

    }
}
