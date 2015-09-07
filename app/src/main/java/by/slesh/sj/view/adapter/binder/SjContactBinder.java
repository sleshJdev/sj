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
public class SjContactBinder implements SimpleAdapter.ViewBinder {
    private static final String TAG = SjContactBinder.class.getCanonicalName();
    private DeleteContactListener deleteContactListener;

    public interface DeleteContactListener {
        public void performDeleting(Integer id);
    }

    public SjContactBinder(DeleteContactListener deleteContactListener) {
        this.deleteContactListener = deleteContactListener;
    }

    @Override
    public boolean setViewValue(final View view, final Object data, String textRepresentation) {
        switch (view.getId()) {
            case R.id.contact_avatar:
                ImageView imageView = ((ImageView) view);
                if (data != null && !SjUtil.isBlank(data.toString())) {
                    imageView.setImageURI(Uri.parse(data.toString()));
                } else {
                    imageView.setImageResource(R.drawable.icon_avatar_small);
                }
                break;
            case R.id.contact_delete_button:
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (deleteContactListener != null) {
                            deleteContactListener.performDeleting(Integer.valueOf(data.toString()));
                        }
                    }
                });
                break;
            default:
                return false;
        }

        return true;
    }
}
