package com.example.ianlin.ianinfo.pages.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.SparseArray;

import com.apkfuns.logutils.LogUtils;
import com.example.ianlin.ianinfo.constant.Page;
import com.example.ianlin.ianinfo.utils.ActivityUtils;

public class PaneViewActivity extends BaseActivity implements OnPageInteractionListener.Pane {

    private static final String TAG = PaneViewActivity.class.getSimpleName();

    private SparseArray<String> mTopFragment = new SparseArray<>(2);
    private int mResultCode = -1;
    private Bundle mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
            Listen to back stack changed event so that we could notify every topmost fragment in
            each container.
         */
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                for (int i = 0; i < mTopFragment.size(); i++) {
                    int container = mTopFragment.keyAt(i);
                    String tag = mTopFragment.valueAt(i);
                    Fragment currentTop = getSupportFragmentManager().findFragmentById(container);

                    /*
                        When a new fragment has been added, say B over A, this method would be invoked twice,
                        for currentTop is A and for currentTop is B respectively. Since we only need the second
                        event, compare the last top fragment's tag with the current one. If equals, ignore it.

                     */
                    if (currentTop != null && !TextUtils.equals(tag, currentTop.getTag())) {
                        if (currentTop instanceof OnFragmentBackStackChangedListener) {
                            ((OnFragmentBackStackChangedListener) currentTop).onBackStackChanged(mResultCode, mData);
                            mResultCode = -1;
                            mData = null;
                        }

                        // Record down as the last top fragment.
                        mTopFragment.put(container, currentTop.getTag());
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Whether any fragment has consumed the back event
        boolean isConsumed = false;
        int size = mTopFragment.size();
        Fragment top = null;
        for (int i = 0; i < size; i++) {
            int container = mTopFragment.keyAt(i);
            top = getSupportFragmentManager().findFragmentById(container);
            if (!(top instanceof Contract.View)) {
                continue;
            }

            Contract.View view = (Contract.View) top;
            if (view.onBackPressed()) {
                isConsumed = true;
            }
        }

        if (isConsumed) {
            return;
        }

        if (getSupportFragmentManager().getBackStackEntryCount() < 2) {
            finish();
            return;
        }

        super.onBackPressed();
    }

    @Override
    public void switchPage(int container, int page, Bundle args, boolean addToBackStack, boolean withAnimation) {
        if(isActivityDestroying()){
            return;
        }
        LogUtils.d( "[switchPage] page="+page);
        Contract.View nextView = findViewByPage(page);
        if (nextView == null) {
            nextView = createNewPage(container, page, args);
        }

        Fragment fragment = (Fragment) nextView;
        String tag = Page.tag(page);

        if (mTopFragment.indexOfKey(container) < 0) {
            // First page
            mTopFragment.put(container, tag);
        }

        ActivityUtils.replaceFragment(
                getSupportFragmentManager(),
                fragment,
                container,
                tag,
                addToBackStack,
                withAnimation);
    }

    @Override
    public void addPage(int container, int page, Bundle args, boolean addToBackStack, boolean withAnimation) {
        if (isActivityDestroying())
            return;
        LogUtils.d(TAG, "[addPage] page=", page);

        Contract.View nextView = findViewByPage(page);

        if (nextView != null) {
            // Pop back to this page
            String tag = ((Fragment) nextView).getTag();
            ActivityUtils.popBackByPageTag(getSupportFragmentManager(), tag);
        } else {
            nextView = createNewPage(container, page, args);
            Fragment fragment = (Fragment) nextView;
            String tag = Page.tag(page);

            if (mTopFragment.indexOfKey(container) < 0) {
                // First page
                mTopFragment.put(container, tag);
            }

            ActivityUtils.addFragment(
                    getSupportFragmentManager(),
                    fragment,
                    container,
                    tag,
                    addToBackStack,
                    withAnimation);
        }
    }

    /*--------------------------------------------------------------------------------------------*/
    /* Internal helpers */
    private Contract.View findViewByPage(int page) {
        Contract.View view = null;

        String tag = Page.tag(page);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment instanceof Contract.View) {
            view = (Contract.View) fragment;
        }

        return view;
    }

    private Contract.View createNewPage(@IdRes int container, int page, Bundle args) {
        if (args == null) {
            args = new Bundle();
        }

        args.putInt(PaneView.ARG_CONTAINER, container);
        return Page.view(page, args);
    }

    /**
     * When the back stack changed, activity would notify every topmost fragment in each container that implements
     * this listener.
     * First fragment won't be notified while it was not added into back stack.
     */
    public interface OnFragmentBackStackChangedListener {
        void onBackStackChanged(int resultCode, Bundle data);
    }
}
