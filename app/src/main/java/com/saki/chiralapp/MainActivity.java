package com.saki.chiralapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static float downX=-1;
    static float downY=-1;
    static final float ANCHOR_RADIUS = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<AnchorPoint> myStructure = new ArrayList<>();




        ImageButton clearButton = (ImageButton) findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               myStructure.clear();

            }
        });

        ConstraintLayout Layout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        //data collection from sketchpad user touch input; data dump into myStructure arraylist
        Layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v1, MotionEvent event) {
                SketchPad v = (SketchPad) findViewById(R.id.sketchPad);
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
                        boolean isValid=true;

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
                            if (dist<ANCHOR_RADIUS && downPointAnchorIndex==-1 && upPointAnchorIndex==-1) {
                                myStructure.get(size - 1).setRight(curAnchorPoint);
                                curAnchorPoint.setLeft(myStructure.get(size - 1));
                            } else if (downPointAnchorIndex==-1 && upPointAnchorIndex==-1){
                                //both uppoint and downpoint are invalid; do nothing
                                return true;
                            } else if (downPointAnchorIndex!=-1 && upPointAnchorIndex==-1) {
                                //downpoint is valid and uppoint is invalid; fill empty slot with highest priority, set existing anchor as wedge/dash start
                                isValid=AnchorPoint.checkAndSetDownValid(myStructure.get(downPointAnchorIndex),curAnchorPoint);

                            } else if (downPointAnchorIndex==-1 && upPointAnchorIndex!=-1) {

                                //this is unique case where downpoint is the curAnchor location; need to fix the location of curAnchor
                                curAnchorPoint.setX(downX);
                                curAnchorPoint.setY(downY);

                                //downpoint is invalid and uppoint is valid; fill empty slot with highest priority, set new anchor as wedge/dash start
                                isValid=AnchorPoint.checkAndSetUpValid(myStructure.get(upPointAnchorIndex),curAnchorPoint);

                            } else if (downPointAnchorIndex!=-1 && upPointAnchorIndex!=-1 && downPointAnchorIndex!=upPointAnchorIndex) {
                                //downpoint is valid, upoint is valid, upoint does not equal downpoint
                                //todo: fix this - currently only checks target availability, need to check curAnchor's availability...
                                //todo: maybe fix above in the AnchorPoint.checkAndSetDownValid method
                                //todo: implement double bonds here

                                isValid=AnchorPoint.checkAndSetDownValid(myStructure.get(upPointAnchorIndex),myStructure.get(downPointAnchorIndex));
                                //myStructure.get(downPointAnchorIndex).setRight(myStructure.get(upPointAnchorIndex));
                                //myStructure.get(upPointAnchorIndex).setLeft(myStructure.get(downPointAnchorIndex));
                                return true;
                            } else if ((downPointAnchorIndex!=-1 && upPointAnchorIndex!=-1 && downPointAnchorIndex==upPointAnchorIndex)) {
                                //upoint and downpoint are valid, upoint equals downpoint
                                //todo: implement element switching here
                                myStructure.get(size - 1).setRight(myStructure.get(downPointAnchorIndex));
                                myStructure.get(downPointAnchorIndex).setLeft(myStructure.get(size-1));
                                return true;

                            }
                        }
                        if (isValid) myStructure.add(curAnchorPoint);
                        v.DrawList = myStructure;
                        return true;
                    }
                }
                return true;
            }
        });


    } //end of OnCreate





}
