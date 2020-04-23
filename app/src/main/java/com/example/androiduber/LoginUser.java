package com.example.androiduber;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginUser extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference users;

    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin, btnSignUp;
    private CheckBox cbRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_login);

        mapping();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        //init data if user save email and password before
        String email = MyPreference.getString(this, MyPreference.EMAIL);
        if (!TextUtils.isEmpty(email)) {
            String passWord = MyPreference.getString(this, email);
            edtEmail.setText(email);
            edtPassword.setText(passWord);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = edtEmail.getText().toString();
                final String passWord = edtPassword.getText().toString();
                //login user
                auth.signInWithEmailAndPassword(email, passWord)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                //save username and password to shared preference
                                if (cbRemember.isChecked()) {
                                    MyPreference.putString(getApplicationContext(), MyPreference.EMAIL, email);
                                    MyPreference.putString(getApplicationContext(), email, passWord);
                                }
                                Intent intent = new Intent(new Intent(LoginUser.this, MapsActivity.class));
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Login fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginUser.this, RegisterUser.class));
            }
        });
    }

    private void mapping() {
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_go_sign_up);
        cbRemember = findViewById(R.id.checkbox_remember);
    }
}
