package com.example.app.inclass05;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {
    TextView title;
    TextView url;
    TextView ingredients;
    TextView ingredientsValue;
    TextView loading;
    ImageView next;
    ImageView last;
    ImageView prev;
    ImageView first;
    Button finish;
    ArrayList<Recipes> recipeList;
    ProgressBar progressBar;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        loading = (TextView) findViewById(R.id.progress);
        next = (ImageView) findViewById(R.id.next);
        prev = (ImageView) findViewById(R.id.prev);
        last = (ImageView) findViewById(R.id.last);
        first = (ImageView) findViewById(R.id.first);
        finish = (Button) findViewById(R.id.finish);
        StringBuilder userIngre = new StringBuilder();
        for(int j=0;j<MainActivity.A.size()-1;j++){
            userIngre.append(MainActivity.A.get(i));
            userIngre.append(",");
        }
        userIngre.append(MainActivity.A.get(MainActivity.A.size()-1));

        if(isNetworkConnection()){
            new GetData().execute(" http://www.recipepuppy.com/api/?i="+userIngre.toString()+"&q="+MainActivity.dish.getText().toString());
            Log.d("DEMO","http://www.recipepuppy.com/api/?i="+userIngre.toString()+"&q="+MainActivity.dish.getText().toString());

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
                    title.setText("Title: "+recipeList.get(i).getTitle());
                    ingredients.setText("Ingredients:");
                    ingredientsValue.setText(recipeList.get(i).getIngredients());
                    url.setText("URL:" + recipeList.get(i).getHref());
                    new GetImage().execute(recipeList.get(i).getThumbnail());
                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i>0)
                    i--;
                if(i>0){
                    title.setText("Title: "+recipeList.get(i).getTitle());
                    ingredients.setText("Ingredients:");
                    ingredientsValue.setText(recipeList.get(i).getIngredients());
                    url.setText("URL:" + recipeList.get(i).getHref());
                    new GetImage().execute(recipeList.get(i).getThumbnail());
                }

            }
        });

        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = recipeList.size()-1;
                title.setText("Title: "+recipeList.get(i).getTitle());
                ingredients.setText("Ingredients:");
                ingredientsValue.setText(recipeList.get(i).getIngredients());
                url.setText("URL:" + recipeList.get(i).getHref());
                new GetImage().execute(recipeList.get(i).getThumbnail());
            }
        });

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i=0;
                title.setText("Title: "+recipeList.get(i).getTitle());
                ingredients.setText("Ingredients:");
                ingredientsValue.setText(recipeList.get(i).getIngredients());
                url.setText("URL:" + recipeList.get(i).getHref());
                new GetImage().execute(recipeList.get(i).getThumbnail());

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
    class GetData extends AsyncTask<String,Integer,ArrayList<Recipes>> {
        BufferedReader reader = null;

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
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
        protected void onPostExecute(ArrayList<Recipes> s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.INVISIBLE);
            loading.setVisibility(View.INVISIBLE);
            if(s.size() == 0){
                Toast.makeText(RecipeActivity.this,"No recipes found",Toast.LENGTH_SHORT).show();
            }
            else{
                next.setVisibility(View.VISIBLE);
                last.setVisibility(View.VISIBLE);
                prev.setVisibility(View.VISIBLE);
                first.setVisibility(View.VISIBLE);

                String a = "a";
                title.setText("Title: " + s.get(i).getTitle());
                ingredients.setText("Ingredients:");
                ingredientsValue.setText(s.get(i).getIngredients());
                url.setText("URL:" + s.get(i).getHref());
                new GetImage().execute(s.get(i).getThumbnail());
            }
            finish.setVisibility(View.VISIBLE);
        }
    }

    class GetImage extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {
            try{

                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                Bitmap bitmap = BitmapFactory.decodeStream(con.getInputStream());
                return bitmap;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap image) {
            super.onPostExecute(image);
            ImageView img = (ImageView) findViewById(R.id.imageView);
            img.setImageBitmap(image);
        }
    }

}
