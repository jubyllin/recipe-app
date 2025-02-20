package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class GroceryListTest {
    private GroceryList testGroceryList;

    @BeforeEach
    void runBefore() {
        testGroceryList = new GroceryList();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testGroceryList.getGroceryItems().size());
        assertTrue(testGroceryList.getGroceryItems().isEmpty());

    }

    @Test
    void testAddItem() {
        testGroceryList.addItem("Garlic");
        testGroceryList.addItem("Onion");
        
        Set<String> groceryItems = testGroceryList.getGroceryItems();

        assertEquals(2, groceryItems.size());
        assertTrue(groceryItems.contains("Garlic"));
        assertTrue(groceryItems.contains("Onions"));
    }
    
    @Test
    void testAddDuplicateItems() {
        testGroceryList.addItem("Apple");
        testGroceryList.addItem("Apple");

        Set<String> groceryItems = testGroceryList.getGroceryItems();

        assertEquals(1, groceryItems.size());
    }

    @Test
    void testRemoveItem() {
        testGroceryList.addItem("Ginger");
        testGroceryList.addItem("Cabbage");
        testGroceryList.removeItem("Ginger");

        Set<String> groceryItems = testGroceryList.getGroceryItems();

        assertEquals(1, groceryItems.size());
        assertTrue(groceryItems.contains("Cabbage"));
        assertFalse(groceryItems.contains("Ginger"));
    }

    @Test
    void testRemoveItemNotInList() {
        testGroceryList.addItem("Green onion");
        testGroceryList.removeItem("Ginger");

        Set<String> groceryItems = testGroceryList.getGroceryItems();

        assertEquals(1, groceryItems.size());
        assertTrue(groceryItems.contains("Green onion"));
    }

    @Test
    void testHasItem() {
        testGroceryList.addItem("Enoki mushroom");

        assertTrue(testGroceryList.hasItem("Enoki mushroom"));
        assertFalse(testGroceryList.hasItem("Tomato"));
    }

    @Test
    void testCheckItemAsBought() {
        testGroceryList.addItem("Potato");
        testGroceryList.addItem("Carrot");
        testGroceryList.checkItemAsBought("Potato");
        
        Set<String> groceryItems = testGroceryList.getGroceryItems();

        assertEquals(1, groceryItems.size());
        assertTrue(groceryItems.contains("Carrot"));
        assertFalse(groceryItems.contains("Potato"));
    }

    @Test
    void testClearList() {
        testGroceryList.addItem("Cucumber");
        testGroceryList.addItem("Garlic");
        testGroceryList.addItem("Apple");
        testGroceryList.clearList();

        assertEquals(0, testGroceryList.getGroceryItems().size());
        assertTrue(testGroceryList.getGroceryItems().isEmpty());
    }
}
