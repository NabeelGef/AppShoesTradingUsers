package com.example.firebasenabeel;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.security.spec.ECField;
import java.util.ArrayList;

public class AdapterOrders extends RecyclerView.Adapter<AdapterOrders.ViewHolder> {
    ArrayList<DataBuying>arrayList = new ArrayList<>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterOrders.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_recycle,
                parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!arrayList.isEmpty()) {
                Glide.with(holder.itemView.getContext()).load(arrayList.get(position).getUrl())
                        .into(holder.imageView);
                holder.price.setText("Price : " + arrayList.get(position).getPrice());
                holder.name.setText("Name : " + arrayList.get(position).getName());
                holder.size.setText("Size : " + arrayList.get(position).getSize());
                holder.color.setText("Color : " + arrayList.get(position).getColor());
        }
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView price;
        TextView size;
        TextView color;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.BuyImage);
            price = itemView.findViewById(R.id.BuyPrice);
            size = itemView.findViewById(R.id.BuySize);
            color = itemView.findViewById(R.id.BuyColor);
            name = itemView.findViewById(R.id.BuyName);
        }
    }
    public void setList(ArrayList<DataBuying>dataBuyings)
    {
        this.arrayList = dataBuyings;
    }
}
