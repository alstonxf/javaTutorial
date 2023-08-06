package package01.test2;

import java.util.TreeMap;

public class TreeMapExample {
    public static void main(String[] args) {
        // 创建一个 TreeMap 对象
        TreeMap<String, Integer> scores = new TreeMap<>();

        // 添加键值对
        scores.put("Alice", 85);
        scores.put("Bob", 90);
        scores.put("Charlie", 80);
        scores.put("David", 95);
        scores.put("Alice", 92); // 重复键，新值将覆盖旧值

        // 遍历键值对
        for (String name : scores.keySet()) {
            int score = scores.get(name);
            System.out.println(name + ": " + score);
        }

        // 删除键值对
        scores.remove("Bob");

        // 获取键值对
        System.out.println("David 的分数：" + scores.get("David"));
        System.out.println("第一个键：" + scores.firstKey());
        System.out.println("最后一个键：" + scores.lastKey());
    }
}

