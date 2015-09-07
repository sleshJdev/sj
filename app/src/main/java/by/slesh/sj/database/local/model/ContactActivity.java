package by.slesh.sj.database.local.model;

import java.util.concurrent.TimeUnit;

import by.slesh.sj.database.local.StatusResolver;
import by.slesh.sj.util.DateUtil;

/**
 * Created by slesh on 06.09.2015.
 */
public class ContactActivity {
    private int mQuantityCalls;
    private int mQuantitySms;
    private int mBeginPeriod;
    private int mEndPeriod;

    public ContactActivity() {
    }

    public ContactActivity(int beginPeriod, int endPeriod) {
        this.mBeginPeriod = beginPeriod;
        this.mEndPeriod = endPeriod;
    }

    public int getPoints() {
        return getQuantityCalls() + getQuantitySms();
    }

    public int getQuantityCalls() {
        return mQuantityCalls;
    }

    public int getQuantitySms() {
        return mQuantitySms;
    }

    public void plusSms() {
        ++mQuantitySms;
    }

    public void plusCall() {
        ++mQuantityCalls;
    }

    public int begin() {
        return mBeginPeriod;
    }

    public int end() {
        return mEndPeriod;
    }

    public String beginAsString() {
        return DateUtil.getDate(TimeUnit.SECONDS.toMillis(begin()));
    }

    public String endAsString() {
        return DateUtil.getDate(TimeUnit.SECONDS.toMillis(end()));
    }

    @Override
    public String toString() {
        return "{" +
                "quantityCalls=" + mQuantityCalls +
                ", quantitySms=" + mQuantitySms +
                ", beginPeriod=" + beginAsString() +
                ", endPeriod=" + endAsString() +
                '}';
    }

    public Status getStatus() {
        return StatusResolver.getStatus(getPoints());
    }
}
