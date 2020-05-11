package com.saki.chiralapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.lang.Math.*;

public class MainActivity extends AppCompatActivity {

    static float downX=-1;
    static float downY=-1;
    static final float ANCHOR_RADIUS = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<AnchorPoint> myStructure = new ArrayList<>();



        ConstraintLayout Layout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        Layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v1, MotionEvent event) {
                MyView v = (MyView) findViewById(R.id.sketchPad);
                //AnchorPoint curAnchorPoint = new AnchorPoint(event.getX(),event.getY(),"C");

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        TextView downTestText = (TextView) findViewById(R.id.downTestText);
                        downTestText.setText(event.getX()+", "+event.getY());
                        downX = event.getX();
                        downY = event.getY();
                        return true;
                    }
                    case MotionEvent.ACTION_UP: {
                        TextView upTestText = (TextView) findViewById(R.id.upTestText);
                        upTestText.setText(event.getX()+", "+event.getY());
                        AnchorPoint curAnchorPoint = new AnchorPoint(event.getX(),event.getY(),"C");
                        int size = myStructure.size();
                        float minDist = Float.MAX_VALUE;
                        int downPointAnchorIndex = -1;
                        int upPointAnchorIndex = -1;
                        if (size>0){
                            //find proximity of downpoint to anchor
                            for (AnchorPoint a: myStructure){
                                float dist = (float) Math.sqrt((Math.pow(downX - a.getX(),2)+(Math.pow(downY - a.getY(),2))));
                                if (dist<ANCHOR_RADIUS) {
                                    downPointAnchorIndex = myStructure.indexOf(a);
                                    break;
                                }
                            }
                            //find proximity of uppoint to anchor
                            for (AnchorPoint a: myStructure){
                                float dist = (float) Math.sqrt((Math.pow(curAnchorPoint.getX() - a.getX(),2)+(Math.pow(curAnchorPoint.getY() - a.getY(),2))));
                                if (dist<ANCHOR_RADIUS) {
                                    upPointAnchorIndex = myStructure.indexOf(a);
                                    break;
                                }
                            }

                            //uppoint and downpoint are equal; link to last Anchor
                            float dist = (float) Math.sqrt((Math.pow(curAnchorPoint.getX() - downX,2)+(Math.pow(curAnchorPoint.getY() - downY,2))));
                            if (dist<ANCHOR_RADIUS) {
                                myStructure.get(size - 1).setRight(curAnchorPoint);
                                curAnchorPoint.setLeft(myStructure.get(size - 1));
                            } else if (downPointAnchorIndex==-1 && upPointAnchorIndex==-1){
                                //both uppoint and downpoint are invalid
                                return true;
                            } else if (downPointAnchorIndex!=-1 && upPointAnchorIndex==-1) {
                                //downpoint is valid and uppoint is invalid; fill empty slot with highest priority
                                if (myStructure.get(downPointAnchorIndex).getRight()==null){
                                    myStructure.get(downPointAnchorIndex).setRight(curAnchorPoint);
                                    curAnchorPoint.setLeft(myStructure.get(downPointAnchorIndex));
                                } else if (myStructure.get(downPointAnchorIndex).getLeft()==null){
                                    myStructure.get(downPointAnchorIndex).setLeft(curAnchorPoint);
                                    curAnchorPoint.setRight(myStructure.get(downPointAnchorIndex));
                                } else if (myStructure.get(downPointAnchorIndex).getUp()==null){
                                    myStructure.get(downPointAnchorIndex).setUp(curAnchorPoint);
                                    curAnchorPoint.setDown(myStructure.get(downPointAnchorIndex));
                                } else if (myStructure.get(downPointAnchorIndex).getDown()==null) {
                                    myStructure.get(downPointAnchorIndex).setDown(curAnchorPoint);
                                    curAnchorPoint.setUp(myStructure.get(downPointAnchorIndex));
                                }
                            } else if (downPointAnchorIndex!=-1 && upPointAnchorIndex!=-1 && downPointAnchorIndex!=upPointAnchorIndex) {
                                //todo: fix this, but probably okay
                                myStructure.get(downPointAnchorIndex).setRight(myStructure.get(upPointAnchorIndex));
                                myStructure.get(upPointAnchorIndex).setLeft(myStructure.get(downPointAnchorIndex));
                                return true;
                            }
                        }
                        myStructure.add(curAnchorPoint);
                        v.DrawList = myStructure;
                        return true;
                    }
                }
                return true;
            }
        });


    } //end of OnCreate



}
