package persistence;

import model.RecipeCollection;

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

            RecipeCollection loadedRecipes = new RecipeCollection(jsonObject.getJSONObject("recipeCollection"));
            assertNotNull(loadedRecipes);

            assertEquals(2, loadedRecipes.getRecipes().size());
        } catch (IOException e) {
            //pass
        }
    }
}

