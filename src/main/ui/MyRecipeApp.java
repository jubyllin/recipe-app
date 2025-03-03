package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import org.json.JSONObject;

//Recipe manager application.
public class MyRecipeApp {
    private RecipeCollection recipeCollection;
    private GroceryList groceryList;
    private MealPlan mealPlan;
    private Scanner input;
    private static final String SAVE_FILE = "./data/recipeData.json";

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
        System.out.println("Welcome to My Recipe App!");
        System.out.print("Do you want to load saved data? (yes/no) ");
        String response = input.nextLine().trim().toLowerCase();

        if (response.equals("yes")) {
            loadData();
        }

        boolean keepGoing = true;
        while (keepGoing) {
            displayMenu();
            String command = input.nextLine().trim();
            keepGoing = processCommand(command);
        }

        System.out.print("Do you want to save your data before exiting? (yes/no) ");
        response = input.nextLine().trim().toLowerCase();

        if (response.equals("yes")) {
            saveData();
        }

        System.out.println("Goodbye!");
    }

    // MODIFIES: this
    // EFFECTS: Processess user commands from the menu. Returns true to continue
    // running, otherwise, false to exit the application.
    private boolean processCommand(String command) {
        if (command.equals("11")) {
            return false;
        }
        executeCommand(command);
        return true;
    }

    // MODIFIES: this
    // EFFECTS: Executes the user command by calling the appropriate method.
    private void executeCommand(String command) {
        switch (command) {
            case "1": addRecipe();
                break;
            case "2": deleteRecipe();
                break;
            case "3": viewSelectedRecipe();
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
            case "11": exitApp();
        }
    }

    // EFFECTS: Prints a goodbye message and exits the application.
    private void exitApp() {
        System.out.println("Goodbye;)");
    }

    // EFFECTS: Displays the main menu options to the user.
    private void displayMenu() {
        System.out.println("\n✩✩✩✩✩ My Recipe App ✩✩✩✩✩");
        System.out.println("。1。 Add a Recipe");
        System.out.println("。2。 Delete a Recipe");
        System.out.println("。3。 View a Recipe's Details");
        System.out.println("。4。 View My Recipe Collection");
        System.out.println("。5。 Add Item to Grocery List");
        System.out.println("。6。 Mark Item as Bought");
        System.out.println("。7。 View Grocery List");
        System.out.println("。8。 Add Meal to Meal Plan");
        System.out.println("。9。 Delete a Meal");
        System.out.println("。10。 View Meal Plan");
        System.out.println("。11。 Exit");
        System.out.print("Select an option: ");
    }

    // MODIFIES: this
    // EFFECTS: Conducts adding a new recipe.
    private void addRecipe() {
        Recipe recipe = createRecipe();
        addIngredients(recipe);
        addSteps(recipe);

        recipeCollection.addRecipe(recipe);
        System.out.println("Recipe added successfully!");
    }

    // EFFECTS: Creates and returns a new Recipe from the user input.
    private Recipe createRecipe() {
        System.out.print("Enter recipe name: ");
        String name = input.nextLine().trim();

        System.out.print("Enter category (cuisine, meal type): ");
        String category = input.nextLine().trim();

        return new Recipe(name, category);
    }

    // MODIFIES: this
    // EFFECTS: Enables user to input ingredients for a recipe.
    private void addIngredients(Recipe recipe) {
        String ingredient;
        boolean firstInput = true;
        do {
            if (firstInput) {
                System.out.print("Add an ingredient (or press Enter to finish): ");
                firstInput = false;
            }
            ingredient = input.nextLine().trim();
            if (!ingredient.isEmpty()) {
                recipe.addIngredient(ingredient);
                System.out.print("Add another ingredient (or press Enter to finish): ");
            }
        } while (!ingredient.isEmpty());
    }

    // MODIFIES: this
    // EFFECTS: Enables user to input steps for a recipe.
    private void addSteps(Recipe recipe) {
        String step;
        boolean firstStepInput = true;
        do {
            if (firstStepInput) {
                System.out.print("Add a preparation step (or press Enter to finish): ");
                firstStepInput = false;
            }
            step = input.nextLine().trim();
            if (!step.isEmpty()) {
                recipe.addStep(step);
                System.out.print("Add another step (or press Enter to finish): ");
            }
        } while (!step.isEmpty());
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
    // EFFECT: Conducts adding a new item to the grocery list.
    private void addItemToGroceryList() {
        System.out.print("Enter grocery items (comma-separated, or press Enter to finish): ");
        String itemsInput = input.nextLine().trim();

        if (!itemsInput.isEmpty()) {
            String[] items = itemsInput.split(",");
            for (String item : items) {
                groceryList.addItem(item.trim());
            }
            System.out.println("Items added successfully!");
        }
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

    // EFFECTS: Allows user to select a recipe and displays its details.
    private void viewSelectedRecipe() {
        if (recipeCollection.getRecipes().isEmpty()) {
            System.out.println("No recipes available.");
            return;
        }

        System.out.println("Select a recipe:");
        List<Recipe> recipes = new ArrayList<>(recipeCollection.getRecipes());

        for (int i = 0; i < recipes.size(); i++) {
            System.out.println((i + 1) + ". " + recipes.get(i).getName());
        }

        System.out.print("Enter the number of the recipe: ");
        try {
            int choice = Integer.parseInt(input.nextLine().trim());
            if (choice >= 1 && choice <= recipes.size()) {
                Recipe selectedRecipe = recipes.get(choice - 1);
                System.out.println();
                displayRecipeDetails(selectedRecipe);
            } else {
                System.out.println("Invalid selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    // EFFECTS: Displays the selected recipe with its details, including,
    // name, category, ingredients, and steps.
    private void displayRecipeDetails(Recipe recipe) {
        System.out.println("🥘 Recipe Details 🥘");
        System.out.println("Name: " + recipe.getName());
        System.out.println("Category: " + recipe.getCategory());

        System.out.println("\nIngredients:");
        for (String ingredient : recipe.getIngredients()) {
            System.out.println("‣ " + ingredient);
        }

        System.out.println("\nSteps:");
        int stepNumber = 1;
        for (String step : recipe.getSteps()) {
            System.out.println(stepNumber++ + ". " + step);
        }

        if (!recipe.getImages().isEmpty()) {
            System.out.println("\nImages:");
            for (String image : recipe.getImages()) {
                System.out.println("- " + image);
            }
        }
    }

    // EFFECTS: Displays all recipes in the recipe collection.
    private void viewRecipeCollection() {
        Set<Recipe> recipes = recipeCollection.getRecipes();
        if (recipes.isEmpty()) {
            System.out.println("No recipes available.");
        } else {
            System.out.println("🥢 My Recipe Collection 🥢");
            for (Recipe recipe : recipes) {
                System.out.println("‣ " + recipe.getName() + " [" + recipe.getCategory() + "] ");
            }
        }
    }

    // EFFECTS: Displays all items in the grocery list.
    private void viewGroceryList() {
        Set<String> items = groceryList.getGroceryItems();

        if (items.isEmpty()) {
            System.out.println("Grocery list is empty.");
        } else {
            System.out.println("🛍️ Grocery List 🛍️");
            for (String item : items) {
                System.out.println("‣ " + item);
            }
        }
    }

    // EFFECTS: Displays the current meal plan.
    private void viewMealPlan() {
        Map<String, List<Recipe>> schedule = mealPlan.getMealPlan();
        if (schedule.isEmpty()) {
            System.out.println("Meal plan is empty.");
        } else {
            System.out.println("🍴 Meal Plan 🍴");
            for (String day : schedule.keySet()) {
                System.out.println(day + ":");
                for (Recipe recipe : schedule.get(day)) {
                    System.out.println("  。 " + recipe.getName());
                }
            }
        }
    }

    private void saveData() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("recipeCollection", recipeCollection.toJson());
        jsonObject.put("groceryList", groceryList.toJson());

        if (mealPlan != null) {
            jsonObject.put("mealPlan", mealPlan.toJson());
        } else {
            jsonObject.put("mealPlan", new JSONObject());
        }

        JsonWriter writer = new JsonWriter(SAVE_FILE);
        try {
            writer.write(jsonObject);
            System.out.println("Data saved successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("Error: Unable to save data. File not found.");
        }
    }

    private void loadData() {
        JsonReader reader = new JsonReader(SAVE_FILE);
        try {
            JSONObject jsonObject = reader.read();

            recipeCollection = new RecipeCollection(jsonObject.getJSONObject("recipeCollection"));
            groceryList = new GroceryList(jsonObject.getJSONObject("groceryList"));

            if (jsonObject.has("mealPlan") && !jsonObject.isNull("mealPlan")) {
                mealPlan = new MealPlan(jsonObject.getJSONObject("mealPlan"));
            } else {
                mealPlan = new MealPlan(); // Create a new empty MealPlan if missing
            }

            System.out.println("Data loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error reading saved data: " + e.getMessage());
        }
    }

}
