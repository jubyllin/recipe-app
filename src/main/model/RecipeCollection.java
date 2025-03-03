package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
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

    //Constructs a RecipeCollection from a given JSON object.
    public RecipeCollection(JSONObject jsonObject) {
        this();
        JSONArray jsonArray = jsonObject.getJSONArray("recipes");
        for (int i = 0; i < jsonArray.length(); i++) {
            recipes.add(new Recipe(jsonArray.getJSONObject(i))); // Convert each JSONObject into a Recipe
        }
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
    
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        List<Recipe> sortedRecipes = new ArrayList<>(recipes);
        sortedRecipes.sort(Comparator.comparing(Recipe::getName));

        for (Recipe recipe : recipes) {
            JSONObject recipeJson = new JSONObject();
            recipeJson.put("name", recipe.getName());
            recipeJson.put("category", recipe.getCategory());
            jsonArray.put(recipe.toJson());
        }
        
        jsonObject.put("recipes", jsonArray);
        return jsonObject;
    }

}
