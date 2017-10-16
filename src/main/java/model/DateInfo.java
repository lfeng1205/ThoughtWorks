package model;

/**
 * Created by cyb on 2017/10/14.
 * 日期信息类，封装日期的年月日等信息
 */
public class DateInfo {

    private int year;

    private int month;

    private int day;

    //判断是不是闰年
    public boolean isLeapyear() {
        return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
    }

    public DateInfo(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "model.DateInfo{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }

    public int calculateDays() {
        int[] days = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int countDays = year * 365;
        //如果是闰年，天数是366
        countDays += (year - 1) / 4 + 1;
        countDays -= (year - 1) / 100 + 1;
        countDays += (year - 1) / 400 + 1;

        //求从这年元旦到 month 月 day 日共有多少天
        for (int i = 1; i < month; ++i) {
            countDays += days[i];
        }
        if (month > 2 && isLeapyear()) {
            countDays++;
        }
        countDays += day;
        return countDays;
    }

    //计算从0000/00/00一共有一个月
    public int calculateMonths() {
        return year * 12 + month;
    }
}
