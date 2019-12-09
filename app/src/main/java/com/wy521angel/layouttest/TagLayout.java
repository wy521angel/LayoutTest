package com.wy521angel.layouttest;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TagLayout extends ViewGroup {
    List<Rect> childrenBounds = new ArrayList<>();//存储子View的位置

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthUsed = getPaddingLeft();//最宽的那一行的宽度
        int heightUsed = getPaddingTop();
        int lineWidthUsed = getPaddingLeft();//当前行的宽度，如果换行，这个值要清空为初始值
        int lineMaxHeight = getPaddingTop();//一行中最高的高度
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childLeftMargin = lp.leftMargin;
            int childRightMargin = lp.rightMargin;
            int childTopMargin = lp.topMargin;
            int childBottomMargin = lp.bottomMargin;

            measureChildWithMargins(child, widthMeasureSpec, getPaddingLeft()
                    , heightMeasureSpec,
                    heightUsed);

            if (specMode != MeasureSpec.UNSPECIFIED && lineWidthUsed + child.getMeasuredWidth() + getPaddingRight() > specWidth) {//已用宽度加上子View的宽度超过了父布局整体宽度，换行
                lineWidthUsed = getPaddingLeft();
                heightUsed += lineMaxHeight;
                lineMaxHeight = getPaddingTop();
                measureChildWithMargins(child, widthMeasureSpec,
                        getPaddingLeft(), heightMeasureSpec,
                        heightUsed);
            }

            Rect childBound;
            if (childrenBounds.size() <= i) {
                childBound = new Rect();
                childrenBounds.add(childBound);
            } else {
                childBound = childrenBounds.get(i);
            }

            childBound.set(lineWidthUsed, heightUsed, lineWidthUsed + child.getMeasuredWidth(),
                    heightUsed + child.getMeasuredHeight());
            lineWidthUsed += child.getMeasuredWidth();
            widthUsed = Math.max(widthUsed, lineWidthUsed);
            lineMaxHeight = Math.max(lineMaxHeight, child.getMeasuredHeight());
        }
        int width = widthUsed;
        int height = lineMaxHeight + heightUsed + getPaddingBottom();
        setMeasuredDimension(specWidth, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Rect childBounds = childrenBounds.get(i);
            child.layout(childBounds.left, childBounds.top, childBounds.right, childBounds.bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }
}


