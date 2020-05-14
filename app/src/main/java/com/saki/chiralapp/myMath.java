package com.saki.chiralapp;

public class myMath {

    //takes the coordinates of two points and the boolean above or below; returns a shorter line segment translated up or down from x axis
    //return [0: xOrigin, 1: yOrigin, 2: phi, 3: xsbStart, 4: ysbStart, 5: xsbEnd, 6: ysbEnd]
    public static float[] shortBond(float xs, float ys, float xe, float ye, boolean isAbove){
        float r = (float) Math.hypot((xe-xs),(ye-ys));
        float phi = (float) Math.asin((ye-ys)/r);
        int shortenBy = 20;
        int gap = 20;

        if (ys>ye && isAbove){

            float xsbStart = xs+shortenBy;
            float ysbStart = ys-gap;
            float xsbEnd = xs+r-shortenBy;
            float ysbEnd = ys-gap;
            float xOrigin = xs-shortenBy;
            float yOrigin = ys-gap;

            float[] sbInfo = new float[7];
            sbInfo[0] = xOrigin;
            sbInfo[1] = yOrigin;
            sbInfo[2] = phi;
            sbInfo[3] = xsbStart;
            sbInfo[4] = ysbStart;
            sbInfo[5] = xsbEnd;
            sbInfo[6] = ysbEnd;

            return sbInfo;

        } else if (ye>ys && !isAbove){

            float xsbStart = xs+shortenBy;
            float ysbStart = ys+gap;
            float xsbEnd = xs+r-shortenBy;
            float ysbEnd = ys+gap;
            float xOrigin = xs-shortenBy;
            float yOrigin = ys+gap;

            float[] sbInfo = new float[7];
            sbInfo[0] = xOrigin;
            sbInfo[1] = yOrigin;
            sbInfo[2] = phi;
            sbInfo[3] = xsbStart;
            sbInfo[4] = ysbStart;
            sbInfo[5] = xsbEnd;
            sbInfo[6] = ysbEnd;

            return sbInfo;

        } else if (ys>ye && !isAbove){

            float xsbStart = xs+2*shortenBy;
            float ysbStart = ys+gap;
            float xsbEnd = xs+r+shortenBy;
            float ysbEnd = ys+gap;
            float xOrigin = xs+shortenBy;
            float yOrigin = ys+gap;

            float[] sbInfo = new float[7];
            sbInfo[0] = xOrigin;
            sbInfo[1] = yOrigin;
            sbInfo[2] = phi;
            sbInfo[3] = xsbStart;
            sbInfo[4] = ysbStart;
            sbInfo[5] = xsbEnd;
            sbInfo[6] = ysbEnd;

            return sbInfo;

        } else {

            float xsbStart = xs+2*shortenBy;
            float ysbStart = ys-gap;
            float xsbEnd = xs+r+shortenBy;
            float ysbEnd = ys-gap;
            float xOrigin = xs+shortenBy;
            float yOrigin = ys-gap;

            float[] sbInfo = new float[7];
            sbInfo[0] = xOrigin;
            sbInfo[1] = yOrigin;
            sbInfo[2] = phi;
            sbInfo[3] = xsbStart;
            sbInfo[4] = ysbStart;
            sbInfo[5] = xsbEnd;
            sbInfo[6] = ysbEnd;

            return sbInfo;

        }
    }

    //takes the coordinates of a point and an origin of rotation, and rotates the point around the origin by an angle phi;
    //returns an array of floats: the coordinates of the rotated points
    public static float[] rotate(float x1, float y1, float xo, float yo, float phi) {
        //translate point to true origin by subtracting out xo and yo; t = "translated", Br = "before rotation"
        float x1tBr = x1-xo;
        float y1tBr = y1-yo;
        //rotate point by phi; Ar = "after rotation"
        float x1tAr = (float) ((x1tBr* Math.cos(phi))-(y1tBr* Math.sin(phi)));
        float y1tAr = (float) ((y1tBr*Math.cos(phi))+(x1tBr*Math.sin(phi)));
        //translate rotated points back to original location by adding xo and yo
        float xFinal = x1tAr+xo;
        float yFinal = y1tAr+yo;
        //place final points in array of size 2 and output the array
        float[] coordinates = new float[2];
        coordinates[0] = xFinal;
        coordinates[1] = yFinal;
        return coordinates;
    }

    //this method takes the coordinates of two points and returns the angle between the line segment and the x axis with the minimum absolute value
    public static float phi(float xs, float ys, float xe, float ye) {
       float r = (float) Math.hypot((xe-xs),(ye-ys));
       float phi = (float) Math.asin((ye-ys)/r);
       return phi;

    }
}
