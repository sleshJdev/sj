<<<<<<< HEAD
package by.slesh.sj.database.model;

import android.content.ContentValues;

import by.slesh.sj.database.model.core.Model;

/**
 * Created by slesh on 05.09.2015.
 */
public class Contact extends Model {
    public static final String TABLE_NAME = "contacts";

    public static final String PHONE_FIELD = "phone";

    public static final String ATTRIBUTE_ID = _ID;
    public static final String ATTRIBUTE_NAME = "name";
    public static final String ATTRIBUTE_AVATAR = "avatar";
    public static final String ATTRIBUTE_STATUS = "status";
    public static final String ATTRIBUTE_GRAPHIC = "graphic";
    public static final String ATTRIBUTE_PHONE = PHONE_FIELD;

    private Integer mId;
    private String name;
    private String phone;
    private String avatarPath;
    private int mQuantitySms;
    private int mQuantityCalls;

    public Contact() {
    }

    public Contact(Integer id, String phone) {
        this.mId = id;
        this.phone = phone;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(_ID, getId());
        values.put(PHONE_FIELD, getPhone());

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

    public int getQuantitySms() {
        return mQuantitySms;
    }

    public void setQuantitySms(int sms) {
        this.mQuantitySms = sms;
    }

    public int getQuantityCalls() {
        return mQuantityCalls;
    }

    public void setQuantityCalls(int calls) {
        this.mQuantityCalls = calls;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    @Override
    public String toString() {
        return "{" +
                "mId=" + mId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                ", mSms=" + mQuantitySms +
                ", mCalls=" + mQuantityCalls +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        return mId.equals(contact.mId);

    }

    @Override
    public int hashCode() {
        return mId.hashCode();
    }
}
=======
package by.slesh.sj.database.model;

import android.content.ContentValues;

import java.util.List;

import by.slesh.sj.database.local.StatusResolver;

/**
 * Created by slesh on 05.09.2015.
 */
public class Contact extends Model<Integer> {
    public static final String TABLE_NAME = "contacts";

    public static final String DATE_FIELD = "date";
    public static final String STATUS_FIELD = "status";

    public static final String ATTRIBUTE_ID = _ID;
    public static final String ATTRIBUTE_NAME = "name";
    public static final String ATTRIBUTE_AVATAR = "avatar";
    public static final String ATTRIBUTE_GRAPHIC = "graphic";
    public static final String ATTRIBUTE_PHONE = "phone";
    public static final String ATTRIBUTE_QUANTITY_SMS = "quantity-sms";
    public static final String ATTRIBUTE_QUANTITY_CALLS = "quantity-calls";
    public static final String ATTRIBUTE_STATUS = STATUS_FIELD;
    public static final String ATTRIBUTE_DATE = DATE_FIELD;

    private Integer mId;
    private String mName;
    private String mPhone;
    private Integer mDate;
    private String mStatus;
    private String mAvatarPath;
    private List<Call> mCalls;
    private List<Sms> mSms;
    private Integer mGraphic;

    public Contact() {
    }

    public Contact(Integer id, Integer date) {
        this.mId = id;
        this.mDate = date;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(_ID, getId());
        values.put(DATE_FIELD, getDate());
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

    public List<Call> getCalls() {
        return mCalls;
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

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        this.mStatus = status;
    }

    public Integer getGraphic() {
        return StatusResolver.getGraphic(getQuantityCalls() + getQuantitySms());
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
>>>>>>> 49d28eb0877fb4d02ca30ad13e6abeecdf62e99e
