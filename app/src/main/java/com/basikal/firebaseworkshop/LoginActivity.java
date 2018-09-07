package com.basikal.firebaseworkshop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mEmailView, mPasswordView;
    private Button mLoginButton;
    private TextView mRegisterView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //create instances
        mAuth = FirebaseAuth.getInstance();

        //link with xml
        mEmailView = findViewById(R.id.etEmail);
        mPasswordView = findViewById(R.id.etPassword);
        mLoginButton = findViewById(R.id.btnLogin);
        mRegisterView = findViewById(R.id.tvRegister);

        //attach listener to the button
        mLoginButton.setOnClickListener(this);
        mRegisterView.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            //Already logged in
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    private void login(){
        String email = mEmailView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }else{
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == mLoginButton){
            login();
        }else if(v == mRegisterView){
            finish();
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }
    }
}
