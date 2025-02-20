package model;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

//Represents a recipe with name, category, ingredients needed,  preparation steps, and images.
public class Recipe {
    private String name;                         //name of recipe
    private String category;                     //category(cuisine, meal type)
    private Map<String, Double> ingredients;     //required ingredients
    private List<String> steps;                  //list of preparation steps
    private List<String> images;                 //images of dish

    //Create a new recipe with given name and category.
    public Recipe(String name, String category) {
        // Stub
    } 

    //REQUIRES: amount > 0
    //MODIFIES: this
    //EFFECTS: Adds the ingredient to the recipe.
    public void addIngredient(String name, double amount) {
        // Stub
    }

    //MODIFIES: this
    //EFFECTS: Removes the ingredient from the recipe.
    public void removeIngredient(String name) {
        // Stub
    }


    //REQUIRES: newAmount >= 0
    //MODIFIES: this
    //EFFECTS: Updates the amount of ingredients specified.
    public void editIngredientAmount(String name, double newAmount) {
        // Stub
    }

    //MODIFIES: this
    //EFFECTS: Adds a step to the list of preparation steps.
    public void addStep(String step){
        // Stub
    }

    //MODIFIES: this
    //EFFECTS: Remove a step from the list of preparatino steps.
    public void removeStep(String step){
        // Stub
    }


    //MODIFIES: this
    //EFFECTS: Adds an image path to the list of images in the recipe.
    public void addImage(String imagePath) {
        // Stub
    }

    //MODIFIES: this
    //EFFECTS: Removes an image path to the list of images in the recipe.
    public void removeImage(int index) {
        // Stub
    }

    //EFFECTS: Checks if this recipe can be made through existing home ingredients. 
    //          Returns true if all ingredients are available, otherwise, false.
    public boolean canMakeRecipe(IngredientList ingredientList) {
        // Stub
        return false;
    }

    public String getName() {
        // Stub
        return null;
    }
    
    public String getCategory() {
        // Stub
        return null;
    }

    public Map<String, Double> getIngredients() {
        // Stub
        return null;
    }

    public List<String> getSteps() {
        // Stub
        return null;
    }

    public List<String> getImages() {
        // Stub
        return null;
    }
}
