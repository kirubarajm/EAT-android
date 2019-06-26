package com.tovo.eat.ui.home.homemenu;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class OverlapDecoration extends RecyclerView.ItemDecoration {

    private final static int vertOverlap = -200;

    @Override
    public void getItemOffsets (Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        final int itemPosition = parent.getChildAdapterPosition(view);
        if (itemPosition == 0) {
            return; }
        outRect.set(vertOverlap, 0,0 , 0);


    }
} 