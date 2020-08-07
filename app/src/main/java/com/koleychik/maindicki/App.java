package com.koleychik.maindicki;

import android.app.Application;
import android.util.Log;

import com.koleychik.maindicki.additions.Keys;
import com.koleychik.maindicki.coroutines.PutToSPrefStyle;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("ClearFromRecentService", "on Create app");

        PutToSPrefStyle putToSPrefStyle = new PutToSPrefStyle(getSharedPreferences(Keys.APP_NAME_FOR_SPREF, MODE_PRIVATE));
        putToSPrefStyle.getStyle();
    }


}
