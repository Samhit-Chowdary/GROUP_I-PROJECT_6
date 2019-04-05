package com.example.samhi.firebasedemo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UsersViewHolder> {

    private List<UserModel> mUserList;
    private Context mContext;

    public UserAdapter(List<UserModel> mUserList, Context mContext) {
        this.mUserList = mUserList;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customlist_layout, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UsersViewHolder holder, final int position) {

       holder.mTvTitle.setImageResource(mUserList.get(position).getImage());

       holder.mTvTitle.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent( mContext , FilterActivity.class );
             intent.putExtra("Image" ,  mUserList.get(holder.getAdapterPosition()).getDesc());

                mContext.startActivity(intent);
           }
       });

    }

    @Override
    public int getItemCount() {
        return (mUserList.size() > 0) ? mUserList.size() :0;
    }

    static class UsersViewHolder extends RecyclerView.ViewHolder {



        ImageView mTvTitle;

        UsersViewHolder(View itemView) {
            super(itemView);


            mTvTitle = itemView.findViewById(R.id.image);

        }
    }
}