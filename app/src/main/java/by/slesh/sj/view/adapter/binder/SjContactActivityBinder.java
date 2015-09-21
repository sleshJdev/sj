package by.slesh.sj.view.adapter.binder;

import android.app.Activity;
import android.view.View;
import android.widget.SimpleAdapter;

import by.slesh.sj.activity.R;
import by.slesh.sj.view.view.Share;

/**
 * Created by slesh on 06.09.2015.
 */
public class SjContactActivityBinder implements SimpleAdapter.ViewBinder {
    @Override
    public boolean setViewValue(View view, Object data, String textRepresentation) {
        switch (view.getId()) {
            case R.id.share:
                final String message = (String) data;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Share.share("Шкала Жалимова", message, "https://play.google.com/store", (Activity) v.getContext());
                    }
                });
                break;
            default:
                return false;
        }
        return false;
    }
}
