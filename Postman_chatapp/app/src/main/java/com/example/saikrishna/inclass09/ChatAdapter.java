package com.example.saikrishna.inclass09;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by saikrishna on 11/7/17.
 */

public class ChatAdapter extends ArrayAdapter<MessageClass> {
    Context context;
    int mresource;
    ArrayList<MessageClass> arrayList;
    SharedPreferences mypref ;
    deleteMy delThr;


    static interface deleteMy{
        public void deleteThread(int pos);
    }
    public ChatAdapter(@NonNull ChatroomActivity context, @LayoutRes int resource, @NonNull List<MessageClass> objects) {
        super(context, resource, objects);

        this.context = context.getApplicationContext();
        this.delThr =   context;

        this.mresource = resource;
        this.arrayList = (ArrayList<MessageClass>) objects;

    }
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.chatroomlayout, null);
            mypref = context.getSharedPreferences(MainActivity.MY_PREFS_NAME, MODE_PRIVATE);

            String id = mypref.getString("user_id","");

            //Log.d("ID",id);
            if (arrayList != null){


                final TextView sender = convertView.findViewById(R.id.Sender);
                final TextView contentofmsg = convertView.findViewById(R.id.MessageContent);

                final TextView time = convertView.findViewById(R.id.TimeTextview);
                final ImageView del = convertView.findViewById(R.id.imageView2);



                sender.setText(arrayList.get(position).getFirstname() + " "+arrayList.get(position).getFirstname());
                contentofmsg.setText(arrayList.get(position).getMessagecontent());


                String dateString =arrayList.get(position).getCreatedtime() ;


               // Date outputDate =

                try {

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                    Date inputDate =dateFormat.parse(dateString);
                            Date convertedDate;
                    String stringCD = dateFormat.format(inputDate);
                    convertedDate = dateFormat.parse(stringCD);
                    //String stringCD = dateFormat.format(convertedDate);

                    PrettyTime p = new PrettyTime();
                    String prettyTimeDAte = p.format(convertedDate);
                    time.setText(prettyTimeDAte);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //System.out.println(convertedDate);




                if(id.equals(arrayList.get(position).getUserid())){
                    del.setImageResource(R.drawable.delete);
                    del.setTag("Delete");
                }
                    // Log.d("ALIS",arrayList.get(position).getTitle());

                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageView iv = (ImageView) v;
                        if(iv.getTag()!=null&& iv.getTag().equals("Delete")){

                            int p = position;
                            MessageClass x = arrayList.get(p);
                            arrayList.remove(p);
                            notifyDataSetChanged();
                            if(x.getMsgID()!=null)
                                delThr.deleteThread((int)Integer.parseInt(x.getMsgID()));

                        }
                    }
                });








            }

            return convertView;
        }


}

