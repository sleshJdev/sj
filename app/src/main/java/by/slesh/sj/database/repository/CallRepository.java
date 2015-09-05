package by.slesh.sj.database.repository;

import by.slesh.sj.database.core.Database;
import by.slesh.sj.database.model.Call;
import by.slesh.sj.database.model.Contact;
import by.slesh.sj.database.repository.core.SpecificRepository;

/**
 * Created by slesh on 05.09.2015.
 */
public class CallRepository extends SpecificRepository {
    public CallRepository(Database connector) {
        super(connector);
    }

    private static final String DELETE_FOR_CONTACT_WHERE_CLAUSE =
            Call.WHO_CALLED_ID_FIELD +  " = ? ";

    public Integer deleteForContact(Contact contact){
        String[] arguments = new String[]{Integer.toString(contact.getId())};

        return super.delete(Call.TABLE_NAME, DELETE_FOR_CONTACT_WHERE_CLAUSE, arguments);
    }

    @Override
    public Integer deleteAll() {
        return super.delete(Call.TABLE_NAME, null, null);
    }

    public Integer countCallsForContact(Contact contact) {
        return super.count(Call.TABLE_NAME, Call.WHO_CALLED_ID_FIELD, Integer.toString(contact.getId()));
    }
}
