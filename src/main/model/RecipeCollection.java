package model;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

//Manages a collection of favorite recipes.
public class RecipeCollection implements Writable {
    private Set<Recipe> recipes;

    //Creates an empty RecipeCollection.
    public RecipeCollection() {
        this.recipes = new HashSet<>();
    }

    //MODIFIES: this
    //EFFECTS: Adds the recipe to the collection if it does not exists.
    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    //MODIFIES: this
    //EFFECTS: Removes the recipe from the collection if it exists.
    public void removeRecipe(Recipe recipe) {
        recipes.remove(recipe);

    }

    public Set<Recipe> getRecipes() {
        return new HashSet<>(recipes);
    }
    
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Recipe recipe : recipes) {
            jsonArray.put(recipe.toJson());
        }
        json.put("recipes", jsonArray);
        return json;
    }
}
