package com.geekmanish.wallpapers.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class GridImageItem extends ImageView {

    public GridImageItem(Context context) {
        super(context);
    }

    public GridImageItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridImageItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec); // This is the   key that will make the height equivalent to its width
    }
}
