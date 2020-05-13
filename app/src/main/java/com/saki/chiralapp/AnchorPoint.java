package com.saki.chiralapp;

import java.util.ArrayList;
import java.util.Arrays;

public class AnchorPoint {
    private float x;
    private float y;
    private String symbol = "C";
    private AnchorPoint left;
    private AnchorPoint right;
    private AnchorPoint up;
    private boolean isWedgeStart = false;
    private AnchorPoint down;
    private boolean isDashStart = false;
    private boolean isHorizontalFilled = false;
    private ArrayList<String> elementList = new ArrayList<String>(Arrays.asList("C","O","N","H","F","Cl","Br","I"));

    public AnchorPoint(float x, float y, String symbol) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public AnchorPoint getLeft() {
        return left;
    }

    public void setLeft(AnchorPoint left) {
        this.left = left;
    }

    public AnchorPoint getRight() {
        return right;
    }

    public void setRight(AnchorPoint right) {
        this.right = right;
    }

    public AnchorPoint getUp() {
        return up;
    }

    public void setUp(AnchorPoint up) {
        this.up = up;
    }

    public AnchorPoint getDown() {
        return down;
    }

    public void setDown(AnchorPoint down) {
        this.down = down;
    }

    public boolean isWedgeStart() {
        return isWedgeStart;
    }

    public void setWedgeStart(boolean wedgeStart) {
        isWedgeStart = wedgeStart;
    }

    public boolean isDashStart() {
        return isDashStart;
    }

    public void setDashStart(boolean dashStart) {
        isDashStart = dashStart;
    }

    public boolean isHorizontalFilled() {
        return isHorizontalFilled;
    }

    public void setHorizontalFilled(boolean horizontalFilled) {
        isHorizontalFilled = horizontalFilled;
    }

    public static boolean checkAndSetDownValid(AnchorPoint targetAnchor, AnchorPoint curAnchor) {
        //todo:remove fluorine from here and checksetupvalid later, only here to figure out drawing
        if ((targetAnchor.getSymbol().equals("C") || targetAnchor.getSymbol().equals("F")) && (targetAnchor.getLeft()==null || targetAnchor.getRight()==null || targetAnchor.getUp()==null || targetAnchor.getDown()==null)) {


            if (targetAnchor.getRight() == null && curAnchor.getLeft()== null) {
                targetAnchor.setRight(curAnchor);
                curAnchor.setLeft(targetAnchor);

            } else if (targetAnchor.getLeft() == null && curAnchor.getRight()==null) {
                targetAnchor.setLeft(curAnchor);
                curAnchor.setRight(targetAnchor);

            } else if (targetAnchor.getUp() == null && curAnchor.getDown()==null) {
                targetAnchor.setWedgeStart(true);
                targetAnchor.setUp(curAnchor);
                curAnchor.setDown(targetAnchor);

            } else if (targetAnchor.getDown() == null && curAnchor.getUp() == null) {
                targetAnchor.setDashStart(true);
                targetAnchor.setDown(curAnchor);
                curAnchor.setUp(targetAnchor);

            }

            return true;

        } else return false;
    }

   public  static boolean checkAndSetUpValid (AnchorPoint targetAnchor, AnchorPoint curAnchor) {
       if ((targetAnchor.getSymbol().equals("C") || targetAnchor.getSymbol().equals("F")) && (targetAnchor.getLeft() == null || targetAnchor.getRight() == null || targetAnchor.getUp() == null || targetAnchor.getDown() == null)) {


           if (targetAnchor.getRight() == null && curAnchor.getLeft() == null) {
               targetAnchor.setRight(curAnchor);
               curAnchor.setLeft(targetAnchor);

           } else if (targetAnchor.getLeft() == null && curAnchor.getRight() == null) {
               targetAnchor.setLeft(curAnchor);
               curAnchor.setRight(targetAnchor);

           } else if (targetAnchor.getUp() == null && curAnchor.getDown() == null) {
               curAnchor.setDashStart(true);
               targetAnchor.setUp(curAnchor);
               curAnchor.setDown(targetAnchor);

           } else if (targetAnchor.getDown() == null && curAnchor.getUp() == null) {
               curAnchor.setWedgeStart(true);
               targetAnchor.setDown(curAnchor);
               curAnchor.setUp(targetAnchor);

           }

           return true;
       } else return false;
   }

   public void switchElement() {
        int location = elementList.indexOf(getSymbol());
        if(location==elementList.size()-1){
            setSymbol("C");
        } else setSymbol(elementList.get(location+1));
    }

}
