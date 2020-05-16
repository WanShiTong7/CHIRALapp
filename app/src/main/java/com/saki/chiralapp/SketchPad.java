package com.saki.chiralapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Array;
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
       p.setStrokeWidth(7);
       Paint p1 = new Paint();
       p1.setStrokeWidth(5);
       p1.setStyle(Paint.Style.STROKE);
       p1.setARGB(255, 255, 255, 255);
       //p1.setPathEffect(new DashPathEffect(new float[] {10,10},0));

       //c.drawArc(10,10,400,400,270,10,true,p);
       Paint dashPaint = new Paint();
       dashPaint.setARGB(255, 0, 255, 0);
       //dashPaint.setStrokeWidth(10);
       //dashPaint.setStrokeCap(Paint.Cap.SQUARE);
       Paint wedgePaint = new Paint();
       wedgePaint.setARGB(255, 255, 0, 0);
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


               if(DrawList.size()==1){
                    if(a.getSymbol().equals("C")){
                        if(Stereochemistry.isAnchorPointChiral(a)){
                            c.drawCircle(a.getX(), a.getY(), 10, wedgePaint);
                        } else {
                            c.drawCircle(a.getX(), a.getY(), 10, p);
                        }
                    } else {
                        c.drawText(a.getSymbol(),a.getX(),a.getY(),elementalPaint);
                    }

               }

               if(a.getUp()!=null && a==a.getUp().getUp()){

                    //Draw Double bond

                   if(a.isDoubleBondStart()){

                       //todo: fix this to use new methods
                       float[] dbInfo = myMath.shortBond(a.getX(),a.getY(),a.getUp().getX(),a.getUp().getY(),a.isDoubleBondAbove());
                       //above method returns [0: xOrigin, 1: yOrigin, 2: phi, 3: xsbStart, 4: ysbStart, 5: xsbEnd, 6: ysbEnd]
                       float[] dbStartCoordinates = myMath.rotate(dbInfo[3],dbInfo[4],dbInfo[0],dbInfo[1],dbInfo[2]);
                       float[] dbEndCoordinates = myMath.rotate(dbInfo[5],dbInfo[6],dbInfo[0],dbInfo[1],dbInfo[2]);

                       c.drawLine(dbStartCoordinates[0],dbStartCoordinates[1],dbEndCoordinates[0],dbEndCoordinates[1],p);
                       c.drawLine(a.getX(),a.getY(),a.getUp().getX(),a.getUp().getY(),p);

                   }

               }

               if (a.getDown()!=null && a!=a.getDown().getDown() && a.getDown() != null && a.isDashStart()==true) {
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

               if (a.getUp() != null && a!=a.getUp().getUp() && a.isWedgeStart()==true) {
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

           float molarmass = 0;

           for (AnchorPoint a : DrawList) {
               Paint elementalPaint = Element.elementHashMap.get(a.getSymbol()).getElementPaint();


               if(!a.getSymbol().equals("C")) {

                   molarmass = molarmass + Element.elementHashMap.get(a.getSymbol()).getMass();

                   int symbolSquareSize = 30;
                   Paint symbolSquarePaint = new Paint();
                   symbolSquarePaint.setARGB(255,252,252,252);
                   c.drawRoundRect(a.getX()-symbolSquareSize,a.getY()-symbolSquareSize,a.getX()+symbolSquareSize,a.getY()+symbolSquareSize,20,20,symbolSquarePaint);
                   c.drawText(a.getSymbol(),a.getX()-symbolSquareSize/2, a.getY()+symbolSquareSize/2,elementalPaint);

               } else {

                   if(Stereochemistry.isAnchorPointChiral(a)){
                       if(Stereochemistry.RorS(Stereochemistry.Rank(a),a)) {
                           c.drawCircle(a.getX(), a.getY(), 10, wedgePaint);
                       } else {
                           c.drawCircle(a.getX(), a.getY(), 10, dashPaint);
                       }
                   }

                    molarmass = molarmass + Element.elementHashMap.get(a.getSymbol()).getMass();
                    if (a.getUp()==null) molarmass = molarmass + Element.elementHashMap.get("H").getMass();
                   if (a.getDown()==null) molarmass = molarmass + Element.elementHashMap.get("H").getMass();
                   if (a.getRight()==null) molarmass = molarmass + Element.elementHashMap.get("H").getMass();
                   if (a.getLeft()==null) molarmass = molarmass + Element.elementHashMap.get("H").getMass();

               }

               Paint molarMassPaint = new Paint();
               Paint molarMassRectPaint = new Paint();
               molarMassRectPaint.setARGB(255,252,252,252);
               molarMassPaint.setARGB(255,0,0,0);
               molarMassPaint.setTextSize(30);

               c.drawRect(750,110,950,155,molarMassRectPaint);
               c.drawText(molarmass+"",750,150,molarMassPaint);

           }

       }
           invalidate();
       }
}





