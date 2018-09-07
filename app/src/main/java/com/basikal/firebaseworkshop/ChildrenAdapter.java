package com.basikal.firebaseworkshop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ChildrenAdapter extends RecyclerView.Adapter<ChildrenAdapter.ChildrenViewHolder> {

    private Context mCtx;
    private List<Children> childrenList;

    public ChildrenAdapter(Context mCtx, List<Children> childrenList) {
        this.mCtx = mCtx;
        this.childrenList = childrenList;
    }

    @NonNull
    @Override
    public ChildrenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout, parent, false);
        return new ChildrenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChildrenViewHolder holder, final int position) {
        final Children children = childrenList.get(position);
        holder.nameView.setText(children.getName());
        holder.mykidView.setText(children.getMykid());
        holder.dobView.setText(children.getDob());

        holder.childrenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, UpdateChildrenActivity.class);
                intent.putExtra("uid",children.getUid());
                intent.putExtra("name",children.getName());
                intent.putExtra("icNo",children.getMykid());
                intent.putExtra("dob",children.getDob());
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return childrenList.size();
    }

    class ChildrenViewHolder extends RecyclerView.ViewHolder {
        TextView nameView, mykidView, dobView;
        CardView childrenLayout;

        public ChildrenViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.tvName);
            mykidView = itemView.findViewById(R.id.tvMykid);
            dobView = itemView.findViewById(R.id.tvDob);
            childrenLayout = itemView.findViewById(R.id.cvChildren);
        }
    }
}
