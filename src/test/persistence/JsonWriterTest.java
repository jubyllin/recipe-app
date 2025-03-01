package persistence;

import model.*;
import persistence.*;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class JsonWriterTest {
    private static final String TEST_FILE = "./data/testRecipeData.json";
    RecipeCollection recipes = new RecipeCollection();
    GroceryList groceryList = new GroceryList();
    MealPlan mealPlan = new MealPlan();


    @Test
    void testWriteEmptyRecipeCollection() {
        try {
            RecipeCollection recipes = new RecipeCollection();
            JsonWriter writer = new JsonWriter("./data/testWriteEmptyRecipeCollection.json");
            writer.open();
            writer.write(recipes.toJson());
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriteEmptyRecipeCollection.json");
            RecipeCollection loadedRecipes = reader.read();
            assertEquals(0, loadedRecipes.getRecipes().size());
        } catch (IOException e) {
            fail("Exception should not be thrown");
        }
    }
    @Test
    void testWriteRecipeCollection() {
        recipes.addRecipe(new Recipe("Spaghetti", "Italian"));
        recipes.addRecipe(new Recipe("Sushi", "Japanese"));

        JsonWriter writer = new JsonWriter(TEST_FILE);
        
        try {
            writer.open();
            writer.write(recipes.toJson());
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriteRecipeCollectionWithRecipes.json");
            RecipeCollection loadedRecipes = reader.read();
            assertEquals(2, loadedRecipes.getRecipes().size());
        } catch (IOException e) {
        fail("Exception should not be thrown");
        }
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/invalid\0file.json"); // Invalid filename
            writer.open();
            fail("Expected IOException was not thrown");
        } catch (IOException e) {
        }
    }   
}
