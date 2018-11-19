package com.dev.anton.cbr.presentation.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.ProgressBar;

import com.dev.anton.cbr.R;

import static android.view.View.MeasureSpec.getSize;

public class LoadingView extends ViewGroup {

    private ProgressBar loading;
    private int fadeDuration = 400;

    public LoadingView(Context context) {
        super(context);
        init(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }


    private void init(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setZ(Float.MAX_VALUE);
        }
        loading = new ProgressBar(context,
                null,
                android.R.attr.progressBarStyle);
        loading.setIndeterminate(true);
        loading.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        this.addView(loading);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getSize(widthMeasureSpec);
        int height = getSize(heightMeasureSpec);
        width = Math.max(width, getMinimumWidth());
        height = Math.max(height, getMinimumHeight());

        View view = getChildAt(0);
        view.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST));

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View view = getChildAt(0);

        int width = view.getMeasuredWidth() / 2;
        int height = view.getMeasuredHeight() / 2;
        int centerW = (r - l) / 2;
        int centerH = (b - t) / 2;

        view.layout(centerW - width, centerH - height, centerW + width, centerH + height);
    }

    public void showLoading() {
        startFadeAnimation(true).withStartAction(new Runnable() {
            @Override
            public void run() {
                setVisibility(true);
            }
        });
    }

    public void hideLoading() {
        startFadeAnimation(false).withEndAction(new Runnable() {
            @Override
            public void run() {
                setVisibility(false);
            }
        });
    }

    private void setVisibility(boolean visible) {
        this.setVisibility(visible ? VISIBLE : GONE);
    }

    private ViewPropertyAnimator startFadeAnimation(final boolean in) {
        float alpha = in ? 1f : 0f;
        float resetAlpha = 1f - alpha;
        loading.animate().cancel();
        loading.setAlpha(resetAlpha);
        return loading.animate().alpha(alpha).setDuration(fadeDuration);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        loading.animate().cancel();
    }
}
