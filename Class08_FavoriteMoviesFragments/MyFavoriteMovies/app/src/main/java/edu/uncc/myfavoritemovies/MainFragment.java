package edu.uncc.myfavoritemovies;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARAM1 = "moviesList";

    // TODO: Rename and change types of parameters
    private ArrayList<Movie> movieArrayList;

    private MainFragmentCallback mListener;

    public MainFragment() {
    }

    public static MainFragment newInstance(ArrayList<Movie> movieArrayList) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, movieArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieArrayList = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        getActivity().findViewById(R.id.btnSaveMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null)
                mListener.addMovie();
                else
                    Toast.makeText(getActivity().getApplicationContext(),"something went wrong",Toast.LENGTH_LONG);
            }
        });

        getActivity().findViewById(R.id.btnEditMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> movieList = new ArrayList<String>();
                for(Movie movie : movieArrayList){
                    movieList.add(movie.getName());
                }

                final CharSequence[] movies = movieList.toArray(new CharSequence[movieList.size()]);

                if(movieArrayList.size()==0)
                    Toast.makeText(getActivity().getApplicationContext(),"There are no movies in database to edit.",Toast.LENGTH_SHORT).show();
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getView().getContext());
                    builder.setTitle("Pick a movie").
                            setItems( movies ,new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int item){
                                    Movie selectedMovie=null;
                                    for(Movie movie : movieArrayList){
                                        if(movie.getName()==movies[item]) {
                                            selectedMovie = movie;
                                            mListener.setCurrentEditIndex(item);
                                            break;
                                        }
                                    }
                                mListener.editMovie(selectedMovie);

                                }
                            });

                    AlertDialog editAlert = builder.create();
                    editAlert.show();
                }
            }
        });

        getActivity().findViewById(R.id.btnDeleteMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> movieList = new ArrayList<String>();
                for(Movie movie : movieArrayList){
                    movieList.add(movie.getName());
                }

                final CharSequence[] movies = movieList.toArray(new CharSequence[movieList.size()]);

                if(movieArrayList.size()==0)
                    Toast.makeText(getActivity().getApplicationContext(),"There are no movies in database to Delete.",Toast.LENGTH_SHORT).show();
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getView().getContext());
                    builder.setTitle("Pick a movie").
                            setItems( movies ,new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int item){
                                    Movie selectedMovie=null;
                                    for(Movie movie : movieArrayList){
                                        if(movie.getName()==movies[item]) {
                                            selectedMovie = movie;
                                            break;
                                        }
                                    }

                                    mListener.deleteMovie(selectedMovie);

                                }
                            });

                    AlertDialog editAlert = builder.create();
                    editAlert.show();
                }
            }
        });

        getActivity().findViewById(R.id.btnListbyYear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movieArrayList.size()==0)
                    Toast.makeText(getActivity().getApplicationContext(),"There are no movies in database to show.",Toast.LENGTH_SHORT).show();
                else {
                    mListener.showListByYear();
                }
            }
        });

        getActivity().findViewById(R.id.btnListbyRating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movieArrayList.size()==0)
                    Toast.makeText(getActivity().getApplicationContext(),"There are no movies in database to show.",Toast.LENGTH_SHORT).show();
                else {
                    mListener.showListByRating();
                }
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (MainFragmentCallback) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface MainFragmentCallback {
        void addMovie();
        void editMovie(Movie movie);
        void deleteMovie(Movie movie);
        void showListByYear();
        void showListByRating();
        void setCurrentEditIndex(int index);
    }
}
