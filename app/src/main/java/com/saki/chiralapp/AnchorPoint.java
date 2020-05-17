package com.saki.chiralapp;

import java.util.ArrayList;
import java.util.Arrays;

public class AnchorPoint {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
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
    private static ArrayList<String> elementList = new ArrayList<String>(Arrays.asList("C","O","N","H","F","Cl","Br","I"));
    private int bondCount = 0;
    private boolean isDoubleBondValid = false;
    private boolean isDoubleBondStart = false;
    private boolean isDoubleBondAbove = false;


    public AnchorPoint(float x, float y, String symbol) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
    }

    public AnchorPoint(AnchorPoint a){

        this.id = a.id;
        this.x = a.x;
        this.y = a.y;
        this.symbol = a.symbol;

        this.right = a.right;
        this.left = a.left;
        this.down = a.down;
        this.up = a.up;

        this.isWedgeStart = a.isWedgeStart;
        this.isDashStart = a.isDashStart;
        this.isHorizontalFilled = a.isHorizontalFilled;
        this.bondCount = a.bondCount;
        this.isDoubleBondValid = a.isDoubleBondValid;
        this.isDoubleBondStart = a.isDoubleBondStart;
        this.isDoubleBondAbove = a.isDoubleBondAbove;

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

    public boolean isDoubleBondValid() {
        return isDoubleBondValid;
    }

    public void setDoubleBondValid(boolean doubleBondValid) {
        isDoubleBondValid = doubleBondValid;
    }

    public boolean isDoubleBondStart() {
        return isDoubleBondStart;
    }

    public void setDoubleBondStart(boolean doubleBondStart) {
        isDoubleBondStart = doubleBondStart;
    }

    public boolean isDoubleBondAbove() {
        return isDoubleBondAbove;
    }

    public void setDoubleBondAbove(boolean doubleBondAbove) {
        isDoubleBondAbove = doubleBondAbove;
    }

    //GODTIER
    public static boolean checkAndSet(AnchorPoint targetAnchor, AnchorPoint curAnchor, boolean isStart) {

        if(targetAnchor.bondCount<Element.elementHashMap.get(targetAnchor.getSymbol()).getAllowedBonds() &&
                curAnchor.bondCount<Element.elementHashMap.get(curAnchor.getSymbol()).getAllowedBonds()){

            if (targetAnchor.getRight() == null && curAnchor.getLeft()== null) {
                targetAnchor.setRight(curAnchor);
                curAnchor.setLeft(targetAnchor);
                targetAnchor.bondCount++;
                curAnchor.bondCount++;

            } else if (targetAnchor.getLeft() == null && curAnchor.getRight()==null) {
                targetAnchor.setLeft(curAnchor);
                curAnchor.setRight(targetAnchor);
                targetAnchor.bondCount++;
                curAnchor.bondCount++;

            } else if (targetAnchor.getUp() == null && curAnchor.getDown()==null) {
                if(!isStart) {
                    targetAnchor.setWedgeStart(true);
                } else {
                    curAnchor.setDashStart(true);
                }
                targetAnchor.setUp(curAnchor);
                curAnchor.setDown(targetAnchor);
                targetAnchor.bondCount++;
                curAnchor.bondCount++;

            } else if (targetAnchor.getDown() == null && curAnchor.getUp() == null) {
                if (!isStart) {
                    targetAnchor.setDashStart(true);
                } else {
                    curAnchor.setWedgeStart(true);
                }
                targetAnchor.setDown(curAnchor);
                curAnchor.setUp(targetAnchor);
                targetAnchor.bondCount++;
                curAnchor.bondCount++;

            }

            return true;

        } else return false;
    }

   public void switchElement() {
        int location = elementList.indexOf(getSymbol());
       if(location==elementList.size()-1) {
           location=0;
       } else {
           location++;
       }
        while (this.bondCount>Element.elementHashMap.get(elementList.get(location)).getAllowedBonds()){
            if(location==elementList.size()-1) {
                location = 0;

            } else {
                location++;
            }
        }

        setSymbol(elementList.get(location));
    }

    public static boolean checkDoubleBondValidity(AnchorPoint anchor1, AnchorPoint anchor2){
        //check if nodes are already connected
        if((anchor1.getLeft()==anchor2 || anchor1.getRight()==anchor2 || anchor1.getUp()==anchor2 || anchor1.getDown()==anchor2)
        //check if all other connections on node 1 are planar
        && ((anchor1.getUp()==anchor2 && anchor1.getDown()==null) || (anchor1.getDown()==anchor2 && anchor1.getUp()==null) || (anchor1.getDown()==null && anchor1.getUp()==null))
        //check if all other connections on node 2 are planar
        && ((anchor2.getUp()==anchor1 && anchor2.getDown()==null) || (anchor2.getDown()==anchor1 && anchor2.getUp()==null) || (anchor2.getDown()==null && anchor2.getUp()==null))){

            return true;

        } else {

            return false;

        }
    }

    public static void setDoubleBond(AnchorPoint anchor1, AnchorPoint anchor2){
        //blindly set double bond
        anchor1.setUp(anchor2);
        anchor1.setDown(anchor2);
        anchor2.setUp(anchor1);
        anchor2.setDown(anchor1);
        anchor1.bondCount++;
        anchor2.bondCount++;
        if(anchor1.getX()<anchor2.getX()){

            anchor1.setDoubleBondStart(true);

        } else {

            anchor2.setDoubleBondStart(true);

        }
        //remove planar connections between acnhor 1 and anchor 2
        if(anchor1.getRight()==anchor2) {

            anchor1.setRight(null);
            anchor2.setLeft(null);

        } else if (anchor1.getLeft()==anchor2){

            anchor1.setLeft(null);
            anchor2.setRight(null);

        }
    }

}
