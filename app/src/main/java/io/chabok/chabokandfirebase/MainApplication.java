package io.chabok.chabokandfirebase;

import android.app.Application;

import com.adpdigital.push.AdpPushClient;
import com.google.firebase.FirebaseApp;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(this);

        //AdpPushClient.init() should always be called in onCreate of Application class
        AdpPushClient.init(
                getApplicationContext(),
                MainActivity.class,
                "ipiguto", //based on your environment
                "3188804e67ec2fa182d9df74d7de8e249ec7edb3",          //based on your environment
                "sijloraci",     //based on your environment
                "tamnitajbag",  //based on your
                "703393057547"
        );

        //true connects to Sandbox environment
        //false connects to Production environment
        AdpPushClient.get().setDevelopment(true);

        String userId = AdpPushClient.get().getUserId();

        if (userId != null){
            AdpPushClient.get().register(userId);
        } else {
            AdpPushClient.get().registerAsGuest();
        }
    }
}
