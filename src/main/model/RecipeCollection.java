package model;

import java.util.List;
import java.util.Set;

//Manages a collection of favorite recipes.
public class RecipeCollection {
    private Set<Recipe> recipes;

    //Creates an empty RecipeCollection.
    public RecipeCollection() {
        // Stub
    }

    //MODIFIES: this
    //EFFECTS: Adds the recipe to the collection if it does not exist.
    public void addRecipe(String name) {
        // Stub
    }

    //MODIFIES: this
    //EFFECTS: Removes the recipe from the collection if it exists.
    public void removeRecipe(String name) {
        // Stub

    }


    //EFFECTS: Returns a list of recipes that include the specified name.
    public List<Recipe> searchRecipeByName(String name) {
        // Stub
        return null;

    } 

    //EFFECTS: Returns a list of recipes that include the specified ingredient.
    public List<Recipe> searchRecipeByIngredient(String ingredient) {
        // Stub
        return null;
    }

    public Set<Recipe> getRecipes() {
        // Stub
        return null;
    }
    
}
