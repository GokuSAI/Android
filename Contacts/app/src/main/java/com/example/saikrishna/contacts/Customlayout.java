package com.example.saikrishna.contacts;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Customlayout extends AppCompatActivity {
    ArrayList<contacts> s;

    private ListView listView;
    static int REQUEST_CODE = 100;

    static String position_key = "keytst";
    static String contact_key = "values";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customlayout);

         listView =(ListView)findViewById(R.id.listview);
       // s = (ArrayList<contacts>) getIntent().getExtras().get(MainActivity.contacts_key);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(Customlayout.this, "Great", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Customlayout.this,EditContacts.class);
                intent.putExtra(MainActivity.contacts_key, MainActivity.c.get(position));
                intent.putExtra(position_key,position);
                startActivityForResult(intent,REQUEST_CODE);
                finish();
            }
        });




    }






    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return MainActivity.c.size();
        }

        @Override
        public Object getItem(int position) {
            return MainActivity.c.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.customlayout, null);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
            TextView fnametext = (TextView) convertView.findViewById(R.id.textView);
            TextView lnametext = (TextView) convertView.findViewById(R.id.textView2);
            TextView phntext =  (TextView)  convertView.findViewById(R.id.textView3);
            Drawable img = Drawable.createFromPath(MainActivity.c.get(position).imagePath);
            if(img == null)
                imageView.setImageResource(R.drawable.default_image);
            else
               imageView.setImageDrawable(Drawable.createFromPath(MainActivity.c.get(position).imagePath));
            fnametext.setText(MainActivity.c.get(position).FirstName);
            lnametext.setText(MainActivity.c.get(position).LastName);
            phntext.setText(MainActivity.c.get(position).Phone);
            /*String fn = fnametext.getText().toString();
            fn = s.get(position).FirstName;
            String ln = lnametext.getText().toString();
            ln = s.get(position).LastName;*/

            return convertView;
        }
    }


}
