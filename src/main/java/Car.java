import java.util.Date;

/**
 * Created by cyb on 2017/10/14.
 */
public class Car {

    private String carNumber;//车牌号

    private DateInfo dateInfo;//购买日期

    private String carName;//车名

    private int kilometer;//目前运行公里数

    private boolean isRepair;//是否大修



    public Car(String carNumber, DateInfo dateInfo, String carName, int kilometer, boolean isRepair) {
        this.carNumber = carNumber;
        this.dateInfo = dateInfo;
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
        return dateInfo;
    }

    public void setDateInfo(DateInfo dateInfo) {
        this.dateInfo = dateInfo;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carNumber='" + carNumber + '\'' +
                ", dateInfo=" + dateInfo +
                ", carName='" + carName + '\'' +
                ", kilometer=" + kilometer +
                ", isRepair=" + isRepair +
                '}';
    }
}
