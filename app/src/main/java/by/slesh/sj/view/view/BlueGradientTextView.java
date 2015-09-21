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
public class BlueGradientTextView extends TextView {
    private int strokeColor= Color.RED;
    private int strokeWidth=4;

    public BlueGradientTextView( Context context )
    {
        super( context, null, -1 );
        init();
    }
    public BlueGradientTextView( Context context,
                             AttributeSet attrs )
    {
        super( context, attrs, -1 );
        init();
    }
    public BlueGradientTextView( Context context,
                             AttributeSet attrs, int defStyle )
    {
        super( context, attrs, defStyle );
        init();
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

    public void init() {
        // Изменение шрифта
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Days.otf");
        setTypeface(tf);
    }

//    public void onDraw(Canvas canvas){
//        final ColorStateList textColor = getTextColors();
//
//        TextPaint paint = this.getPaint();
//
//        paint.setShader(new LinearGradient(
//                0, 0, 0, getHeight(),
//                Color.WHITE, Color.parseColor("#aed8f3"),
//                Shader.TileMode.MIRROR));
//
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeJoin(Paint.Join.ROUND);
//        paint.setStrokeMiter(10);
//        this.setTextColor(strokeColor);
//        paint.setStrokeWidth(strokeWidth);
//
//        super.onDraw(canvas);
//        paint.setStyle(Paint.Style.FILL);
//
//        setTextColor(textColor);
//
//
//        super.onDraw(canvas);
//    }
}
