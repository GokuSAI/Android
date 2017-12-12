package com.example.saikrishna.homework6;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements Login.OnFragmentInteractionListener,Signup.OnFragmentInteractionListener,AddInstructor.OnFragmentInteractionListener,CoursesFragment.OnFragmentInteractionListener,AddNewCourse.OnFragmentInteractionListener,InstructorsDisplayFragment.OnFragmentInteractionListener,DisplayCourseFragment.OnFragmentInteractionListener,DisplayInstrFragment.OnFragmentInteractionListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().add(R.id.container,new Login(),"Login Fragment").commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void gotosignupfragment() {
        getFragmentManager().beginTransaction().replace(R.id.container, new Signup(),"Signup fragment").addToBackStack("null").commit();
    }

    @Override
    public void gotocourses() {
        getFragmentManager().beginTransaction().replace(R.id.container, new CoursesFragment(),"Courses fragment").addToBackStack("null").commit();

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.Home :
               getFragmentManager().beginTransaction().replace(R.id.container, new Login(),"Login Fragment").addToBackStack("null").commit();


                return  true;
            case R.id.Instructors :

                getFragmentManager().beginTransaction().replace(R.id.container,new InstructorsDisplayFragment(),"Display Instructors").addToBackStack("null").commit();

                return  true;
            case R.id.AddtoInstructors :
                getFragmentManager().beginTransaction().replace(R.id.container, new AddInstructor(),"Add Instructor Fragment").addToBackStack("null").commit();

                return  true;
            case R.id.Logout :
                SharedPreferences sh = this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sh.edit();
                ed.clear();
                ed.commit();

                getFragmentManager().beginTransaction().replace(R.id.container,new Login(),"Login Fragment").commit();



                return  true;
            case R.id.Exit:
                finishAffinity();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void addcourse() {
        getFragmentManager().beginTransaction().replace(R.id.container, new AddNewCourse(), "Add New Courses").addToBackStack("null").commit();
        //getFragmentManager().beginTransaction().replace(R.id.container, new CoursesFragment(),"Add Course Fragment").addToBackStack("null").commit();

    }

    @Override
    public void Aftersignup() {
        getFragmentManager().beginTransaction().replace(R.id.container, new CoursesFragment(),"Courses fragment").addToBackStack("null").commit();
    }

    @Override
    public void gotohomescreen() {
        getFragmentManager().beginTransaction().replace(R.id.container, new CoursesFragment(),"Courses fragment").addToBackStack("null").commit();

    }


//    @Override
//    public void onBackPressed() {
//
//        if(getFragmentManager().getBackStackEntryCount() >0){
//            getFragmentManager().popBackStack();
//        }
//        else{
//            super.onBackPressed();
//        }
//        super.onBackPressed();
//    }
    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }
}
