package com.wy521angel.layouttest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class CircleView extends View {
    private static final int RADIUS = (int) Utils.dp2px(80);
    private static final int PADDING = (int) Utils.dp2px(30);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);


    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = (PADDING + RADIUS) * 2;
        int width = resolveSizeAndState(size, widthMeasureSpec, 0);
        int height = resolveSizeAndState(size, heightMeasureSpec, 0);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.RED);
        canvas.drawCircle(PADDING + RADIUS, PADDING + RADIUS, RADIUS, paint);
    }
}
