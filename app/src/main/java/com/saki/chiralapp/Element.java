package com.saki.chiralapp;

import android.graphics.Paint;

import java.util.HashMap;
import java.util.Map;

public class Element {

    private String symbol;
    private int allowedBonds;
    private Paint elementPaint;
    private float mass;
    private int count;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getAllowedBonds() {
        return allowedBonds;
    }

    public void setAllowedBonds(int allowedBonds) {
        this.allowedBonds = allowedBonds;
    }

    public Paint getElementPaint() {
        return elementPaint;
    }

    public void setElementPaint(Paint elementPaint) {
        this.elementPaint = elementPaint;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Element(String symbol, int allowedBonds, float mass) {
        this.symbol = symbol;
        this.allowedBonds = allowedBonds;

        //Element paints
        Paint p = new Paint();
        p.setTextSize(50);
        p.setFakeBoldText(true);

        if(symbol=="C"){
            p.setARGB(255,0,0,0);
        } else if (symbol=="F") {
            p.setARGB(255, 0, 255, 0);
        } else if (symbol=="H") {
            p.setARGB(255, 0, 0, 0);
        } else if (symbol=="O") {
            p.setARGB(255, 255, 0, 0);
        } else if (symbol=="N") {
            p.setARGB(255, 0, 0, 204);
        } else if (symbol=="Cl") {
            p.setARGB(255, 0, 102, 0);
        } else if (symbol=="Br") {
            p.setARGB(255, 102, 0, 255);
        }else if (symbol=="I") {
            p.setARGB(255, 102, 0, 102);

        }

        this.elementPaint = p;
        this.mass = mass;

    }


    static HashMap<String,Element> elementHashMap = new HashMap<String, Element>(){{
        put("C",new Element("C",4,(float) 12.0107));
        put("F",new Element("F",1,(float) 18.998403));
        put("H",new Element("H",1,(float) 1.00784));
        put("O",new Element("O",3,(float) 15.999));
        put("N",new Element("N",4,(float) 14.0067));
        put("Cl",new Element("Cl",1, (float) 35.453));
        put("Br",new Element("Br",1,(float) 79.904));
        put("I",new Element("I",1,(float) 126.90447));



    }};



}
