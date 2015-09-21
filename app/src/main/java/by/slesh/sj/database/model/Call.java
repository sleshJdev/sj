<<<<<<< HEAD
package by.slesh.sj.database.model;

import android.content.ContentValues;

import by.slesh.sj.database.model.core.Model;

/**
 * Created by slesh on 05.09.2015.
 */
public class Call extends Model {
    public static final String TABLE_NAME = "calls";
    public static final String DATE_FIELD = "date";
    public static final String WHO_CALLED_ID_FIELD = "who_called_id";

    private Integer id;
    private Integer mDate;
    private Integer mWhoCalledId;

    public Call() {
    }

    public Call(Integer date) {
        this.mDate = date;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(_ID, getId());
        values.put(DATE_FIELD, getDate());
        values.put(WHO_CALLED_ID_FIELD, getWhoCalledId());

        return values;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDate() {
        return mDate;
    }

    public void setDate(Integer date) {
        this.mDate = date;
    }

    public Integer getWhoCalledId() {
        return mWhoCalledId;
    }

    public void setWhoCalledId(Integer whoCalledId) {
        this.mWhoCalledId = whoCalledId;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", date=" + mDate +
                ", whoCalledId=" + mWhoCalledId +
                '}';
    }
}
=======
package by.slesh.sj.database.model;

import android.content.ContentValues;

/**
 * Created by slesh on 05.09.2015.
 */
public class Call extends Model<Integer> {
    public static final String TABLE_NAME = "calls";
    public static final String DATE_FIELD = "date";
    public static final String WHO_CALLED_ID_FIELD = "who_called_id";

    private Integer id;
    private Integer mDate;
    private Integer mWhoCalledId;

    public Call() {
    }

    public Call(Integer date, Integer callerId) {
        this.mDate = date;
        this.mWhoCalledId = callerId;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(_ID, getId());
        values.put(DATE_FIELD, getDate());
        values.put(WHO_CALLED_ID_FIELD, getWhoCalledId());

        return values;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDate() {
        return mDate;
    }

    public void setDate(Integer date) {
        this.mDate = date;
    }

    public Integer getWhoCalledId() {
        return mWhoCalledId;
    }

    public void setWhoCalledId(Integer whoCalledId) {
        this.mWhoCalledId = whoCalledId;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", date=" + mDate +
                ", whoCalledId=" + mWhoCalledId +
                '}';
    }
}
>>>>>>> 49d28eb0877fb4d02ca30ad13e6abeecdf62e99e
