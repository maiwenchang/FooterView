# FooterView
 实现FooterView的一种新思路，当距离屏幕顶部不超过一屏高度会自动消失。
 
``` java

public class FooterView extends FrameLayout {

    public FooterView(@NonNull Context context) {
        super(context);
    }

    public FooterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FooterView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        View child = getChildAt(0);
        if (child != null) {
            int heightPixels = getContext().getResources().getDisplayMetrics().heightPixels;
            int rawY = heightPixels - getRawTop(getParent());
            if (rawY > 0 && top > rawY) {//FooterView的顶部距离屏幕顶部超过一屏高度
                getChildAt(0).setVisibility(VISIBLE);
            } else {
                getChildAt(0).setVisibility(GONE);
            }
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

    @Override
    public void addView(View child) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("FooterView can host only one direct child");
        }

        super.addView(child);
    }

}

```
