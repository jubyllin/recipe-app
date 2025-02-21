package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class RecipeCollectionTest {
    private RecipeCollection testrecipecollection;
    private Recipe recipe1;
    private Recipe recipe2;

    @BeforeEach
    void runBefore() {
        testrecipecollection = new RecipeCollection();
        recipe1 = new Recipe("Pancake", "Breakfast");
        recipe2 = new Recipe("Mapo tofu", "Chinese");
    }

    @Test
    void testConstructor() {
        Set<Recipe> recipes = testrecipecollection.getRecipes();

        assertEquals(0, recipes.size());
        assertTrue(recipes.isEmpty());
    }

    @Test
    void testAddRecipe() {
        testrecipecollection.addRecipe(recipe1);
        testrecipecollection.addRecipe(recipe2);
        
        Set<Recipe> recipes = testrecipecollection.getRecipes();

        assertEquals(2, recipes.size());
        assertTrue(recipes.contains(recipe1));
        assertTrue(recipes.contains(recipe2));
    }

    @Test
    void testDuplicateRecipe() {
        testrecipecollection.addRecipe(recipe1);
        testrecipecollection.addRecipe(recipe1);

        Set<Recipe> recipes = testrecipecollection.getRecipes();

        assertEquals(1, recipes.size());
    }

    @Test
    void testRemoveRecipe() {
        testrecipecollection.addRecipe(recipe1);
        testrecipecollection.addRecipe(recipe2);    
        testrecipecollection.removeRecipe(recipe1);

        Set<Recipe> recipes = testrecipecollection.getRecipes();

        assertEquals(1, recipes.size());
        assertTrue(recipes.contains(recipe2));
        assertFalse(recipes.contains(recipe1));
    }
}
