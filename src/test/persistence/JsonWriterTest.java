package persistence;

import model.*;
import persistence.*;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonWriterTest {
    private static final String TEST_FILE = "./data/testRecipeData.json";
    private static final String INVALID_FILE = "/invalid_path/invalid_file.json";
    private RecipeCollection recipes;
    GroceryList groceryList = new GroceryList();
    MealPlan mealPlan = new MealPlan();

    @BeforeEach
    void runBefore() {
        recipes = new RecipeCollection();
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
            //pass
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
            //pass
        }
    }


    @Test
    void testWriterInvalidFile() {
        assertThrows(IOException.class, () -> {
            JsonWriter writer = new JsonWriter(INVALID_FILE);
            writer.open(); 
        });
    }   
}
