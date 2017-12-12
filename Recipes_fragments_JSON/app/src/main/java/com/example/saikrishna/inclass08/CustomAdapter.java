package com.example.saikrishna.inclass08;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by saikrishna on 10/30/17.
 */



public class CustomAdapter extends  RecyclerView.Adapter<CustomAdapter.ViewHolder>  {

    static ArrayList<flag> mData = new ArrayList<flag>();

    public CustomAdapter(ArrayList<flag> mData) {
        this.mData = mData;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customlayout,parent,false );
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(mData.get(position).tag.equals("add")){
            holder.img.setImageResource(R.drawable.add);
            holder.img.setTag("add");
        }
        else{
            holder.img.setImageResource(R.drawable.remove);
            holder.img.setTag("remove");

        }
        holder.name.setText(mData.get(position).edittextname);
        holder.name.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(holder.img.getTag().equals("add")) {
                    mData.get(position).edittextname = holder.name.getText().toString();}
                return false;
            }
        });
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Onclick","enter");
                try {
                    if ( v.getTag().equals("add")) {
                        if (mData.size() < 5) {
                            Log.d("button", "added");
                            mData.get(position).tag = "remove";
                            mData.get(position).edittextname= holder.name.getText().toString();
                            flag f1 = new flag();
                            f1.edittextname = "";
                            f1.tag = "add";
                            mData.add(f1);
                            notifyDataSetChanged();
                        }
                    }
                    else if( v.getTag().equals("remove")) {
                            Log.d("button", "removed");
                            mData.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, getItemCount());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

   



    public static class ViewHolder extends RecyclerView.ViewHolder{
        EditText name;
        ImageView img;
        EditText dishname;
        public ViewHolder(final View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.editText);
            img = itemView.findViewById(R.id.imageView);
            img.setTag("add");
            img.setImageResource(R.drawable.add);
            dishname = itemView.findViewById(R.id.DishEditText);

        }

    }

}
