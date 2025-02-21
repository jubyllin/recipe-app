package ui;

import model.*;
import java.util.*;

//Recipe manager application.
public class MyRecipeApp {
    private RecipeCollection recipeCollection;
    private GroceryList groceryList;
    private MealPlan mealPlan;
    private Scanner input;

    // Initializes the appplication.
    public MyRecipeApp() {
        recipeCollection = new RecipeCollection();
        groceryList = new GroceryList();
        mealPlan = new MealPlan();
        input = new Scanner(System.in);
        runMyRecipe();
    }

    // MODIFIES: this
    // EFFECTS: Runs the main application.
    private void runMyRecipe() {
        boolean keepGoing = true;

        while (keepGoing) {
            displayMenu();
            String command = input.nextLine().trim();
            keepGoing = processCommand(command);
        }
        System.out.println("Goodbye!");
    }

    // MODIFIES: this
    // EFFECTS: Processess user commands from the menu. Returns true to continue
    // running, otherwise, false to exit the application.
    private boolean processCommand(String command) {
        executeCommand(command);
        return true; 
    }

    //MODIFIES: this
    //EFFECTS: Executes the user command by calling the appropriate method.
    private void executeCommand(String command) {
        switch (command) {
            case "1": addRecipe();
                break;
            case "2": deleteRecipe();
                break;
            case "3": addRecipeToCollection();
                break;
            case "4": viewRecipeCollection();
                break;
            case "5": addItemToGroceryList();
                break;
            case "6": markItemAsBought();
                break;
            case "7": viewGroceryList();
                break;
            case "8": addMeal();
                break;
            case "9": deleteMeal();
                break;
            case "10": viewMealPlan();
                break;
            default: handleInvalidCommand();
        }
    }

    //EFFECTS: Conducts invalid user input.
    private void handleInvalidCommand() {
        System.out.println("Invalid selection. Please try again!");
    }

    // EFFECTS: Displays the main menu options to the user.
    private void displayMenu() {
        System.out.println("\n=== My Recipe App ===");
        System.out.println("1. Add a Recipe");
        System.out.println("2. Delete a Recipe");
        System.out.println("3. Add Recipe to Collection");
        System.out.println("4. View My Recipe Collection");
        System.out.println("5. Add Item to Grocery List");
        System.out.println("6. Mark Item as Bought");
        System.out.println("7. View Grocery List");
        System.out.println("8. Add Meal to Meal Plan");
        System.out.println("9. Delete a Meal");
        System.out.println("10. View Meal Plan");
        System.out.println("11. View Meal Plan for a Specific Day");
        System.out.println("12. Exit");
        System.out.print("Select an option: ");
    }

    // MODIFIES: this
    // EFFECTS: Conducts adding a new recipe.
    private void addRecipe() {
        System.out.print("Enter recipe name: ");
        String name = input.nextLine();
        System.out.print("Enter category (cuisine, meal type): ");
        String category = input.nextLine();

        Recipe recipe = new Recipe(name, category);

        while (true) {
            System.out.print("Add an ingredient: ");
            String ingredient = input.nextLine();
            recipe.addIngredient(ingredient);
            recipeCollection.addRecipe(recipe);
            System.out.println("Recipe added successfully!");
        }

    }

    // MODIFIES: this
    // EFFECTS: Conducts deleting an existing recipe.
    private void deleteRecipe() {
        System.out.print("Enter the name of the recipe to delete: ");
        String name = input.nextLine();
        Recipe toRemove = null;
        for (Recipe recipe : recipeCollection.getRecipes()) {
            if (recipe.getName().equalsIgnoreCase(name)) {
                toRemove = recipe;
                break;
            }
        }
        if (toRemove != null) {
            recipeCollection.removeRecipe(toRemove);
            System.out.println("Recipe deleted.");
        } else {
            System.out.println("Recipe not found.");
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds the selected recipe to the recipe collection.
    private void addRecipeToCollection() {
        System.out.print("Enter the name of the recipe to add to My Recipe Collection: ");
        String name = input.nextLine();

        for (Recipe recipe : recipeCollection.getRecipes()) {
            if (recipe.getName().equalsIgnoreCase(name)) {
                System.out.println("Recipe is already in the collection.");
                return;
            }
        }

        System.out.print("Enter category for the recipe: ");
        String category = input.nextLine();
        Recipe newRecipe = new Recipe(name, category);

        while (true) {
            System.out.print("Add an ingredient (or press Enter to finish): ");
            String ingredient = input.nextLine();
            if (ingredient.isEmpty()) {
                break;
            }
            newRecipe.addIngredient(ingredient);
        }

        recipeCollection.addRecipe(newRecipe);
        System.out.println("Recipe added to collection successfully!");
    }

    // MODIFIES: this
    // EFFECT: Conducts adding a new item to the grocery list.
    private void addItemToGroceryList() {
        System.out.print("Enter item name: ");
        String item = input.nextLine();
        groceryList.addItem(item);
        System.out.println("Item added to grocery list.");
    }

    // MODIFIES: this
    // EFFECT: Conducts removing a specified item from the grocery list.
    private void markItemAsBought() {
        System.out.print("Enter the item to mark as bought: ");
        String item = input.nextLine();
        groceryList.checkItemAsBought(item);
        System.out.println("Item removed from grocery list.");
    }

    // MODIFIES: this
    // EFFECTS: Conducts adding a meal in the meal plan.
    private void addMeal() {
        System.out.print("Enter day of the week (e.g., Monday, Tuesday): ");
        String day = input.nextLine();
        System.out.print("Enter recipe name: ");
        String recipeName = input.nextLine();

        Recipe foundRecipe = null;
        for (Recipe recipe : recipeCollection.getRecipes()) {
            if (recipe.getName().equalsIgnoreCase(recipeName)) {
                foundRecipe = recipe;
                break;
            }
        }

        if (foundRecipe != null) {
            mealPlan.addMeal(day, foundRecipe);
            System.out.println("Meal added to meal plan.");
        } else {
            System.out.println("Recipe not found! Please add it first.");
        }
    }

    // MODIFIES: this
    // EFFECTS: Conducts deleting a specified meal in the meal plan.
    private void deleteMeal() {
        System.out.print("Enter the day to delete a meal from: ");
        String day = input.nextLine();
        mealPlan.removeMeal(day);
        System.out.println("Meal deleted.");
    }

    // EFFECTS: Displays all recipes in the recipe collection.
    private void viewRecipeCollection() {
        Set<Recipe> recipes = recipeCollection.getRecipes();
        if (recipes.isEmpty()) {
            System.out.println("No recipes available.");
        } else {
            System.out.println("\n=== My Recipe Collection ===");
            for (Recipe recipe : recipes) {
                System.out.println("。 " + recipe.getName() + " (" + recipe.getCategory() + ")");
            }
        }
    }

    // EFFECTS: Displays all items in the grocery list.
    private void viewGroceryList() {
        Set<String> items = groceryList.getGroceryItems();
        if (items.isEmpty()) {
            System.out.println("Grocery list is empty.");
        } else {
            System.out.println("\\n"
                    + //
                    "=== Grocery List ===");
            for (String item : items) {
                System.out.println("。 " + item);
            }
        }
    }

    // EFFECTS: Displays the current meal plan.
    private void viewMealPlan() {
        Map<String, List<Recipe>> schedule = mealPlan.getMealPlan();
        if (schedule.isEmpty()) {
            System.out.println("Meal plan is empty.");
        } else {
            System.out.println("\\n"
                    + //
                    "=== Meal Plan ===");
            for (String day : schedule.keySet()) {
                System.out.println(day + ":");
                for (Recipe recipe : schedule.get(day)) {
                    System.out.println("  。 " + recipe.getName());
                }
            }
        }
    }

}
