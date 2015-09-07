package by.slesh.sj.view.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by arseniy on 20/06/15.
 */
public class PFdinMediumFontGradientBlue extends TextView {
    public PFdinMediumFontGradientBlue(Context context) {
        super(context);
        init();
    }

    public PFdinMediumFontGradientBlue(Context context,
                                       AttributeSet attrs)
    {
        super( context, attrs, -1 );
        init();
    }
    public PFdinMediumFontGradientBlue(Context context,
                                       AttributeSet attrs, int defStyle)
    {
        super( context, attrs, defStyle );
        init();
    }

    public void init() {
        // Изменение шрифта
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/pfdintextcondpro-medium.ttf");
        setTypeface(tf);
    }

    @Override
    protected void onLayout( boolean changed,
                             int left, int top, int right, int bottom )
    {
        super.onLayout(changed, left, top, right, bottom);
        if(changed)
        {
            TextPaint paint = getPaint();
            paint.setShader(new LinearGradient(
                    0, 0, 0, getHeight(),
                    Color.parseColor("#2d9ed1"), Color.parseColor("#0e63ad"),
                    Shader.TileMode.CLAMP));
        }
    }
}
