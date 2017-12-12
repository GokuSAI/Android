package com.example.saikrishna.inclass08;

import android.content.Context;
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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

   static EditText dish;

    ArrayList<flag> A = new ArrayList();

    ArrayList<flag > recipeList;
    ProgressBar progressBar;

    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Button search;
    int i=0;
    RecyclerView recyclerView;
    static StringBuilder userIngre1 = new StringBuilder();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        return inflater.inflate(R.layout.fragment_main, container, false);
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

        //A = new ArrayList<>();
        flag f = new flag();
        f.tag = "add";
        A.add(f);
        recyclerView = getActivity().findViewById(R.id.my_recycler_view);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        CustomAdapter.mData.clear();
        adapter = new CustomAdapter(A);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        search=getActivity().findViewById(R.id.SearchButton);

        dish=getActivity().findViewById(R.id.DishEditText);
        Log.d("Bing0",dish.getText().toString());

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeList = new ArrayList<flag>(CustomAdapter.mData);
                //CustomAdapter.mData.clear();
                Log.d("array list", recipeList.toString());
                for (int j = 0; j < recipeList.size() - 1; j++) {
                    userIngre1.append(recipeList.get(i).edittextname);
                    userIngre1.append(",");
                }
                userIngre1.append(recipeList.get(recipeList.size() - 1).edittextname);
                Log.d("DUH", userIngre1.toString());
                if (recipeList.size()==0 || dish.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Inputs cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("search",""+recipeList.size());
                    mListener.gotonextfragment();
                }
            }
        });

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
        void gotonextfragment();
    }


    }


