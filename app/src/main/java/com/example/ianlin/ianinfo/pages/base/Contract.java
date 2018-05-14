package com.example.ianlin.ianinfo.pages.base;

import android.content.Context;

public interface Contract {
    interface View {
        Context getActivityContext();
        boolean onBackPressed();
        boolean isActive();
//        void showFullScreenLoading();
//        void dismissFullScreenLoading();
    }
}
