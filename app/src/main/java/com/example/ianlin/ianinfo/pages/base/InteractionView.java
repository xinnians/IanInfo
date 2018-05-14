package com.example.ianlin.ianinfo.pages.base;

import android.content.Context;

public abstract class InteractionView<I extends OnPageInteractionListener.Base> extends BaseView {
    private static final String TAG = InteractionView.class.getSimpleName();

    private I mInteractionListener;

    /*--------------------------------------------------------------------------------------------*/
    /* Fragment */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            if (getParentFragment() != null) {
                mInteractionListener = (I) getParentFragment();
            } else {
                mInteractionListener = (I) context;
            }
        } catch (ClassCastException e) {
            throw new ClassCastException("Either parent fragment or activity should implement OnPageInteractionListener.");
        }
    }

    /*--------------------------------------------------------------------------------------------*/
    /* Helpers */
    protected I getInteractionListener() {
        return mInteractionListener;
    }


}
