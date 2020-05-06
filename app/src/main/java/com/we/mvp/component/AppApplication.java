package com.we.mvp.component;

import android.app.Application;
import android.util.Log;

import com.we.mvp.WeMvpFrame;

/**
 * Created to :
 *
 * @author cc.wang
 * @date 2020/5/6
 */
public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        WeMvpFrame.findClass();
        Log.e("cc.wang","AppApplication.onCreate.");
    }
}
