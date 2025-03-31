# Recipe Manager
The *Recipe Manager* is a desktop application that allows users to manage recipes and effectively utilize their present ingredients. Users can add new recipes, manage ingredients, browse collections, and add recipes to their favorites. The target users for this application include: frequently home cooks, people who want to properly manage their ingredients, food enthusiasts who like to create their own recipes, and professional chefs who need to organize their recipes systematically. This topic inspired me because I am a cooking and baking enthusiast, and I would like to organize and track the recipes I've made and want to collect.

## User stories
- As a user, I want to be able to add recipes, including name, category(cuisine or meal type), ingredients required, and the preparation    steps in the recipe collection.
- As a user, I want to be able to delete recipes.
- As a user, I want to be able to view my recipe collections.
- As a user, I want to be able to select a recipe from my collection and view its details.
- As a user, I want to be able to add and delete meals to customize my meal plan.
- As a user, I want to be able to add and delete items in the grocery list.
- As a user, I want to be able to save the entire state of my recipe app, including my recipe, recipe collection, meal plan, and grocery list, when I select the exit option of the application.
- As a user, I want to be provided the option to load the saved state of my recipe app from a file when I start the application.

## Instructions for End User
- You can generate the first required action related to the user story "adding/deleting multiple recipes to/from a recipe collection" by clicking the 'Add Recipe!' or 'Delete Recipe' button in the Recipes tab, where you can enter the recipe name, its category, ingredients, and the preparation steps to add a new recipe to your recipe collection.
- You can generate the second required action related to the user story "adding/deleting meals to/from the meal plan" by clicking the 'Add Item' or 'Remove Meal' button in the Meal Plan tab, where you can enter the meal and the assigned day of the week.
- You can generate the third required action related to the user story "adding/deleting items to/from the grocery list" by clicking the 'Add Item' or 'Delete Item' button in the Grocery List tab.
- You can generate the fourth required action related to the user story "select a recipe from my collection and view its details" by clicking the name of the meal under the Recipe Collection section in the Recipes tab to view the details(name, category, ingredients, and preparation steps).
- You can locate my visual component by going to the Recipes tab and clicking on the '📊 Show Category Chart' button. A separate window will navigate to a pie chart of your recipe categories.
- You can save the state of my application by clicking the 'File' button then '📌 Save' button to the right of the "Recipes" tab.
- You can reload the state of my application by clicking the 'file' button then the '📂 Load' button on the right-hand side of the Recipes tab.Selecting a saved file will restore all previously saved recipes, meal plan, and grocery list.

## Phase 4: Task 2
--- Event Log ---
Sun Mar 30 23:28:56 PDT 2025
Recipe added: curry
Sun Mar 30 23:29:09 PDT 2025
Meal added: curry on Monday
Sun Mar 30 23:29:10 PDT 2025
Grocery item added: cabbage

## Phase 4: Task 3
If I had more time, one important refactoring task would be to abstract and unify shared behaviors throughout the model. For example, RecipeCollection, MealPlan, and GroceryList all keep a list and have add/remove/clear operations. These can be refactored into a shared abstract class or interface, such as ItemCollection<T>, to reduce code duplication and improve maintainability. I could also create a Clearable interface that includes a common clear() function.
In addition, I would consider adding a RecipeReference function, such as putting an image or link to each recipe to help people cook.  From a UI/UX standpoint, I would include more visual elements to better showcase user data, such as displaying recipe categories as interactive charts or a calendar-based meal planner. Finally, adding more "humanized" actions such as "Clear All" buttons and visual confirmations (eg. animations or toast popups) would enhance the user experience without altering the app's fundamental functionality.










