package com.example.saikrishna.homework6;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

import java.io.ByteArrayOutputStream;
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
 * Use the {@link AddInstructor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddInstructor extends Fragment {
    Realm realm;
    static final int CAM_REQ = 2;
    int PICK_IMAGE_REQUEST= 1;
    EditText InstructorFirstNameEdittext,InstructorLastNameEdittext,InstructorEmailEdittext,InstructorWebsiteEdittext;
    Button RegisterInstructor,resetinstructor;
    String IFname,ILname,IEmail,IWebsite;
    CharSequence[] image_option = {"Pick from gallery","Click a photo"};
    ImageView img;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
     Instructordetails mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddInstructor() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment AddInstructor.
     */
    // TODO: Rename and change types and number of parameters
    public static AddInstructor newInstance(Instructordetails param1) {
        AddInstructor fragment = new AddInstructor();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);

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
        ((MainActivity)getActivity()).setActionBarTitle("Add Instructor");
        return inflater.inflate(R.layout.fragment_add_instructor, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.gotohomescreen();
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
        InstructorFirstNameEdittext = getActivity().findViewById(R.id.InstructorFirstName);
        InstructorLastNameEdittext = getActivity().findViewById(R.id.InstructorLastname);
        InstructorEmailEdittext = getActivity().findViewById(R.id.InstructorEmail);
        InstructorWebsiteEdittext =getActivity().findViewById(R.id.InstructorWebsite);
        RegisterInstructor = getActivity().findViewById(R.id.InstructorRegisterButton);
        resetinstructor =getActivity().findViewById(R.id.ResetInstructorButton);
        if(mParam1!= null){
            InstructorFirstNameEdittext.setText(mParam1.getInstructorFirstName());
            InstructorLastNameEdittext.setText(mParam1.getInstructorLastName());
            InstructorEmailEdittext.setText(mParam1.getInstructorEmail());
            InstructorWebsiteEdittext.setText(mParam1.getInstructorWebsite());
            Log.d("toim",mParam1.toString());
        }
        img = getActivity().findViewById(R.id.Instructordp);
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
                                    AddInstructor.this.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                                }
                                if(image_option[i].equals("Click a photo")){
                                    Intent Cintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(Cintent,CAM_REQ);;
                                }
                            }
                        });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        resetinstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertdialog = new AlertDialog.Builder(getActivity());
                alertdialog.setTitle("Reset All Fields")
                        .setMessage("Are you sure you want to Reset?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                InstructorFirstNameEdittext.setText("");
                                InstructorLastNameEdittext.setText("");
                                InstructorEmailEdittext.setText("");
                                InstructorWebsiteEdittext.setText("");
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


        RegisterInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IFname = InstructorFirstNameEdittext.getText().toString();
                ILname = InstructorLastNameEdittext.getText().toString();
                IEmail = InstructorEmailEdittext.getText().toString();
                IWebsite = InstructorWebsiteEdittext.getText().toString();
                if (IFname.equals("") || ILname.equals("") || IEmail.equals("")) {
                    Toast.makeText(getActivity(), "Missing ", Toast.LENGTH_SHORT).show();
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(IEmail).matches()) {
                    Toast.makeText(getActivity(), "Email Invalid", Toast.LENGTH_SHORT).show();
                } else{
                    SharedPreferences sh = getActivity().getPreferences(Context.MODE_PRIVATE);

                    String s = sh.getString("uname", "default");
                    Drawable drawable = img.getDrawable();
                    Drawable drawable1 = getResources().getDrawable(R.drawable.addphoto);

                    byte[] imgBytes = null;
                    if (!drawable.getConstantState().equals(drawable1.getConstantState())) {
                        Bitmap bm = ((BitmapDrawable) img.getDrawable()).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        imgBytes = baos.toByteArray();
                    }

                    RegisternewInstructor(IFname, ILname, IEmail, IWebsite, s, imgBytes);

                    Log.d("Username", IFname);
                    RefreshDB();
                //RealmResults<Instructordetails> instrdetails= realm.where(Instructordetails.class).findAll();
            }


            }
        });
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

    private void RefreshDB() {

        RealmResults<Instructordetails> instrdetails= realm.where(Instructordetails.class).findAll();
        Log.d("RESULTS",instrdetails.toString());
    }

    private void RegisternewInstructor(final String iFname, final String iLname, final String iEmail,
                                       final String iWebsite, final String courseManagerUNAME, final byte[] imgBytes) {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Instructordetails instructor = bgRealm.createObject(Instructordetails.class);
                instructor.setInstructorFirstName(iFname);
               instructor.setInstructorLastName(iLname);
                instructor.setInstructorEmail(iEmail);
                instructor.setInstructorWebsite(iWebsite);
                instructor.setImg(imgBytes);
               // instructor.setLoginusername(Login.username);
                instructor.setCourseManagerUserName(courseManagerUNAME);
                Log.d("USERNAME",iFname);
                Log.d("SHAred preferences",courseManagerUNAME);

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
        void gotohomescreen();
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
