package DEMO.E1;

import java.util.Scanner;

public class exercise5 {
    public static void main(String[] args) {
        /**
         * 5.根据输入的年份、产品类型和随机数产生固定资产编号
         *
         * 即：固定资产编号=年份+0+产品类型+3位随机数
         *
         * 程序运行流程：
         * 请输入年份：
         * 请选择产品类型（1.台式机 2.笔记本 3.其他）：
         *
         * 生成3位随机数    最后显示固定资产编号
         *
         */
        Scanner scn = new Scanner(System.in);
        System.out.println("请输入年份：");
        String year = String.valueOf(scn.nextLine());
        String pro = getPro();
        String id = createId(year,pro);
        System.out.println("id="+id);

    }

    public static String createId(String year,String product){
        String id = null;
        id = year+product+ String.valueOf(Math.random()*10000000).substring(0,4);
        return id;
    }

    public static String getPro(){
        boolean status = false;
        String pro = null;
        while (status!=true){
            Scanner scn = new Scanner(System.in);
            System.out.println("请选择产品类型（1.台式机 2.笔记本 3.其他）： ");
            pro = String.valueOf(scn.nextLine());
            if (pro.equals("1")){
                pro = "台式机";
                status = true;
            }else if (pro.equals("2")){
                pro = "笔记本";
                status = true;
            }else if (pro.equals("3")){
                pro="其他";
                status = true;
            }else{
                System.out.println("error:请重新输入");
            }
        }
        return pro;
    }

}
