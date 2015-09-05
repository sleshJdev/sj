package by.slesh.sj.database.core;

import android.database.sqlite.SQLiteDatabase;

import by.slesh.sj.database.model.Call;
import by.slesh.sj.database.model.Contact;
import by.slesh.sj.database.model.Sms;

/**
 * Created by slesh on 05.09.2015.
 */
public class DatabaseSchemaCreator {
    public static final String CREATE_TABLE_CONTACTS_QUERY =
            " create table " + Contact.TABLE_NAME + " ( " +
                    Contact._ID + " integer primary key, " +
                    Contact.PHONE_FIELD + " varchar(15) not null " +
            " ) ";

    public static final String CREATE_TABLE_CALLS_QUERY =
            " create table " + Call.TABLE_NAME + " ( " +
                Call._ID + " integer primary key, " +
                Call.DATE_FIELD + " integer default 0, " +
                Call.WHO_CALLED_ID_FIELD + " integer not null, " +
                    " foreign key (" + Call.WHO_CALLED_ID_FIELD + ") references " + Contact.TABLE_NAME + "(" + Contact._ID + ")" +
            " ) ";

    public static final String CREATE_TABLE_SMS_QUERY =
            " create table " + Sms.TABLE_NAME + " ( " +
                Sms._ID + " integer primary key, " +
                Sms.DATE_FIELD + " integer default 0, " +
                Sms.SENDER_ID_FIELD + " integer not null, " +
                    " foreign key (" + Sms.SENDER_ID_FIELD + ") references " + Contact.TABLE_NAME + "(" + Contact._ID + ")" +
            " ) ";

    public static final void initialize(SQLiteDatabase connection){
        connection.execSQL(CREATE_TABLE_CONTACTS_QUERY);
        connection.execSQL(CREATE_TABLE_CALLS_QUERY);
        connection.execSQL(CREATE_TABLE_SMS_QUERY);
    }
}
