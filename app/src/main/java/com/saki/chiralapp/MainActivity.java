package com.saki.chiralapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Canvas c = new Canvas();
        Paint p = new Paint ();
        p.setARGB(40,70,30,80);
        // anchor.draw(c,p);
        c.drawCircle(50,50,20, p);
        View v = (View) findViewById (R.id.view);

        ConstraintLayout Layout = (ConstraintLayout) findViewById(R.id.Layout);
        Layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                TextView testText = (TextView) findViewById(R.id.testText);
                testText.setText(event.getX()+"");
                //OvalShape anchor = new OvalShape();
                //anchor.resize(100,100);
                Canvas c = new Canvas();
                Paint p = new Paint ();
                p.setARGB(40,70,30,80);
               // anchor.draw(c,p);
                c.drawCircle(event.getX(),event.getY(),20, p);
                v.draw(c);
                return false;
            }
        });
    }
}
