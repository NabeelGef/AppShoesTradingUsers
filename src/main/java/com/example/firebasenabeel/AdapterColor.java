package com.example.firebasenabeel;

import android.graphics.drawable.GradientDrawable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class AdapterColor extends RecyclerView.Adapter<AdapterColor.ViewHolder> {
    ArrayList<String> arrayList = new ArrayList<>();
    SparseBooleanArray array = new SparseBooleanArray();
    int Position = -1 ;
    String color = "" ;
    BackGround_color backGround_color;
    View view;
    boolean[] select = new boolean [25];
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterColor.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.colors
        ,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(""+arrayList.get(position));
        view = holder.textView;
        if(!array.get(position)){
            backGround_color.button_onClick_Color(holder.textView,position,"one");
            }else{
            backGround_color.button_onClick_Color(holder.textView,position,"two");
            }
    }
    private boolean CheakArray(){
        for(int i=0;i<arrayList.size();i++){
            if(array.get(i))
                return false;
        }
        return true;
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.Datacolors);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!array.get(getAdapterPosition()))
                        if(CheakArray())
                        {
                            array.put(getAdapterPosition(),true);
                        }else{
                            Toast.makeText(itemView.getContext(),"You are selected Color yet",Toast.LENGTH_LONG).show();
                        }

                    else{
                        array.put(getAdapterPosition(),false);
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }
    public void setArrayList(ArrayList <String> arr , BackGround_color backGround_color1)
    {
        this.backGround_color = backGround_color1;
        this.arrayList = arr;
    }
    public String getColor(){
     return color;
    }
}
