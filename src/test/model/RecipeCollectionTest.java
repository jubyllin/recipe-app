package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class RecipeCollectionTest {
    private RecipeCollection testrecipecollection;
    private Recipe recipe1;
    private Recipe recipe2;

    @BeforeEach
    void runBefore() {
        testrecipecollection = new RecipeCollection();
        recipe1 = new Recipe("Pancake", "Breakfast");
        recipe2 = new Recipe("Mapo tofu", "Chinese");
    }

    @Test
    void testConstructor() {
        Set<Recipe> recipes = testrecipecollection.getRecipes();

        assertEquals(0, recipes.size());
        assertTrue(recipes.isEmpty());
    }

    @Test
    void testAddRecipe() {
        testrecipecollection.addRecipe(recipe1);
        testrecipecollection.addRecipe(recipe2);
        
        Set<Recipe> recipes = testrecipecollection.getRecipes();

        assertEquals(2, recipes.size());
        assertTrue(recipes.contains(recipe1));
        assertTrue(recipes.contains(recipe2));
    }

    @Test
    void testDuplicateRecipe() {
        testrecipecollection.addRecipe(recipe1);
        testrecipecollection.addRecipe(recipe1);

        Set<Recipe> recipes = testrecipecollection.getRecipes();

        assertEquals(1, recipes.size());
    }

    @Test
    void testRemoveRecipe() {
        testrecipecollection.addRecipe(recipe1);
        testrecipecollection.addRecipe(recipe2);    
        testrecipecollection.removeRecipe(recipe1);

        Set<Recipe> recipes = testrecipecollection.getRecipes();

        assertEquals(1, recipes.size());
        assertTrue(recipes.contains(recipe2));
        assertFalse(recipes.contains(recipe1));
    }

    @Test
    void testToJsonEmptyRecipeCollection() {
        JSONObject json = testrecipecollection.toJson();

        assertTrue(json.has("recipes"));  
        JSONArray jsonArray = json.getJSONArray("recipes");
        assertEquals(0, jsonArray.length());  
    }

    @Test
    void testToJsonWithRecipes() {
        testrecipecollection.addRecipe(recipe1);
        testrecipecollection.addRecipe(recipe2);
    
        JSONObject json = testrecipecollection.toJson();
    
        assertTrue(json.has("recipes"));  
    
        JSONArray jsonArray = json.getJSONArray("recipes");
        assertEquals(2, jsonArray.length()); 
    
        JSONObject recipe1 = jsonArray.getJSONObject(0);
        assertEquals("Pancake", recipe1.getString("name"));
        assertEquals("Breakfast", recipe1.getString("category"));
    
        JSONObject recipe2 = jsonArray.getJSONObject(1);
        assertEquals("Mapo tofu", recipe2.getString("name"));
        assertEquals("Chinese", recipe2.getString("category"));
    }

    @Test
    void testRecipeCollectionFromEmptyJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("recipes", new JSONArray()); 

        RecipeCollection collection = new RecipeCollection(jsonObject);
        assertTrue(collection.getRecipes().isEmpty()); 
    }

    @Test
    void testRecipeCollectionFromJson() {
        JSONObject jsonObject = new JSONObject();
        JSONArray recipesArray = new JSONArray();
        
        JSONObject recipe1 = new JSONObject();
        recipe1.put("name", "Pizza");
        recipe1.put("category", "Italian");

        JSONObject recipe2 = new JSONObject();
        recipe2.put("name", "Sushi");
        recipe2.put("category", "Japanese");

        recipesArray.put(recipe1);
        recipesArray.put(recipe2);
        jsonObject.put("recipes", recipesArray);

        RecipeCollection collection = new RecipeCollection(jsonObject);
        Set<Recipe> recipes = collection.getRecipes();

        assertEquals(2, recipes.size());

        // Convert to List of Strings for comparison
        Set<String> actualRecipes = new HashSet<>();
        for (Recipe r : recipes) {
            actualRecipes.add(r.getName() + ":" + r.getCategory());
        }

        // Expected values
        Set<String> expectedRecipes = new HashSet<>();
        expectedRecipes.add("Pizza:Italian");
        expectedRecipes.add("Sushi:Japanese");

        assertEquals(expectedRecipes, actualRecipes);
    }

}
