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
                AnchorPoint curAnchorPoint = new AnchorPoint(event.getX(),event.getY(),"C");
                int size = myStructure.size();
                float minDist = Float.MAX_VALUE;
                int minDistIndex = -1;
                if (size>0){
                    for (AnchorPoint a: myStructure){
                        float dist = (float) Math.sqrt((Math.pow(curAnchorPoint.getX() - a.getX(),2)+(Math.pow(curAnchorPoint.getY() - a.getY(),2))));
                        if (dist<minDist) {
                            minDist=dist;
                            minDistIndex=myStructure.indexOf(a);
                        }
                    }
                    if(curAnchorPoint.getX()> myStructure.get(minDistIndex).getX() && myStructure.get(minDistIndex).getRight()==null) {
                        myStructure.get(minDistIndex).setRight(curAnchorPoint);
                        curAnchorPoint.setLeft(myStructure.get(minDistIndex));

                    } else if (curAnchorPoint.getX()< myStructure.get(minDistIndex).getX() && myStructure.get(minDistIndex).getLeft()==null){
                        myStructure.get(minDistIndex).setLeft(curAnchorPoint);
                        curAnchorPoint.setRight(myStructure.get(minDistIndex));
                    } else if (myStructure.get(minDistIndex).getUp()==null){
                        myStructure.get(minDistIndex).setUp(curAnchorPoint);
                        curAnchorPoint.setDown(myStructure.get(minDistIndex));
                    } else if (myStructure.get(minDistIndex).getDown()==null) {
                        myStructure.get(minDistIndex).setDown(curAnchorPoint);
                        curAnchorPoint.setUp(myStructure.get(minDistIndex));
                    }
                }
                myStructure.add(curAnchorPoint);
                v.DrawList = myStructure;
                return false;
            }
        });


    } //end of OnCreate



}
