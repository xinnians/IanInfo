package com.example.ianlin.ianinfo.pages.base;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity implements OnPageInteractionListener.Base {

    @Override
    public void pressBack() {
        if (isActivityDestroying())
            return;
        onBackPressed();
    }

    /*--------------------------------------------------------------------------------------------*/
    /* Internal helpers */
    public boolean isActivityDestroying() {
        if (isFinishing()) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && isDestroyed()) {
            return true;
        }

        return false;
    }
}
