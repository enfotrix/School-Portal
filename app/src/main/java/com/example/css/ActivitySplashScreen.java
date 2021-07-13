package com.example.css;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

public class ActivitySplashScreen extends AppCompatActivity {

    private Utils utils;

    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBar = (ProgressBar)findViewById(R.id.progressBa_splash);
        utils=new Utils(this);

        new CountDownTimer(2000, 1000) {
            public void onFinish() {
                if(utils.isLoggedIn()){
                    Intent intent = new Intent(ActivitySplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(ActivitySplashScreen.this, ActivitySignIn.class);
                    startActivity(intent);
                    finish();
                }
            }

            public void onTick(long millisUntilFinished) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }.start();
    }


}