package com.example.ianlin.ianinfo.pages.splash;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.example.ianlin.ianinfo.R;
import com.example.ianlin.ianinfo.pages.base.OnPageInteractionListener;
import com.example.ianlin.ianinfo.pages.base.PaneView;

public class SplashFragment extends PaneView<OnPageInteractionListener.Pane>{

    SplashViewModel model = new SplashViewModel(getContext(),getInteractionListener());

    public static SplashFragment newInstance(){
        return new SplashFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ViewDataBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_splash,container,false);
        binding.setVariable(BR.viewModel, new SplashViewModel(getContext(),getInteractionListener()));
        return binding.getRoot();
    }
}
