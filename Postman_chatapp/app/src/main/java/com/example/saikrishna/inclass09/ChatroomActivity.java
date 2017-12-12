package com.example.saikrishna.inclass09;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatroomActivity extends AppCompatActivity implements  GettingMessagesTask.LinkData,AddMessageTask.AddLinkData,ChatAdapter.deleteMy{

    EditText msg;
    TextView nameofthread;
    ListView msglistview;
    ImageView send,home;




    String z; //here we have to use the thread_id,




    SharedPreferences pref;
    ChatAdapter chatAdapter;
    ArrayList<MessageClass> existing = new ArrayList<>();

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        msg = (EditText)findViewById(R.id.MessageEdittext);
        send = (ImageView)findViewById(R.id.sendmessage);
        home = (ImageView)findViewById(R.id.HomeImageview);
        nameofthread=(TextView) findViewById(R.id.NameofThread);
        msglistview=(ListView)findViewById(R.id.ChatroomListview);
        pref = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        if(getIntent().getExtras()!=null){
            z = (String) getIntent().getExtras().getSerializable(ThreadActivity.position_key);
            Log.d("Position key",z+"");
        }
        new GettingMessagesTask(ChatroomActivity.this).execute("http://ec2-54-164-74-55.compute-1.amazonaws.com/api/messages/"+z,pref.getString("token",""));
        Log.d("IDOFTHE",z+"");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddMessageTask(ChatroomActivity.this).execute("http://ec2-54-164-74-55.compute-1.amazonaws.com/api/message/add",pref.getString("token",""),msg.getText().toString(),z);
                msg.setText("");


            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatroomActivity.this,ThreadActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void printmessages(ArrayList<MessageClass> th) {

        existing = th;

        nameofthread.setText(ThreadActivity.threadname);
        chatAdapter = new ChatAdapter(this, R.layout.chatroomlayout,th);
        //Log.d("Title",th.toString());

        msglistview.setAdapter(chatAdapter);

    }


    @Override
    public  void printAddedUserDetails(ArrayList<MessageClass> th) {
        th.addAll(existing);
        nameofthread.setText(ThreadActivity.threadname);

        chatAdapter = new ChatAdapter(this, R.layout.chatroomlayout,th);
        //Log.d("Title",th.toString());

        msglistview.setAdapter(chatAdapter);
    }

    @Override
    public void deleteThread(int pos) {
        new DeletemessageTask(ChatroomActivity.this).execute("http://ec2-54-164-74-55.compute-1.amazonaws.com/api/message/delete/"+pos,pref.getString("token",""));

    }
}
