package com.example.user.attendee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Attendee extends AppCompatActivity {
    private TextView splashtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_attendee);

        SharedPreferences sharedPreferences = getSharedPreferences("Shared Pref", MODE_PRIVATE);

        if(!sharedPreferences.contains("First Run")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Attendee.this, WelcomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000 );
        }
        if(sharedPreferences.contains("First Run")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Attendee.this, TwoPointOh.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000);
        }


    }

}
