package com.example.hp.quesec;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hp.quesec.Activities.LogIn;
import com.example.hp.quesec.Activities.LoginSignup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run()
            {
                startActivity(new Intent(MainActivity.this, LogIn.class));
            }
        },3000);
    }
}
