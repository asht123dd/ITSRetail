package com.sdl.dart.itsretail;

import android.app.Application;
import android.os.SystemClock;

/**
 * Created by ashutosh on 7/2/18.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(2000);
    }
}
