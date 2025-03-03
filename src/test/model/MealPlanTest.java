package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MealPlanTest {
    private MealPlan testMealPlan;
    private Recipe recipe1;
    private Recipe recipe2;
    private Recipe recipe3;

    @BeforeEach
    void runBefore() {
        testMealPlan = new MealPlan();

        recipe1 = new Recipe("Basque cheesecake", "Snack");
        recipe2 = new Recipe("Curry", "Japanese");
        recipe3 = new Recipe("Acai bowl", "Snack");
    }

    @Test
    void testConstructor() {
        assertEquals(0, testMealPlan.getMealPlan().size());
        assertTrue(testMealPlan.getMealPlan().isEmpty());
    }

    @Test
    void testAddMeal() {
        testMealPlan.addMeal("Monday", recipe1);
        testMealPlan.addMeal("Tuesday", recipe2);

        Map<String, List<Recipe>> schedule = testMealPlan.getMealPlan();

        assertEquals(2, schedule.size());
        assertTrue(schedule.containsKey("Monday"));
        assertTrue(schedule.containsKey("Tuesday"));
    }

    @Test
    void testAddMutipleMealsPerDay() {
        testMealPlan.addMeal("Friday", recipe2);
        testMealPlan.addMeal("Friday", recipe3);

        List<Recipe> fridayMeals = testMealPlan.getMeals("Friday");

        assertEquals(2, fridayMeals.size());
        assertTrue(fridayMeals.contains(recipe2));
        assertTrue(fridayMeals.contains(recipe3));
    }

    @Test
    void testRemoveSpecificMeal() {
        testMealPlan.addMeal("Sunday", recipe1);
        testMealPlan.addMeal("Sunday", recipe3);
        testMealPlan.removeSpecificMeal("Sunday", recipe1);

        List<Recipe> sundayMeals = testMealPlan.getMeals("Sunday");

        assertEquals(1, sundayMeals.size());
        assertTrue(sundayMeals.contains(recipe3));
        assertFalse(sundayMeals.contains(recipe1));
    }

    @Test
    void testRemoveSpecificMealFromEmptyDay() {
        testMealPlan.removeSpecificMeal("Thursday", recipe1); 
        assertTrue(testMealPlan.getMealPlan().isEmpty()); 
    }


    @Test
    void testRemoveMeal() {
        testMealPlan.addMeal("Monday", recipe1);
        testMealPlan.addMeal("Monday", recipe2);
        testMealPlan.removeMeal("Monday");

        List<Recipe> mondayMeals = testMealPlan.getMeals("Monday");

        assertEquals(0, mondayMeals.size());
        assertFalse(mondayMeals.contains(recipe1));
        assertFalse(mondayMeals.contains(recipe2));
    }

    @Test
    void testClearMealPlan() {
        testMealPlan.addMeal("Wednesday", recipe3);
        testMealPlan.addMeal("Thursday", recipe1);
        testMealPlan.addMeal("Thursday", recipe2);
        testMealPlan.addMeal("Friday", recipe3);
        testMealPlan.clearMealPlan();

        assertEquals(0, testMealPlan.getMealPlan().size());
        assertTrue(testMealPlan.getMealPlan().isEmpty());
    }

    @Test
    void testToJsonEmptyMealPlan() {
        JSONObject json = testMealPlan.toJson();

        assertTrue(json.has("mealPlan"));  
        JSONObject schedule = json.getJSONObject("mealPlan");
        assertEquals(0, schedule.length()); 
    }

    @Test
    void testToJsonSingleDaySingleRecipe() {
        testMealPlan.addMeal("Monday", new Recipe("Hamburger", "American")); 
    
        JSONObject json = testMealPlan.toJson();
        assertTrue(json.has("mealPlan"));
    
        JSONObject schedule = json.getJSONObject("mealPlan");
        assertTrue(schedule.has("Monday"));
    
        JSONArray mondayRecipes = schedule.getJSONArray("Monday");
        assertEquals(1, mondayRecipes.length());
    
        JSONObject recipeJson = mondayRecipes.getJSONObject(0);
        assertEquals("Hamburger", recipeJson.getString("name"));
        assertEquals("American", recipeJson.getString("category"));
    }
    
    @Test
    void testToJsonMultipleDaysMultipleRecipes() {
        testMealPlan.addMeal("Monday", new Recipe("Sandwich", "Breakfast"));
        testMealPlan.addMeal("Monday", new Recipe("Dumplings", "Chinese"));
        testMealPlan.addMeal("Tuesday", new Recipe("Green curry", "Thai"));
    
        JSONObject json = testMealPlan.toJson();
        assertTrue(json.has("mealPlan"));
    
        JSONObject schedule = json.getJSONObject("mealPlan");
    
        assertTrue(schedule.has("Monday"));
        JSONArray mondayRecipes = schedule.getJSONArray("Monday");
        assertEquals(2, mondayRecipes.length());
        assertEquals("Sandwich", mondayRecipes.getJSONObject(0).getString("name"));
        assertEquals("Dumplings", mondayRecipes.getJSONObject(1).getString("name"));
    
        assertTrue(schedule.has("Tuesday"));
        JSONArray tuesdayRecipes = schedule.getJSONArray("Tuesday");
        assertEquals(1, tuesdayRecipes.length());
        assertEquals("Green curry", tuesdayRecipes.getJSONObject(0).getString("name"));
    }

    @Test
    void testLoadMealPlanFromJson() {
        JSONObject recipeJson1 = new JSONObject();
        recipeJson1.put("name", "Pasta");
        recipeJson1.put("category", "Dinner");
        recipeJson1.put("ingredients", new JSONArray(Arrays.asList("Pasta", "Tomato Sauce", "Cheese")));
        recipeJson1.put("steps", new JSONArray(Arrays.asList("Boil pasta", "Add sauce", "Sprinkle cheese")));

        JSONObject recipeJson2 = new JSONObject();
        recipeJson2.put("name", "Salad");
        recipeJson2.put("category", "Lunch");
        recipeJson2.put("ingredients", new JSONArray(Arrays.asList("Lettuce", "Tomato", "Dressing")));
        recipeJson2.put("steps", new JSONArray(Arrays.asList("Chop lettuce", "Add tomato", "Mix with dressing")));

        JSONObject jsonMealPlan = new JSONObject();
        jsonMealPlan.put("Monday", new JSONArray(Arrays.asList(recipeJson1)));
        jsonMealPlan.put("Tuesday", new JSONArray(Arrays.asList(recipeJson2)));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mealPlan", jsonMealPlan);

        MealPlan loadedMealPlan = new MealPlan(jsonObject);

        assertTrue(loadedMealPlan.getMealPlan().containsKey("Monday"));
        assertTrue(loadedMealPlan.getMealPlan().containsKey("Tuesday"));
        assertEquals(1, loadedMealPlan.getMealPlan().get("Monday").size());
        assertEquals(1, loadedMealPlan.getMealPlan().get("Tuesday").size());

        Recipe loadedRecipe1 = loadedMealPlan.getMealPlan().get("Monday").get(0);
        assertEquals("Pasta", loadedRecipe1.getName());
        assertEquals("Dinner", loadedRecipe1.getCategory());
        assertTrue(loadedRecipe1.getIngredients().contains("Pasta"));
        assertTrue(loadedRecipe1.getIngredients().contains("Tomato Sauce"));
        assertTrue(loadedRecipe1.getSteps().contains("Boil pasta"));

        Recipe loadedRecipe2 = loadedMealPlan.getMealPlan().get("Tuesday").get(0);
        assertEquals("Salad", loadedRecipe2.getName());
        assertEquals("Lunch", loadedRecipe2.getCategory());
        assertTrue(loadedRecipe2.getIngredients().contains("Lettuce"));
        assertTrue(loadedRecipe2.getIngredients().contains("Tomato"));
        assertTrue(loadedRecipe2.getSteps().contains("Chop lettuce"));
    }

    @Test
    void testLoadEmptyMealPlanFromJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mealPlan", new JSONObject()); 

        MealPlan loadedMealPlan = new MealPlan(jsonObject);

        assertTrue(loadedMealPlan.getMealPlan().isEmpty());
    }

}
