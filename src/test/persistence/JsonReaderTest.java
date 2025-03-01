package persistence;

import model.RecipeCollection;
import model.GroceryList;
import model.MealPlan;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {
    private static final String TEST_FILE = "./data/testRecipeData.json";

    @Test
    void testReadRecipeCollection() {
        JsonReader reader = new JsonReader(TEST_FILE);
        try {
            JSONObject jsonObject = reader.read();
            assertNotNull(jsonObject);

            RecipeCollection loadedRecipes = new RecipeCollection(jsonObject.getJSONObject("recipes"));
            assertEquals(1, loadedRecipes.getRecipes().size());
        } catch (IOException e) {
            fail("Could not read file!");
        }
    }
}

