package com.saki.chiralapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class MyView extends View {

    public ArrayList<AnchorPoint> DrawList;

    public MyView(Context context) {
        super(context);


    }

    public MyView(Context context, AttributeSet a) {
        super(context,a);
    }

   @Override
    protected void onDraw(Canvas c) {
       Paint p = new Paint();
       p.setARGB(70, 0, 0, 0);
       Paint dashPaint = new Paint();
       dashPaint.setARGB(70, 0, 0, 255);
       Paint wedgePaint = new Paint();
       wedgePaint.setARGB(70, 255, 0, 0);
       if (DrawList != null) {
           for (AnchorPoint a : DrawList) {
               c.drawCircle(a.getX(), a.getY(), 5, p);
               if (a.getDown() != null) {
                   c.drawLine(a.getX(), a.getY(), a.getDown().getX(), a.getDown().getY(), dashPaint);
               } else if (a.getUp() != null) {
                   c.drawLine(a.getX(), a.getY(), a.getUp().getX(), a.getUp().getY(), wedgePaint);
               } else if (a.getLeft() != null) {
                   c.drawLine(a.getX(), a.getY(), a.getLeft().getX(), a.getLeft().getY(), p);
               }
           }

       }
           invalidate();
       }
}





