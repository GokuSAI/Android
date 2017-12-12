package com.example.saikrishna.contacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class EditContacts extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11,e12,e13;
    Button b;
    contacts c;
    contacts ls;
    ImageView img;
    String path ="";
    int z;
    boolean mandatorydetails = true;
    boolean validEmail = true;
    boolean validPhone = true;
    public static  String _imagePath="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contacts);




        if(getIntent().getExtras()!= null){
            //c=getIntent().get
Log.d("Enter","Entered loop");
            ls= (contacts) getIntent().getExtras().getSerializable(MainActivity.contacts_key);
            z = (Integer)getIntent().getExtras().getSerializable(Customlayout.position_key);
            Log.d("DEMO",ls.toString());
            //Log.d("DEMO",c.toString());
        }
        e1 = (EditText)findViewById(R.id._fName);
        e2 = (EditText)findViewById(R.id._lName);
        e3 = (EditText)findViewById(R.id._Company);
        e4 = (EditText)findViewById(R.id._Phone);
        e5 = (EditText)findViewById(R.id._Email);
        e6 = (EditText)findViewById(R.id._URL);
        e7 = (EditText)findViewById(R.id._Adress);
        e8 = (EditText)findViewById(R.id._Birthday);
        e9 = (EditText)findViewById(R.id._NickName);
        e10 =(EditText)findViewById(R.id._fbURL);
        e11 = (EditText)findViewById(R.id._TwitterURL);
        e12 = (EditText)findViewById(R.id._Skype);
        e13 = (EditText)findViewById(R.id._Youtube);
        img = (ImageView)findViewById(R.id._imageView);
        Drawable drawable = Drawable.createFromPath(ls.imagePath);
        if(drawable == null)
            img.setImageResource(R.drawable.default_image);
        else
            img.setImageDrawable(Drawable.createFromPath(ls.imagePath));
        //img.setImageDrawable(Drawable.createFromPath(ls.imagePath));
        e1.setText(ls.FirstName);
        e2.setText(ls.LastName);
        e3.setText(ls.Company);
        e4.setText(ls.Phone);
        e5.setText(ls.Email);
        e6.setText(ls.URL);
        e7.setText(ls.Adress);
        e8.setText(ls.Birthday);
        e9.setText(ls.NickName);
        e10.setText(ls.FacebookURL);
        e11.setText(ls.TwitterURL);
        e12.setText(ls.Skype);
        e13.setText(ls.Youtube);
        _imagePath = ls.imagePath;

        //int id =getResources().getIdentifier(,"drawable",getPackageName());
        //img.setImageDrawable(getResources().getDrawable(id));

_imagePath="";
        findViewById(R.id._imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivityForResult(camera_intent,0);
                File file = getFile();
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(camera_intent,0);
            }
        });
        b=(Button)findViewById(R.id.e_Save);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ls.FirstName = e1.getText().toString();
                ls.LastName = e2.getText().toString();
                ls.Company = e3.getText().toString();
                ls.Phone = e4.getText().toString();
                ls.Email = e5.getText().toString();
                ls.URL = e6.getText().toString();
                ls.Adress = e7.getText().toString();
                ls.Birthday = e8.getText().toString();
                ls.NickName =e9.getText().toString();
                ls.FacebookURL = e10.getText().toString();
                ls.TwitterURL = e11.getText().toString();
                ls.Skype = e12.getText().toString();
                ls.Youtube = e13.getText().toString();


                if ( ls.FirstName.length() == 0 || ls.LastName.length() == 0 || ls.Phone.length() == 0){
                    Toast.makeText(getApplicationContext(), " Mandatory details", Toast.LENGTH_SHORT).show();
                }
                else mandatorydetails = true;
                if(ls.Email.length()!=0){
                    if(!isValidEmail(ls.Email)){
                        Toast.makeText(EditContacts.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                        validEmail=false;
                    }
                    else{
                        validEmail=true;
                    }
                }
                if(ls.Phone.length()!=0){
                    char c = ls.Phone.charAt(0);
                    String temp = ls.Phone;
                    if(c=='+'){
                        ls.Phone = ls.Phone.substring(1);
                    }
                    if(!Number_Validate(ls.Phone)){
                        Toast.makeText(EditContacts.this, "Invalid Phone", Toast.LENGTH_SHORT).show();
                        validPhone = false;
                    }
                    else {
                        validPhone = true;
                        ls.Phone = temp;
                    }
                }
                if(mandatorydetails && validEmail && validPhone ){

                   contacts c1 = new contacts(_imagePath,ls.FirstName,ls.LastName,ls.Company,ls.Phone,ls.Email,ls.URL,ls.Adress,ls.Birthday,ls.NickName,ls.FacebookURL,ls.TwitterURL,ls.Skype,ls.Youtube);
                   MainActivity.c.set(z,c1);
                   Intent intent = new Intent(EditContacts.this,Customlayout.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }
    private File getFile(){
        File folder = new File("sdcard/camer_app");


        File image_file = new File(folder,(MainActivity.number+1)+".jpg");
        _imagePath = "sdcard/camer_app/"+(MainActivity.number+1)+".jpg";
        MainActivity.number++;
        return image_file;
    }
    public final static boolean isValidEmail(CharSequence target) {
        boolean flag = !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();

        return flag;

    }
    private Boolean Number_Validate(String number)
    {
        return  !TextUtils.isEmpty(number) && (number.length()==10) && android.util.Patterns.PHONE.matcher(number).matches();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Bitmap bitmap = (Bitmap)data.getExtras().get("data");
       // findViewById(R.id._imageView).setImageBitmap(bitmap);
        if(requestCode == 0){
            String path = _imagePath;
            ImageView image = (ImageView)findViewById(R.id._imageView);
            Drawable drawable = Drawable.createFromPath(path);
            if(drawable != null)
                image.setImageDrawable(drawable);
            else {
                image.setImageResource(R.drawable.default_image);
            }
        }
    }
}
