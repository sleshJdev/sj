package by.slesh.sj.view.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by arseniy on 20/06/15.
 */
public class PFdinMediumFont extends TextView {
    public PFdinMediumFont(Context context) {
        super(context);
        init();
    }

    public PFdinMediumFont( Context context,
                      AttributeSet attrs )
    {
        super( context, attrs, -1 );
        init();
    }
    public PFdinMediumFont( Context context,
                      AttributeSet attrs, int defStyle )
    {
        super( context, attrs, defStyle );
        init();
    }

    public void init() {
        // Изменение шрифта
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/pfdintextcondpro-medium.ttf");
        setTypeface(tf);
    }
}
