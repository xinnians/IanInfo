package com.example.ianlin.ianinfo.pages.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.ianlin.ianinfo.constant.Page;

public abstract class BaseView extends Fragment implements Contract.View{
    private static final String TAG = BaseView.class.getSimpleName();

    private int mPage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args.containsKey(Page.ARG_PAGE)) {
            mPage = args.getInt(Page.ARG_PAGE);
        } else {
            throw new IllegalStateException("Be sure that you put Page as an argument via Fragment#setArguments().");
        }
    }

    @Override
    public Context getActivityContext() {
        return getContext();
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public boolean isActive() {
        if(!isAdded())
            return false;

        Activity activity = getActivity();
        if(activity == null)
            return false;

        if (activity.isFinishing()) {
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
            return false;
        }

        return true;
    }

    /*--------------------------------------------------------------------------------------------*/
    /* Helpers */
    protected int getPage() {
        return mPage;
    }
}
