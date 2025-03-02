package persistence;

import model.Recipe;
import model.RecipeCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



public class JsonTest {

    @Test
    void testCheckRecipe() {
        Recipe recipe = new Recipe("Pasta", "Italian");
        checkRecipe("Pasta", "Italian", recipe);
    }

    @Test
    void testCheckRecipeList() {
        RecipeCollection expected = new RecipeCollection();
        expected.addRecipe(new Recipe("Sushi", "Japanese"));
        expected.addRecipe(new Recipe("Pizza", "Italian"));

        RecipeCollection actual = new RecipeCollection();
        actual.addRecipe(new Recipe("Sushi", "Japanese"));
        actual.addRecipe(new Recipe("Pizza", "Italian"));

        checkRecipeList(expected, actual);
    }

    private void checkRecipe(String name, String category, Recipe recipe) {
        assertEquals(name, recipe.getName());
        assertEquals(category, recipe.getCategory());
    }

    private void checkRecipeList(RecipeCollection expected, RecipeCollection actual) {
        JSONObject expectedJson = expected.toJson();
        JSONObject actualJson = actual.toJson();

        JSONArray expectedRecipes = expectedJson.getJSONArray("recipes");
        JSONArray actualRecipes = actualJson.getJSONArray("recipes");

        assertEquals(expectedRecipes.length(), actualRecipes.length());
    }
}