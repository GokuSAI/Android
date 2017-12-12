package com.example.saikrishna.contactsfirebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;

public class SignupActivity extends AppCompatActivity {
    EditText Firstname,Lastname,username,Password,RepeatPassword;
    Button Cancel,SignupButton;
    ImageView img;
    User user1;
    static final int CAM_REQ = 2;
    int PICK_IMAGE_REQUEST= 1;
    CharSequence[] image_option = {"Pick from gallery","Click a photo"};
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        childref = dataref.child("users");
        mAuth = FirebaseAuth.getInstance();
        Firstname = (EditText)findViewById(R.id.NewUserFirstName);
        Lastname=(EditText)findViewById(R.id.NewUserLastName);
        username = (EditText)findViewById(R.id.NewUSerEmail);
        Password=(EditText)findViewById(R.id.NewUserPhone);
        RepeatPassword=(EditText)findViewById(R.id.RepeatPassEditText);
        img = (ImageView)findViewById(R.id.UserDp);
        Cancel=(Button)findViewById(R.id.CancelButton);
        SignupButton=(Button)findViewById(R.id.SignUpButton);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
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
                                    SignupActivity.this.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
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


        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Firstname.getText().toString().equals("") || Lastname.getText().toString().equals(" ") || username.getText().toString().equals("") || Password.getText().toString().equals("")|| RepeatPassword.getText().toString().equals("")){
                    Toast.makeText(SignupActivity.this, "Please enter the fields", Toast.LENGTH_SHORT).show();
                }
                if(Password.getText().toString().trim().length()<8){
                    Toast.makeText(SignupActivity.this, "password must contain 6 or more characters", Toast.LENGTH_SHORT).show();

                }
                if(!Password.getText().toString().equals(RepeatPassword.getText().toString())){
                    Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
                else{


                    user1 = new User();
                    user1.setfirstname(Firstname.getText().toString());
                    user1.setlastname(Lastname.getText().toString());
                    user1.setUsername(username.getText().toString());
                    user1.setpassword(Password.getText().toString());


                    //Log.d("Created",user.toString());
                    //DatabaseReference newRef = childref.push();
                    //user.setid(String.valueOf(newRef));




                    mAuth.createUserWithEmailAndPassword(username.getText().toString(), Password.getText().toString())
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(SignupActivity.this, "Successfully created user", Toast.LENGTH_SHORT).show();
                                        // Sign in success, update UI with the signed-in user's information
                                        //Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                        DatabaseReference newRef = childref.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        user1.setid(String.valueOf(newRef));

                                        newRef.setValue(user1);
                                  //      FirebaseAuth.getInstance().getCurrentUser().
                                        if (firebaseUser != null) {

                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(user1.getfirstname()+user1.getlastname()).build();
                                            firebaseUser.updateProfile(profileUpdates)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {

                                                                FirebaseAuth.getInstance().signOut();
                                                                finish();
                                                            }
                                                        }
                                                    });
                                            // Log.d("Display Name",firebaseUser.getDisplayName());
                                        }


                                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                        //updateUI(user);
                                    } else {
                                        Toast.makeText(SignupActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        // updateUI(null);
                                    }

                                    // ...
                                }
                            });





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
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(SignupActivity.this.getContentResolver(), uri);
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


}
