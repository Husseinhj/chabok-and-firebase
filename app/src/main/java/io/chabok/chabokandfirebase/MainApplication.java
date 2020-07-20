package io.chabok.chabokandfirebase;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.adpdigital.push.AdpPushClient;
import com.adpdigital.push.AppState;
import com.adpdigital.push.LogLevel;
import com.adpdigital.push.config.Environment;
import com.google.firebase.FirebaseApp;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences p = getSharedPreferences("mamad", Context.MODE_PRIVATE);

        AdpPushClient.setLogLevel(LogLevel.VERBOSE);

        FirebaseApp.initializeApp(this);

        AdpPushClient.configureEnvironment(Environment.SANDBOX);

        AdpPushClient.get().addListener(this);

    }

    public void onEvent(AppState state){

    }
}
