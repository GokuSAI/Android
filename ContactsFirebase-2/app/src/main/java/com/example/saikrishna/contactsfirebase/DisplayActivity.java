package com.example.saikrishna.contactsfirebase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class DisplayActivity extends AppCompatActivity {
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
                Intent intent = new Intent(DisplayActivity.this,MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);


        firstname = (EditText)findViewById(R.id.EFirstname);
        lastname = (EditText)findViewById(R.id.ELastname);
        email=(EditText)findViewById(R.id.EEMAIL);
        phone=(EditText)findViewById(R.id.EPhone);
        submit=(Button)findViewById(R.id.ESubmitButton);
        img = (ImageView)findViewById(R.id.Eimage);
        childref = dataref.child("contacts");

        if(getIntent().getExtras().getSerializable("LIST")!= null){
            intentuser= (User) getIntent().getExtras().getSerializable("LIST");
            firstname.setText(intentuser.getfirstname());
            lastname.setText(intentuser.getlastname());
            phone.setText(Integer.toString(intentuser.getphone()));
            email.setText(intentuser.getemail());

            Picasso.with(DisplayActivity.this).load(intentuser.getUrl()).into(img);

        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newuser = new User();
                newuser.setfirstname(firstname.getText().toString());
                newuser.setlastname(lastname.getText().toString());

                newuser.setemail(email.getText().toString());
                newuser.setphone(Integer.parseInt(phone.getText().toString()));
                newuser.setid(intentuser.getid());
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

                Toast.makeText(DisplayActivity.this, "Started uploading... ", Toast.LENGTH_SHORT).show();

                UploadTask uploadTask = ref.putBytes(data, metadata);

                uploadTask.addOnSuccessListener(DisplayActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
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

        dataref.child("contacts").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshot.getRef().child(intentuser.getid()).setValue(newuser);
                //dialog.dismiss();
                Intent intent = new Intent(DisplayActivity.this, ContactsActivity.class);
                //intent.putExtra(MainActivity.user_data,AllUsers);
                startActivity(intent);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("User", databaseError.getMessage());
            }
        });
    }
}
