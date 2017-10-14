import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cyb on 2017/10/14.
 */
public class CarTest {
    public static void main(String[] args) {
        String filname = "d:\\input.txt";
        process(filname);
    }

    private static void process(String filname) {
        Information information = inputData(filname);
    }

    //输入数据并进行解析
    private static Information inputData(String filname) {

        Information information = null;

        File file = new File(filname);
        BufferedReader reader = null;
        String inputStr = null;
        int line = 0;

        Set<Car> records = new HashSet<>();//汽车信息总记录
        String[] car_record = new String[5];

        DateInfo submitDate = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((inputStr = reader.readLine()) != null) {
                if (line == 0) {
                    String dateInfo = getDate(inputStr);
                    int year = Integer.parseInt(dateInfo.split(" ")[0]);
                    int month = Integer.parseInt(dateInfo.split(" ")[1]);
                    int day = Integer.parseInt(dateInfo.split(" ")[2]);
                    submitDate = new DateInfo(year, month, day);
                    System.out.println(submitDate);
                    line++;
                } else {
                    DateInfo carDate = null;
                    car_record = inputStr.split("\\|");
                    int year = Integer.parseInt(car_record[1].split("\\/")[0]);
                    int month = Integer.parseInt(car_record[1].split("\\/")[1]);
                    int day = Integer.parseInt(car_record[1].split("\\/")[2]);
                    carDate = new DateInfo(year, month, day);
                    Car car = new Car(car_record[0], carDate, car_record[2],
                            Integer.parseInt(car_record[3]), car_record[4].equals("T") ? true : false);
                    records.add(car);
                    System.out.println(car);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        information = new Information(records, submitDate);

        return information;
    }

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
}