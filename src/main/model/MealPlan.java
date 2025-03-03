package model;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

//Represents a weekly meal plan.
public class MealPlan implements Writable {
    private Map<String, List<Recipe>> mealPlan;

    //Creates an empty meal plan.
    public MealPlan() {
        this.mealPlan = new HashMap<>();
    }

    //Create a meal plan that accepts a JSONObject.
    public MealPlan(JSONObject jsonObject) {
        this();
        JSONObject jsonMealPlan = jsonObject.getJSONObject("mealPlan"); 
        for (String day : jsonMealPlan.keySet()) {
            JSONArray recipesArray = jsonMealPlan.getJSONArray(day);
            List<Recipe> recipes = new ArrayList<>();
            for (int i = 0; i < recipesArray.length(); i++) {
                recipes.add(new Recipe(recipesArray.getJSONObject(i)));
            }
            mealPlan.put(day, recipes);
        }
    }
    

    //MODIFIES: this
    //EFFECTS: Adds a recipe to a specific day in the meal plan.
    public void addMeal(String day, Recipe recipe) {
        if (!mealPlan.containsKey(day)) {
            mealPlan.put(day, new ArrayList<>());
        }
        mealPlan.get(day).add(recipe);
    }

    //MODIFIES: this
    //EFFECTS: Removes a specified meal from the day in the meal plan.
    public void removeSpecificMeal(String day, Recipe recipe) {
        if (mealPlan.containsKey(day)) {
            mealPlan.get(day).remove(recipe);
        }
    }
        
    //MODIFIES: this
    //EFFECTS: Removes all meals assigned to the specified day.
    public void removeMeal(String day) {
        mealPlan.remove(day);
    }



    //MODIFIES: this
    //EFFECTS: Reset the weekly meal plan to empty.
    public void clearMealPlan() {
        mealPlan.clear();
    }

    public List<Recipe> getMeals(String day) {
        if (mealPlan.containsKey(day)) {
            return mealPlan.get(day);
        } else {
            return new ArrayList<>();
        }
    }
    
    public Map<String, List<Recipe>> getMealPlan() {
        return new HashMap<>(mealPlan);
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonMealPlan = new JSONObject();
        
        for (String day : mealPlan.keySet()) {
            JSONArray recipesArray = new JSONArray();
            for (Recipe recipe : mealPlan.get(day)) {
                recipesArray.put(recipe.toJson());
            }
            jsonMealPlan.put(day, recipesArray);
        }
    
        jsonObject.put("mealPlan", jsonMealPlan);    
        return jsonObject;
    }
}
