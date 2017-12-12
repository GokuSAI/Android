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

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder>  {
    ArrayList<AppData> mData;
    Context mContext;
    ViewHolder vh;


    static public interface IData
    {
        public void setUpData(boolean isFav,AppData data) ;
    }
    IData activity;


    public AppAdapter(ArrayList<AppData> data,IData activity)
    {
        this.mData = data;
        this.activity = activity;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        AppData data;
        final IData activity;

        public TextView mTextViewname,mTextViewPrice;
        public ImageView imageView,imageview2;
        private Context mCon;
        DatabaseDataManager dm;
        public ViewHolder(View v,AppAdapter adap,IData MainActivity) {
            super(v);
            activity= MainActivity;
            mCon= adap.mContext;
            final AppAdapter adapter = adap;

            mTextViewname = v.findViewById(R.id.Appname);
            mTextViewPrice = v.findViewById(R.id.Price);
            imageView = v.findViewById(R.id.imageView);
            imageview2 = v.findViewById(R.id.imageview2);

            v.setOnLongClickListener(new View.OnLongClickListener(){

                @Override
                public boolean onLongClick(View view) {

                    DatabaseDataManager dm = new DatabaseDataManager(mCon);

                    dm.saveNote(data);

                    activity.setUpData(false,null);

                    adapter.mData.remove(getAdapterPosition());

                    if(adapter.mData.size() == 0)
                            Toast.makeText(mCon, "All the apps are filtered", Toast.LENGTH_LONG).show();

                    adapter.deleteItem(getAdapterPosition());

                    return true;

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
    public AppAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        this.mContext = parent.getContext();
        final Context con = this.mContext;
        // create a new view
        View view = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listviewlayout, parent, false);

        vh = new ViewHolder(view,this,activity);
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
            holder.imageview2.setImageResource(R.drawable.price_low);
        }
        else if(Double.parseDouble(email.getAppPrice())>=2 && Double.parseDouble(email.getAppPrice())<= 5.99){
            holder.imageview2.setImageResource(R.drawable.price_medium);
        }

        else if(Double.parseDouble(email.getAppPrice())>=6 ){
            holder.imageview2.setImageResource(R.drawable.price_high);
        }

                Picasso.with(this.mContext).load(email.getImage()).into(holder.imageView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mData.size();
    }
}

