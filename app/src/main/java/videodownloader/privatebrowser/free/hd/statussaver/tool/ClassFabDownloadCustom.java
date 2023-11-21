package videodownloader.privatebrowser.free.hd.statussaver.tool;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ClassFabDownloadCustom extends FloatingActionButton implements View.OnTouchListener {
    private static final float D_TOLERANCE = 10.0f;
    private float dX;
    private float dY;
    private float downRawX;
    private float downRawY;

    public ClassFabDownloadCustom(Context context) {
        super(context);
        init();
    }

    private void init() {
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int action = motionEvent.getAction();
        if (action == 0) {
            this.downRawX = motionEvent.getRawX();
            this.downRawY = motionEvent.getRawY();
            this.dX = view.getX() - this.downRawX;
            this.dY = view.getY() - this.downRawY;
            return true;
        } else if (action != 2) {
            if (action == 1) {
                float rawX = motionEvent.getRawX();
                float rawY = motionEvent.getRawY();
                float f = rawX - this.downRawX;
                float f2 = rawY - this.downRawY;
                if (Math.abs(f) >= D_TOLERANCE || Math.abs(f2) >= D_TOLERANCE) {
                    return true;
                }
                return performClick();
            }
            return super.onTouchEvent(motionEvent);
        } else {
            int width = view.getWidth();
            int height = view.getHeight();
            View view2 = (View) view.getParent();
            int width2 = view2.getWidth();
            int height2 = view2.getHeight();
            float min = Math.min((width2 - width) - marginLayoutParams.rightMargin, Math.max(marginLayoutParams.leftMargin, motionEvent.getRawX() + this.dX));
            view.animate().x(min).y(Math.min((height2 - height) - marginLayoutParams.bottomMargin, Math.max(marginLayoutParams.topMargin, motionEvent.getRawY() + this.dY))).setDuration(0L).start();
            return true;
        }
    }

    public ClassFabDownloadCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClassFabDownloadCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
}
