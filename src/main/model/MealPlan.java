package model;

import java.util.Map;

//Represents a weekly meal plan.
public class MealPlan {
    private Map<String, Recipe> mealSchedule;

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
    //EFFECTS: Removes the meal assigned to the specified day.
    public void removeMeal(String day) {
        // Stub
    }

    //MODIFIES: this
    //EFFECTS: Reset the weekly meal plan to empty.
    public void clearMealPlan() {
        // Stub
    }

    public Recipe getMeal(String day) {
        // Stub
        return null;
    }
    
    public Map<String, Recipe> getMealPlan() {
        // Stub
        return null;
    }

    
}
