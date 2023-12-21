package com.example.e_recycler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    String message = "event being invoked";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Log.d(message, "Oncreate() method");

        TextView textView = findViewById(R.id.textViewSplash);
        textView.animate().translationX(2000).setDuration(1000).setStartDelay(2500);

        Thread thread = new Thread(){
            public void run(){
                try{
                    Thread.sleep(4000);
                } catch (Exception e){
                    e.printStackTrace();
                } finally{
                    Intent intent = new Intent(SplashScreen.this, WelcomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        thread.start();
    }
}