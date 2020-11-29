package com.example.crytochat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.crytochat.MessageActivity;
import com.example.crytochat.Model.Users;
import com.example.crytochat.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>
{
    public Context context;
    public List<Users> mUsers;


    public UserAdapter(Context context, List<Users> mUsers) {
        this.context = context;
        this.mUsers = mUsers;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_item,viewGroup,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
          final Users users=mUsers.get(position);
          holder.username.setText(users.getUsername());

          if(users.getImageURL().equals("default")){
              holder.imageView.setImageResource(R.mipmap.ic_launcher);
          }else{
              Glide.with(context)
                      .load(users.getImageURL())
                      .into(holder.imageView);
          }

          holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent i = new Intent(context, MessageActivity.class);
                  i.putExtra("userid", users.getId());
                  context.startActivity(i);
              }
          });

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.textView3);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }
}
