package com.example.ianlin.ianinfo.pages.base;

import android.os.Bundle;
import android.support.annotation.IdRes;

public interface OnPageInteractionListener {

    interface Base{
        void pressBack();
    }

    interface Pane extends Base{
        void switchPage(@IdRes int container, int page, Bundle args, boolean addToBackStack, boolean withAnimation);
        void addPage(@IdRes int container, int page, Bundle args, boolean addToBackStack, boolean withAnimation);
    }
}
