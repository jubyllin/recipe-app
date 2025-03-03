package model;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

//Represents a shopping list of ingredients
public class GroceryList implements Writable {
    private Set<String> groceryItems;   //a list of item names

    //Constructs an empty GroceryList.
    public GroceryList() {
        this.groceryItems = new HashSet<>();
    }

    //Constructs a GroceryList that accepts a JSONObject.
    public GroceryList(JSONObject jsonObject) {
        this();
        JSONArray jsonArray = jsonObject.getJSONArray("groceryItems"); 
        for (int i = 0; i < jsonArray.length(); i++) {
            groceryItems.add(jsonArray.getString(i));
        }
    }
    
    
    //MODIFIES: this
    //EFFECTS: Adds the item to the list if it does not exist.
    public void addItem(String item) {
        groceryItems.add(item);
    }

    //MODIFIES: this
    //EFFECTS: Removes the item from the grocery list if it exists.
    public void removeItem(String item) {
        groceryItems.remove(item);
    }

    //EFFECTS: Check if the grocery list contains the specified item. Returns true
    //          if it exists, otherwise, false.
    public boolean hasItem(String item) {
        return groceryItems.contains(item);
    }

    //REQUIRES: item != null && item is in the grocery list
    //EFFECTS: Removes the item from the grocery list.
    public void checkItemAsBought(String item) {
        groceryItems.remove(item);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS: Removes all item from grocery list.
    public void clearList() {
        groceryItems.clear();
    }

    public Set<String> getGroceryItems() {
        return new HashSet<>(groceryItems);
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("groceryItems",  new JSONArray(groceryItems));
        return jsonObject;
    }
}
