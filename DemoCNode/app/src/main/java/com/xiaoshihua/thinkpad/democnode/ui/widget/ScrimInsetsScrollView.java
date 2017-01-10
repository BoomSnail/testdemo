package com.xiaoshihua.thinkpad.democnode.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.xiaoshihua.thinkpad.democnode.R;

/**
 * Created by ThinkPad on 2016/8/5.
 */
public class ScrimInsetsScrollView extends ScrollView {

    private Drawable mInsertForeground;

    private Rect mInsets;
    private Rect mTempRect = new Rect();
    OnInsetsCallback mOnInsetsCallback;

    public ScrimInsetsScrollView(Context context) {
        super(context);
        init(context,null,0,0);
    }

    public ScrimInsetsScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0,0);
    }

    public ScrimInsetsScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr,0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ScrimInsetsScrollView,defStyleAttr,defStyleRes);
        mInsertForeground = array.getDrawable(R.styleable.ScrimInsetsScrollView_appInsetForeground);
        array.recycle();

        setWillNotDraw(true);
    }


    @Override
    protected boolean fitSystemWindows(Rect insets) {
        mInsets = new Rect(insets);
        setWillNotDraw(mInsertForeground == null);
        ViewCompat.postInvalidateOnAnimation(this);
        if (mOnInsetsCallback != null) {
            mOnInsetsCallback.onInsetsChanged(insets);
        }
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (mInsets != null && mInsertForeground != null){
            int sec = canvas.save();
            canvas.translate(getScrollX(),getScrollY());
            //top
            mTempRect.set(0,0,width,mInsets.top);
            mInsertForeground.setBounds(mTempRect);
            mInsertForeground.draw(canvas);
            //bottom
            mTempRect.set(0,height - mInsets.bottom,width,height);
            mInsertForeground.setBounds(mTempRect);
            mInsertForeground.draw(canvas);
            //left
            mTempRect.set(0,mInsets.top,mInsets.left,height - mInsets.bottom);
            mInsertForeground.setBounds(mTempRect);
            mInsertForeground.draw(canvas);
            //right
            mTempRect.set(width - mInsets.right,mInsets.top,width,height - mInsets.bottom);
            mInsertForeground.setBounds(mTempRect);
            mInsertForeground.draw(canvas);

            canvas.restoreToCount(sec);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mInsertForeground != null){
            mInsertForeground.setCallback(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mInsertForeground != null){
            mInsertForeground.setCallback(null);
        }
    }

    public void setOnInsetsCallback(OnInsetsCallback onInsetsCallback){
        this.mOnInsetsCallback = onInsetsCallback;
    }


    interface OnInsetsCallback{
        void  onInsetsChanged(Rect insets);
    }
}
