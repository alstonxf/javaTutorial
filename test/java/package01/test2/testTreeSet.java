package package01.test2;

import java.util.TreeSet;

public class testTreeSet {
    public static void main(String[] args) {
        // 创建一个 TreeSet 对象
        TreeSet<Integer> numbers = new TreeSet<>();

        // 添加元素
        numbers.add(5);
        numbers.add(3);
        numbers.add(8);
        numbers.add(1);
        numbers.add(5); // 重复元素，将被忽略

        // 遍历元素
        for (Integer number : numbers) {
            System.out.println(number);
        }

        // 删除元素
        numbers.remove(3);

        // 检索元素
        System.out.println("第一个元素：" + numbers.first());
        System.out.println("最后一个元素：" + numbers.last());
    }
}
