package by.slesh.sj.database.model;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Model model = (Model) o;

        return getId().equals(model.getId());

    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
