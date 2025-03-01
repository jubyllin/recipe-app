package test.persistence;

import model.*;
import persistence.*;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

class JsonWriterTest {
    private static final String TEST_FILE = "./data/testRecipeData.json";

    @Test
    void testWriteRecipeCollection() {
        RecipeCollection recipes = new RecipeCollection();
        recipes.addRecipe(new Recipe("Spaghetti", "Italian"));

        JsonWriter writer = new JsonWriter(TEST_FILE);
        writer.write(recipes.toJson());

        File file = new File(TEST_FILE);
        assertTrue(file.exists());  
    }
}
