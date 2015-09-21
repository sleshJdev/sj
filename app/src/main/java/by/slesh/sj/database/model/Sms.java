<<<<<<< HEAD
package by.slesh.sj.database.model;

import android.content.ContentValues;

import by.slesh.sj.database.model.core.Model;

/**
 * Created by yauheni.putsykovich on 05.09.2015.
 */
public class Sms extends Model {
    public static final String TABLE_NAME = "sms";
    public static final String DATE_FIELD = "date";
    public static final String SENDER_ID_FIELD = "sender_id";

    private Integer mId;
    private Integer mDate;
    private Integer mSenderId;

    public Sms() {
    }

    public Sms(Integer date, Integer senderId) {
        this.mDate = date;
        this.mSenderId = senderId;
    }

    @Override
    public ContentValues getContentValues(){
        ContentValues values = new ContentValues();
        values.put(_ID, getId());
        values.put(DATE_FIELD, getDate());
        values.put(SENDER_ID_FIELD, getSenderId());

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

    public Integer getDate() {
        return mDate;
    }

    public void setDate(Integer date) {
        this.mDate = date;
    }

    public Integer getSenderId() {
        return mSenderId;
    }

    public void setSenderId(Integer senderId) {
        this.mSenderId = senderId;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + mId +
                ", date=" + mDate +
                ", senderId=" + mSenderId +
                '}';
    }
}
=======
package by.slesh.sj.database.model;

import android.content.ContentValues;

/**
 * Created by yauheni.putsykovich on 05.09.2015.
 */
public class Sms extends Model<Integer> {
    public static final String TABLE_NAME = "sms";
    public static final String DATE_FIELD = "date";
    public static final String SENDER_ID_FIELD = "sender_id";

    private Integer mId;
    private Integer mDate;
    private Integer mSenderId;

    public Sms() {
    }

    public Sms(Integer date, Integer senderId) {
        this.mDate = date;
        this.mSenderId = senderId;
    }

    @Override
    public ContentValues getContentValues(){
        ContentValues values = new ContentValues();
        values.put(_ID, getId());
        values.put(DATE_FIELD, getDate());
        values.put(SENDER_ID_FIELD, getSenderId());

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

    public Integer getDate() {
        return mDate;
    }

    public void setDate(Integer date) {
        this.mDate = date;
    }

    public Integer getSenderId() {
        return mSenderId;
    }

    public void setSenderId(Integer senderId) {
        this.mSenderId = senderId;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + mId +
                ", date=" + mDate +
                ", senderId=" + mSenderId +
                '}';
    }
}
>>>>>>> 49d28eb0877fb4d02ca30ad13e6abeecdf62e99e
