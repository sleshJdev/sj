package by.slesh.sj.database.repository;

import by.slesh.sj.database.core.Database;
import by.slesh.sj.database.model.Contact;
import by.slesh.sj.database.model.Sms;
import by.slesh.sj.database.repository.core.SpecificRepository;

/**
 * Created by slesh on 05.09.2015.
 */
public class SmsRepository extends SpecificRepository {
    private static final String TAG = SmsRepository.class.getCanonicalName();

    public SmsRepository(Database connector) {
        super(connector);
    }

    private static final String DELETE_FOR_CONTACT_WHERE_CLAUSE =
            Sms.SENDER_ID_FIELD + " = ? ";

    public Integer deleteForContact(Contact contact) {
        String[] arguments = new String[]{Integer.toString(contact.getId())};

        return super.delete(Sms.TABLE_NAME, DELETE_FOR_CONTACT_WHERE_CLAUSE, arguments);
    }

    @Override
    public Integer deleteAll() {
        return super.delete(Sms.TABLE_NAME, null, null);
    }

    public Integer countSmsForContact(Contact contact) {
        return super.count(Sms.TABLE_NAME, Sms.SENDER_ID_FIELD, Integer.toString(contact.getId()));
    }
}
