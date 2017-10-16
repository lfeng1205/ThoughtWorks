package model;

import java.util.Set;

/**
 * Created by cyb on 2017/10/14.
 * 此类封装汽车信息和提交日期
 *
 */
public class CarSubmitDate {

    private Set<Car> set;

    private DateInfo submitDate ;

    public CarSubmitDate(Set<Car> set, DateInfo submitDate) {
        this.set = set;
        this.submitDate = submitDate;
    }

    public Set<Car> getSet() {
        return set;
    }

    public void setSet(Set<Car> set) {
        this.set = set;
    }

    public DateInfo getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(DateInfo submitDate) {
        this.submitDate = submitDate;
    }
}
