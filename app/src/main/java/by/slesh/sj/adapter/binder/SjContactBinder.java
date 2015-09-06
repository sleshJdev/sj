package by.slesh.sj.adapter.binder;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import by.slesh.sj.activity.R;
import by.slesh.sj.database.model.Contact;
import by.slesh.sj.database.repository.ContactRepository;

/**
 * Created by slesh on 05.09.2015.
 */
public class SjContactBinder implements SimpleAdapter.ViewBinder {
    private static final String TAG = SjContactBinder.class.getCanonicalName();

    private ContactRepository contactRepository = new ContactRepository();

    @Override
    public boolean setViewValue(View view, final Object data, String textRepresentation) {
        switch (view.getId()) {
            case R.id.contact_avatar:
                ImageView imageView = ((ImageView) view);
                if (data != null) {
                    imageView.setImageURI(Uri.parse(data.toString()));
                } else {
                    imageView.setImageResource(R.drawable.icon_avatar_small);
                }
                break;
            case R.id.contact_delete_button:
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Contact contact = (Contact) data;
                        Log.d(TAG, "con: " + contactRepository.delete(contact));

                        Log.d(TAG, "delete sj-contact with id " + data);
                    }
                });
                break;
            default:
                return false;
        }

        return true;
    }
}
