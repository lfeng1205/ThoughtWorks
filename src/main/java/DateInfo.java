/**
 * Created by cyb on 2017/10/14.
 */
public class DateInfo {

    private int year;

    private int month;

    private int day;

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
        return "DateInfo{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }
}
