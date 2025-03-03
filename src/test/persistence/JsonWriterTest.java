package persistence;

import model.*;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonWriterTest {
    private static final String TEST_FILE = "./data/testRecipeData.json";
    private RecipeCollection recipes;
    GroceryList groceryList = new GroceryList();
    MealPlan mealPlan = new MealPlan();

    @BeforeEach
    void runBefore() {
        recipes = new RecipeCollection();
        try {
            Files.deleteIfExists(Paths.get(TEST_FILE));
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriteEmptyRecipeCollection() {
        try {
            JsonWriter writer = new JsonWriter(TEST_FILE);
            writer.open();
            writer.write(recipes.toJson());
            writer.close();

            String content = new String(Files.readAllBytes(Paths.get(TEST_FILE)));
            JSONObject json = new JSONObject(content);

            assertTrue(json.has("recipes"));
            assertEquals(0, json.getJSONArray("recipes").length());

        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriteRecipeCollectionWithRecipes() {
        recipes.addRecipe(new Recipe("Spaghetti", "Italian"));
        recipes.addRecipe(new Recipe("Sushi", "Japanese"));

        try {
            JsonWriter writer = new JsonWriter(TEST_FILE);
            writer.open();
            writer.write(recipes.toJson());
            writer.close();

            String content = new String(Files.readAllBytes(Paths.get(TEST_FILE)));
            JSONObject json = new JSONObject(content);

            assertTrue(json.has("recipes"));
            assertEquals(2, json.getJSONArray("recipes").length());

        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriteToInvalidFile() {
        String invalidFile = "/invalid_path/test.json";
        JsonWriter writer = new JsonWriter(invalidFile);

        JSONObject jsonObject = new JSONObject();

        assertThrows(FileNotFoundException.class, () -> writer.write(jsonObject));
    }

}
