package com.example.saikrishna.gsignin;

import android.content.Intent;
import android.net.ParseException;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{
    FirebaseAuth.AuthStateListener firebaseAuthListener;
    SignInButton signin;
    EditText username,pass;
    TextView SignUp;
    Button login;
    TextView t;
    private FirebaseAuth mAuth;
    DatabaseReference dataref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childref;
    DatabaseReference currentUserProfile;

    GoogleApiClient mAPIClient;
    private  static final int RC_SIGN_IN = 8001;
    private static final String Tag = "Singinactivity";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =item.getItemId();

//        switch (id){
//            case R.id.show_all:
//
//                break;


        return super.onOptionsItemSelected(item);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayUseLogoEnabled(true);

//         firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
//                Log.d("login00", "fireauth  " + firebaseUser);
//                if (firebaseUser != null) {
//                    Log.d("login01", "user already logged in");
//                    // Toast.makeText(getBaseContext(), "Already logged in", Toast.LENGTH_LONG).show();
//
//                    Intent intent = new Intent(MainActivity.this, Home.class);
//                    startActivity(intent);
//                    finish();
//                    return;
//                }
////
////                }
//            }
//
//
//        };
        
        getSupportActionBar().setIcon(R.mipmap.logo);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //getSupportActionBar().setDisplayUseLogoEnabled(true);
        username = (EditText)findViewById(R.id.UserNameEditText);
        pass = (EditText)findViewById(R.id.PasswordEditText);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mAPIClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /*FragmentActivity */ , this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        signin = (SignInButton)findViewById(R.id.sign_in_button);

        login=(Button)findViewById(R.id.LoginButton);
        //signout=(Button)findViewById(R.id.button);
        t =(TextView)findViewById(R.id.textView);
        SignUp = (TextView)findViewById(R.id.SignUpTextview);
        signin.setOnClickListener(this);
        login.setOnClickListener(this);
        SignUp.setOnClickListener(this);
        //signout.setOnClickListener(this);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(Tag,"failed"+connectionResult);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.LoginButton:
                Login();
                break;
            case R.id.SignUpTextview:
                Signup();
                break;
        }
    }

    private void signIn(){




        Intent signInintent = Auth.GoogleSignInApi.getSignInIntent(mAPIClient);
        startActivityForResult(signInintent,RC_SIGN_IN);

    }
    private void Signup(){
        Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
        startActivity(intent);

    }

    private void Login(){


                if(username.getText().toString().equals("")||username.getText().toString()== null|| pass.getText().toString().equals("")||pass.getText().toString()==null){
                    Toast.makeText(MainActivity.this, "please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.signInWithEmailAndPassword(username.getText().toString(), pass.getText().toString())
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d("dssds", "signInWithEmail:onComplete:" + task.isSuccessful());
                                    if (task.isSuccessful()) {


                                        currentUserProfile = dataref.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                                        Log.d("chiru","122");

                                        currentUserProfile.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                Home.curr = dataSnapshot.getValue(Profile.class);
                                                //Fragment3.revievedRequests = curr.getRecievedList();
                                                Home.friendsList = Home.curr.getFriendlist();
                                                Log.d("friends", Home.friendsList.toString());

                                                Intent intent = new Intent(MainActivity.this, Home.class);
                                                //intent.putExtra(LIST, user);
                                                startActivity(intent);
                                                finish();

                                                currentUserProfile.removeEventListener(this);

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });


                                    }


                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Log.w("demo", "signInWithEmail:failed", task.getException());
                                        Toast.makeText(MainActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
            }


    private void signOut(){
        Auth.GoogleSignInApi.signOut(mAPIClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                t.setText("Signed out");
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInresult(result);
        }
    }
    private void handleSignInresult(GoogleSignInResult result){
        Log.d(Tag," success");
        if(result.isSuccess()){

//            GoogleSignInAccount acct = result.getLastSignedInAccount(getActivity());
//            if (acct != null) {
//                String personName = acct.getDisplayName();
//                String personGivenName = acct.getGivenName();
//                String personFamilyName = acct.getFamilyName();
//                String personEmail = acct.getEmail();
//                String personId = acct.getId();
//                //Uri personPhoto = acct.getPhotoUrl();
//            }

            GoogleSignInAccount acc = result.getSignInAccount();
//            currentUserProfile = dataref.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//
//            currentUserProfile.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//
//                    Home.curr = dataSnapshot.getValue(Profile.class);
//                    //Fragment3.revievedRequests = curr.getRecievedList();
//                    Home.friendsList = Home.curr.getFriendlist();
//                    Log.d("friends", Home.friendsList.toString());
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });

           Intent intent = new Intent(MainActivity.this,Home.class);
            startActivity(intent);

        }
        else{

            Log.d("Result","Not Success");
        }
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        //mAuth.addAuthStateListener(firebaseAuthListener);
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if ((currentUser!=null)){
//            Intent intent = new Intent(MainActivity.this,Home.class);
//            startActivity(intent);
//        }
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (firebaseAuthListener != null) {
//            mAuth.removeAuthStateListener(firebaseAuthListener);
//        }
//    }


}
