package com.example.saikrishna.contacts;

import android.app.DatePickerDialog;
import android.content.Intent;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.File;
import java.util.Calendar;


public class CreateNewContact extends AppCompatActivity {
    boolean mandatorydetails = true;
    boolean validEmail = true;
    boolean validPhone = true;
    public static String _imagePath = "";
    EditText first,last,company,phone,email,url,adress,birthday,nickname,fb,twitter,skype,youtube;
    Button save;
    public ImageView camera;
    String mob;
    public String dp;
    String regexStr = "^\\+[0-9]{10,13}$";
    DatePickerDialog mdatePickerdialog;
    DatePickerDialog mDatePickerDialog = null;
     Calendar myCalendar = Calendar.getInstance();
/*
    private final DatePickerDialog.OnDateSetListener date= new DatePickerDialog.OnDateSetListener(){
        Date current;

        Date minDate = new Date(1990, 10, 25);

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth - 5);
            view.setMaxDate(myCalendar.getTimeInMillis());

            current = new Date(year, monthOfYear, dayOfMonth);


                updateLabel();

        }

        private void updateLabel() {

            String myFormat = "MM/dd/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            birthday.setText(sdf.format(myCalendar.getTime()));
            if (current.getTime() < minDate.getTime()) {
                DatePicker.(1850, 01, 10);
            }
        }
    };*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       // Bitmap bitmap = (Bitmap)data.getExtras().get("data");
       //camera.setImageBitmap(bitmap);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_contact);
//dp=camera.getTag().toString();
        /*Intent imageintent = new Intent();
        imageintent.putExtra(MainActivity.image_key,dp);
        setResult(RESULT_OK,imageintent);
        finish();*/
        first = (EditText)findViewById(R.id._fName);
        last = (EditText)findViewById(R.id._lName);
        company = (EditText)findViewById(R.id._Company);
        phone = (EditText)findViewById(R.id._Phone);
        email = (EditText)findViewById(R.id._Email);
        url = (EditText)findViewById(R.id._URL);
        adress = (EditText)findViewById(R.id._Adress);
        myCalendar = Calendar.getInstance();
myCalendar.add(Calendar.DATE,0);
        birthday = (EditText)findViewById(R.id._Birthday);









        nickname = (EditText)findViewById(R.id._NickName);
        fb = (EditText)findViewById(R.id._fbURL);
        twitter=(EditText)findViewById(R.id._TwitterURL);
        skype = (EditText)findViewById(R.id._Skype);
        youtube = (EditText)findViewById(R.id._Youtube);
        save = (Button)findViewById(R.id.Save);
        camera =(ImageView)findViewById(R.id._imageView);
        mob = phone.getText().toString();

        _imagePath="";
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivityForResult(camera_intent,0);
                File file = getFile();
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(camera_intent,0);
            }
        });
            //dp = camera.getTag().toString();

        birthday.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                if (mDatePickerDialog == null) {
                    mDatePickerDialog = new DatePickerDialog(CreateNewContact.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    birthday.setText(String.format(
                                            "%04d-%02d-%02d", year, monthOfYear + 1,
                                            dayOfMonth));
                                    myCalendar.set(Calendar.YEAR, year);
                                    myCalendar.set(Calendar.MONTH, monthOfYear);
                                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                }
                            },1849,12,01);
                    myCalendar.set(Calendar.YEAR, 1849);
                    myCalendar.set(Calendar.MONTH, 12);
                    myCalendar.set(Calendar.DAY_OF_MONTH, 01);
                    mDatePickerDialog.setCanceledOnTouchOutside(true);
                   // mDatePickerDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
                    mDatePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                }
                mDatePickerDialog.show();

                /*new DatePickerDialog(CreateNewContact.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();*/
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String FirstName = first.getText().toString();
                    String LastName = last.getText().toString();
                    String Company = company.getText().toString();
                    String Phone = phone.getText().toString();
                    String Email = email.getText().toString();
                    String URL = url.getText().toString();
                    String Adress = adress.getText().toString();
                    String Birthday = birthday.getText().toString();

                    String NickName = nickname.getText().toString();
                    String FacebookURL = fb.getText().toString();
                    String TwitterURL = twitter.getText().toString();
                    String Skype = skype.getText().toString();
                    String Youtube = youtube.getText().toString();

                    if (FirstName.length() == 0 || LastName.length() == 0 || Phone.length() == 0) {
                        Toast.makeText(CreateNewContact.this, " First Name or Last Name or Phone missing ", Toast.LENGTH_SHORT).show();
                        mandatorydetails = false;
                    } else mandatorydetails = true;
                    if (Email.length() != 0) {
                        if (!isValidEmail(Email)) {
                            Toast.makeText(CreateNewContact.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                            validEmail = false;
                        } else {
                            validEmail = true;
                        }
                    }
                    if (Phone.length() != 0) {
                        char c = Phone.charAt(0);
                        String temp = Phone;
                        if (c == '+') {
                            Phone = Phone.substring(1);
                        }
                        if (!Number_Validate(Phone)) {
                            Toast.makeText(CreateNewContact.this, "Invalid Phone", Toast.LENGTH_SHORT).show();
                            validPhone = false;
                        } else {
                            validPhone = true;
                            Phone = temp;
                        }
                    }
                    if (mandatorydetails && validEmail && validPhone) {
                        contacts c = new contacts(_imagePath, FirstName, LastName, Company, Phone, Email, URL, Adress, Birthday, NickName, FacebookURL, TwitterURL, Skype, Youtube);
                        Intent intent = new Intent();
                        intent.putExtra(MainActivity.contacts_key, c);
                        setResult(RESULT_OK, intent);

                        finish();
                    }
                }
                catch (Exception e){

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

}
