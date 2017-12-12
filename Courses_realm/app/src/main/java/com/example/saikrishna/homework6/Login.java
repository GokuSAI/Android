package com.example.saikrishna.homework6;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login extends Fragment {
    Realm realm;
 String username,password;
    EditText loginusername,loginpassword;
    TextView signup;
    Button loginButton;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }



    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        }catch (Exception e){
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
            //Realm.removeDefaultConfiguration();
            realm.init(getActivity());

            realm = Realm.getDefaultInstance();


        } catch (Exception e) {
            e.printStackTrace();
        }

        loginusername=getActivity().findViewById(R.id.UsernameEdittext);
        loginpassword=getActivity().findViewById(R.id.PasswordEdittext);
        loginButton=getActivity().findViewById(R.id.LoginButton);
        signup = getActivity().findViewById(R.id.SignupTextview);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               username=loginusername.getText().toString();
                password=loginpassword.getText().toString();

                if(username.equals("") || password.equals("")){
                    Toast.makeText(getActivity(), "please enter the details", Toast.LENGTH_SHORT).show();
                }
                else {
                    RealmResults<Userdetails> realmObjects = realm.where(Userdetails.class).findAll();
                    RealmResults<Userdetails> realmObjects_1 = realm.where(Userdetails.class).contains("PersonUsername",username)
                            .contains("PersonPassword",password).findAll();
                    if(realmObjects.size()==0){
                        Toast.makeText(getActivity(),"New User. Please SignUp first",Toast.LENGTH_SHORT).show();
                    }
                    else if(realmObjects_1.size()==0){
                        Toast.makeText(getActivity(),"Username or password is wrong",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        for (Userdetails myRealmObject : realmObjects) {
                            if (username.equals(myRealmObject.getPersonUsername()) && password.equals(myRealmObject.getPersonPassword())) {
                                Log.d("login success", myRealmObject.getPersonFirstname());
                                SharedPreferences sh = getActivity().getPreferences(Context.MODE_PRIVATE);
                                SharedPreferences.Editor ed = sh.edit();
                                ed.putString("uname", username);
                                ed.commit();
                                mListener.gotocourses();


                            }
                        }
                    }

                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotosignupfragment();
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name

        void gotosignupfragment();
        void gotocourses();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu,menuInflater);
        menuInflater.inflate(R.menu.actionbar, menu);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.Home).setEnabled(false);
        menu.findItem(R.id.Logout).setEnabled(false);
        menu.findItem(R.id.Instructors).setEnabled(false);
        menu.findItem(R.id.AddtoInstructors).setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.Exit:
                getActivity().finishAffinity();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }
}
