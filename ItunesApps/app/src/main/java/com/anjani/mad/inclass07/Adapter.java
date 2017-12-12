package com.anjani.mad.inclass07;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.resource;

/**
 * Created by Anjani Reddy on 23-10-2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    ArrayList<AppData> mData;
    Context mContext;

    static public interface IData
    {
        public void setUpData(boolean isFav,AppData data) ;
    }
    Adapter.IData activity;

    public Adapter(ArrayList<AppData> data, Adapter.IData act)
    {
        this.mData = data;
        this.activity = act;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        AppData data;

        public TextView mTextViewname,mTextViewPrice,mTextViewEdition;
        public ImageView imageViewHeading,imageViewDel,imageViewPrice;
        private Context mCon;
        DatabaseDataManager dm;
        public ViewHolder(View v,Adapter adap) {
            super(v);
            mCon= adap.mContext;
            final Adapter adapter = adap;

            mTextViewname = v.findViewById(R.id.name);
            mTextViewPrice = v.findViewById(R.id.Price);
            imageViewHeading = v.findViewById(R.id.HeadingImage);
            imageViewPrice = v.findViewById(R.id.PriceImage);
            imageViewDel = v.findViewById(R.id.DeleteImage);

           ((ImageView)(v.findViewById(R.id.DeleteImage))).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {

                    DatabaseDataManager dm = new DatabaseDataManager(mCon);
                    dm.deleteNote(data);

                    adapter.activity.setUpData(true,data);

                    adapter.mData.remove(getAdapterPosition());

                    adapter.deleteItem(getAdapterPosition());

                }
            });

        }

        void setData(AppData data)
        {
            this.data = data;
        }

    }

    private void deleteItem(int position) {

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mData.size());
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        this.mContext = parent.getContext();
        // create a new view
        View view = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filteredlayout, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(view,this);
        return vh;
    }



    public void onBindViewHolder(ViewHolder holder, int position) {

        AppData email = mData.get(position);
        holder.setData(email);

        holder.mTextViewname.setText(email.getAppName());

        Double price = Double.parseDouble(email.getAppPrice());
        String.format("%.2f",price);
        holder.mTextViewPrice.setText("Price: USD " + price);
        if(Double.parseDouble(email.getAppPrice())>=0 && Double.parseDouble(email.getAppPrice())<= 1.99){
            holder.imageViewPrice.setImageResource(R.drawable.price_low);
        }
        else if(Double.parseDouble(email.getAppPrice())>=2 && Double.parseDouble(email.getAppPrice())<= 5.99){
            holder.imageViewPrice.setImageResource(R.drawable.price_medium);
        }
        else if(Double.parseDouble(email.getAppPrice())>=6 ){
            holder.imageViewPrice.setImageResource(R.drawable.price_high);
        }

        Picasso.with(this.mContext).load(email.getImage()).into(holder.imageViewHeading);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mData.size();
    }
}

