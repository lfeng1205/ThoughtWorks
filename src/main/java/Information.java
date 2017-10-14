import java.util.Set;

/**
 * Created by cyb on 2017/10/14.
 */
public class Information {

    private Set<Car> set;

    private DateInfo submitDate;

    public Information(Set<Car> set, DateInfo submitDate) {
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
