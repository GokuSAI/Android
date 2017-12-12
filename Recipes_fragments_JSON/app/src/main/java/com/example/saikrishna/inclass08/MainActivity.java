package com.example.saikrishna.inclass08;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener,RecipesFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().replace(R.id.container,new MainFragment(),"Main").commit();


    }

    @Override
    public void gotonextfragment() {
        getFragmentManager().beginTransaction().replace(R.id.container,new RecipesFragment(),"Recipes").addToBackStack(null).commit();
    }



    @Override
    public void onFragmentInteraction() {
        getFragmentManager().beginTransaction().replace(R.id.container,new MainFragment(),"Main").commit();
        CustomAdapter.mData=new ArrayList<>();

    }



    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount()>0){
            getFragmentManager().beginTransaction().replace(R.id.container,new MainFragment(),"Main").commit();
            CustomAdapter.mData=new ArrayList<flag>();
        }
        else{
            super.onBackPressed();
        }


    }
}
