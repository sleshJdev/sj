package by.slesh.sj.database.repository.core;

import by.slesh.sj.database.core.Database;

/**
 * Created by slesh on 05.09.2015.
 */
public abstract class SpecificRepository extends Repository {
    private static final String COUNT_FIELD = "count(*)";
    private static final String COUNT_SMS_FOR_CONTACT_QUERY_FORMAT =
            " select " + COUNT_FIELD +
            " from %s " +
            " where %s = ?";

    public SpecificRepository(Database connector) {
        super(connector);
    }

    public Integer count(String from, String whereName, String whereValue){
        Integer quantity = 0;
        try{
            String[] arguments = new String[]{whereValue};
            String query = String.format(COUNT_SMS_FOR_CONTACT_QUERY_FORMAT, from, whereName);
            connection = openConnection();
            cursor = connection.rawQuery(query, arguments);
            cursor.moveToFirst();
            quantity = cursor.getInt(cursor.getColumnIndex(COUNT_FIELD));
        } finally {
            close();
        }

        return quantity;
    }
}
