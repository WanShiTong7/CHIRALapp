package com.saki.chiralapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class SketchPad extends View {

    public ArrayList<AnchorPoint> DrawList;

    public SketchPad(Context context) {
        super(context);


    }

    public SketchPad(Context context, AttributeSet a) {
        super(context,a);
    }

   @Override
    protected void onDraw(Canvas c) {

       Paint p = new Paint();
       p.setARGB(255, 0, 0, 0);
       p.setStrokeWidth(10);
       //c.drawArc(10,10,400,400,270,10,true,p);
       Paint dashPaint = new Paint();
       dashPaint.setARGB(255, 0, 0, 255);
       dashPaint.setStrokeWidth(10);
       dashPaint.setStrokeCap(Paint.Cap.SQUARE);
       Paint wedgePaint = new Paint();
       wedgePaint.setARGB(255, 255, 0, 0);
       wedgePaint.setStrokeWidth(10);
       wedgePaint.setStrokeCap(Paint.Cap.SQUARE);
       //todo: make sweepAngle variable inversely proportional to distance d
       int sweepAngle = 5;
      /* Paint dashPaint2 = new Paint();
       dashPaint2.setARGB(255, 0, 255, 0);
       dashPaint2.setStrokeWidth(10);
       Paint wedgePaint2 = new Paint();
       wedgePaint2.setARGB(255, 255, 255, 0);
       wedgePaint2.setStrokeWidth(10);*/

      //Draw the structure
       if (DrawList != null) {
           for (AnchorPoint a : DrawList) {
               //todo: make dot appear only on first instance
               c.drawCircle(a.getX(), a.getY(), 5, p);

               if (a.getDown() != null && a.isDashStart()==true) {
                   c.drawLine(a.getX(), a.getY(), a.getDown().getX(), a.getDown().getY(), dashPaint);
               }

               if (a.getUp() != null && a.isWedgeStart()==true) {
                   //c.drawLine(a.getX(), a.getY(), a.getUp().getX(), a.getUp().getY(), wedgePaint);
                   float d = (float) java.lang.Math.hypot(a.getX()-a.getUp().getX(),a.getY()-a.getUp().getY());
                   //arc out into quadrant 4
                   if(a.getUp().getX()>a.getX() && a.getUp().getY()<a.getY()){
                       float startAngle = (float) (360-((180/ Math.PI)* (java.lang.Math.asin((a.getY()-a.getUp().getY())/d))));
                       c.drawArc(a.getX()-d,a.getY()-d,a.getX()+d,a.getY()+d,startAngle-sweepAngle,2*sweepAngle,true,p);
                   } else if (a.getUp().getX()<a.getX() && a.getUp().getY()<a.getY()){
                       float startAngle = (float) (180+((180/ Math.PI)* (java.lang.Math.asin((a.getY()-a.getUp().getY())/d))));
                       c.drawArc(a.getX()-d,a.getY()-d,a.getX()+d,a.getY()+d,startAngle-sweepAngle,2*sweepAngle,true,p);
                   } else if (a.getUp().getX()<a.getX() && a.getUp().getY()>a.getY()) {
                       float startAngle = (float) (180-((180/ Math.PI)* (java.lang.Math.asin((a.getUp().getY()-a.getY())/d))));
                       c.drawArc(a.getX()-d,a.getY()-d,a.getX()+d,a.getY()+d,startAngle-sweepAngle,2*sweepAngle,true,p);
                   } else if(a.getUp().getX()>a.getX() && a.getUp().getY()>a.getY()){
                       float startAngle = (float) (((180/ Math.PI)* (java.lang.Math.asin((a.getUp().getY()-a.getY())/d))));
                       c.drawArc(a.getX()-d,a.getY()-d,a.getX()+d,a.getY()+d,startAngle-sweepAngle,2*sweepAngle,true,p);
                   } /*else if (1==1) {
                       c.drawLine(a.getX(), a.getY(), a.getUp().getX(), a.getUp().getY(), wedgePaint);
                   }*/
               }

               if (a.getLeft() != null) {
                   c.drawLine(a.getX(), a.getY(), a.getLeft().getX(), a.getLeft().getY(), p);
               }

               if (a.getRight() != null) {
                   c.drawLine(a.getX(), a.getY(), a.getRight().getX(), a.getRight().getY(), p);
               }
           }

       }
           invalidate();
       }
}





