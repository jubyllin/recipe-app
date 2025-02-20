package model;

import java.util.Set;

//Represents a collection of existing ingredients at home. 
public class IngredientList {
    private Set<String> ingredients;   //a list of ingredient names

    //Create an empty IngredientList.
    public IngredientList() {
        // Stub
    }

    //REQUIRES: name != null
    //MODIFIES: this
    //EFFECTS: Add the ingredient to the ingredient list if it does not exist.
    public void addIngredient() {
        // Stub
    }

    //REQUIRES: name != null
    //MODIFIES: this
    //EFFECTS: Removes the ingredient from the ingredient list if it exists.
    public void removeIngredient() {
        // Stub
    }

    //REQUIRES: name != null
    //EFFECTS: Checks if the ingredient is in the list. Returns true if the ingredient exists, 
    //          otherwise, false.
    public void hasIngredient() {
        // Stub
    }


    public Set<String> getIngredients() {
        // Stub
        return null;
    } 
}
