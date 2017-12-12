package com.example.saikrishna.inclass10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectAvatar extends AppCompatActivity {
    String dp = "img1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_avatar);









                final Intent intent = new Intent();

                findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dp="img1";
                        intent.putExtra(CreateNewContact.VALUE_KEY,dp);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });

                findViewById(R.id.imageView2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dp="img2";
                        intent.putExtra(CreateNewContact.VALUE_KEY,dp);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                findViewById(R.id.imageView3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dp="img3";
                        intent.putExtra(CreateNewContact.VALUE_KEY,dp);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                findViewById(R.id.imageView4).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dp="img4";
                        intent.putExtra(CreateNewContact.VALUE_KEY,dp);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                findViewById(R.id.imageView5).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dp="img5";
                        intent.putExtra(CreateNewContact.VALUE_KEY,dp);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                findViewById(R.id.imageView6).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dp="img6";
                        intent.putExtra(CreateNewContact.VALUE_KEY,dp);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });


            }

    }

