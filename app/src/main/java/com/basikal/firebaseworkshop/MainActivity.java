package com.basikal.firebaseworkshop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mAddButton, mLogoutButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Children> mChildrenList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create instances //initialize
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.keepSynced(true);

        //link with xml
        mAddButton = findViewById(R.id.btnAddChildren);
        mLogoutButton = findViewById(R.id.btnLogout);
        mRecyclerView = findViewById(R.id.rvChildren);

        //attach listener
        mAddButton.setOnClickListener(this);
        mLogoutButton.setOnClickListener(this);

        mDatabase.child("Children").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //iterating through all the values in database
                mChildrenList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Children children = postSnapshot.getValue(Children.class);
                    mChildrenList.add(children);
                }
                //creating adapter
                mAdapter = new ChildrenAdapter(getApplicationContext(), mChildrenList);

                //adding adapter to recyclerview
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //other method
        mRecyclerView.setHasFixedSize(true); //set fixed size for element in recycler view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }



    @Override
    public void onClick(View v) {
        if(v == mAddButton){
            AddChildrenDialog dialog = new AddChildrenDialog();
            dialog.show(getFragmentManager(),"UpdateUserDialog");
        }else {
            mAuth.signOut();
            finish();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
}
