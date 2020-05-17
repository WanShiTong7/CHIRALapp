package com.saki.chiralapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.telephony.AccessNetworkConstants;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    static float downX=-1;
    static float downY=-1;
    static final float ANCHOR_RADIUS = 90;

    static ArrayList<AnchorPoint> myStructure = new ArrayList<>();

    //History for undo redo
    static StructureHistory myStructureHistory = new StructureHistory();

    //Clone Structure by  Deep coping
    static ArrayList<AnchorPoint> cloneStructure(ArrayList<AnchorPoint> s){
        HashMap<AnchorPoint,Integer> smap = new HashMap<>();
        HashMap<Integer, AnchorPoint> tmap = new HashMap<>();
        ArrayList<AnchorPoint> c = new ArrayList<>();
        for (int i=0; i<s.size(); i++) {
            s.get(i).setId(i);
            smap.put(s.get(i),i);
            c.add(new AnchorPoint(s.get(i)));
            tmap.put(i,c.get(i));
            //c.add(ap);
        }

        //fix LRUD pointers in the clone
        for (int i=0; i<c.size(); i++) {

            if(c.get(i).getLeft()!=null) c.get(i).setLeft(tmap.get(smap.get(c.get(i).getLeft())));
            if(c.get(i).getRight()!=null) c.get(i).setRight(tmap.get(smap.get(c.get(i).getRight())));
            if(c.get(i).getUp()!=null) c.get(i).setUp(tmap.get(smap.get(c.get(i).getUp())));
            if(c.get(i).getDown()!=null) c.get(i).setDown(tmap.get(smap.get(c.get(i).getDown())));
        }

        return c;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton clearButton = (ImageButton) findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //myStructureHistory.add((ArrayList<AnchorPoint>) myStructure.clone());
                //curHistoryIndex++;
                myStructure.clear();

                myStructure = MainActivity.cloneStructure(myStructureHistory.get());

                SketchPad sp = (SketchPad) findViewById(R.id.sketchPad);
                sp.DrawList=myStructure;
                sp.refreshDrawableState();
            }
        });

        /*
        ImageButton undoButton = (ImageButton) findViewById(R.id.undoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myStructure=myStructureHistory.get(curHistoryIndex);
                curHistoryIndex--;

            }
        });

        ImageButton redoButton = (ImageButton) findViewById(R.id.redoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                curHistoryIndex++;
                myStructure=myStructureHistory.get(curHistoryIndex);

            }
        });
    */

        ConstraintLayout Layout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        //data collection from sketchpad user touch input; data dump into myStructure arraylist
        Layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v1, MotionEvent event) {
                SketchPad v = (SketchPad) findViewById(R.id.sketchPad);
                //AnchorPoint curAnchorPoint = new AnchorPoint(event.getX(),event.getY(),"C");

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        downX = event.getX();
                        downY = event.getY();

                        return true;
                    }
                    case MotionEvent.ACTION_UP: {

                        AnchorPoint curAnchorPoint = new AnchorPoint(event.getX(),event.getY(),"C");
                        int size = myStructure.size();
                        float minDist = Float.MAX_VALUE;
                        int downPointAnchorIndex = -1;
                        int upPointAnchorIndex = -1;
                        boolean isValid=true;

                        //Take snapshot for undo redo
                        myStructureHistory.add(MainActivity.cloneStructure(myStructure));

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
                                isValid=AnchorPoint.checkAndSet(myStructure.get(size - 1),curAnchorPoint,true);
                                //myStructure.get(size - 1).setRight(curAnchorPoint);
                                //curAnchorPoint.setLeft(myStructure.get(size - 1));
                            } else if (downPointAnchorIndex==-1 && upPointAnchorIndex==-1){
                                //both uppoint and downpoint are invalid; do nothing
                                myStructureHistory.get();
                                return true;
                            } else if (downPointAnchorIndex!=-1 && upPointAnchorIndex==-1) {
                                //downpoint is valid and uppoint is invalid; fill empty slot with highest priority, set existing anchor as wedge/dash start
                                isValid=AnchorPoint.checkAndSet(myStructure.get(downPointAnchorIndex),curAnchorPoint,false);

                            } else if (downPointAnchorIndex==-1 && upPointAnchorIndex!=-1) {

                                //this is unique case where downpoint is the curAnchor location; need to fix the location of curAnchor
                                curAnchorPoint.setX(downX);
                                curAnchorPoint.setY(downY);

                                //downpoint is invalid and uppoint is valid; fill empty slot with highest priority, set new anchor as wedge/dash start
                                isValid=AnchorPoint.checkAndSet(myStructure.get(upPointAnchorIndex),curAnchorPoint,true);

                            } else if (downPointAnchorIndex!=-1 && upPointAnchorIndex!=-1
                                    && downPointAnchorIndex!=upPointAnchorIndex) {
                                //downpoint is valid, upoint is valid, upoint does not equal downpoint
                                //todo: implement double bonds here
                                boolean doubleBond = AnchorPoint.checkDoubleBondValidity(myStructure.get(upPointAnchorIndex),myStructure.get(downPointAnchorIndex));

                                if(!doubleBond) {
                                    if(!(myStructure.get(upPointAnchorIndex).getLeft()==myStructure.get(downPointAnchorIndex) || myStructure.get(upPointAnchorIndex).getRight()==myStructure.get(downPointAnchorIndex) || myStructure.get(upPointAnchorIndex).getUp()==myStructure.get(downPointAnchorIndex) || myStructure.get(upPointAnchorIndex).getDown()==myStructure.get(downPointAnchorIndex))) {
                                        isValid = AnchorPoint.checkAndSet(myStructure.get(upPointAnchorIndex), myStructure.get(downPointAnchorIndex), false);
                                    }
                                } else {
                                    AnchorPoint.setDoubleBond(myStructure.get(upPointAnchorIndex),myStructure.get(downPointAnchorIndex));

                                    float phi = myMath.phi(myStructure.get(downPointAnchorIndex).getX(),myStructure.get(downPointAnchorIndex).getY(),myStructure.get(upPointAnchorIndex).getX(),myStructure.get(upPointAnchorIndex).getY());
                                    float theta = Math.abs(phi);

                                    if(theta < Math.sqrt(2)*Math.PI/2){
                                        if(downY<myStructure.get(downPointAnchorIndex).getY()) {

                                            //Add to the history and set double bonds
                                           // myStructureHistory.add(MainActivity.cloneStructure(myStructure));

                                            myStructure.get(downPointAnchorIndex).setDoubleBondAbove(true);
                                            myStructure.get(upPointAnchorIndex).setDoubleBondAbove(true);

                                        }
                                    } else {
                                        if (downX < myStructure.get(downPointAnchorIndex).getX()) {

                                            //Add to the history and set double bonds
                                            //myStructureHistory.add(MainActivity.cloneStructure(myStructure));

                                            myStructure.get(downPointAnchorIndex).setDoubleBondAbove(true);
                                            myStructure.get(upPointAnchorIndex).setDoubleBondAbove(true);
                                        }
                                    }

                                }

                                //myStructure.get(downPointAnchorIndex).setRight(myStructure.get(upPointAnchorIndex));
                                //myStructure.get(upPointAnchorIndex).setLeft(myStructure.get(downPointAnchorIndex));
                                return true;
                            } else if ((downPointAnchorIndex!=-1 && upPointAnchorIndex!=-1 && downPointAnchorIndex==upPointAnchorIndex)) {
                                //upoint and downpoint are valid, upoint equals downpoint; enter element switcher
                                //todo: implement special cases for oxygen and nitrogen

                                //Add to the history and switch the element
                                //myStructureHistory.add(MainActivity.cloneStructure(myStructure));
                                myStructure.get(downPointAnchorIndex).switchElement();

                                //myStructure.get(size - 1).setRight(myStructure.get(downPointAnchorIndex));
                                //myStructure.get(downPointAnchorIndex).setLeft(myStructure.get(size-1));
                                return true;

                            }
                        }
                        if (isValid) {

                            myStructure.add(curAnchorPoint);
                        }
                        v.DrawList = myStructure;
                        return true;
                    }
                }
                return true;
            }

        });


    } //end of OnCreate





}
