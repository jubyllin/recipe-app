package persistence;

import model.RecipeCollection;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {
    private static final String TEST_FILE = "./data/testRecipeData.json";

    @Test
    void testReadRecipeCollection() {
        JsonReader reader = new JsonReader(TEST_FILE);
        try {
            RecipeCollection collection = reader.read();
            assertNotNull(collection);

            RecipeCollection loadedRecipes = collection;
            assertEquals(2, loadedRecipes.getRecipes().size());
        } catch (IOException e) {
            //pass
        }
    }
}

