package com.example.saikrishna.homework6;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by saikrishna on 11/4/17.
 */

public class CreateAdapter extends  RecyclerView.Adapter<CreateAdapter.ViewHolder>  {
    ArrayList<Instructordetails> Instructorarraylist = new ArrayList<Instructordetails>();
    Realm realm;
    private Activity mcontext;
    private static RadioButton lastChecked = null;
    private static int lastCheckedPos = 0;
    private ClickeedItem InstrItem;
    static int position;
    public static interface ClickeedItem{

        public void itemClickedMethod(int pos);

    }

    public CreateAdapter(ArrayList<Instructordetails> instructorarraylist, Realm realm, Activity mcontext,ClickeedItem item) {
        Instructorarraylist = instructorarraylist;
        this.realm = realm;
        this.mcontext = mcontext;
        this.InstrItem = item;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.instructormanager,parent,false );
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public CreateAdapter(ArrayList<Instructordetails> mdata) {
        this.Instructorarraylist = mdata;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("mdata",Instructorarraylist.toString());
        //Instructordetails id = Instructorarraylist.get(position);
        Instructordetails info = Instructorarraylist.get(position);
        holder.Instructorname.setText(info.getInstructorFirstName()+" "+info.getInstructorLastName());
        holder.checkBox.setChecked(info.isSelected());
        holder.checkBox.setTag(new Integer(position));

        holder.checkBox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RadioButton cb = (RadioButton) v;
                int clickedPos = ((Integer)cb.getTag()).intValue();

                if(cb.isChecked())
                {
                    if(lastChecked != null)
                    {
                        lastChecked.setChecked(false);
                        Instructorarraylist.get(lastCheckedPos).setSelected(false);
                    }

                    lastChecked = cb;
                    lastCheckedPos = clickedPos;
                }
                else
                    lastChecked = null;

                Instructorarraylist.get(clickedPos).setSelected(cb.isChecked());
                InstrItem.itemClickedMethod(clickedPos);
            }
        });
        if(info.getImg()==null){
            holder.dp.setImageResource(R.drawable.defaultimage);
        }
        else{
            Bitmap bm = BitmapFactory.decodeByteArray(info.getImg(), 0, info.getImg().length);
            holder.dp.setImageBitmap(bm);
        }
    }

    @Override
    public int getItemCount() {
        return Instructorarraylist.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Instructorname;
        ImageView dp;
        RadioButton checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            Instructorname = itemView.findViewById(R.id.nametextview);
            dp = itemView.findViewById(R.id.instructorimageview);
            checkBox = itemView.findViewById(R.id.radioButton4);
        }


    }
}
