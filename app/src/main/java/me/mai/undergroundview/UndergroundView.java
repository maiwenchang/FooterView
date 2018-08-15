package me.mai.undergroundview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.FrameLayout;

/**
 * > Created by Mai on 2018/7/26
 * *
 * > Description: 不满屏会自动消失的BottomView
 * *
 */
public class UndergroundView extends FrameLayout {

    public UndergroundView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public UndergroundView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public UndergroundView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_baseline, this, false);
        view.setVisibility(GONE);
        addView(view);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int heightPixels = getContext().getResources().getDisplayMetrics().heightPixels;
        int rawY = heightPixels - getRawTop(getParent());
        if (rawY > 0 && top > rawY) {
            getChildAt(0).setVisibility(VISIBLE);
        } else {
            getChildAt(0).setVisibility(GONE);
        }
    }

    //获取与屏幕顶部的距离
    private int getRawTop(ViewParent parent) {
        if (parent == null || ((ViewGroup) parent).getId() == Window.ID_ANDROID_CONTENT) {
            if (parent != null) {
                int[] position = new int[2];
                ((ViewGroup) parent).getLocationOnScreen(position);
                return position[1];
            }
            return 0;
        } else {
            return ((ViewGroup) parent).getTop() + getRawTop(parent.getParent());
        }
    }


}
