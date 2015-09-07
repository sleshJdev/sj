package by.slesh.sj.database.model;

import android.content.ContentValues;

import java.util.List;

/**
 * Created by slesh on 05.09.2015.
 */
public class Contact extends Model<Integer> {
    public static final String TABLE_NAME = "contacts";

    public static final String PHONE_FIELD = "phone";
    public static final String DATE_FIELD = "date";
    public static final String STATUS_FIELD = "status";

    public static final String ATTRIBUTE_ID = _ID;
    public static final String ATTRIBUTE_NAME = "name";
    public static final String ATTRIBUTE_AVATAR = "avatar";
    public static final String ATTRIBUTE_GRAPHIC = "graphic";
    public static final String ATTRIBUTE_QUANTITY_SMS = "quantity-sms";
    public static final String ATTRIBUTE_QUANTITY_CALLS = "quantity-calls";
    public static final String ATTRIBUTE_STATUS = STATUS_FIELD;
    public static final String ATTRIBUTE_DATE = DATE_FIELD;
    public static final String ATTRIBUTE_PHONE = PHONE_FIELD;

    private Integer mId;
    private String mName;
    private String mPhone;
    private Integer mDate;
    private String mStatus;
    private String mAvatarPath;
    private List<Call> mCalls;
    private List<Sms> mSms;

    public Contact() {
    }

    public Contact(Integer id, String phone) {
        this.mId = id;
        this.mPhone = phone;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(_ID, getId());
        values.put(DATE_FIELD, getDate());
        values.put(PHONE_FIELD, getPhone());
        values.put(STATUS_FIELD, getStatus());

        return values;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public void setCalls(List<Call> calls) {
        this.mCalls = calls;
    }

    public List<Sms> getSms() {
        return mSms;
    }

    public void setSms(List<Sms> sms) {
        this.mSms = sms;
    }

    public int getQuantitySms() {
        return getSms() == null ? 0 : getSms().size();
    }

    public int getQuantityCalls() {
        return getCalls() == null ? 0 : getCalls().size();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        this.mPhone = phone;
    }

    public Integer getDate() {
        return mDate;
    }

    public void setDate(Integer date) {
        this.mDate = date;
    }

    public List<Call> getCalls() {
        return mCalls;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        this.mStatus = status;
    }

    public String getAvatarPath() {
        return mAvatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.mAvatarPath = avatarPath;
    }

    @Override
    public String toString() {
        return "{" +
                "mId=" + mId +
                ", name='" + mName + '\'' +
                ", phone='" + mPhone + '\'' +
                ", status='" + mStatus + '\'' +
                ", date='" + mDate + '\'' +
                ", avatarPath='" + mAvatarPath + '\'' +
                ", sms=" + getQuantitySms() +
                ", calls=" + getQuantityCalls() +
                '}';
    }
}
