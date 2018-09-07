package com.basikal.firebaseworkshop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateChildrenActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mNameView, mIcNoView, mDobView;
    private Button mUpdateButton, mDeleteButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_children);

        //show back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get data from intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String icNo = intent.getStringExtra("icNo");
        String dob = intent.getStringExtra("dob");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mNameView = findViewById(R.id.etName);
        mIcNoView = findViewById(R.id.etIcNo);
        mDobView = findViewById(R.id.etDob);
        mUpdateButton = findViewById(R.id.btnUpdate);
        mDeleteButton = findViewById(R.id.btnDelete);

        mNameView.setText(name);
        mIcNoView.setText(icNo);
        mDobView.setText(dob);

        mUpdateButton.setOnClickListener(this);
        mDeleteButton.setOnClickListener(this);
    }

    //show back button
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void updateChildren() {
        Intent intent = getIntent();

        String userUid = mAuth.getCurrentUser().getUid();
        String uid = intent.getStringExtra("uid");
        String name = mNameView.getText().toString().trim();
        String icNo = mIcNoView.getText().toString().trim();
        String dob = mDobView.getText().toString().trim();

        Children children = new Children(uid, name, icNo, dob,userUid);
        mDatabase.child("Children").child(userUid).child(uid).setValue(children)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(UpdateChildrenActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(UpdateChildrenActivity.this,MainActivity.class));
                        }
                    }
                }
        );
    }

    private void deleteChildren(){
        Intent intent = getIntent();
        String userUid = mAuth.getCurrentUser().getUid();
        String uid = intent.getStringExtra("uid");
        mDatabase.child("Children").child(userUid).child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UpdateChildrenActivity.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(UpdateChildrenActivity.this, MainActivity.class));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == mUpdateButton) {
            updateChildren();
        }else if(v == mDeleteButton){
            deleteChildren();
        }
    }
}
