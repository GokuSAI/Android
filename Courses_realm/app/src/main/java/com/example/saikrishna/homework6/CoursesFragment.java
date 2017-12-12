package com.example.saikrishna.homework6;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CoursesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoursesFragment extends Fragment implements  CourseAdapter.ClickeedItem{
    Realm realm;
    RecyclerView.Adapter adapter;
    RecyclerView recyclerView;
    ImageView addcourseimageview,img;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<CourseDetails> saveddata = new ArrayList<CourseDetails>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CoursesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CoursesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CoursesFragment newInstance(String param1, String param2) {
        CoursesFragment fragment = new CoursesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).setActionBarTitle("Course Manager");
        return inflater.inflate(R.layout.fragment_courses, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.addcourse();
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
        recyclerView = getActivity().findViewById(R.id.my_recycler_view);
        img=getActivity().findViewById(R.id.CourseManagerdp);
        addcourseimageview = getActivity().findViewById(R.id.AddCourseImage);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        try {
            realm.init(getActivity());
            realm = Realm.getDefaultInstance();


        } catch (Exception e) {
            e.printStackTrace();
        }
        RealmResults<CourseDetails> realmObjects = realm.where(CourseDetails.class).findAll();
        saveddata.clear();

      SharedPreferences shr = getActivity().getPreferences(Context.MODE_PRIVATE);
        String s = shr.getString("uname","coursemanager");
        Log.d("SHARED",s);

        for(CourseDetails myRealmObject : realmObjects)

        {
            CourseDetails id = new CourseDetails();
            id.setTitleofcourse(myRealmObject.getTitleofcourse());
            id.setNameofinstructor(myRealmObject.getNameofinstructor());
            id.setDay(myRealmObject.getDay());
            id.setTime(myRealmObject.getTime());
            id.setSemester(myRealmObject.getSemester());
            id.setNameofinstructor(myRealmObject.getNameofinstructor());
            id.setCredithours(myRealmObject.getCredithours());
//            Bitmap bm = ((BitmapDrawable)img.getDrawable()).getBitmap();
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            byte[] imgBytes = baos.toByteArray();
            id.setImg(myRealmObject.getImg());
            //id.setImg(myRealmObject.getImg());

           // id.setLoginusername(myRealmObject.getLoginusername());
            Log.d("sss",myRealmObject.toString());
            if(myRealmObject.getCourseManagerUserName().equals(s)) {
                id.setCourseManagerUserName(s);
                saveddata.add(id);
            }


        }

        adapter = new CourseAdapter(realm,getActivity(),saveddata, (CourseAdapter.ClickeedItem)this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        addcourseimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.addcourse();
            }
        });

    }

    @Override
    public void itemClickedMethod(int pos) {
        CourseDetails c =saveddata.get(pos);
        //AddNewCourse adn =new AddNewCourse();
        Log.d("taggg",c.toString());

        getFragmentManager().beginTransaction().replace(R.id.container,DisplayCourseFragment.newInstance(c), "Show Courses").addToBackStack("null").commit();
       // ((EditText)adn.getActivity().findViewById(R.id.CourseTitleEdittext)).setText(c.getTitleofcourse());
//        Fragment frag = getActivity().getFragmentManager().findFragmentById(R.id.newcourse);
//        ((EditText) frag.getActivity().findViewById(R.id.CourseTitleEdittext)).setText(c.getTitleofcourse());
        Log.d("clicked",""+pos);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void addcourse();
    }

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
