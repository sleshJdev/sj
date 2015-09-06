package by.slesh.sj.database.model.core;

import android.content.ContentValues;
import android.provider.BaseColumns;

/**
 * Created by slesh on 05.09.2015.
 */
public abstract class Model<K> implements BaseColumns {
    public abstract ContentValues getContentValues();

    public abstract String getTableName();

    public String getIdName() {
        return _ID;
    }

    public abstract K getId();
}
