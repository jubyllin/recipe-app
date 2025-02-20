package model;

import java.util.HashSet;
import java.util.Set;

//Represents a collection of existing ingredients at home. 
public class IngredientList {
    private Set<String> ingredients;   //a list of ingredient names

    //Creates an empty IngredientList.
    public IngredientList() {
        this.ingredients = new HashSet<>();
    }

    //REQUIRES: name != null
    //MODIFIES: this
    //EFFECTS: Add the ingredient to the ingredient list if it does not exist.
    public void addIngredient(String name) {
        ingredients.add(name);
    }

    //REQUIRES: name != null
    //MODIFIES: this
    //EFFECTS: Removes the ingredient from the ingredient list if it exists.
    public void removeIngredient(String name) {
        ingredients.remove(name);
    }

    //REQUIRES: name != null
    //EFFECTS: Checks if the ingredient is in the list. Returns true if the ingredient exists, 
    //          otherwise, false.
    public boolean hasIngredient(String name) {
        return ingredients.contains(name);
    }


    public Set<String> getIngredients() {
        return new HashSet<>(ingredients);
    } 
}
