package com.example.saikrishna.inclass08;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by saikrishna on 10/30/17.
 */

public class RecipeAdapter extends  RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    static ArrayList<Recipes> mData;
    private Activity mcontext;

    public RecipeAdapter(ArrayList<Recipes> mData,Activity context) {
        this.mData = mData;
        this.mcontext = context;
    }


    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipelayout,parent,false );
        RecipeAdapter.ViewHolder viewHolder = new RecipeAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecipeAdapter.ViewHolder holder, int position) {
        Recipes recipes = mData.get(position);
        holder.title.setText(recipes.getTitle());
        holder.ingredients.setText(recipes.getIngredients());
        holder.url.setText(recipes.getHref());
        if(recipes.getThumbnail().equals("")) {
            Picasso.with(mcontext).load("https://pleper.com/html/assets/img/no-image-found.jpg").into(holder.mainimg);
        }
        else{
            Picasso.with(mcontext).load(recipes.getThumbnail()).into(holder.mainimg);

        }
    }


    @Override
    public int getItemCount() {
        if(mData!=null)
            return mData.size();
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView url;
        TextView ingredients;
        ImageView mainimg;
        Button finish;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.TitleTextView);
            url = itemView.findViewById(R.id.UrlTextView);
            ingredients =  itemView.findViewById(R.id.IngredientsTextView);
            finish=itemView.findViewById(R.id.FinishButton);
            mainimg= itemView.findViewById(R.id.imageView2);



        }

    }
}
