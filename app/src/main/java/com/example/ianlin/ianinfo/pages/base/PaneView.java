package com.example.ianlin.ianinfo.pages.base;

import android.os.Bundle;

public class PaneView<T extends OnPageInteractionListener.Pane> extends InteractionView<T> {

    public static final String ARG_CONTAINER = "com.example.ianlin.ianinfo.pages.base.PaneView.ARG_CONTAINER";

    protected void switchPage(int page, Bundle args, boolean addToBackStack, boolean withAnimation) {
        int container = getArguments().getInt(ARG_CONTAINER, 0);
        getInteractionListener().switchPage(container, page, args, addToBackStack, withAnimation);
//        getInteractionListener().switchPage();
    }

    protected void addPage(int page, Bundle args, boolean addToBackStack, boolean withAnimation) {
        int container = getArguments().getInt(ARG_CONTAINER, 0);
        getInteractionListener().addPage(container, page, args, addToBackStack, withAnimation);
//        getInteractionListener().addPage();
    }
}
