package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.List;

public class RecipeTest {
    private Recipe testRecipe;
    
    @BeforeEach
    void runBefore() {
        testRecipe = new Recipe("French Toast", "Breakfast");
    }

    @Test
    void testAddIngredient() {
        testRecipe.addIngredient("Toast");
        testRecipe.addIngredient("Egg");
        testRecipe.addIngredient("Milk");

        Set<String> ingredients = testRecipe.getIngredients();
        assertTrue(ingredients.contains("Toast"));
        assertTrue(ingredients.contains("Egg"));
        assertTrue(ingredients.contains("Milk"));

        assertEquals("Breakfast", testRecipe.getCategory());
    }

    @Test
    void testRemoveIngredient() {
        testRecipe.addIngredient("Egg");
        testRecipe.removeIngredient("Egg");

        assertFalse(testRecipe.getIngredients().contains("Egg"));
    }

    @Test
    void testAddStep() {
        testRecipe.addStep("Mix milk, eggs, and sugar.");
        testRecipe.addStep("Soak toast in egg wash for five minutes.");
        testRecipe.addStep("Cook on pan");;

        List<String> steps = testRecipe.getSteps();
        assertEquals(3, steps.size());
        assertEquals("Mix milk, eggs, and sugar.", steps.get(0));
        assertEquals("Soak toast in egg wash for five minutes.", steps.get(1));
        assertEquals("Cook on pan", steps.get(2));    
    }

    @Test
    void testRemoveStep() {
        testRecipe.addStep("Mix milk, eggs, and sugar.");
        testRecipe.addStep("Soak toast in egg wash for five minutes.");
        testRecipe.addStep("Cook on pan.");
        testRecipe.removeStep("Cook on pan.");

        List<String> steps = testRecipe.getSteps();

        assertEquals(2, steps.size());
        assertEquals("Mix milk, eggs, and sugar.", steps.get(0));
        assertEquals("Soak toast in egg wash for five minutes.", steps.get(1));    
    }

    @Test
    void testAddImage() {
        testRecipe.addImage("frenchToast.jpg");
        testRecipe.addImage("frenchToast2.jpg");

        Set<String> images = testRecipe.getImages();

        assertEquals(2, images.size());
        assertTrue(images.contains("frenchToast.jpg"));
        assertTrue(images.contains("frenchToast2.jpg"));

    }

    @Test
    void testRemoveImage() {
        testRecipe.addImage("frenchToast.jpg");
        testRecipe.removeImage("frenchToast.jpg");

        Set<String> images = testRecipe.getImages();

        assertEquals(0, images.size());
        assertTrue(images.isEmpty());
        
        images.add("frenchToast2.jpg");
        assertEquals(1, images.size());
        assertTrue(images.contains("frenchToast2.jpg"));
        assertFalse(images.contains("frenchToast.jpg"));
    }

    @Test
    void testCanMakeRecipe() {
        IngredientList ingredientList = new IngredientList();
        ingredientList.addIngredient("Toast");
        ingredientList.addIngredient("Egg");
        ingredientList.addIngredient("Milk");

        testRecipe.addIngredient("Toast");
        testRecipe.addIngredient("Egg");
        testRecipe.addIngredient("Milk");

        assertTrue(testRecipe.canMakeRecipe(ingredientList));

        testRecipe.addIngredient("Butter");
        assertFalse(testRecipe.canMakeRecipe(ingredientList));
    }
}
