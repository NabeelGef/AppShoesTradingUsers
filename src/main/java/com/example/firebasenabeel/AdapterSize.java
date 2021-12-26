package com.example.firebasenabeel;

import android.graphics.drawable.GradientDrawable;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterSize extends RecyclerView.Adapter<AdapterSize.ViewHolder>  {
    ArrayList<Integer> arrayList = new ArrayList<>() ;
    BackGround_color backGround_color;
    private final SparseBooleanArray array=new SparseBooleanArray();
    int Position = -1 , size = -1 ;
    View view;
    @NonNull
    @Override
    public AdapterSize.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterSize.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sizes,
                parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterSize.ViewHolder holder, int position) {
            holder.textView.setText(""+arrayList.get(position));
            view = holder.textView;
            if(!array.get(position)){
                backGround_color.button_onClick_Size(holder.textView,position,"one");
               }else{
                backGround_color.button_onClick_Size(holder.textView,position,"two");
               }
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public  class ViewHolder extends AdapterImage.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.DataSizes);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!array.get(getBindingAdapterPosition()))
                    {
                        if(CheakArray()) {
                            array.put(getBindingAdapterPosition(), true);
                        }
                        else{
                            Toast.makeText(itemView.getContext(),"You are selected Size yet",Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                              array.put(getBindingAdapterPosition(),false);
                    notifyDataSetChanged();
                }
            });
        }
    }
    private boolean CheakArray(){
        for(int i=0;i<arrayList.size();i++){
            if(array.get(i))
                return false;
        }
        return true;
    }
    public void setArrayList(ArrayList <Integer> arr , BackGround_color backGround_color1)
    {
        this.arrayList = arr;
        this.backGround_color = backGround_color1;
    }

    public int getSize(){
        return  size;
    }
}
