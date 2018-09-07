package com.basikal.firebaseworkshop;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class AddChildrenDialog extends DialogFragment implements View.OnClickListener {

    private EditText mNameView, mMykidView, mDobView;
    private Button mAddButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add_children, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //create instances
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //link with xml
        mNameView = view.findViewById(R.id.etName);
        mMykidView = view.findViewById(R.id.etMykid);
        mDobView = view.findViewById(R.id.etDob);
        mAddButton = view.findViewById(R.id.btnAdd);

        //attach listener
        mAddButton.setOnClickListener(this);

    }

    private void addChild(){
        String userUid = mAuth.getCurrentUser().getUid();
        String uid = mDatabase.child("posts").push().getKey();
        String name = mNameView.getText().toString().trim();
        String mykid = mMykidView.getText().toString().trim();
        String dob = mDobView.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(getActivity(), "Enter Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(mykid)){
            Toast.makeText(getActivity(), "Enter mykid", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(dob)){
            Toast.makeText(getActivity(), "Enter dob", Toast.LENGTH_SHORT).show();
            return;
        }

        Children children = new Children(uid,name,mykid,dob,userUid);
        mDatabase.child("Children").child(userUid).child(uid).setValue(children).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getActivity(), "Adding Success", Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();
                }else{
                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == mAddButton) {
            addChild();
        }
    }
}
