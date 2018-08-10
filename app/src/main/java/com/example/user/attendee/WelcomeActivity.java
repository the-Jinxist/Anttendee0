package com.example.user.attendee;

import android.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

public class WelcomeActivity extends AppCompatActivity {
    private Button getstarted_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        getstarted_button = findViewById(R.id.getstarted_button);
        getstarted_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissions();
                SharedPreferences sharedPreferences = getSharedPreferences("Shared Pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("First Run", "This is the first run..");
                editor.apply();
            }
        });


    }
    private void checkPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(WelcomeActivity.this, android.Manifest.permission.CAMERA) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 123);

            }else{
                startActivity(new Intent(WelcomeActivity.this, TwoPointOh.class));
            }
        }else {
            startActivity(new Intent(WelcomeActivity.this, TwoPointOh.class));
        }

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 123 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
            startActivity(new Intent(WelcomeActivity.this , TwoPointOh.class));
        }
        else{
            Toast.makeText(this, "Permission Denied!", Toast.LENGTH_LONG).show();
            checkPermissions();
        }
    }

    @Override
    public void onBackPressed() {

    }
}
