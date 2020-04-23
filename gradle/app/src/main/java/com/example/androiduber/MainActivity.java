package com.example.androiduber;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {


    Button btnSignin;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnSignin = (Button) findViewById(R.id.btn_signin);
        btnSignup = (Button) findViewById(R.id.btn_register);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterUser.class);
                startActivity(intent);
            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginUser.class);
                startActivity(intent);
            }
        });
    }
}
