package com.example.saikrishna.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
Button Create ,Edit, Finish,Delete,Display;
    public  static  int number = 0;
    int whc;
    public static ArrayList <contacts>  c = new ArrayList<contacts>();
    static int REQ_CODE = 100;
    static int REQ_CODE2 = 200;
    static String contacts_key = "value";
    static String image_key = "pictures";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK) {
                c.add((contacts) data.getExtras().get(MainActivity.contacts_key));
                Intent i = new Intent(MainActivity.this,Customlayout.class);
                startActivity(i);
            }


        }
        //else if(resultCode == REQ_CODE2){
          //  c.add ((contacts) data.getExtras().get(MainActivity.contacts_key));
            //c = (ArrayList<contacts>)getIntent().getExtras().get(MainActivity.contacts_key);
            //c.set()
        //}

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
clearFile();


    Create=(Button)findViewById(R.id.Createm);
        Create.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this,CreateNewContact.class);
        startActivityForResult(intent , REQ_CODE);
    }
});
        Edit = (Button)findViewById(R.id.Editm);
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , Customlayout.class);
                //intent.putExtra(contacts_key, c);
                startActivityForResult(intent, REQ_CODE2);

            }
        });
Delete = (Button)findViewById(R.id.Deletem);
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeleteCustomLayout.class);
                startActivity(intent);
            }
        });

        Display = (Button)findViewById(R.id.Displaym);
        Display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayCustomLayout.class);
                startActivity(intent);
            }
        });
            Finish = (Button)findViewById(R.id.button5);
        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void clearFile(){
        File folder = new File("sdcard/camer_app");
        String[]entries = folder.list();
        if(entries!=null) {
            for (String s : entries) {
                File currentFile = new File(folder.getPath(), s);
                currentFile.delete();
            }
        }

    }
}
