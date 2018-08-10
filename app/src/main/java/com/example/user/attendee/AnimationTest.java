package com.example.user.attendee;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class AnimationTest extends AppCompatActivity {
    private Boolean is_pressed;
    private FloatingActionButton sign_in_button, exit_button, web_serices, main_fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_test);

        main_fab = findViewById(R.id.main_fab);
        sign_in_button =findViewById(R.id.sign_in_fab);
        exit_button = findViewById(R.id.exit_button);
        web_serices = findViewById(R.id.web_button);

        sign_in_button.setClickable(false);
        exit_button.setClickable(false);
        web_serices.setClickable(false);

        final Animation show_anim = AnimationUtils.loadAnimation(AnimationTest.this, R.anim.fab_show);
        final Animation hide_anim = AnimationUtils.loadAnimation(AnimationTest.this, R.anim.fab_hide);
        is_pressed= false;

        main_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!is_pressed){
                    sign_in_button.startAnimation(show_anim);
                    exit_button.startAnimation(show_anim);
                    web_serices.startAnimation(show_anim);

                    sign_in_button.setClickable(true);
                    exit_button.setClickable(true);
                    web_serices.setClickable(true);

                    is_pressed = true;
                }else{
                    sign_in_button.startAnimation(hide_anim);
                    exit_button.startAnimation(hide_anim);
                    web_serices.startAnimation(hide_anim);

                    sign_in_button.setClickable(false);
                    exit_button.setClickable(false);
                    web_serices.setClickable(false);

                    is_pressed = false;
                }
            }
        });

    }
}
