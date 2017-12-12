package edu.uncc.myfavoritemovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;


/*
a.Assignment #. InClass08
b.File Name.  InClass8_Group09.zip
c.Name. Sunand Kumar Pujari, Sai Yesaswy Mylavarapu
*/

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentCallback,AddMovieFragment.AddMovieFragmentCallback,
EditMovieFragment.OnFragmentInteractionListener,ShowListByRating.showByRatingCallback,ShowListByYear.showByYearCallback {

    LinearLayout container;
    ArrayList<Movie> moviesList;
    int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = (LinearLayout)findViewById(R.id.layoutContainer);
        moviesList=new ArrayList<>();

        moviesList.add(new Movie("item1","desc1",Genre.Action,3,1990,"link1"));
        moviesList.add(new Movie("item2","desc2",Genre.Animation,5,1982,"link1"));
        moviesList.add(new Movie("item3","desc3",Genre.Documentary,1,1995,"link1"));
        moviesList.add(new Movie("item4","desc4",Genre.Action,2,1996,"link1"));

        getFragmentManager().beginTransaction()
                .add(container.getId(), MainFragment.newInstance(moviesList),"main fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void addMovie() {
        getFragmentManager().beginTransaction()
                .replace(container.getId(), AddMovieFragment.newInstance(moviesList),"add movie fragment")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void editMovie(Movie movie) {
        getFragmentManager().beginTransaction()
                .replace(container.getId(), EditMovieFragment.newInstance(moviesList,movie),"edit movie fragment")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void deleteMovie(Movie movie) {
        moviesList.remove(movie);

        Toast.makeText(this,"movie:"+movie.getName()+" deleted",Toast.LENGTH_SHORT).show();
        getFragmentManager().beginTransaction()
                .replace(container.getId(), MainFragment.newInstance(moviesList),"main fragment")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void showListByYear() {

        getFragmentManager().beginTransaction()
                .replace(container.getId(), ShowListByYear.newInstance(moviesList),"ShowListByYear")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void showListByRating() {

        getFragmentManager().beginTransaction()
                .replace(container.getId(), ShowListByRating.newInstance(moviesList),"ShowListByRating")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void setCurrentEditIndex(int index) {
        this.currentPosition=index;
    }

    @Override
    public void saveMovie(Movie movie) {
        moviesList.add(movie);

        Toast.makeText(getApplicationContext(),"Movie: "+movie.getName()+" saved!",Toast.LENGTH_LONG).show();
        getFragmentManager().beginTransaction()
                .replace(container.getId(), MainFragment.newInstance(moviesList),"main fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void UpdateMovie(Movie movie) {
        moviesList.remove(currentPosition);
        moviesList.add(movie);

        getFragmentManager().beginTransaction()
                .replace(container.getId(), MainFragment.newInstance(moviesList),"main fragment")
                .addToBackStack(null)
                .commit();


        Toast.makeText(getApplicationContext(),"Movie: "+movie.getName()+" saved!",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount()>0){
            getFragmentManager().popBackStack();
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public void finishFramgment() {

        getFragmentManager().beginTransaction()
                .replace(container.getId(), MainFragment.newInstance(moviesList),"main fragment")
                .addToBackStack(null)
                .commit();
    }
}
