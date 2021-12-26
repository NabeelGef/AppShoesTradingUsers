package com.example.firebasenabeel;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Adapter_Notification extends RecyclerView.Adapter<Adapter_Notification.ViewHolder> {
    ArrayList<Upload_price_type> upload_price_types;
    @NonNull
    @Override
    public Adapter_Notification.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.add_notification,parent,false));
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Adapter_Notification.ViewHolder holder, int position) {
     holder.textView.setText("add Shoes " + upload_price_types.get(position).getType()+
     "\nthe price is : " + upload_price_types.get(position).getPrice() +" SYR");
     }
    @Override
    public int getItemCount() {
        return upload_price_types.size();
     }
    public void setList(ArrayList<Upload_price_type> arrayList) {
        this.upload_price_types = arrayList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.add_notification);
        }
    }
}
