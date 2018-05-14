package com.example.ianlin.ianinfo.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.apkfuns.logutils.LogUtils;
import com.example.ianlin.ianinfo.R;
import com.example.ianlin.ianinfo.constant.Page;

public class ActivityUtils {
    private static final String TAG = ActivityUtils.class.getSimpleName();

    /**
     * Replace fragment.
     * Note that when the passing fragment is the root fragment, the current transaction
     * won't be added into back stack EVEN IF {@code addToBackStack} is true.
     *
     * @param manager
     * @param fragment
     * @param containerId
     * @param tag
     * @param addToBackStack
     */
    public static void replaceFragment(FragmentManager manager, Fragment fragment, int containerId, String tag, boolean addToBackStack, boolean withAnimation) {
        if (manager != null && fragment != null) {
            FragmentTransaction transaction = manager.beginTransaction();

            if (withAnimation) {
                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
            }
            transaction.replace(containerId, fragment, tag);

            // DO NOT add the first transaction in back stack,
            // otherwise the very first page would be blank.
            if (manager.getBackStackEntryCount() == 0 || addToBackStack) {
                transaction.addToBackStack(tag);
            }
            try {
                transaction.commit();
            } catch (IllegalStateException e) {
                LogUtils.w(TAG, "[replaceFragment]" + e.getMessage());
            }
        }
    }

    /**
     * Add fragment.
     * Note that when the passing fragment is the root fragment, the current transaction
     * won't be added into back stack EVEN IF {@code addToBackStack} is true.
     *
     * @param manager
     * @param fragment
     * @param containerId
     * @param tag
     * @param addToBackStack
     */
    public static void addFragment(FragmentManager manager, Fragment fragment, int containerId, String tag, boolean addToBackStack, boolean withAnimation) {
        if (manager != null && fragment != null) {
            FragmentTransaction transaction = manager.beginTransaction();

            if (withAnimation) {
                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
            }
            transaction.add(containerId, fragment, tag);

            // DO NOT add the first transaction in back stack,
            // otherwise the very first page would be blank.
            if (manager.getBackStackEntryCount() == 0 || addToBackStack) {
                transaction.addToBackStack(tag);
            }

            try {
                transaction.commit();
            } catch (IllegalStateException e) {
                LogUtils.w(TAG, "[addFragment]" + e.getMessage());
            }
        }
    }

    /**
     * Clear back stack.
     *
     * @param manager
     */
    public static void clearFragmentBackStack(@NonNull FragmentManager manager) {
        manager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    /**
     * Remove fragment by page
     *
     * @param manager
     * @param page
     */
    public static void removeFragment(@NonNull FragmentManager manager, int page) {
        Fragment fragment = manager.findFragmentByTag(Page.tag(page));
        if (fragment != null) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(fragment);

            try {
                transaction.commit();
            } catch (IllegalStateException e) {
                LogUtils.w(TAG, "[removeFragment]" + e.getMessage());
            }
        }
    }

    public static void popBackByPageTag(@NonNull FragmentManager manager, String tag) {
        if (TextUtils.isEmpty(tag)) {
            return;
        }

        manager.popBackStackImmediate(tag, 0);
    }
}
