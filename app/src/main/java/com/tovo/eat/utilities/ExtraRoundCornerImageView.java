package com.tovo.eat.utilities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class ExtraRoundCornerImageView extends AppCompatImageView {

    private float radius =35.0f;
    private Path path;
    private RectF rect;

    public ExtraRoundCornerImageView(Context context) {
        super(context);
        init();
    }

    public ExtraRoundCornerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExtraRoundCornerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        path = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        path.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}