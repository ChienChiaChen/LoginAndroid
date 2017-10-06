package com.prenetics.loginandroid;

import android.app.Application;

/**
 * Created by jianjiacheng on 10/6/17.
 */

public class Globals extends Application {
    private static Globals sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static Globals getInstance() {
        return sInstance;
    }
}
