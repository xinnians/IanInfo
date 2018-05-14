package com.example.ianlin.ianinfo.pages;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ianlin.ianinfo.components.SplashTextView;


public class TestViewModel {

    private Context mContext;

    public ObservableBoolean isAnimation = new ObservableBoolean(false);
    public static ObservableBoolean logoVisiable = new ObservableBoolean(false);

    public TestViewModel(Context context) {
        mContext = context;
        isAnimation.set(true);
        logoVisiable.set(false);
    }

    @BindingAdapter("animateText")
    public static void animateText(SplashTextView view, boolean isAnimation) {
        if (isAnimation) {
            SplashTextView.SplashTextViewAnimationListener listener = new SplashTextView.SplashTextViewAnimationListener() {
                @Override
                public void onAnimationEnd() {
                    logoVisiable.set(true);
                }
            };
            view.animateText("Ian", listener);
        }
    }

    @BindingAdapter("logoView")
    public static void animateLogo(final ImageView view, boolean visiable) {
        if (visiable) {
            ObjectAnimator viewScaleXBig = ObjectAnimator.ofFloat(view, "scaleX", 0, 1.1f);
            ObjectAnimator viewScaleYBig = ObjectAnimator.ofFloat(view, "scaleY", 0, 1.1f);

            ObjectAnimator viewScaleXSmall = ObjectAnimator.ofFloat(view, "scaleX", 1.1f, 0.9f);
            ObjectAnimator viewScaleYSmall = ObjectAnimator.ofFloat(view, "scaleY", 1.1f, 0.9f);

            ObjectAnimator viewScaleXOri = ObjectAnimator.ofFloat(view, "scaleX", 0.9f, 1);
            ObjectAnimator viewScaleYOri = ObjectAnimator.ofFloat(view, "scaleY", 0.9f, 1);

            AnimatorSet animatorBigSet = new AnimatorSet();
            animatorBigSet.setDuration(400);
            animatorBigSet.setInterpolator(new DecelerateInterpolator());
            animatorBigSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation, boolean isReverse) {
                    view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation, boolean isReverse) {

                }
            });
            animatorBigSet.playTogether(viewScaleXBig, viewScaleYBig);

            AnimatorSet animatorSmallSet = new AnimatorSet();
            animatorSmallSet.setDuration(150);
            animatorSmallSet.setInterpolator(new DecelerateInterpolator());
            animatorSmallSet.playTogether(viewScaleXSmall, viewScaleYSmall);

            AnimatorSet animatorOriSet = new AnimatorSet();
            animatorOriSet.setDuration(200);
            animatorOriSet.setInterpolator(new DecelerateInterpolator());
            animatorOriSet.playTogether(viewScaleXOri, viewScaleYOri);

            AnimatorSet all = new AnimatorSet();
            all.playSequentially(animatorBigSet, animatorSmallSet, animatorOriSet);
            all.start();
        }
    }

    public void onClickTest(View view) {
        Toast.makeText(mContext, "onClickTest", Toast.LENGTH_SHORT).show();
    }
}
