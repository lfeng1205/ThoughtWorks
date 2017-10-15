import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
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
        List<Map<String, Set<String>>> tdwList = statisticalMagnitude(information.getSet(), information.getSubmitDate());
        System.out.println("Reminder");
        System.out.println("==================");
        System.out.println("* Time-related maintenance coming soon...");
        outputData(tdwList.get(0));
        System.out.println("* Distance-related maintenance coming soon...");
        outputData(tdwList.get(1));
        System.out.println("*Write-off coming soon...");
        outputData(tdwList.get(2));

    }

    //统计不同情况下的车辆保养情况
    public static List<Map<String, Set<String>>> statisticalMagnitude(Set<Car> records, DateInfo submitDate) {
        List<Map<String, Set<String>>> list = new ArrayList<>();
        Map<String, Set<String>> timeRelatedMaintances = new TreeMap<>();
        Map<String, Set<String>> distanceRelatedMaintenance = new TreeMap<>();
        Map<String, Set<String>> writeOff = new TreeMap<>();

        for (Car car : records) {
            //如果汽车是报废汽车,则直接跳过此车辆
            if (isOff(car.getDateInfo(), submitDate, car.isRepair())) {
                continue;
                //如果即将报废，则写进提醒报废记录
            } else if (isWirteOff(car.getDateInfo(), submitDate, car.isRepair())) {
                inserData(writeOff, car);
            } else if (isDistanceRelatedMaintances(car.getKilometer())) {//如果里程数即将到达保养要求，写入对应记录
                inserData(distanceRelatedMaintenance, car);
            } else if (isTimeRelatedMaintances(car.getDateInfo(), submitDate, car.isRepair())) {
                inserData(timeRelatedMaintances, car);
            }
        }

        list.add(timeRelatedMaintances);
        list.add(distanceRelatedMaintenance);
        list.add(writeOff);

        return list;
    }

    //是否即将到达保养时间
    private static boolean isTimeRelatedMaintances(DateInfo purchasingDate, DateInfo submitDate, boolean isRepair) {
        int intervalYears = calculateIntervalYears(purchasingDate, submitDate);
        int intervalMonths = calculateIntervalMonths(purchasingDate, submitDate);
        //车辆经过大修
        if (isRepair) {
            if (intervalMonths % 3 == 2) {
                return true;
            }
            if (intervalMonths % 3 == 0 && purchasingDate.getDay() >= submitDate.getDay()) {
                return true;
            }
        } else if (intervalYears < 3) {//车辆没有大修过，且购买时间小于三年内
            if (intervalMonths % 12 == 11) {
                return true;
            }
            if (intervalMonths % 12 == 0 && purchasingDate.getDay() >= submitDate.getDay()) {
                return true;
            }
        } else {//车辆没有大修过，且购买时间大于等于三年
            if (intervalMonths % 6 == 5) {
                return true;
            }
            if (intervalMonths % 6 == 0 && purchasingDate.getDay() >= submitDate.getDay()) {
                return true;
            }
        }
        return false;
    }

    //是否即将到达保养里程
    private static boolean isDistanceRelatedMaintances(int kilometer) {
        int count = kilometer % 10000;
        //汽车每跑10000公里保养一次，距离10000公里还有500公里开始提醒
        if ((count == 0 && kilometer > 0) || count >= 9500) {
            return true;
        } else {
            return false;
        }
    }

    //插入数据
    private static void inserData(Map<String, Set<String>> map, Car car) {
        Set<String> set = new TreeSet<>();
        if (!map.containsKey(car.getCarName())) {
            set.add(car.getCarNumber());
            map.put(car.getCarName(), set);
        } else {
            map.get(car.getCarName()).add(car.getCarNumber());
        }
    }

    //判断汽车是否即将报废
    private static boolean isWirteOff(DateInfo purchasingDate, DateInfo submitDate, boolean isRepair) {
        //如果当前日期即将报废，则在这个日期的下个月的最后一天一定是已经达到报废天数
        //所以需要用当前使用天数，加上到下个月月底的天数，通过判断下个月底是否报废，决定现在是否需要提醒
        int year = submitDate.getYear();
        int month = submitDate.getMonth();
        int day = submitDate.getDay();

        if (submitDate.getMonth() == 12) {
            year++;
            month = 1;
            day = 31;
        } else {
            month++;
            if (submitDate.getMonth() == 1) {

                if (submitDate.isLeapyear()) {
                    day = 29;
                } else {
                    day = 28;
                }
            }
            if (submitDate.getMonth() == 10) {
                day = 30;
            }
            if (submitDate.getMonth() == 11) {
                day = 31;
            }

        }

        DateInfo submitDateNext = new DateInfo(year, month, day);
        if (isOff(purchasingDate, submitDateNext, isRepair)) {
            return true;
        }
        return false;
    }

    //判断车辆是否报废
    private static boolean isOff(DateInfo purchasingDate, DateInfo submitDate, boolean isRepair) {
        int intervalDays = calculateIntervalDays(purchasingDate, submitDate);
        if (isRepair && intervalDays >= 1095) {
            return true;
        } else if (intervalDays >= 2190) {
            return true;
        } else {
            return false;
        }
    }

    //计算两个日期之间间隔的天数
    private static int calculateIntervalDays(DateInfo purchasingDate, DateInfo submitDate) {

        return submitDate.calculateDays() - purchasingDate.calculateDays();
    }

    //计算两个日期之间间隔的月数
    private static int calculateIntervalMonths(DateInfo purchasingDate, DateInfo submitDate) {
        return submitDate.calculateMonths() - purchasingDate.calculateMonths();
    }

    //计算两个日期之间间隔的年数
    private static int calculateIntervalYears(DateInfo purchasingDate, DateInfo submitDate) {
        return submitDate.getYear() - purchasingDate.getYear();
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
                    //System.out.println(submitDate);
                    line++;
                } else {
                    DateInfo carDate = null;
                    car_record = inputStr.split("\\|");
                    int year = Integer.parseInt(car_record[1].split("\\/")[0]);
                    int month = Integer.parseInt(car_record[1].split("\\/")[1]);
                    int day = Integer.parseInt(car_record[1].split("\\/")[2]);
                    //时间封装成DateInfo
                    carDate = new DateInfo(year, month, day);
                    //把解析的数据封装成car对象
                    Car car = new Car(car_record[0], carDate, car_record[2],
                            Integer.parseInt(car_record[3]), car_record[4].equals("T") ? true : false);
                    records.add(car);
                    //System.out.println(car);
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
}