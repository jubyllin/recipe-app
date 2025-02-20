package model;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

//Represents a weekly meal plan.
public class MealPlan {
    private Map<String, List<Recipe>> mealSchedule;

    //Creates an empty meal plan.
    public MealPlan() {
        // Stub
    }

    //MODIFIES: this
    //EFFECTS: Adds a recipe to a specific day in the meal plan.
    public void addMeal(String day, Recipe recipe) {
        // Stub
    }

    //MODIFIES: this
    //EFFECTS: Removes a specified meal from the day in the meal plan.
    public void removeSpecificMeal(String day, Recipe recipe) {
        // Stub
    }
    
    //MODIFIES: this
    //EFFECTS: Removes all meals assigned to the specified day.
    public void removeMeal(String day) {
        // Stub
    }



    //MODIFIES: this
    //EFFECTS: Reset the weekly meal plan to empty.
    public void clearMealPlan() {
        // Stub
    }

    public List<Recipe> getMeals(String day) {
        // Stub
        return null;
    }
    
    public Map<String, List<Recipe>> getMealPlan() {
        // Stub
        return null;
    }

    
}
