# FooterView

> 简书: [https://github.com/maiwenchang/FooterView](https://github.com/maiwenchang/FooterView)

 实现FooterView的一种新思路，当FooterView距离屏幕顶部不超过一屏高度会自动消失。
 
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
 
 > Note: `FooterView`继承于`FramLayout`，本身不带视图，可以像上面的例子一样，在xml布局中加入视图，或者调用`FooterView.addView(View child)`添加视图，需要注意的是，`FooterView`只能添加一个直属子View。
 
 Feel free to copy:
 
 
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
### 简单讲一下实现思路
要实现`RecyclerView`不满一屏不显示`FooterView`，关键在于如何知道`RecyclerView`不满一屏，当然`RecyclerView`上边还有`ActionBar`，`Toolbar`，状态栏等等，如果仅仅去比较`RecyclerView`的高度和屏幕高度，显然不可行。因此，产品经理心里想要的效果应当是应当是`RecyclerView`和状态栏等加起来铺不满一屏的时候，FooterView就不显示。

如果用传统的方式，需要把FooterView作为`RecyclerView`的一个Item去处理，并且把状态栏等高度考虑进去，以决定FooterView的是否显示内容。

###### 这里尝试用一种新的方式去实现，就是让FooterView放在`RecyclerView`的下面，根据自己的位置自行决定要不要显示。实现步骤：

 - 1.新建一个类`FooterView.class`,让它继承自`FramLayout`。
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
}

```

 - 2.重写`onLayout()`方法，在FooterView添加到布局或者布局发生变动时判断是否显示内容。
``` java
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
```
- 3.利用递归获取与屏幕顶部的距离。
``` java
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
```

