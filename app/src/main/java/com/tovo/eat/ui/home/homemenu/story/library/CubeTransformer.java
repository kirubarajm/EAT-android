package com.tovo.eat.ui.home.homemenu.story.library;

import android.support.v4.view.ViewPager;
import android.view.View;

public class CubeTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View view, float position) {
        //if position = 0 (current image) pivot on x axis is on the right, else if
        // position > 0, (next image) pivot on x axis is on the left (origin of the axis)
        view.setPivotX(position <= 0 ? view.getWidth() : 0.0f);
        view.setPivotY(view.getHeight() * 0.5f);

        //it rotates with 90 degrees multiplied by current position
        view.setRotationY(90f * position);
    }
}
