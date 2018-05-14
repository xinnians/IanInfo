package com.example.ianlin.ianinfo.components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.example.ianlin.ianinfo.R;

public class SplashTextView extends ConstraintLayout {

    private View mView;
    private TextView mViewTitle, mViewDescription;
    private View mViewMask;
    private String mTextTitle;
    private int mTitleIndex;
    private long mDelay = 150; //Default 150ms delay
    private SplashTextViewAnimationListener mSplashTextViewAnimationListener;
    private Handler mHandler = new Handler();
    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            mViewTitle.setText(mTextTitle.subSequence(0, mTitleIndex++));
            if (mTitleIndex <= mTextTitle.length()) {
                mHandler.postDelayed(characterAdder, mDelay);
            } else {
                test();
//                test2();
            }
        }
    };


    public SplashTextView(Context context) {
        super(context);
        initView(context);
    }

    public SplashTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public SplashTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context) {
        initView(context, null);
    }

    private void initView(Context context, AttributeSet attrs) {
        mView = inflate(context, R.layout.view_splash_text, this);
        mViewTitle = mView.findViewById(R.id.text_title);
        mViewDescription = mView.findViewById(R.id.test_description);
        mViewMask = mView.findViewById(R.id.view_mask);
    }

    public void animateText(String text, SplashTextViewAnimationListener splashTextViewAnimationListener) {
        mSplashTextViewAnimationListener = splashTextViewAnimationListener;
        mTextTitle = text;
        mTitleIndex = 0;

        mViewTitle.setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }

    private void test() {

        ObjectAnimator viewTitleAnimator = ObjectAnimator.ofFloat(mViewTitle, "translationX", 0, mViewTitle.getWidth() * (-0.5f));
        ObjectAnimator viewDescriptionAnimator = ObjectAnimator.ofFloat(mViewDescription, "translationX", 0, mViewDescription.getWidth() * (0.2f));
        ObjectAnimator viewMaskAnimator = ObjectAnimator.ofFloat(mViewMask, "translationX", 0, mViewMask.getWidth() * (-1));


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.play(viewTitleAnimator)
                .with(viewMaskAnimator)
                .with(viewDescriptionAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation, boolean isReverse) {

            }

            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                if (mSplashTextViewAnimationListener == null) {
                    return;
                }
                mSplashTextViewAnimationListener.onAnimationEnd();
            }
        });
        animatorSet.start();

    }

    public interface SplashTextViewAnimationListener {
        void onAnimationEnd();
    }
}