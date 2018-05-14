package com.example.ianlin.ianinfo.pages.base;

import android.content.Context;

public class BaseViewModel<T extends OnPageInteractionListener.Pane> {

    T mInteractionView;
    Context mContext;

    public BaseViewModel(Context context, T interactionView) {
        mContext = context;
        mInteractionView = interactionView;
    }

    public T getInteractionView() {
        return mInteractionView;
    }

    public Context getContext() {
        return mContext;
    }
}
