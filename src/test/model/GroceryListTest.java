package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;
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
        assertTrue(groceryItems.contains("Onion"));
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

    @Test
    void testToJsonEmptyGroceryList() {
        
        JSONObject json = testGroceryList.toJson();

        assertTrue(json.has("groceryItems"));  
        JSONArray jsonArray = json.getJSONArray("groceryItems");
        assertEquals(0, jsonArray.length());  
    }

    @Test
    void testToJsonWithItems() {
        testGroceryList.addItem("Milk");
        testGroceryList.addItem("Eggs");
        testGroceryList.addItem("Bread");

        JSONObject json = testGroceryList.toJson();
        
        assertTrue(json.has("groceryItems")); 
        JSONArray jsonArray = json.getJSONArray("groceryItems");

        assertEquals(3, jsonArray.length());  
        assertTrue(jsonArray.toList().contains("Milk"));
        assertTrue(jsonArray.toList().contains("Eggs"));
        assertTrue(jsonArray.toList().contains("Bread"));
    }

    @Test
    void testToJsonWithSpecialCharacters() {
        GroceryList groceryList = new GroceryList();
        groceryList.addItem("Apple🍎");
        groceryList.addItem("Orange Juice");

        JSONObject json = groceryList.toJson();

        JSONArray jsonArray = json.getJSONArray("groceryItems");
        assertEquals(2, jsonArray.length());
        
        assertTrue(jsonArray.toList().contains("Apple🍎"));
        assertTrue(jsonArray.toList().contains("Orange Juice"));
    }

    @Test
    void testFromJson() {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.put("Rice");
        jsonArray.put("Chicken");
        json.put("groceryItems", jsonArray);

        GroceryList loadedList = new GroceryList(json);
        Set<String> items = loadedList.getGroceryItems();

        assertEquals(2, items.size());
        assertTrue(items.contains("Rice"));
        assertTrue(items.contains("Chicken"));
    }

}
