package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Manages a collection of favorite recipes.
public class RecipeCollection {
    private Set<Recipe> recipes;

    //Creates an empty RecipeCollection.
    public RecipeCollection() {
        this.recipes = new HashSet<>();
    }

    //MODIFIES: this
    //EFFECTS: Adds the recipe to the collection if it does not exist.
    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    //MODIFIES: this
    //EFFECTS: Removes the recipe from the collection if it exists.
    public void removeRecipe(Recipe recipe) {
        recipes.remove(recipe);

    }


    //EFFECTS: Returns a list of recipes that include the specified name.
    public List<Recipe> searchRecipeByName(String name) {
        List<Recipe> results = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.getName().equalsIgnoreCase(name)) {
                results.add(recipe);
            }
        }
        return results;
    } 

    //EFFECTS: Returns a list of recipes that include the specified ingredient.
    public List<Recipe> searchRecipeByIngredient(String ingredient) {
        List<Recipe> results = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.getIngredients().contains(ingredient)) {
                results.add(recipe);
            }
        }
        return results;    
    }

    public Set<Recipe> getRecipes() {
        return new HashSet<>(recipes);
    }
    
}
