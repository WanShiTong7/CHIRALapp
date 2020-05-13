package com.saki.chiralapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
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
       Paint p1 = new Paint();
       p1.setStrokeWidth(5);
       p1.setStyle(Paint.Style.STROKE);
       p1.setARGB(255, 255, 255, 255);
       //p1.setPathEffect(new DashPathEffect(new float[] {10,10},0));

       //c.drawArc(10,10,400,400,270,10,true,p);
       Paint dashPaint = new Paint();
       dashPaint.setARGB(255, 0, 255, 0);
       dashPaint.setStrokeWidth(10);
       //dashPaint.setStrokeCap(Paint.Cap.SQUARE);
       Paint wedgePaint = new Paint();
       wedgePaint.setARGB(255, 255, 0, 0);
       wedgePaint.setStrokeWidth(10);
       wedgePaint.setStrokeCap(Paint.Cap.SQUARE);
       //todo: make sweepAngle variable inversely proportional to distance d
       //int sweepAngle = 5;
      /* Paint dashPaint2 = new Paint();
       dashPaint2.setARGB(255, 0, 255, 0);
       dashPaint2.setStrokeWidth(10);
       Paint wedgePaint2 = new Paint();
       wedgePaint2.setARGB(255, 255, 255, 0);
       wedgePaint2.setStrokeWidth(10);*/

      //Draw the structure
       if (DrawList != null) {
           for (AnchorPoint a : DrawList) {
               Paint elementalPaint = Element.elementHashMap.get(a.getSymbol()).getElementPaint();
               if(!a.getSymbol().equals("C")) c.drawText(a.getSymbol(),a.getX(),a.getY(),elementalPaint);

               if(DrawList.size()==1){
                    if(a.getSymbol().equals("C")) c.drawCircle(a.getX(), a.getY(), 10, p);
                    //if(a.getSymbol()=="F") c.drawCircle(a.getX(), a.getY(), 10, dashPaint);
               }

               if(a.getUp()!=null && a==a.getUp().getUp()){
                   //todo: fix this! causing the app to crash... :,(
                    //Draw Double bond
                   float doubleBondWidth = 20;
                   c.drawLine(a.getX()+doubleBondWidth, a.getY(), a.getUp().getX()+doubleBondWidth, a.getUp().getY(), p);
                   c.drawLine(a.getX()-doubleBondWidth, a.getY(), a.getUp().getX()-doubleBondWidth, a.getUp().getY(), p);

               }

               if (a.getDown() != null && a.isDashStart()==true) {
                  //Draw dashes
                   {

                       float d = (float) java.lang.Math.hypot(a.getX()-a.getDown().getX(),a.getY()-a.getDown().getY());
                       float dashDistance = 10;
                       float dashDistanceIncrement = 10;

                       //adjust inverse proportionality of d to sweep angle
                       int sweepRatio = 1000;
                       float sweepAngle = (float) sweepRatio/d;

                       //arc out into quadrant 4
                       if(a.getDown().getX()>a.getX() && a.getDown().getY()<a.getY()){
                           float startAngle = (float) (360-((180/ Math.PI)* (java.lang.Math.asin((a.getY()-a.getDown().getY())/d))));
                           c.drawArc(a.getX()-d,a.getY()-d,a.getX()+d,a.getY()+d,startAngle-sweepAngle,2*sweepAngle,true,p);
                           while (dashDistance<d){
                               c.drawArc(a.getX()-dashDistance,a.getY()-dashDistance,a.getX()+dashDistance,a.getY()+dashDistance,startAngle-sweepAngle,2*sweepAngle,false,p1);
                               dashDistance+=dashDistanceIncrement;
                           }
                       } else if (a.getDown().getX()<a.getX() && a.getDown().getY()<a.getY()){
                           float startAngle = (float) (180+((180/ Math.PI)* (java.lang.Math.asin((a.getY()-a.getDown().getY())/d))));
                           c.drawArc(a.getX()-d,a.getY()-d,a.getX()+d,a.getY()+d,startAngle-sweepAngle,2*sweepAngle,true,p);
                           while (dashDistance<d){
                               c.drawArc(a.getX()-dashDistance,a.getY()-dashDistance,a.getX()+dashDistance,a.getY()+dashDistance,startAngle-sweepAngle,2*sweepAngle,false,p1);
                               dashDistance+=dashDistanceIncrement;
                           }
                       } else if (a.getDown().getX()<a.getX() && a.getDown().getY()>a.getY()) {
                           float startAngle = (float) (180-((180/ Math.PI)* (java.lang.Math.asin((a.getDown().getY()-a.getY())/d))));
                           c.drawArc(a.getX()-d,a.getY()-d,a.getX()+d,a.getY()+d,startAngle-sweepAngle,2*sweepAngle,true,p);
                           while (dashDistance<d){
                               c.drawArc(a.getX()-dashDistance,a.getY()-dashDistance,a.getX()+dashDistance,a.getY()+dashDistance,startAngle-sweepAngle,2*sweepAngle,false,p1);
                               dashDistance+=dashDistanceIncrement;
                           }
                       } else if(a.getDown().getX()>a.getX() && a.getDown().getY()>a.getY()){
                           float startAngle = (float) (((180/ Math.PI)* (java.lang.Math.asin((a.getDown().getY()-a.getY())/d))));
                           c.drawArc(a.getX()-d,a.getY()-d,a.getX()+d,a.getY()+d,startAngle-sweepAngle,2*sweepAngle,true,p);
                           while (dashDistance<d){
                               c.drawArc(a.getX()-dashDistance,a.getY()-dashDistance,a.getX()+dashDistance,a.getY()+dashDistance,startAngle-sweepAngle,2*sweepAngle,false,p1);
                               dashDistance+=dashDistanceIncrement;
                           }
                       }
                   }
               }

               if (a.getUp() != null && a.isWedgeStart()==true) {
                 //Draw Wedges
                   float d = (float) java.lang.Math.hypot(a.getX()-a.getUp().getX(),a.getY()-a.getUp().getY());

                   //adjust inverse proportionality of d to sweep angle

                   int sweepRatio = 1000;
                   float sweepAngle = (float) sweepRatio/d;
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
                   }
               }

               if (a.getLeft() != null) {
                   //draw planar bonds
                   c.drawLine(a.getX(), a.getY(), a.getLeft().getX(), a.getLeft().getY(), p);
               }

               if (a.getRight() != null) {
                   //draw planar bonds
                   c.drawLine(a.getX(), a.getY(), a.getRight().getX(), a.getRight().getY(), p);
               }
           }

       }
           invalidate();
       }
}





