package by.slesh.sj.view.adapter.binder;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import by.slesh.sj.activity.R;
import by.slesh.sj.util.SjUtil;

/**
 * Created by slesh on 05.09.2015.
 */
public class PlainContactBinder implements SimpleAdapter.ViewBinder {
    private static final String TAG = PlainContactBinder.class.getCanonicalName();

    public boolean setViewValue(View view, Object data, String textRepresentation) {
        switch (view.getId()) {
            case R.id.plain_contact_avatar:
                ImageView imageView = ((ImageView) view);
                if (data != null && !SjUtil.isBlank(data.toString())) {
                    imageView.setImageURI(Uri.parse(data.toString()));
                } else {
                    imageView.setImageResource(R.drawable.icon_avatar_small);
                }
                break;
            default:
                return false;
        }

        return true;
    }
}
