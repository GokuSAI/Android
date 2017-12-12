package com.example.saikrishna.recipepuppy;

/**
 * Created by saikrishna on 10/2/17.
 */

public class Items {
    String title,href,ingredients;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Items{" +
                "title='" + title + '\'' +
                ", href='" + href + '\'' +
                ", ingredients='" + ingredients + '\'' +
                '}';
    }
}
