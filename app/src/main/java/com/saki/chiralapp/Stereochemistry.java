package com.saki.chiralapp;

import java.util.Arrays;

public class Stereochemistry {

    public static boolean Compare(AnchorPoint r, AnchorPoint l, AnchorPoint u, AnchorPoint d) {
        //return false if any of four are the same, return true if all four are different
        //todo: below comment occurred as expected, fix this. Current idea: create copy, set copy's null slots to Hydrogen, pass copy through compare
        //below might fail if a position is null...
        if (r.getSymbol() == l.getSymbol() || r.getSymbol() == u.getSymbol() || r.getSymbol() == d.getSymbol()) {
            return false;
        } else if (l.getSymbol() == u.getSymbol() || l.getSymbol() == d.getSymbol()){
            return false;
        } else if (u.getSymbol() == d.getSymbol()) {
            return false;
        } return true;
    }

    public static boolean isAnchorPointChiral(AnchorPoint a) {
        //step out by one and compare four nodes
        if(a.getRight()==null || a.getLeft() ==null || a.getUp() == null || a.getDown() == null){
            //todo: below works, but could be cleaner...
            AnchorPoint b = new AnchorPoint(a.getX(),a.getY(),a.getSymbol());
            b.setRight(a.getRight());
            b.setLeft(a.getLeft());
            b.setDown(a.getDown());
            b.setUp(a.getUp());
            AnchorPoint c = new AnchorPoint(0,0,"H");
            AnchorPoint d = new AnchorPoint(0,0,"H");
            AnchorPoint e = new AnchorPoint(0,0,"H");
            AnchorPoint f = new AnchorPoint(0,0,"H");
            if (b.getRight() == null) {
                b.setRight(c);
            }

            if (b.getLeft() == null) {
                b.setLeft(d);
            }

            if (b.getUp() == null) {
                b.setUp(e);
            }

            if (b.getDown() == null) {
                b.setDown(f);
            }
            if(Stereochemistry.Compare(b.getRight(),b.getLeft(),b.getUp(),b.getDown())){
                return true;
            }
        }
        if(a.getRight()!=null && a.getLeft() != null && a.getUp()!= null && a.getDown() != null){
            if (Stereochemistry.Compare(a.getRight(), a.getLeft(), a.getUp(), a.getDown())){
                return true;
            }
        }

        return false;
    }

    public static int[] Rank(AnchorPoint a){
        //ranks substituents, returns [rank of right, rank of left, rank of up, rank of down]
        int rR = 0;
        int rL = 0;
        int rU = 0;
        int rD = 0;

        //todo: once moving past primitive stereochem assigner, need to change these to mass of first point of difference
        float mR = Element.elementHashMap.get(a.getRight().getSymbol()).getMass();
        float mL = Element.elementHashMap.get(a.getLeft().getSymbol()).getMass();
        float mU = Element.elementHashMap.get(a.getUp().getSymbol()).getMass();
        float mD = Element.elementHashMap.get(a.getDown().getSymbol()).getMass();

        float[] sort = new float[4];
        sort[0] = mR;
        sort[1] = mL;
        sort[2] = mU;
        sort[3] = mD;

        Arrays.sort(sort);
        float[] descendingSort = new float[4];
        descendingSort[0] = sort[3];
        descendingSort[1] = sort[2];
        descendingSort[2] = sort[1];
        descendingSort[3] = sort[0];

        int r =1;
        for (int i = 0; i < sort.length; i++) {

           if (sort[i] == mR) {
               rR = i+1;
            } else if (sort[i] == mL) {
                rL = i+1;
            } else if (sort[i] == mU) {
               rU = i+1;
           }else if (sort[i] == mD) {
               rD = i+1;
           }
        }

        int[] rank = new int[4];
        rank[0] = rR;
        rank[1] = rL;
        rank[2] = rU;
        rank[3] = rD;

        return rank;
    }

    public static boolean RorS(AnchorPoint a) {
        //input is anchorpoint, returns true if R, false if S
        //R arrays when down is back
        int[] R1 = new int[3];
        R1[0] = 1;
        R1[1] = 2;
        R1[2] = 3;

        int[] R2 = new int[3];
        R1[0] = 3;
        R1[1] = 1;
        R1[2] = 2;

        int[] R3 = new int[3];
        R1[0] = 2;
        R1[1] = 3;
        R1[2] = 1;
        if(a.getRight()==null || a.getLeft() ==null || a.getUp() == null || a.getDown() == null){
            //todo: below works, but could be cleaner...
            AnchorPoint b = new AnchorPoint(a.getX(),a.getY(),a.getSymbol());
            b.setRight(a.getRight());
            b.setLeft(a.getLeft());
            b.setDown(a.getDown());
            b.setUp(a.getUp());
            AnchorPoint c = new AnchorPoint(0,0,"H");
            AnchorPoint d = new AnchorPoint(0,0,"H");
            AnchorPoint e = new AnchorPoint(0,0,"H");
            AnchorPoint f = new AnchorPoint(0,0,"H");
            if (b.getRight() == null) {
                b.setRight(c);
            }

            if (b.getLeft() == null) {
                b.setLeft(d);
            }

            if (b.getUp() == null) {
                b.setUp(e);
            }

            if (b.getDown() == null) {
                b.setDown(f);
            }

            int[] ranks = Rank(b);
            if (ranks[3]==4){

                int[] downBackRanks = new int[3];
                downBackRanks[0] = ranks[0];
                downBackRanks[1] = ranks[1];
                downBackRanks[2] = ranks[2];

                if(Arrays.equals(downBackRanks, R1) || Arrays.equals(downBackRanks, R2) || Arrays.equals(downBackRanks, R3)) {
                    return true;
                } else {

                    return false;
                }
            } else if (ranks[2]==4){

                int[] upBackRanks = new int[3];
                upBackRanks[0] = ranks[0];
                upBackRanks[1] = ranks[1];
                upBackRanks[2] = ranks[3];

                if(Arrays.equals(upBackRanks, R1) || Arrays.equals(upBackRanks, R2) || Arrays.equals(upBackRanks, R3)) {
                    return false;
                } else {
                    return true;
                }

            } else if (ranks[1]==4){
                int[] leftBackRanks = new int[3];
                leftBackRanks[0] = ranks[0];
                leftBackRanks[1] = ranks[3];
                leftBackRanks[2] = ranks[2];

                if(Arrays.equals(leftBackRanks, R1) || Arrays.equals(leftBackRanks, R2) || Arrays.equals(leftBackRanks, R3)) {
                    return false;
                } else {
                    return true;
                }
            }else if (ranks[0]==4){
                int[] rightBackRanks = new int[3];
                rightBackRanks[0] = ranks[0];
                rightBackRanks[1] = ranks[3];
                rightBackRanks[2] = ranks[2];

                if(Arrays.equals(rightBackRanks, R1) || Arrays.equals(rightBackRanks, R2) || Arrays.equals(rightBackRanks, R3)) {
                    return false;
                } else {
                    return true;
                }
            }
        }

        int[] ranks = Rank(a);
        if (ranks[3]==4){

            int[] downBackRanks = new int[3];
            downBackRanks[0] = ranks[0];
            downBackRanks[1] = ranks[1];
            downBackRanks[2] = ranks[2];

            if(Arrays.equals(downBackRanks, R1) || Arrays.equals(downBackRanks, R2) || Arrays.equals(downBackRanks, R3)) {
                return true;
            } else {

                return false;
            }
        } else if (ranks[2]==4){

            int[] upBackRanks = new int[3];
            upBackRanks[0] = ranks[0];
            upBackRanks[1] = ranks[1];
            upBackRanks[2] = ranks[3];

            if(Arrays.equals(upBackRanks, R1) || Arrays.equals(upBackRanks, R2) || Arrays.equals(upBackRanks, R3)) {
                return false;
            } else {
                return true;
            }

        } else if (ranks[1]==4){
            int[] leftBackRanks = new int[3];
            leftBackRanks[0] = ranks[0];
            leftBackRanks[1] = ranks[3];
            leftBackRanks[2] = ranks[2];

            if(Arrays.equals(leftBackRanks, R1) || Arrays.equals(leftBackRanks, R2) || Arrays.equals(leftBackRanks, R3)) {
                return false;
            } else {
                return true;
            }
        }else if (ranks[0]==4){
            int[] rightBackRanks = new int[3];
            rightBackRanks[0] = ranks[0];
            rightBackRanks[1] = ranks[3];
            rightBackRanks[2] = ranks[2];

            if(Arrays.equals(rightBackRanks, R1) || Arrays.equals(rightBackRanks, R2) || Arrays.equals(rightBackRanks, R3)) {
                return false;
            } else {
                return true;
            }
        }

        return true;
    }

}
