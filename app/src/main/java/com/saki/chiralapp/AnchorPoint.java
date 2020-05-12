package com.saki.chiralapp;

public class AnchorPoint {
    private float x;
    private float y;
    private String symbol;
    private AnchorPoint left;
    private AnchorPoint right;
    private AnchorPoint up;
    private boolean isWedgeStart = false;
    private AnchorPoint down;
    private boolean isDashStart = false;

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
}
