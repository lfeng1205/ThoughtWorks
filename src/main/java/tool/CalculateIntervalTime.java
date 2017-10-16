package tool;

import model.DateInfo;

/**
 * Created by Feng on 2017/10/16.
 * 用于计算两个日期间隔的年数或者月数或者天数
 */
public class CalculateIntervalTime {

    //计算两个日期之间间隔的天数
    public static int calculateIntervalDays(DateInfo purchasingDate, DateInfo submitDate) {

        return submitDate.calculateDays() - purchasingDate.calculateDays();
    }

    //计算两个日期之间间隔的月数
    public static int calculateIntervalMonths(DateInfo purchasingDate, DateInfo submitDate) {
        return submitDate.calculateMonths() - purchasingDate.calculateMonths();
    }

    //计算两个日期之间间隔的年数
    public static int calculateIntervalYears(DateInfo purchasingDate, DateInfo submitDate) {
        return submitDate.getYear() - purchasingDate.getYear();
    }
}
