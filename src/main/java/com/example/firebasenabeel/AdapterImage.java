package com.example.firebasenabeel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class AdapterImage extends RecyclerView.Adapter<AdapterImage.ViewHolder> {
   ArrayList <ShoesImages> shoes = new ArrayList<>();
   boolean login = false;
   String IDUser = "";

    public String getIDUser() {
        return IDUser;
    }

    public void setIDUser(String IDUser) {

        this.IDUser = IDUser;
    }

    @NonNull
    @Override
    public AdapterImage.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.add_image,
                parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterImage.ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(shoes.get(position).getUrl()).into(
                    holder.imageView1);
            holder.imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(),ShoesInfo.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ID" , shoes.get(position).getIndex());
                    bundle.putBoolean("Login",isLogin());
                    bundle.putString("UserId",getIDUser());
                    intent.putExtras(bundle);
                    holder.itemView.getContext().startActivity(intent);
                    }
            });
            holder.textView1.append(""+shoes.get(position).getPrice() + " SYR");
            holder.textView2.append(""+shoes.get(position).getType());
    }
    @Override
    public int getItemCount() {
        return shoes.size();
    }
    public void setList(ArrayList<ShoesImages> images)
    {
    this.shoes = images;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
       ImageView imageView1 ;
       TextView textView1 , textView2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView1 = itemView.findViewById(R.id.image1);
            textView1 = itemView.findViewById(R.id.price);
            textView2=itemView.findViewById(R.id.type);
            //progressBar=itemView.findViewById(R.id.progHome);
        }
    }

    public boolean isLogin() {
        return login;
    }
    public void setLogin(boolean login) {
        this.login = login;
    }
}
