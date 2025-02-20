package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class IngredientListTest {
    private IngredientList testIngredientList;

    @BeforeEach
    void runBefore() {
        testIngredientList = new IngredientList();
    }


    @Test
    void testConstructor() {
        Set<String> ingredients = testIngredientList.getIngredients();
        
        assertTrue(ingredients.isEmpty());
        assertEquals(0, ingredients.size());
    }

    @Test
    void testAddIngredient() {
        testIngredientList.addIngredient("Milk");
        testIngredientList.addIngredient("Apple");

        Set<String> ingredients = testIngredientList.getIngredients();

        assertEquals(2, ingredients.size());
        assertTrue(ingredients.contains("Milk"));
        assertTrue(ingredients.contains("Apple"));
    }

    @Test
    void testRemoveIngredient() {
        testIngredientList.addIngredient("Cherry");
        testIngredientList.addIngredient("Egg");
        testIngredientList.addIngredient("Chicken breast");
        testIngredientList.removeIngredient("Egg");

        Set<String> ingredients = testIngredientList.getIngredients();

        assertEquals(2, ingredients.size());
        assertTrue(ingredients.contains("Cherry"));
        assertTrue(ingredients.contains("Chicken breast"));
        assertFalse(ingredients.contains("Egg"));
    }

    @Test
    void testRemoveIngredientNotInList() {
        testIngredientList.addIngredient("Spaghetti");
        testIngredientList.removeIngredient("Rice");

        Set<String> ingredients = testIngredientList.getIngredients();

        assertEquals(1, ingredients.size());
        assertTrue(ingredients.contains("Spaghetti"));
    }

    @Test
    void testHasIngredients() {
        testIngredientList.addIngredient("Potato");

        assertTrue(testIngredientList.hasIngredient("Potato"));
        assertFalse(testIngredientList.hasIngredient("Tomato"));
    }
}
