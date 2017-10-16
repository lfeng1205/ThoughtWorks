package tool;

import model.Car;
import model.DateInfo;

import java.util.*;

/**
 * Created by Feng on 2017/10/16.
 * 用于处理汽车的三种情况
 */
public class CarHandling {

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

    //统计不同情况下的车辆保养情况
    public static List<Map<String, Set<String>>> statisticalMagnitude(Set<Car> records, DateInfo submitDate) {
        List<Map<String, Set<String>>> list = new ArrayList<>();
        //定期保养
        Map<String, Set<String>> timeRelatedMaintances = new TreeMap<>();
        //每一万公里保养
        Map<String, Set<String>> distanceRelatedMaintenance = new TreeMap<>();
        //提醒报废
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
                //如果达到定时保养要求，写入对应记录
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
        int intervalYears = CalculateIntervalTime.calculateIntervalYears(purchasingDate, submitDate);
        int intervalMonths = CalculateIntervalTime.calculateIntervalMonths(purchasingDate, submitDate);
        //车辆经过大修
        if (isRepair) {
            //每三个月保养一次，提前一个月提醒
            if (intervalMonths % 3 == 2 || (intervalMonths % 3 == 0 && purchasingDate.getDay() >= submitDate.getDay())) {
                return true;
            }
        } else if (intervalYears < 3) {//车辆没有大修过，且购买时间小于三年内
            //每12个月保养一次，提前一个月提醒
            if (intervalMonths % 12 == 11 || intervalMonths % 12 == 0 && purchasingDate.getDay() >= submitDate.getDay()) {
                return true;
            }
        } else {//车辆没有大修过，且购买时间大于等于三年
            //每6个月保养一次，提前一个月提醒
            if (intervalMonths % 6 == 5 || intervalMonths % 6 == 0 && purchasingDate.getDay() >= submitDate.getDay()) {
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
            switch (submitDate.getMonth()) {
                case 1:
                    day = submitDate.isLeapyear() ? 29 : 28;
                    break;
                case 2:
                case 4:
                case 6:
                case 7:
                case 9:
                case 11:
                    day = 31;
                    break;
                case 3:
                case 5:
                case 8:
                case 10:
                    day = 30;
                    break;
                default:
                    break;
            }

        }

        //判断车辆下个月是否报废
        DateInfo submitDateNext = new DateInfo(year, month, day);
        if (isOff(purchasingDate, submitDateNext, isRepair)) {
            return true;
        }
        return false;
    }

    //判断车辆是否报废
    private static boolean isOff(DateInfo purchasingDate, DateInfo submitDate, boolean isRepair) {
        int intervalDays = CalculateIntervalTime.calculateIntervalDays(purchasingDate, submitDate);
        if (isRepair && intervalDays >= 1095) {
            return true;
        } else if (intervalDays >= 2190) {
            return true;
        } else {
            return false;
        }
    }
}
