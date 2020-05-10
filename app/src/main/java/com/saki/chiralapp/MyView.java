package com.saki.chiralapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {

    public static float x = 100;
    public static float y = 100;

    public MyView(Context context) {
        super(context);


    }

    public MyView(Context context, AttributeSet a) {
        super(context,a);
    }

   @Override
    protected void onDraw(Canvas c){
        Paint p = new Paint();
        p.setARGB(40,70,30,80);
        c.drawCircle(x,y,50,p);
        invalidate();
    }

   /* public void drawCircle(float x, float y){
        Paint p = new Paint();
        p.setARGB(40,70,30,80);
        this..drawCircle(x,y,50,p);
    } */
}


