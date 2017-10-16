package util;

import model.Car;
import model.DateInfo;
import model.CarSubmitDate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Feng on 2017/10/16.
 */
public class FileUtil {

    private static CarSubmitDate carSubmitDate;

    private static List<String> dataList = null;
    private static Set<Car> records = null;//汽车信息总记录
    private static String[] carRecord;
    private static DateInfo submitDate = null;

    public static List<String> inputData(String filename) {

        List<String> list = new ArrayList<>();

        File file = new File(filename);
        if (!file.isFile()) {
            System.out.println("文件不存在！！！");
        }
        BufferedReader reader = null;
        String inputStr = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((inputStr = reader.readLine()) != null) {
                list.add(inputStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //解析提交时间中的年月日
    public static String getDate(String inputStr) {
        String regEx = "[^0-9]";//匹配指定范围内的数字

        //Pattern是一个正则表达式经编译后的表现模式
        Pattern p = Pattern.compile(regEx);

        // 一个Matcher对象是一个状态机器，它依据Pattern对象做为匹配模式对字符串展开匹配检查。
        Matcher m = p.matcher(inputStr);

        //将输入的字符串中非数字部分用空格取代并存入一个字符串
        String string = m.replaceAll(" ").trim();
        return string;
    }

    //解析数据并封装
    public static CarSubmitDate regexData(String filename) {

        dataList = FileUtil.inputData(filename);
        records = new HashSet<>();
        for (int i = 0; i < dataList.size(); i++) {
            if (i == 0) {
                String dateInfo = getDate(dataList.get(i));
                int year = Integer.parseInt(dateInfo.split(" ")[0]);
                int month = Integer.parseInt(dateInfo.split(" ")[1]);
                int day = Integer.parseInt(dateInfo.split(" ")[2]);
                submitDate = new DateInfo(year, month, day);
            } else {
                DateInfo carDate = null;
                carRecord = dataList.get(i).split("\\|");
                int year = Integer.parseInt(carRecord[1].split("\\/")[0]);
                int month = Integer.parseInt(carRecord[1].split("\\/")[1]);
                int day = Integer.parseInt(carRecord[1].split("\\/")[2]);
                //时间封装成DateInfo
                carDate = new DateInfo(year, month, day);
                //把解析的数据封装成car对象
                Car car = new Car(carRecord[0], carDate, carRecord[2],
                        Integer.parseInt(carRecord[3]), carRecord[4].equals("T") ? true : false);
                records.add(car);
            }
        }
        carSubmitDate = new CarSubmitDate(records, submitDate);
        return carSubmitDate;
    }
}
