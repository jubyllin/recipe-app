package model;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

import persistence.Writable;


//Represents a recipe with name, category, ingredients needed,  preparation steps, and images.
public class Recipe implements Writable{
    private String name;                         //name of recipe
    private String category;                     //category(cuisine, meal type)
    private Set<String> ingredients;             //required ingredients
    private List<String> steps;                  //list of preparation steps
    private Set<String> images;                 //images of dish

    //Create a new recipe with given name and category.
    public Recipe(String name, String category) {
        this.name = name;
        this.category = category;
        this.ingredients = new HashSet<>();
        this.steps = new ArrayList<>();
        this.images = new HashSet<>();
    } 

    //REQUIRES: amount > 0
    //MODIFIES: this
    //EFFECTS: Adds the ingredient to the recipe.
    public void addIngredient(String name) {
        ingredients.add(name);
    }

    //MODIFIES: this
    //EFFECTS: Removes the ingredient from the recipe.
    public void removeIngredient(String name) {
        ingredients.remove(name);
    }


    //MODIFIES: this
    //EFFECTS: Adds a step to the list of preparation steps.
    public void addStep(String step) {
        steps.add(step);
    }

    //MODIFIES: this
    //EFFECTS: Remove a step from the list of preparatino steps.
    public void removeStep(String step) {
        steps.remove(step);
    }



    //MODIFIES: this
    //EFFECTS: Adds an image path to the list of images in the recipe.
    public void addImage(String imagePath) {
        images.add(imagePath);
    }

    //MODIFIES: this
    //EFFECTS: Removes an image path to the list of images in the recipe.
    public void removeImage(String imagePath) {
        images.remove(imagePath);
    }

    public String getName() {
        return name;
    }
    
    public String getCategory() {
        return category;
    }

    public Set<String> getIngredients() {
        return new HashSet<>(ingredients);
    }

    public List<String> getSteps() {
        return new ArrayList<>(steps);
    }

    public Set<String> getImages() {
        return new HashSet<>(images);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("category", category);
        json.put("ingredients", ingredients);
        json.put("steps", steps);
        json.put("images", images);
        return json;
    }
}
