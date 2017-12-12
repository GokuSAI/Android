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

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by saikrishna on 11/3/17.
 */

public class CourseAdapter extends  RecyclerView.Adapter<CourseAdapter.ViewHolder>  {
    private Activity mcontext;
    private ClickeedItem mitem;
    Realm realm;
    ArrayList<CourseDetails> mdata = new ArrayList<CourseDetails>();

    public static interface ClickeedItem{

        public void itemClickedMethod(int pos);

    }

    public CourseAdapter(Realm realm, Activity mcontext, ArrayList<CourseDetails> mdata, ClickeedItem item) {
        this.realm = realm;
        this.mcontext = mcontext;
        this.mdata = mdata;
        mitem = item;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coursemanager,parent,false );
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    public CourseAdapter(ArrayList<CourseDetails> mdata) {
        this.mdata = mdata;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("mdata",mdata.toString());
        CourseDetails id = mdata.get(position);
        holder.title.setText(id.getTitleofcourse());
        holder.Instructor.setText(id.getNameofinstructor());
        holder.time.setText(id.getDay()+" "+id.getTime());
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
        TextView title,Instructor,time;
        ImageView dp;
        public ViewHolder(View itemView) {
            super(itemView);
            Instructor = itemView.findViewById(R.id.InstructorTextView);
            time =itemView.findViewById(R.id.TimeTextView);
            title = itemView.findViewById(R.id.TitleTextView);
            dp=itemView.findViewById(R.id.CourseManagerdp);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    Log.d("registered click","long");
                    AlertDialog.Builder alertdialog = new AlertDialog.Builder(mcontext);
                    alertdialog.setTitle("Delete Course")
                            .setMessage("Are you sure you want to delete the course ?")
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
                     mitem.itemClickedMethod(getAdapterPosition());

                    //Log.d("registered click","");
                    //mcontext.getFragmentManager().beginTransaction().replace(R.id.container,new AddNewCourse(), "Add New Courses").addToBackStack("null").commit();
                    //RealmResults<CourseDetails> result = realm.where(CourseDetails.class).findAll();
                    //CourseDetails temp =result.get(getAdapterPosition());

                    //String title=temp.getTitleofcourse();
                    //Log.d("TITLE",temp.toString());
//                    Fragment frag = mcontext.getFragmentManager().findFragmentById(R.id.newcourse);
//                    ((EditText) frag.getActivity().findViewById(R.id.CourseTitleEdittext)).setText(title);

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
                RealmResults<CourseDetails> result = bgRealm.where(CourseDetails.class).findAll();
                int j=0;
                for(CourseDetails is: result){

                    if(is.equals(mdata.get(pos)))
                        break;

                    j++;
                }

                CourseDetails i = result.get(j);
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
