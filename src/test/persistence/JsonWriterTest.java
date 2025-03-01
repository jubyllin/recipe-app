package persistence;

import model.*;
import persistence.*;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileNotFoundException;

class JsonWriterTest {
    private static final String TEST_FILE = "./data/testRecipeData.json";

    @Test
    void testWriteRecipeCollection() {
        RecipeCollection recipes = new RecipeCollection();
        recipes.addRecipe(new Recipe("Spaghetti", "Italian"));

        GroceryList groceryList = new GroceryList();
        groceryList.addItem("Tomatoes");
        groceryList.addItem("Garlic");

        MealPlan mealPlan = new MealPlan();
        mealPlan.addMeal("Friday", new Recipe("Spaghetti", "Italian"));

        JsonWriter writer = new JsonWriter(TEST_FILE);
        
        try {
            writer.open();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("recipes", recipes.toJson());
            jsonObject.put("groceryList", groceryList.toJson());
            jsonObject.put("mealPlan", mealPlan.toJson());

            writer.write(jsonObject);
            writer.close();
        } catch (FileNotFoundException e) {
            fail("File not found!");
        }
    }
}
