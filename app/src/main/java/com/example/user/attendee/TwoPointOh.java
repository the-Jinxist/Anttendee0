package com.example.user.attendee;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class TwoPointOh extends AppCompatActivity {
    private com.github.clans.fab.FloatingActionButton signin_fab, signout_fab, services_fab;
    private DatabaseReference dbaseRef;
    private TextView instructions;
    private ZXingScannerView zxingScannerView;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_point_oh);

        date =  new SimpleDateFormat("yyyy,MM,dd", Locale.getDefault()).format(new Date());

        dbaseRef = FirebaseDatabase.getInstance().getReference().child(date);
        zxingScannerView = new ZXingScannerView(TwoPointOh.this);
        instructions = findViewById(R.id.signing_instructions);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/dosismedium.ttf");
        instructions.setTypeface(custom_font);

        signin_fab = findViewById(R.id.signin_fab_menu_item);
        signout_fab = findViewById(R.id.signout_fab_menu_tem);
        services_fab = findViewById(R.id.services_fab_menu_tem);


       signin_fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            sign_in_scan(v);
           }
       });

       signout_fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               sign_out_scan(v);
           }
       });

       services_fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent start_futa_acm_intent = new Intent(Intent.ACTION_VIEW);
               String futa_acm_url = "https://futa.acm.org";
               start_futa_acm_intent.setData(Uri.parse(futa_acm_url));
               startActivity(start_futa_acm_intent);
           }
       });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_two, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.back_two_point:
                startActivity(new Intent(TwoPointOh.this, TwoPointOh.class));
                return true;

            case R.id.exit_two_point:
                finish();
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }

    }
   ZXingScannerView.ResultHandler  signin_handler = new ZXingScannerView.ResultHandler() {
       @Override
       public void handleResult( final Result result) {
                   long date1 = System.currentTimeMillis();
                   SimpleDateFormat asdf = new SimpleDateFormat("MMM dd yyyy \n hh mm ss a");
                   final String dateStyle = asdf.format(date1);
                   DatabaseReference newPost = dbaseRef.child("Sign Ins");
                   final DatabaseReference signInRef = newPost.push();
                   signInRef.child("Time Stamp").setValue(dateStyle).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){
                               signInRef.child("User").setValue(result.getText()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                       if (task.isSuccessful()){
                                           Toast.makeText(TwoPointOh.this, result.getText() + " signed in at: \n " + dateStyle, Toast.LENGTH_LONG)
                                                   .show();
                                           startActivity(new Intent(TwoPointOh.this, TwoPointOh.class));
                                       }

                                   }
                               });
                           }
                       }
                   });
       }
   };

    ZXingScannerView.ResultHandler  signout_handler = new ZXingScannerView.ResultHandler() {
        @Override
        public void handleResult( final Result result) {
            long date1 = System.currentTimeMillis();
            SimpleDateFormat asdf = new SimpleDateFormat("MMM dd yyyy \n hh mm ss a");
            final String dateStyle = asdf.format(date1);
            DatabaseReference newPost = dbaseRef.child("Sign Outs");
            final DatabaseReference signOut_ref = newPost.push();
            signOut_ref.child("Time Stamp").setValue(dateStyle).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        signOut_ref.child("User").setValue(result.getText()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(TwoPointOh.this, result.getText() + " signed out at:  \n" + dateStyle, Toast.LENGTH_LONG)
                                            .show();
                                    Intent intent = new Intent(TwoPointOh.this, TwoPointOh.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }
            });
        }
    };

    public void sign_in_scan(View view){
        Context context = view.getContext();
        zxingScannerView=new ZXingScannerView(context);
        setContentView(zxingScannerView);
        zxingScannerView.setResultHandler(signin_handler);
        zxingScannerView.startCamera();
    }

    public void sign_out_scan (View view){
        Context context = view.getContext();
        zxingScannerView=new ZXingScannerView(context);
        setContentView(zxingScannerView);
        zxingScannerView.setResultHandler(signout_handler);
        zxingScannerView.startCamera();
    }
}
