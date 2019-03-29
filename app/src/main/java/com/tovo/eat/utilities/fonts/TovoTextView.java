package com.tovo.eat.utilities.fonts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class TovoTextView extends android.support.v7.widget.AppCompatTextView {


    public TovoTextView(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "icomoon.ttf");
        this.setTypeface(face);
    }

    public TovoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "icomoon.ttf");
        this.setTypeface(face);
    }

    public TovoTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "icomoon.ttf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }

}