package com.basikal.firebaseworkshop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mEmailView, mPasswordView, mNameView, mPhoneNoView, mAddressView;
    private Button mRegisterButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //create instances
        mAuth = FirebaseAuth.getInstance();
        mUserRef = FirebaseDatabase.getInstance().getReference();

        //link with xml
        mEmailView = findViewById(R.id.etEmail);
        mPasswordView = findViewById(R.id.etPassword);
        mNameView = findViewById(R.id.etName);
        mPhoneNoView = findViewById(R.id.etPhoneNo);
        mAddressView = findViewById(R.id.etAddress);
        mRegisterButton = findViewById(R.id.btnRegister);

        //attach listener to the button
        mRegisterButton.setOnClickListener(this);
    }

    private void createUser(){
        String uid = mAuth.getCurrentUser().getUid();
        String email = mEmailView.getText().toString().trim();
        String name = mNameView.getText().toString().trim();
        String phoneNo = mPhoneNoView.getText().toString().trim();
        String address = mAddressView.getText().toString().trim();

        User user = new User(uid,email,name,phoneNo,address);
        mUserRef.child("User").child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void register(){
        String email = mEmailView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();
        String name = mNameView.getText().toString().trim();
        String phoneNo = mPhoneNoView.getText().toString().trim();
        String address = mAddressView.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(phoneNo)){
            Toast.makeText(this, "Please enter your phoneNo", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(address)){
            Toast.makeText(this, "Please enter your address", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    createUser();
                    finish();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                }else{
                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == mRegisterButton){
            register();
        }
    }
}
