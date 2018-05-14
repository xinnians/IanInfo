package com.example.ianlin.ianinfo.constant;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.ianlin.ianinfo.pages.base.Contract;
import com.example.ianlin.ianinfo.pages.splash.SplashFragment;

public class Page {

    public static final String ARG_PAGE = "com.example.ianlin.ianinfo.constant.Page.ARG_PAGE";

    public static final int INVALID_PAGE = -1;

    public static final int SPLASH = 1000;

    /*--------------------------------------------------------------------------------------------*/
    /* Helpers */
    public static String tag(int page) {
        return "page_" + page;
    }

    /*--------------------------------------------------------------------------------------------*/
    /* Page's view */
    public static Contract.View view(int page, Bundle args){
        if(args == null){
            args = new Bundle();
        }

        Contract.View result;
        switch (page){
            case SPLASH:
                result = SplashFragment.newInstance();
                break;
            default:
                throw new IllegalArgumentException("No matched view! page = " + page);
        }

        args.putInt(ARG_PAGE, page);
        putData((Fragment) result, args);

        return result;
    }
    /*--------------------------------------------------------------------------------------------*/
    /* Internal helpers */
    private static void putData(Fragment fragment, Bundle data) {
        Bundle args = fragment.getArguments();
        if (args == null) {
            fragment.setArguments(data);
        } else {
            args.putAll(data);
        }
    }

}
