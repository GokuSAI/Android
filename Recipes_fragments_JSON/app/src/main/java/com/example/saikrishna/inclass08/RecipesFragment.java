package com.example.saikrishna.inclass08;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.saikrishna.inclass08.MainFragment.dish;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipesFragment extends Fragment {
    ArrayList<Recipes> A = new ArrayList();
    TextView loading;
    static ImageView img;
    Button finish;
    ArrayList<Recipes> recipeList;
    ProgressBar progressBar;
    LinearLayout l,h;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Button search;
    RecyclerView recyclerView;
    StringBuilder userIngre = new StringBuilder();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RecipesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipesFragment newInstance(String param1, String param2) {
        RecipesFragment fragment = new RecipesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipes, container, false);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.recipe_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        progressBar=getActivity().findViewById(R.id.progressBar);
        loading=getActivity().findViewById(R.id.LoadingTextView);
        search = getActivity().findViewById(R.id.SearchButton);
        finish = getActivity().findViewById(R.id.FinishButton);
        img = getView().findViewById(R.id.imageView2);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.onFragmentInteraction();

            }
        });
        userIngre = MainFragment.userIngre1;
        MainFragment.userIngre1 = new StringBuilder();
        Log.d("user ingredients",userIngre.toString());
        Log.d("dish name",dish.getText().toString());
        new GetData().execute(" http://www.recipepuppy.com/api/?i="+userIngre.toString()+"&q="+dish.getText().toString());
        Log.d("URL generated","http://www.recipepuppy.com/api/?i="+userIngre.toString()+"&q="+dish.getText().toString());
    }

    class GetData extends AsyncTask<String,Integer,ArrayList<Recipes>> {
        BufferedReader reader = null;

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //progressBar.setProgress(values[0]);
        }

        @Override
        protected ArrayList<Recipes> doInBackground(String... params) {
            try{
                int count = 0;
                StringBuilder sb = new StringBuilder();
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                int statusCode = con.getResponseCode();
                if(statusCode == HttpURLConnection.HTTP_OK){
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line = "";
                    while((line=reader.readLine())!=null){
                        sb.append(line);
                    }
                }

                publishProgress(100);
                recipeList = RecipeUtil.RecipeJSONParser.parseRecipe(sb.toString());
                return RecipeUtil.RecipeJSONParser.parseRecipe(sb.toString());
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    if(reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();



            //finish.setVisibility(View.INVISIBLE);

        }
        @Override
        protected void onPostExecute(ArrayList<Recipes> s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            loading.setVisibility(View.GONE);
            if (s.size() == 0) {

                Toast.makeText(getActivity(), "No recipes found", Toast.LENGTH_SHORT).show();
            }
            Log.d("LIST",s.toString());

            adapter = new RecipeAdapter(s,getActivity());
            recyclerView.setAdapter(adapter);


        }
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();

    }
}
