package com.example.ianlin.ianinfo.pages;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.android.databinding.library.baseAdapters.BR;
import com.example.ianlin.ianinfo.R;
import com.example.ianlin.ianinfo.pages.base.PaneViewActivity;

public class MainActivity extends PaneViewActivity {

    private MainViewModel mViewModel = new MainViewModel(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setVariable(BR.mainActivity, mViewModel);

        mViewModel.mPageNumber.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {

            }
        });
    }

}
