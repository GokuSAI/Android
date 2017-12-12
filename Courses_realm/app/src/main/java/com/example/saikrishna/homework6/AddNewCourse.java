package com.example.saikrishna.homework6;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddNewCourse#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewCourse extends Fragment implements CreateAdapter.ClickeedItem{
    Realm realm;
     EditText title,timehours,timemins;
    Spinner day,time,semester;
    RecyclerView.Adapter adapter;
    RecyclerView horizontalrecyclerView;
    Button reset,create;
    ImageView img;
    RecyclerView.LayoutManager layoutManager2;
    RadioGroup rg;
    String instr_name;
    TextView text;
    Instructordetails instructordetails;
    ArrayList<Instructordetails> arraylist = new ArrayList<Instructordetails>();
    View rootview;
    RadioButton rb;
    String creditHr;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    CourseDetails mParam1;

    private OnFragmentInteractionListener mListener;

    public AddNewCourse() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment AddNewCourse.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNewCourse newInstance(CourseDetails param1) {
        AddNewCourse fragment = new AddNewCourse();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1,  param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(ARG_PARAM1);
            Log.d("EDITFRAG",mParam1.toString());

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).setActionBarTitle("Create Course");
        rootview =  inflater.inflate(R.layout.fragment_add_new_course, container, false);
        return rootview;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (Exception e){
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        try {
            realm.init(getActivity());
            realm = Realm.getDefaultInstance();


        } catch(Exception e) {
            e.printStackTrace();
        }
        horizontalrecyclerView = getActivity().findViewById(R.id.horizontal_recycler_view);
        layoutManager2 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        horizontalrecyclerView.setLayoutManager(layoutManager2);
        title = getActivity().findViewById(R.id.CourseTitleEdittext);
        img = getActivity().findViewById(R.id.instructorimageview);
        day = getActivity().findViewById(R.id.DaySpinner);
        time = getActivity().findViewById(R.id.TimeSpinner);
        semester=getActivity().findViewById(R.id.semesterspinner);
        timehours=getActivity().findViewById(R.id.Time1);
        timemins = getActivity().findViewById(R.id.Time2);
        reset = getActivity().findViewById(R.id.ResetCourseButton);
        create =getActivity().findViewById(R.id.CreateCourseButton);
        text = getActivity().findViewById(R.id.text);
        rg = getActivity().findViewById(R.id.radioGroup);

        RealmResults<Instructordetails> realmObjects = realm.where(Instructordetails.class).findAll();
        arraylist.clear();

        if(mParam1!= null){
            title.setText(mParam1.getTitleofcourse());
            Log.d("toim",mParam1.getTime());
        }
        SharedPreferences shr = getActivity().getPreferences(Context.MODE_PRIVATE);
        String s = shr.getString("uname","coursemanager");
        Log.d("SHARED",s);

        for(Instructordetails myRealmObject : realmObjects)

        {
            Instructordetails id = new Instructordetails();
            //id.setTitleofcourse(myRealmObject.getTitleofcourse());
            id.setInstructorFirstName(myRealmObject.getInstructorFirstName());
            id.setInstructorLastName(myRealmObject.getInstructorLastName());
            id.setImg(myRealmObject.getImg());

//            Bitmap bm = ((BitmapDrawable)img.getDrawable()).getBitmap();
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            byte[] imgBytes = baos.toByteArray();
//            id.setImg(imgBytes);
            //id.setTime(myRealmObject.getTime());


            // id.setLoginusername(myRealmObject.getLoginusername());
            Log.d("sss",myRealmObject.toString());
            if(myRealmObject.getCourseManagerUserName().equals(s)) {
                id.setCourseManagerUserName(s);
                arraylist.add(id);
            }


        }
        adapter = new CreateAdapter(arraylist,realm,getActivity(),(CreateAdapter.ClickeedItem)this);
        horizontalrecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if(arraylist.size()==0){
            create.setEnabled(false);
        }
        else {
            text.setVisibility(View.GONE);
            create.setEnabled(true);
        }

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(getActivity());
                alertdialog.setTitle("Reset All Fields")
                        .setMessage("Are you sure you want to Reset?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                title.setText("");
                                timehours.setText("");
                                timemins.setText("");
                                day.setSelection(0);
                                time.setSelection(0);
                                semester.setSelection(0);
                                rg.clearCheck();
                                

                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alert = alertdialog.create();
                alert.show();
            }
        });
        timehours.setFilters(new InputFilter[]{new InputFilterMinMax(0,12)});
        timemins.setFilters(new InputFilter[]{new InputFilterMinMax(0,59)});
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Titleofcourse =title.getText().toString();
                String hours = timehours.getText().toString();
                String mins = timemins.getText().toString();
                String arr[] = getResources().getStringArray(R.array.dayarray);
                String dayvariable = arr[(int)day.getSelectedItemId()];
                String arr1[] = getResources().getStringArray(R.array.timearray);
                String timevariable = arr1[(int)time.getSelectedItemId()];
                String arr2[] = getResources().getStringArray(R.array.semesterarray);
                String semvariable = arr2[(int)semester.getSelectedItemId()];
                SharedPreferences sh = getActivity().getPreferences(Context.MODE_PRIVATE);
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        if(i != -1) {
                            rb = getActivity().findViewById(i);
                            creditHr = rb.getText().toString();
                            Log.d("CREDIT hours",creditHr);
                        }
                    }
                });
                /*int id_Rg = rg.getCheckedRadioButtonId();
                String creditHr = "";
                if (id_Rg != -1) {

                    RadioButton selectedRadioButton = (RadioButton) rootview.findViewById(id_Rg);
                    creditHr = selectedRadioButton.getText().toString();
                }*/
                if(Titleofcourse.equals("")||hours.equals("")||mins.equals("")||dayvariable.equals("Select")||timevariable.equals("Select")||semvariable.equals("Select Semester")||instructordetails== null||creditHr == null){
                    Toast.makeText(getActivity(), "Missing details", Toast.LENGTH_SHORT).show();
                }




                    else {
                        String s = sh.getString("uname", "default");

                        RegisternewCourse(Titleofcourse, hours, mins, dayvariable, timevariable, semvariable, s, instructordetails, creditHr);

                        Log.d("Username", s);
                        RefreshDB();
                    }


            }
        });



}

    @Override
    public void itemClickedMethod(int pos) {

        instructordetails = arraylist.get(pos);
    }

    public interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    void onFragmentInteraction(Uri uri);
}
    private void RefreshDB() {
        RealmResults<CourseDetails> Crsedetails= realm.where(CourseDetails.class).findAll();
        Log.d("RESULTS",Crsedetails.toString());
    }
    private void RegisternewCourse(final String titleofcourse, final String hours,
                                   final String mins, final String dayvariable,
                                   final String timevariable, final String semvariable, final String s,
                                   final Instructordetails instructordetails, final String creditHr) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                CourseDetails Course = bgRealm.createObject(CourseDetails.class);
                Course.setTitleofcourse(titleofcourse);
                Course.setTime(hours+":"+mins+" "+timevariable);
                Log.d("timing",hours+":"+mins+" "+timevariable);
                Course.setDay(dayvariable);
                Course.setNameofinstructor(instructordetails.getInstructorFirstName()+" "+instructordetails.getInstructorLastName());
                Course.setSemester(semvariable);
                Course.setImg(instructordetails.getImg());
                //Course.setImg(g);
                Course.setCredithours(creditHr);
                Course.setCourseManagerUserName(s);


                Log.d("SHAred preferences",s);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Log.d("RESULTS","success");
                getFragmentManager().beginTransaction().replace(R.id.container, new CoursesFragment()).addToBackStack("null").commit();



            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.d("RESULTS","failure");

            }
        });
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu,menuInflater);
        menuInflater.inflate(R.menu.actionbar, menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.Home :
                getFragmentManager().beginTransaction().replace(R.id.container, new CoursesFragment(),"Login Fragment").addToBackStack("null").commit();


                return  true;
            case R.id.Instructors :

                getFragmentManager().beginTransaction().replace(R.id.container,new InstructorsDisplayFragment(),"Display Instructors").addToBackStack("null").commit();

                return  true;
            case R.id.AddtoInstructors :
                getFragmentManager().beginTransaction().replace(R.id.container, new AddInstructor(),"Add Instructor Fragment").addToBackStack("null").commit();

                return  true;
            case R.id.Logout :
                SharedPreferences sh = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sh.edit();
                ed.clear();
                ed.commit();

                getFragmentManager().beginTransaction().replace(R.id.container,new Login(),"Login Fragment").commit();



                return  true;
            case R.id.Exit:
                getActivity().finishAffinity();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }
}
