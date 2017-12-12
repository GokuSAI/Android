package com.example.saikrishna.homework6;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by saikrishna on 11/5/17.
 */

public class InstructorAdapter extends  RecyclerView.Adapter<InstructorAdapter.ViewHolder>  {
    private Activity mcontext;
    Realm realm;
    private ClickeedItem InstrItem;

    ArrayList<Instructordetails> mdata = new ArrayList<Instructordetails>();

    public static interface ClickeedItem{

        public void Instrclickeditem(int pos);

    }

    public InstructorAdapter(Realm realm, Activity mcontext, ArrayList<Instructordetails> mdata,ClickeedItem item) {
        this.realm = realm;
        this.mcontext = mcontext;
        this.mdata = mdata;
        InstrItem=item;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coursemanager,parent,false );
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    public InstructorAdapter(ArrayList<Instructordetails> mdata) {
        this.mdata = mdata;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("mdata", mdata.toString());
        Instructordetails id = mdata.get(position);
        holder.title.setText(id.getInstructorFirstName() +" "+ id.getInstructorLastName());
        holder.mail.setText(id.getInstructorEmail());
        //Bitmap bm = BitmapFactory.decodeByteArray(R.drawable.addphoto, 0, id.getImg().length);
        if (id.getImg() == null) {
            holder.dp.setImageResource(R.drawable.defaultimage);
        } else {
            Bitmap bm = BitmapFactory.decodeByteArray(id.getImg(), 0, id.getImg().length);
            holder.dp.setImageBitmap(bm);
        }
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }






    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,mail,time;
        ImageView dp;
        public ViewHolder(View itemView) {
            super(itemView);
            mail = itemView.findViewById(R.id.InstructorTextView);
            time =itemView.findViewById(R.id.TimeTextView);
            title = itemView.findViewById(R.id.TitleTextView);
            dp=itemView.findViewById(R.id.CourseManagerdp);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    Log.d("registered click","long");
                    AlertDialog.Builder alertdialog = new AlertDialog.Builder(mcontext);
                    alertdialog.setTitle("Delete Instructor")
                            .setMessage("Are you sure you want to delete the Instructor ?")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteFrpomDB(getAdapterPosition());
                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    AlertDialog alert = alertdialog.create();
                    alert.show();


                    return false;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InstrItem.Instrclickeditem(getAdapterPosition());

                   // Instructordetails idetails =mdata.get(getAdapterPosition());
                    //Log.d("registered click","");
                    Toast.makeText(mcontext, "clickedeg", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }


    void deleteFrpomDB(final int pos){


        try{
            realm.init(mcontext);
            realm.getDefaultInstance();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                RealmResults<Instructordetails> result = bgRealm.where(Instructordetails.class).findAll();
                int j=0;
                for(Instructordetails is: result){

                    if(is.equals(mdata.get(pos)))
                        break;

                    j++;
                }

                Instructordetails i = result.get(j);
                i.deleteFromRealm();

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

                // Transaction was a success.
                mdata.remove(pos);
                notifyDataSetChanged();

                Log.d("RESULTS","success");



            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.d("RESULTS","failure"+error.getMessage());

            }
        });



    }




}