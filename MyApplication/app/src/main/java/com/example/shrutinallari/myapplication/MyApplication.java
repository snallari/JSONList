package com.example.shrutinallari.myapplication;

import android.app.Application;
import android.content.Context;

/**
 * Created by shrutinallari on 9/25/15.
 */
public class MyApplication extends Application {

    private static MyApplication mInstance;
    private static Context mAppContext;

    public static MyApplication getInstance() {
        return mInstance;
    }

    public static Context getmAppContext() {
        return mAppContext;
    }

    public static void setmAppContext(Context mAppContext) {
        MyApplication.mAppContext = mAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        setmAppContext(getApplicationContext());
    }


}
