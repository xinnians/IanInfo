package com.example.ianlin.ianinfo.pages.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.example.ianlin.ianinfo.components.SplashTextView;
import com.example.ianlin.ianinfo.pages.base.BaseViewModel;
import com.example.ianlin.ianinfo.pages.base.OnPageInteractionListener;

public class SplashViewModel extends BaseViewModel{

    public SplashViewModel(Context context, OnPageInteractionListener.Pane interactionView) {
        super(context, interactionView);
        isAnimation.set(true);
        logoVisiable.set(false);
        test.set(new SplashTextView.SplashTextViewAnimationListener() {
            @Override
            public void onAnimationEnd() {
                logoVisiable.set(true);
            }
        });
    }

    public ObservableBoolean isAnimation = new ObservableBoolean(false);
    public ObservableBoolean logoVisiable = new ObservableBoolean(false);
    public ObservableField<SplashTextView.SplashTextViewAnimationListener> test = new ObservableField<>();

    @BindingAdapter({"animateText","animationListener"})
    public static void animateText(SplashTextView view, boolean isAnimation,SplashTextView.SplashTextViewAnimationListener listener) {
        LogUtils.d("[animateText]"+" isAnimation"+isAnimation);
        if (isAnimation) {
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
        Toast.makeText(getContext(), "onClickTest", Toast.LENGTH_SHORT).show();
    }


}
