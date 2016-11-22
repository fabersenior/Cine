package com.faberospina.cine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private static final  long SPLAS_DELAY=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        SavePreferences("kCompra","1");
        //commit 3

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

//                if (name.length()!=0 || correo.length()!=0 || pass.length()!=0){
//                    Intent intent = new Intent(getApplicationContext(),Main1Activity.class);
//                    startActivity(intent);
//                    finish();
//
//                }else {

                    Intent i = new Intent().setClass(SplashActivity.this, PreLoginActivity.class);//loginActivtty
                    startActivity(i);
                    finish();
//                }
//                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task,SPLAS_DELAY);
    }

    private void SavePreferences(String key, String value){
        SharedPreferences prefs  = getApplicationContext().getSharedPreferences("com.sp.main_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
