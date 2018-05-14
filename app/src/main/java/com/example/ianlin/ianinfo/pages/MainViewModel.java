package com.example.ianlin.ianinfo.pages;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableInt;
import android.widget.FrameLayout;

import com.example.ianlin.ianinfo.constant.Page;

public class MainViewModel {

    private Context mContext;

    public ObservableInt mPageNumber = new ObservableInt();

    public MainViewModel(Context context) {
        mContext = context;
        mPageNumber.set(Page.SPLASH);
    }

    @BindingAdapter("pageId")
    public static void goPage(FrameLayout layout,int pageId){
        if(layout.getContext() instanceof MainActivity){
            ((MainActivity) layout.getContext()).switchPage(layout.getId(), pageId, null, true, false);
        }
    }

}
