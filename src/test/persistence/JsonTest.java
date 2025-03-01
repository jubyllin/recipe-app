package persistence;

import model.Recipe;
import model.RecipeCollection;
import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkRecipe(String name, String category, Recipe recipe) {
        assertEquals(name, recipe.getName());
        assertEquals(category, recipe.getCategory());
        }
        
    protected void checkRecipeList(RecipeCollection expected, RecipeCollection actual) {
        JSONArray expectedRecipes = expected.toJson().getJSONArray("recipes");
        JSONArray actualRecipes = actual.toJson().getJSONArray("recipes");
        
        assertEquals(expectedRecipes.length(), actualRecipes.length());
    }

}

