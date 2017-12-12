package com.example.saikrishna.contactsfirebase;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    SignInButton signin;
    User user;

    static String LIST = "LIST";
    EditText username,pass;
    TextView SignUp;
    Button login;
    TextView t;
    static String user_data = "DATA";

    private FirebaseAuth mAuth;

    GoogleApiClient mAPIClient;
    private  static final int RC_SIGN_IN = 8001;
    private static final String Tag = "Singinactivity";


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:


                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();


        username = (EditText)findViewById(R.id.UserNameEditText);
        pass = (EditText)findViewById(R.id.NewUserPhone);
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
        Intent intent = new Intent(MainActivity.this,SignupActivity.class);
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
                                Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
                                //intent.putExtra(LIST, user);
                                startActivity(intent);
                                finish();
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
            GoogleSignInAccount acc = result.getSignInAccount();
            //Intent intent = new Intent(MainActivity.this,Home.class);
            //startActivity(intent);
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(this, "not Success", Toast.LENGTH_SHORT).show();

            Log.d("Result","Not Success");
        }
    }

}
