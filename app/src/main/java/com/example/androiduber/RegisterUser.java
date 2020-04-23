package com.example.androiduber;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androiduber.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference users;

    private EditText edtEmail;
    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtPhone;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_register);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        mapping();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //register new user
                auth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                //save user to database
                                User user = new User();
                                user.setEmail(edtEmail.getText().toString());
                                user.setUsername(edtUsername.getText().toString());
                                user.setPassword(edtPassword.getText().toString());
                                user.setPhone(edtPhone.getText().toString());

                                //use email to key
                                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(RegisterUser.this, "Register Success", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(RegisterUser.this, "Register fail", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterUser.this, "Register fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void mapping() {
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtUsername = (EditText) findViewById(R.id.edt_username);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        edtPhone = (EditText) findViewById(R.id.edt_phone);

        btnRegister = (Button) findViewById(R.id.btn_register);
    }

}
