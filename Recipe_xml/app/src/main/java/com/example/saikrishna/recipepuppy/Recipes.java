package com.example.saikrishna.recipepuppy;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import static java.lang.System.in;

public class Recipes extends AppCompatActivity {
    TextView title;
    TextView url;
    TextView ingredients;
    TextView ingredientsValue;
    TextView loading;
    ImageView next;
    ImageView last;
    ImageView prev;
    ImageView first;
    ImageView img;
    Button finish;

    ArrayList<Items> recipeList;
    ProgressBar progressBar;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        loading = (TextView) findViewById(R.id.progress);
        next = (ImageView) findViewById(R.id.next);
        prev = (ImageView) findViewById(R.id.prev);
        last = (ImageView) findViewById(R.id.last);
        first = (ImageView) findViewById(R.id.first);
        finish = (Button) findViewById(R.id.finish);
        img = (ImageView)findViewById(R.id.imageView);
        StringBuilder userIngre = new StringBuilder();
        for(int j=0;j<MainActivity.A.size()-1;j++){
            userIngre.append(MainActivity.A.get(i));
            userIngre.append(",");
        }
        userIngre.append(MainActivity.A.get(MainActivity.A.size()-1));

        if(isNetworkConnection()){
            new GetData().execute(" http://www.recipepuppy.com/api/?format=xml&i="+userIngre.toString()+"&q="+MainActivity.dish.getText().toString());
            Log.d("DEMO","http://www.recipepuppy.com/api/?format=xml&i="+userIngre.toString()+"&q="+MainActivity.dish.getText().toString());

        }
        else {
            Toast.makeText(getApplicationContext(),"No network connection",Toast.LENGTH_SHORT);
        }
        findViewById(R.id.finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i!=recipeList.size()-1)
                    i++;
                if(i<recipeList.size()){
                    title.setText(recipeList.get(i).getTitle());
                    ingredients.setText("Ingredients:");
                    ingredientsValue.setText(recipeList.get(i).getIngredients());
                    url.setText("URL:" + recipeList.get(i).getHref());
                    Picasso.with(Recipes.this).load("https://c1.staticflickr.com/5/4286/35513985750_2690303c8b_z.jpg").into(img);

                    //new GetImage().execute(recipeList.get(i).getThumbnail());
                }
                if(i == recipeList.size()-1){
                    title.setText(recipeList.get(i).getTitle());
                    ingredients.setText("Ingredients:");
                    ingredientsValue.setText(recipeList.get(i).getIngredients());
                    url.setText("URL:" + recipeList.get(i).getHref());
                    Picasso.with(Recipes.this).load("https://c1.staticflickr.com/5/4286/35513985750_2690303c8b_z.jpg").into(img);
                    Toast.makeText(Recipes.this, "Last Recipe", Toast.LENGTH_SHORT).show();
                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i>0)
                    i--;
                if(i>0){
                    title.setText(recipeList.get(i).getTitle());
                    ingredients.setText("Ingredients:");
                    ingredientsValue.setText(recipeList.get(i).getIngredients());
                    url.setText("URL:" + recipeList.get(i).getHref());
                    Picasso.with(Recipes.this).load("https://c1.staticflickr.com/5/4286/35513985750_2690303c8b_z.jpg").into(img);

                    //new GetImage().execute(recipeList.get(i).getThumbnail());
                }
                if(i == 0){
                    title.setText(recipeList.get(i).getTitle());
                    ingredients.setText("Ingredients:");
                    ingredientsValue.setText(recipeList.get(i).getIngredients());
                    url.setText("URL:" + recipeList.get(i).getHref());
                    Picasso.with(Recipes.this).load("https://c1.staticflickr.com/5/4286/35513985750_2690303c8b_z.jpg").into(img);
                    Toast.makeText(Recipes.this, "First Recipe", Toast.LENGTH_SHORT).show();
                }

            }
        });

        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = recipeList.size()-1;
                title.setText(recipeList.get(i).getTitle());
                ingredients.setText("Ingredients:");
                ingredientsValue.setText(recipeList.get(i).getIngredients());
                url.setText("URL:" + recipeList.get(i).getHref());
                Picasso.with(Recipes.this).load("https://c1.staticflickr.com/5/4286/35513985750_2690303c8b_z.jpg").into(img);
                Toast.makeText(Recipes.this, "Last Recipe", Toast.LENGTH_SHORT).show();
                //new GetImage().execute(recipeList.get(i).getThumbnail());
            }
        });

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i=0;
                title.setText(recipeList.get(i).getTitle());
                ingredients.setText("Ingredients:");
                ingredientsValue.setText(recipeList.get(i).getIngredients());
                url.setText("URL:" + recipeList.get(i).getHref());
                Picasso.with(Recipes.this).load("https://c1.staticflickr.com/5/4286/35513985750_2690303c8b_z.jpg").into(img);
                Toast.makeText(Recipes.this, "First Recipe", Toast.LENGTH_SHORT).show();

                //new GetImage().execute(recipeList.get(i).getThumbnail());

            }
        });

    }

    private boolean isNetworkConnection(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected())
            return true;
        return false;
    }
    class GetData extends AsyncTask<String,Integer,ArrayList<Items>> {
        //BufferedReader reader = null;

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected ArrayList<Items> doInBackground(String... params) {
            try {
                int count = 0;
                StringBuilder sb = new StringBuilder();
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                int statusCode = con.getResponseCode();
                Log.d("STATUS",statusCode+"");
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    InputStream in = con.getInputStream();
                    //Log.d("OUTPUT", ItemsUtil.RecipesPullParser.ParseRecipes(in).toString());
                    recipeList = ItemsUtil.RecipesPullParser.ParseRecipes(in);
                    return recipeList;
                    /*reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line = "";
                    while((line=reader.readLine())!=null){
                        sb.append(line);
                    }
                }

                publishProgress(100);
                recipeList = ItemsUtil.RecipesPullParser.ParseRecipes(in);*/



                }
                return recipeList;
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
            @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            title = (TextView) findViewById(R.id.title);
            url = (TextView) findViewById(R.id.url);
            ingredients = (TextView) findViewById(R.id.ingredients);
            ingredientsValue = (TextView)findViewById(R.id.ingredientsValue);
            next.setVisibility(View.INVISIBLE);
            last.setVisibility(View.INVISIBLE);
            prev.setVisibility(View.INVISIBLE);
            first.setVisibility(View.INVISIBLE);
            finish.setVisibility(View.INVISIBLE);

        }
        @Override
        protected void onPostExecute(ArrayList<Items> s) {
           // super.onPostExecute(s);
            progressBar.setVisibility(View.INVISIBLE);
            loading.setVisibility(View.INVISIBLE);
            if(s.size() == 0){
                Toast.makeText(Recipes.this,"No recipes found",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Recipes.this,MainActivity.class);
                startActivity(intent);
            }
            else{
                next.setVisibility(View.VISIBLE);
                last.setVisibility(View.VISIBLE);
                prev.setVisibility(View.VISIBLE);
                first.setVisibility(View.VISIBLE);

                //String a = "a";
                title.setText("Title");

                title.setText(s.get(i).getTitle());
                ingredients.setText("Ingredients:");
                ingredientsValue.setText(s.get(i).getIngredients());
                url.setText("URL:" + s.get(i).getHref());
                Picasso.with(Recipes.this).load("https://c1.staticflickr.com/5/4286/35513985750_2690303c8b_z.jpg").into(img);

                //new GetImage().execute(s.get(i).getThumbnail());
            }
            finish.setVisibility(View.VISIBLE);
        }
    }

}
