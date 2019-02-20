package demo.great.zhang.railwayvideo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class HeaderLayout extends RelativeLayout {
    public HeaderLayout(Context context) {
        this(context,null);
    }

    public HeaderLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        performClick();
        return true;
    }
}
