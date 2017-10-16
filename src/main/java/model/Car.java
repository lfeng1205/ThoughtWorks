package model;

/**
 * Created by cyb on 2017/10/14.
 * 汽车类，封装汽车的车牌号，购买日期等属性
 */
public class Car {

    private String carNumber;//车牌号

    private DateInfo purchaseDate;//购买日期

    private String carName;//车名

    private int kilometer;//目前运行公里数

    private boolean isRepair;//是否大修


    public Car(String carNumber, DateInfo purchaseDate, String carName, int kilometer, boolean isRepair) {
        this.carNumber = carNumber;
        this.purchaseDate = purchaseDate;
        this.carName = carName;
        this.kilometer = kilometer;
        this.isRepair = isRepair;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }


    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getKilometer() {
        return kilometer;
    }

    public void setKilometer(int kilometer) {
        this.kilometer = kilometer;
    }

    public boolean isRepair() {
        return isRepair;
    }

    public void setRepair(boolean repair) {
        isRepair = repair;
    }

    public DateInfo getDateInfo() {
        return purchaseDate;
    }

    public void setDateInfo(DateInfo dateInfo) {
        this.purchaseDate = dateInfo;
    }

    @Override
    public String toString() {
        return "model.Car{" +
                "carNumber='" + carNumber + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", carName='" + carName + '\'' +
                ", kilometer=" + kilometer +
                ", isRepair=" + isRepair +
                '}';
    }
}
