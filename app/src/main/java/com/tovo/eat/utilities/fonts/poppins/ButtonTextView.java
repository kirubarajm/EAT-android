package com.tovo.eat.utilities.fonts.poppins;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class ButtonTextView extends android.support.v7.widget.AppCompatTextView {


    public ButtonTextView(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Poppins-SemiBold.otf");
        this.setTypeface(face,  Typeface.BOLD);
    }

    public ButtonTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Poppins-SemiBold.otf");
        this.setTypeface(face,  Typeface.BOLD);
    }

    public ButtonTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Poppins-SemiBold.otf");
        this.setTypeface(face,  Typeface.BOLD);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }

}