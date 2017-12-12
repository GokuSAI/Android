package com.example.saikrishna.inclass09;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by saikrishna on 11/6/17.
 */

public class CustomAdapter extends ArrayAdapter<Threadclass> {
    Context context;
    int mresource;
    ArrayList<Threadclass> arrayList;
    String fav;
    SharedPreferences mypref ;
    SharedPreferences.Editor editor;
    deleteMy delThr;
//    msg text;

    static interface deleteMy{
        public void deleteThread(int pos);
    }
//    static  interface msg{
//        public void mesagedisplay(int pos);
//    }

    public CustomAdapter(@NonNull ThreadActivity context, @LayoutRes int resource, @NonNull List<Threadclass> objects) {
        super(context, resource, objects);

        this.context = context.getApplicationContext();
        this.delThr = context;
        this.mresource = resource;
        this.arrayList = (ArrayList<Threadclass>) objects;
        mypref = context.getSharedPreferences(MainActivity.MY_PREFS_NAME, MODE_PRIVATE);


    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.customlayout, null);
        String id = mypref.getString("user_id","");

        Log.d("ID",id);
        if (arrayList != null){
            ImageView del = convertView.findViewById(R.id.DeleteImageview);
            final TextView title = convertView.findViewById(R.id.TitleTextview);
            {

                title.setText(arrayList.get(position).getTitle());
               // Log.d("ALIS",arrayList.get(position).getTitle());
                if(id.equals(arrayList.get(position).getUserid())){
                    del.setImageResource(R.drawable.remove);
                    del.setTag("Delete");
                }
            }


//            title.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int p = position;
//                    Threadclass x = arrayList.get(p);
//                    arrayList.remove(p);
//                    notifyDataSetChanged();
//                    if(x.getID()!=null)
//                        text.mesagedisplay(Integer.parseInt(x.getID()));
//
//                //Intent intent = new Intent(CustomAdapter.this,ChatroomActivity.class);
//                }
//            });


            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView iv = (ImageView) v;

                    if(iv.getTag()!=null&& iv.getTag().equals("Delete")){

                        int p = position;
                        Threadclass x = arrayList.get(p);
                        arrayList.remove(p);
                        notifyDataSetChanged();
                        if(x.getID()!=null)
                        delThr.deleteThread((int)Integer.parseInt(x.getID()));

                    }
                }
            });


        }

        return convertView;
    }


}


