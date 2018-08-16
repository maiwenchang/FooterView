# FooterView
 实现FooterView的一种新思路，当距离屏幕顶部不超过一屏高度会自动消失。
 
 - ![image](https://github.com/maiwenchang/FooterView/raw/master/art/Screenshot.png)
 
 ### Usage
 放在RecyclerView下方即可：
 ``` xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <android.support.v7.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

    <org.salient.autofooter.FooterView
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <include layout="@layout/layout_footer"/>

    </org.salient.autofooter.FooterView>

</LinearLayout>
 ```
 
 > Note: `FooterView`继承于`FramLayout`，本身不带视图，可以向上面的例子一样，在xml布局中加入视图，或者调用`FooterView.addView(View child)`添加视图，需要注意的是，`FooterView`只能添加一个直属子View
 
 
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
