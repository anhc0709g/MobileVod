package com.itpro.mobileapp.mobilevod;

import android.app.Application;
import android.content.Context;

/**
 * Created by anhnt on 5/21/2015.
 */
public class MobileVodApplication extends Application {
    private static MobileVodApplication mInstance;
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        this.setAppContext(getApplicationContext());
    }

    public static MobileVodApplication getInstance(){
        return mInstance;
    }
    public static Context getAppContext() {
        return mAppContext;
    }
    public void setAppContext(Context mAppContext) {
        this.mAppContext = mAppContext;
    }
}
