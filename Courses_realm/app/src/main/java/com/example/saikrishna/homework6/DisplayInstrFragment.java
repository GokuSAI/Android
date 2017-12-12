package com.example.saikrishna.homework6;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DisplayInstrFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DisplayInstrFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayInstrFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Instructordetails mParam1;
    TextView fname,lname,email,web;
    ImageView img;
    private OnFragmentInteractionListener mListener;

    public DisplayInstrFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment DisplayInstrFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayInstrFragment newInstance(Instructordetails param1) {
        DisplayInstrFragment fragment = new DisplayInstrFragment();
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        fname = getActivity().findViewById(R.id.IFNAME);
        lname = getActivity().findViewById(R.id.ILNAME);
        email = getActivity().findViewById(R.id.IEMAIL);
        web = getActivity().findViewById(R.id.IWEBSITE);
        img = getActivity().findViewById(R.id.IIMAGE);
        if(mParam1!=null){
            fname.setText(mParam1.getInstructorFirstName());
            lname.setText(mParam1.getInstructorLastName());
            email.setText(mParam1.getInstructorEmail());
            web.setText(mParam1.getInstructorWebsite());
            Bitmap bm = BitmapFactory.decodeByteArray(mParam1.getImg(), 0, mParam1.getImg().length);
            img.setImageBitmap(bm);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).setActionBarTitle("Instructor");
        return inflater.inflate(R.layout.fragment_display_instr, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
