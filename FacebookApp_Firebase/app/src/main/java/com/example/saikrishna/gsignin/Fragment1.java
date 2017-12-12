package com.example.saikrishna.gsignin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView Recyclerview1;
    ArrayList<Profile> AllUsers = new ArrayList<>();
    static ArrayList<String> friends;

    ArrayList<Profile> FriendsArrayList = new ArrayList<>();


    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref2;
    String userEmail;
    Adapter1 adapter1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Fragment1() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment1 newInstance(String param1, String param2) {
        Fragment1 fragment = new Fragment1();
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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            userEmail= user.getEmail();

            Log.d("NAME", FirebaseAuth.getInstance().getCurrentUser().getUid());
        }

        childref2=dataref.child("users");

        childref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AllUsers.clear();
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    Profile prof = d.getValue(Profile.class);
                    if(friends.contains(prof.getId())){

//                        ArrayList<String> sent = curr.getSentList();
//                        ArrayList<String> recv = curr.getRecievedList();
//                        ArrayList<String> friends = curr.getFriendlist();
//
//
//
//
                        //                         if(!(sent.contains(prof.getId()) || recv.contains(prof.getId())
                        //                               || friends.contains(prof.getId()))) {

                        AllUsers.add(prof);
                        adapter1.notifyDataSetChanged();

                        //                      }




                    }


                }

                Log.d("AllUsers",AllUsers.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d("FRAGMENT 1",FriendsArrayList.toString());
        Profile p = new Profile();

        FriendsArrayList.add(p);




//
        Recyclerview1 = getActivity().findViewById(R.id.FriendsRecyclerview);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        Recyclerview1.setLayoutManager(layoutManager);

        adapter1 = new Adapter1(AllUsers,getActivity());
        Log.d("adaptersending",AllUsers.toString());
        Recyclerview1.setAdapter(adapter1);
        Recyclerview1.setLayoutManager(new LinearLayoutManager(getContext()));



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//
//        Log.d("FRAGMENT 1",FriendsArrayList.toString());
//        View root = inflater.inflate(R.layout.fragment_fragment1, container, false);
//        Recyclerview1 = getActivity().findViewById(R.id.FriendsRecyclerview);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        Recyclerview1.setLayoutManager(layoutManager);
//
//        adapter = new Adapter2(FriendsArrayList);
//        Recyclerview1.setAdapter(adapter);

return inflater.inflate(R.layout.fragment_fragment1, container, false);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        void onFragmentInteraction(Uri uri);
    }
}
