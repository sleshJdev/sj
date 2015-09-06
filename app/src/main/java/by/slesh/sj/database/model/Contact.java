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
