package test;

import tool.CarHandling;
import model.CarSubmitDate;
import util.FileUtil;

import java.util.*;

/**
 * Created by cyb on 2017/10/14.
 * 测试类
 */
public class CarTest {
    public static void main(String[] args) {
        String filname = "src/main/input.txt";
        process(filname);
    }

    //程序的入口
    private static void process(String filname) {
        CarSubmitDate carSubmitDate = FileUtil.regexData(filname);
        List<Map<String, Set<String>>> tdwList =
                CarHandling.statisticalMagnitude(carSubmitDate.getSet(), carSubmitDate.getSubmitDate());
        System.out.println("Reminder");
        System.out.println("==================");

        System.out.println();
        System.out.println("* Time-related maintenance coming soon...");
        outputData(tdwList.get(0));

        System.out.println();
        System.out.println("* Distance-related maintenance coming soon...");
        outputData(tdwList.get(1));

        System.out.println();
        System.out.println("*Write-off coming soon...");
        outputData(tdwList.get(2));
    }

    //按照指定的格式输出数据
    private static void outputData(Map<String, Set<String>> datas) {
        for (Map.Entry<String, Set<String>> entry : datas.entrySet()) {
            System.out.print(entry.getKey() + "：" + entry.getValue().size() + "（");
            int count = 1;
            for (String carNo : entry.getValue()) {
                System.out.print(carNo);
                if (count != entry.getValue().size()) {
                    System.out.print(",");
                }
                count++;
            }
            System.out.println("）");
        }
    }
}