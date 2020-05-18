package com.saki.chiralapp;

import java.util.ArrayList;

public class StructureHistory {
    //History for undo redo
    ArrayList<ArrayList<AnchorPoint>> myStructureHistory = new ArrayList<>();
    int curHistoryIndex = -1;
    int maxSize = 200;

    public StructureHistory() {
        myStructureHistory = new ArrayList<>();
        curHistoryIndex = -1;
        maxSize = 200;
    }

    public  void add(ArrayList<AnchorPoint> a){

        //Remove all from the current index to end of the list
        while(myStructureHistory.size()-1>curHistoryIndex) {
            myStructureHistory.remove(myStructureHistory.size() - 1);
        }

        myStructureHistory.add(a);
        curHistoryIndex++;

    }

    public  ArrayList<AnchorPoint> get(){
        if (curHistoryIndex <=0){
            return new ArrayList<>();
        }else {
            curHistoryIndex--;
            return myStructureHistory.get(curHistoryIndex);
        }
    }

    public  ArrayList<AnchorPoint> getNext(){


        if (myStructureHistory.size() > curHistoryIndex+1){
            curHistoryIndex++;
            return myStructureHistory.get(curHistoryIndex);
        }else if (curHistoryIndex == -1){
            return new ArrayList<>();
        }else {
            return myStructureHistory.get(curHistoryIndex);
        }
    }
}
