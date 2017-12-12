package com.example.saikrishna.contactsfirebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class CreateNewContact extends AppCompatActivity {

    EditText firstname,lastname,email,phone;
    Button submit;
    ImageView img;
    User newuser;
    User intentuser;
    static final int CAM_REQ = 2;
    int PICK_IMAGE_REQUEST= 1;
    CharSequence[] image_option = {"Pick from gallery","Click a photo"};
    ArrayList<User> AllUsers = new ArrayList<>();
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);


        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:


                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(CreateNewContact.this,MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_contact);




        childref = dataref.child("contacts");
        firstname = (EditText)findViewById(R.id.NewUserFirstName);
        lastname = (EditText)findViewById(R.id.NewUserLastName);
        email=(EditText)findViewById(R.id.NewUSerEmail);
        phone=(EditText)findViewById(R.id.NewUserPhone);
        submit=(Button)findViewById(R.id.UserSubmitButton);
        img = (ImageView)findViewById(R.id.UserDp);




        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(CreateNewContact.this);
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
                                    CreateNewContact.this.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
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
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newuser = new User();
                newuser.setfirstname(firstname.getText().toString());
                newuser.setlastname(lastname.getText().toString());

                newuser.setemail(email.getText().toString());
                newuser.setphone(Integer.parseInt(phone.getText().toString()));


                Log.d("NEW USER",newuser.toString());



                Log.d("ALL users",AllUsers.toString());
//


//
//

                //image storing


                img.setDrawingCacheEnabled(true);
                img.buildDrawingCache();
                Bitmap bitmap = img.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();


                String path = "Fireimages/"+ UUID.randomUUID() + ".png";
                StorageReference ref = storage.getReference(path);


                StorageMetadata metadata = new StorageMetadata.Builder()
                        .setCustomMetadata("text",firstname.getText().toString())
                        .build();

                Toast.makeText(CreateNewContact.this, "Started uploading... ", Toast.LENGTH_SHORT).show();

                UploadTask uploadTask = ref.putBytes(data, metadata);

                uploadTask.addOnSuccessListener(CreateNewContact.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri url = taskSnapshot.getDownloadUrl();
                        newuser.setUrl(url.toString());
                        Log.d("URL",url.toString());
                       Log.d("Image link",url.toString());
                        callSome();
                    }
                });

            }
        });

    }
    public void callSome(){


        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String ky = dataref.child("contacts").child(id).push().getKey();
        newuser.setid(ky);
        dataref.child("contacts").child(id).child(ky).setValue(newuser);


        Log.d("USERID",id);
        String idnote = id.substring(id.indexOf("Notes/-")+6).trim();
        Log.d("USERID",idnote);
        AllUsers.add(newuser);
        Intent intent = new Intent(CreateNewContact.this, ContactsActivity.class);
        intent.putExtra(MainActivity.user_data,AllUsers);
        startActivity(intent);

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(CreateNewContact.this.getContentResolver(), uri);
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
