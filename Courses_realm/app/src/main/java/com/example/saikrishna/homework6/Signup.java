package com.example.saikrishna.homework6;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Signup#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Signup extends Fragment {
    Realm realm;

    EditText Firstname,Lastname,Username,Password;
    ImageView img;
    Button Registerbutton;
    String uname,fname,lname,pword;
    int PICK_IMAGE_REQUEST= 1;
    static final int CAM_REQ = 2;
    CharSequence[] image_option = {"Pick from gallery","Click a photo"};
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Signup() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Signup.
     */
    // TODO: Rename and change types and number of parameters
    public static Signup newInstance(String param1, String param2) {
        Signup fragment = new Signup();
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
        ((MainActivity)getActivity()).setActionBarTitle("Register");
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.Aftersignup();
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


        } catch (Exception e) {
            e.printStackTrace();
        }

        Firstname=getActivity().findViewById(R.id.FirstName);
        Lastname=getActivity().findViewById(R.id.LastName);
        Password=getActivity().findViewById(R.id.Password);
        Username = getActivity().findViewById(R.id.UserName);
        img = getActivity().findViewById(R.id.dp);
        Registerbutton = getActivity().findViewById(R.id.RegisterButton);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pick one")
                        .setItems(image_option, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(image_option[i].equals("Pick from gallery")){
                                    Intent intent = new Intent();
// Show only images, no videos or anything else
                                    intent.setType("image/*");
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                                    Signup.this.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                                }
                                if(image_option[i].equals("Click a photo")){
                                    Intent Cintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(Cintent,CAM_REQ);
                                }
                            }
                        });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        Registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname=Username.getText().toString().trim();
                fname=Firstname.getText().toString().trim();
                lname=Lastname.getText().toString().trim();
                pword=Password.getText().toString().trim();
                realm.init(getActivity());

                realm = Realm.getDefaultInstance();
                RealmResults<Userdetails> realmObjects = realm.where(Userdetails.class).contains("PersonUsername",uname).findAll();
                if(uname.equals("")||fname.equals("")||lname.equals("")){
                    Toast.makeText(getActivity(),"Please enter all the details",Toast.LENGTH_SHORT).show();
                }
                else if(realmObjects.size()>0){
                    Toast.makeText(getActivity(),"User already exists",Toast.LENGTH_SHORT).show();
                }
                else if(pword.length()<8){
                    Toast.makeText(getActivity(),"Password should have at least 8 characters",Toast.LENGTH_SHORT).show();
                }
                else {
                    RegisterintoDB(uname, fname, lname, pword);
                    Log.d("Username", Username.getText().toString().trim());
                    RefreshDB();
                    mListener.Aftersignup();
                }
            }

            
        });

    }



    private void RegisterintoDB(final String username1, final String firstname1, final String lastname1, final String password1) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Userdetails user = bgRealm.createObject(Userdetails.class);
                user.setPersonUsername(username1);
                user.setPersonFirstname(firstname1);
                user.setPersonLastname(lastname1);
                user.setPersonPassword(password1);
               // user.se
                Log.d("USERNAME",username1);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                SharedPreferences sh = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("uname", uname);
                ed.commit();
                // Transaction was a success.
                Log.d("RESULTS","success");
                Log.d("Uname in signup",uname);


            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.d("RESULTS","failure");

            }
        });
        
    }
    private void RefreshDB() {
        RealmResults<Userdetails> details= realm.where(Userdetails.class).findAll();
        Log.d("RESULTS",details.toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(requestCode == CAM_REQ){
            String path = "sdcard/camer_app/cam_img.jpg";
            img.setImageDrawable(Drawable.createFromPath(path));
        }
    }

    private File getFile(){
        File folder = new File("sdcard/camer_application");
        if(!folder.exists()){
            folder.mkdir();
        }
        File image_file = new File(folder,"cam_img.jpg");
        return image_file;
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
        void Aftersignup();
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
