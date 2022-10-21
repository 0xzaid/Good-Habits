package com.example.goodhabits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // hide navigation bar
//        getSupportActionBar().hide();
//
        // hide status bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // text disappearing

        TextView textView = findViewById(R.id.splash_text);
        LottieAnimationView splash = findViewById(R.id.lottieAnimationView);
        textView.animate().translationX(1000).setDuration(1000).setStartDelay(2500);
        splash.animate().translationX(-1000).setDuration(1000).setStartDelay(2500);

        Thread thread = new Thread() {
            public void run(){
                try {
                    Thread.sleep(3500);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally{
                    Intent intent = new Intent(SplashActivity.this,
                            LoginPageActivity.class);
                    startActivity(intent);
                }
            }
        };
        thread.start();
    }
}