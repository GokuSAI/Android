package com.example.saikrishna.inclass08;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by saikrishna on 10/30/17.
 */

public class Recipes {

        String title,href,thumbnail,ingredients;
        public static Recipes createRecipe(JSONObject js) throws JSONException {
            Recipes recipes = new Recipes();
            recipes.setTitle(js.getString("title"));
            recipes.setHref(js.getString("href"));
            recipes.setIngredients(js.getString("ingredients"));
            recipes.setThumbnail(js.getString("thumbnail"));
            return recipes;
        }

        public String getTitle() {
            return title;
        }

        public String getHref() {
            return href;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public String getIngredients() {
            return ingredients;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public void setIngredients(String ingredients) {
            this.ingredients = ingredients;
        }

    @Override
    public String toString() {
        return "Recipes{" +
                "title='" + title + '\'' +
                ", href='" + href + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", ingredients='" + ingredients + '\'' +
                '}';
    }
}

