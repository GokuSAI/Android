package edu.uncc.myfavoritemovies;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowListByRating extends Fragment {

    ArrayList<Movie> movies;
    private static final String ARG_PARAM1 = "param1";
    int counter=0,size;

    private showByRatingCallback mListener;

    public ShowListByRating() {
        // Required empty public constructor
    }

    public static ShowListByRating newInstance(ArrayList<Movie> movieArrayList) {
        ShowListByRating fragment = new ShowListByRating();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, movieArrayList);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_list_by_rating, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("Movies by Rating");

        if (getArguments() != null) {
            movies = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
        
        size = movies.size();
        Log.d("demo",movies.size()+"");
        Collections.sort(movies,new CustomComparator());

        FillMovieDetails(movies.get(0));

        getActivity().findViewById(R.id.btnFirst).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter=0;
                FillMovieDetails(movies.get(counter));

            }
        });
        getActivity().findViewById(R.id.btnLast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter=size-1;
                FillMovieDetails(movies.get(counter));

            }
        });
        getActivity().findViewById(R.id.btnPrevious).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter>0){
                    counter-=1;
                    FillMovieDetails(movies.get(counter));
                }

            }
        });
        getActivity().findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter<size-1){
                    counter+=1;
                    FillMovieDetails(movies.get(counter));
                }
            }
        });
        getActivity().findViewById(R.id.btnFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.finishFramgment();

            }
        });
    }

    public class CustomComparator implements Comparator<Movie> {
        @Override
        public int compare(Movie o1, Movie o2) {
            return (o1.getRating()==o2.getRating())?0:(o1.getRating()>o2.getRating())?1:-1 ;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (showByRatingCallback) activity;
        }

    private void FillMovieDetails(Movie movie){
        TextView title = (TextView)getActivity().findViewById(R.id.tvTitleValue);
        TextView description = (TextView)getActivity().findViewById(R.id.etDescriptionValue);
        TextView genre = (TextView)getActivity().findViewById(R.id.tvGenreValue);
        TextView rating = (TextView)getActivity().findViewById(R.id.tvRatingValue);
        TextView year = (TextView)getActivity().findViewById(R.id.tvYearValue);
        TextView imdb = (TextView)getActivity().findViewById(R.id.tvIMDBValue);

        try{
            title.setText(movie.getName());
            description.setText(movie.getDescription());
            genre.setText(movie.getGenre().toString());
            rating.setText(movie.getRating() +" / 5");
            year.setText(movie.getYear()+"");
            imdb.setText(movie.getImdb());
        }
        catch (Exception ex){
            throw ex;
        }
    }

    public interface showByRatingCallback {
        void finishFramgment();
    }
}
