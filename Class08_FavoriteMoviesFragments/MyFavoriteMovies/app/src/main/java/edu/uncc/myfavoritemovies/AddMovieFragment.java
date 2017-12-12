package edu.uncc.myfavoritemovies;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AddMovieFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private ArrayList<Movie> movieArrayList;

    private AddMovieFragmentCallback mListener;

    public AddMovieFragment() {
        // Required empty public constructor
    }
    public static AddMovieFragment newInstance(ArrayList<Movie> movieArrayList) {
        AddMovieFragment fragment = new AddMovieFragment();
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
        return inflater.inflate(R.layout.fragment_add_movie, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("Add Movie");

        final SeekBar rating = (SeekBar)getActivity().findViewById(R.id.sbRating);
        rating.setLeft(0);
        rating.setMax(5);
        rating.incrementProgressBy(1);
        rating.setProgress(0);

        final TextView ratingDisplay = (TextView)getActivity().findViewById(R.id.tvRatingDisplay);

        rating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ratingDisplay.setText(progress+"");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final Spinner genre = (Spinner)getActivity().findViewById(R.id.spGenre);
        genre.setAdapter(new ArrayAdapter<Genre>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, Genre.values()));

        final EditText name = (EditText)getActivity().findViewById(R.id.etName);
        final EditText description = (EditText)getActivity().findViewById(R.id.etDescription);
        final EditText year = (EditText)getActivity().findViewById(R.id.etYear);
        final EditText imdb = (EditText)getActivity().findViewById(R.id.etIMDB);

        getActivity().findViewById(R.id.btnSaveMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Movie movie;
                    if(isValidForm()){
                        movie = new Movie(name.getText().toString(),
                                description.getText().toString(),
                                Genre.valueOf(genre.getSelectedItem().toString()),
                                rating.getProgress(),
                                Integer.parseInt(year.getText().toString()),
                                imdb.getText().toString()
                        );

                        ArrayList<String> movieNames = new ArrayList<String>();
                        for (Movie item:movieArrayList) {
                            movieNames.add(item.getName());
                        }

                        if(movieNames.contains(movie.getName()))
                            Toast.makeText(getActivity().getApplicationContext(),"Movie name Already exits!",Toast.LENGTH_SHORT).show();
                        else
                        mListener.saveMovie(movie);
                    }
                    else {
                        Toast message = Toast.makeText(getActivity().getApplicationContext(),"Invalid Input",Toast.LENGTH_SHORT);
                        message.show();
                    }
                }
                catch (Exception ex){
                    throw ex;
                }
            }
        });


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (AddMovieFragmentCallback) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface AddMovieFragmentCallback {
        void saveMovie(Movie movie);
    }

    private boolean isValidForm() {
        boolean isValid=true;

        EditText name = (EditText)getActivity().findViewById(R.id.etName);
        EditText description = (EditText)getActivity().findViewById(R.id.etDescription);
        EditText year = (EditText)getActivity().findViewById(R.id.etYear);
        EditText imdb = (EditText)getActivity().findViewById(R.id.etIMDB);
        Spinner genre = (Spinner)getActivity().findViewById(R.id.spGenre);

        try {
            if(isEmptyOrNull(name.getText().toString()) ||
                    isEmptyOrNull(description.getText().toString()) ||
                    isEmptyOrNull(year.getText().toString()) ||
                    isEmptyOrNull(imdb.getText().toString()) ||
                    isEmptyOrNull(genre.getSelectedItem().toString()) )
                isValid=false;
            if(Integer.parseInt(year.getText().toString()) > Calendar.getInstance().get(Calendar.YEAR) || Integer.parseInt(year.getText().toString())<999)
                isValid=false;

        }catch (NumberFormatException ex){
            isValid=false;
        }
        return isValid;
    }

    private boolean isEmptyOrNull(String str){
        return (str == null || str.isEmpty());
    }
}
