package model;

import java.util.Set;

//Represents a shopping list of ingredients
public class GroceryList {
    private Set<String> groceryItems;   //a list of item names

    //Constructs an empty GroceryList.
    public GroceryList() {
        // Stub
    }
    
    //MODIFIES: this
    //EFFECTS: Adds the item to the list if it does not exist.
    public void addItem(String item) {
        // Stub
    }

    //MODIFIES: this
    //EFFECTS: Removes the item from the grocery list if it exists.
    public void removeItem(String item) {
        // Stub
    }

    //EFFECTS: Check if the grocery list contains the specified item. Returns true
    //          if it exists, otherwise, false.
    public boolean hasItem(String item) {
        // Stub
        return false;
    }

    //REQUIRES: item != null && item is in the grocery list
    //EFFECTS: Removes the item from the grocery list.
    public void checkItemAsBought(String item) {
        // Stub
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Removes all item from grocery list.
    public void clearList() {
        // Stub
    }

    public Set<String> getGroceryItems() {
        // Stub
        return null;
    }
}
