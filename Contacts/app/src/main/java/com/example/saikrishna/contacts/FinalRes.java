package com.example.saikrishna.contacts;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class FinalRes extends AppCompatActivity {
    contacts c;
    EditText e1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_res);

        int y =(Integer)  getIntent().getExtras().getSerializable(DisplayCustomLayout.position_key);
        c = MainActivity.c.get(y);
        //e1=(EditText)findViewById(R.id._fName);
        //e1.setText(c.FirstName);
        final TextView first = (TextView) findViewById(R.id.FirstT);
        final TextView last = (TextView) findViewById(R.id.LastT);
        final TextView company = (TextView) findViewById(R.id.CompanyT);
        final TextView phone = (TextView) findViewById(R.id.PhoneT);
        final TextView email = (TextView) findViewById(R.id.EmailT);
        final TextView url = (TextView) findViewById(R.id.URLT);
        final TextView address = (TextView) findViewById(R.id.AddressT);
        final TextView bday = (TextView) findViewById(R.id.BirthdayT);
        final TextView nickname = (TextView) findViewById(R.id.NicknameT);
        final TextView facebookURL = (TextView) findViewById(R.id.FacebookProfileURLT);
        final TextView twitterURL = (TextView) findViewById(R.id.TwitterProfileURLT);
        final TextView skype = (TextView) findViewById(R.id.SkypeT);
        final TextView youTube = (TextView) findViewById(R.id.YouTubeChannelT);
        final ImageView image = (ImageView) findViewById(R.id.imageViewFinal);


       // if(getIntent().getExtras()!=null){
         //   Contact c = (Contact)getIntent().getExtras().getSerializable(DisplayActivity.CONTACT_KEY);
        image.setImageDrawable(Drawable.createFromPath(c.imagePath));
            first.setText(c.FirstName);
            last.setText(c.LastName);
            company.setText(c.Company);
            phone.setText(c.Phone);
            email.setText(c.Email);
            url.setText(c.URL);
            address.setText(c.Adress);
            bday.setText(c.Birthday);
            nickname.setText(c.NickName);
            facebookURL.setText(c.FacebookURL);
            twitterURL.setText(c.TwitterURL);
            skype.setText(c.Skype);
            youTube.setText(c.Youtube);
            String path = c.imagePath;
            Drawable drawable = Drawable.createFromPath(path);
            if(drawable == null){
                drawable = this.getResources().getDrawable(R.drawable.default_image);
                image.setImageDrawable(drawable);
            }
            else image.setImageDrawable(Drawable.createFromPath(path));
        }

    }

