package io.chabok.chabokandfirebase;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.adpdigital.push.AdpPushClient;
import com.adpdigital.push.Callback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {

    public EditText userIdEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userIdEditText = findViewById(R.id.userIdEditText);
        userIdEditText.setText(AdpPushClient.get().getUserId());

        Button btn = (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdpPushClient.get().track("AddToCard");
            }
        });

        if(isNewInstall()){
            this.getFirebaseToken();
        }

        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdpPushClient.get().login(userIdEditText.getText().toString(), new Callback<String>() {
                    @Override
                    public void onSuccess(final String s) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                userIdEditText.setText(s);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("SAMPLE", "onFailure: ", throwable );
                    }
                });
            }
        });
    }

    boolean isNewInstall(){
        boolean newLanuch = getApplicationContext().getSharedPreferences("test", Context.MODE_PRIVATE).getBoolean("newLaunch",true);
        getApplicationContext().
                getSharedPreferences("test", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("newLaunch",false)
                .apply();

        return newLanuch;
    }

    private void getFirebaseToken(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    private static final String TAG = "io.chabok";

                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = "Firebase token is = " + token;
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
