package com.example.androiduber;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androiduber.Common.Common;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginUser  extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference users;

    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_login);

        mapping();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference(Common.user_driver_tbl);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //login user
                auth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Intent intent = new Intent(new Intent(LoginUser.this, MapsActivity.class));
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginUser.this, "Login fail", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void mapping(){
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
    }
}
