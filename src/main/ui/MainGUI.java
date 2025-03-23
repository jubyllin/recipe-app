package ui;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

import model.Recipe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;



// Represents the main GUI window for the application.
public class MainGUI extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel recipePanel;
    private JPanel mealPlanPanel;
    private JPanel groceryListPanel;
    private List<Recipe> recipeCollection = new ArrayList<>();
    private JList<String> recipeList;
    private DefaultListModel<String> recipeListModel;
    private DefaultListModel<String> groceryListModel;
    private JList<String> groceryJList;
    private DefaultListModel<String> mealPlanModel;
    private JList<String> mealPlanJList;



    public MainGUI() {
        super("👩🏻‍🍳 My Recipe App 👩🏻‍🍳");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(900, 600));
        setLayout(new BorderLayout());

        initPanels();
        initTabs();
        setJMenuBar(createMenuBar());


        add(tabbedPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);  
        setVisible(true);
    }
    
    // MODIFIES: this
    // EFFECTS: Set up the tabs.
    private void initTabs() {
        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("🔖 Recipes", recipePanel);
        tabbedPane.addTab("🍴 Meal Plan", mealPlanPanel);
        tabbedPane.addTab("🛒 Grocery List", groceryListPanel);
    }

    // MODIFIES: this
    // EFFECTS: Create colorful panels.
    private void initPanels() {
        recipePanel = new JPanel();
        recipePanel.setBackground(new Color(255, 250, 250));  // Light yellow
        recipePanel.setLayout(new BorderLayout());

        recipePanel.add(createAddRecipeForm(), BorderLayout.NORTH);
        recipePanel.add(createRecipeListPanel(), BorderLayout.CENTER);  


        mealPlanPanel = new JPanel();
        mealPlanPanel.setBackground(new Color(255, 236, 239));  // Light pink

        groceryListPanel = new JPanel();
        groceryListPanel.setBackground(new Color(227, 242, 253)); // Light blue

        recipePanel.add(createDeleteButtonPanel(), BorderLayout.SOUTH);

        groceryListPanel.setLayout(new BorderLayout());
        groceryListPanel.add(createListManagerPanel(
                "✴︎Grocery List✴︎", "Add Item", "Delete Item",
                item -> groceryListModel.addElement(item),
                () -> groceryListModel,
                () -> groceryJList
        ), BorderLayout.CENTER);

        mealPlanPanel.setLayout(new BorderLayout());
        mealPlanPanel.add(createMealPlanManagerPanel(), BorderLayout.CENTER);

    }


    // MODIFIES: this
    // EFFECTS: Adds file -> Save/Load menu to the app
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem saveItem = new JMenuItem("📌 Save");
        saveItem.addActionListener(e -> handleSave());

        JMenuItem loadItem = new JMenuItem("📂 Load");
        loadItem.addActionListener(e -> handleLoad());

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        menuBar.add(fileMenu);

        return menuBar;
    }

    // EFFECTS: Write the current recipeCollection to a file as JSON
    private void handleSave() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
    
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            JSONObject fullData = collectAppDataAsJson();
    
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.write(fullData.toString(2));
                JOptionPane.showMessageDialog(this, "Saved successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Save failed: " + ex.getMessage());
            }
        }
    }

    private JSONObject collectAppDataAsJson() {
        JSONObject fullData = new JSONObject();
    
        JSONArray recipeArray = new JSONArray();
        for (Recipe recipe : recipeCollection) {
            recipeArray.put(recipe.toJson());
        }
    
        JSONArray mealPlanArray = new JSONArray();
        for (int i = 0; i < mealPlanModel.size(); i++) {
            mealPlanArray.put(mealPlanModel.get(i));
        }
    
        JSONArray groceryArray = new JSONArray();
        for (int i = 0; i < groceryListModel.size(); i++) {
            groceryArray.put(groceryListModel.get(i));
        }
    
        fullData.put("recipes", recipeArray);
        fullData.put("mealPlan", mealPlanArray);
        fullData.put("groceryList", groceryArray);
    
        return fullData;
    }

    // MODIFIES: this
    // EFFECTS: Load recipes from the file and update display
    private void handleLoad() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
    
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                String content = new String(Files.readAllBytes(file.toPath()));
                JSONObject fullData = new JSONObject(content);
                loadAppDataFromJson(fullData);
                JOptionPane.showMessageDialog(this, "Loaded successfully!");
            } catch (IOException | JSONException ex) {
                JOptionPane.showMessageDialog(this, "Load failed: " + ex.getMessage());
            }
        }
    }

    private void loadAppDataFromJson(JSONObject fullData) {
        recipeCollection.clear();
        recipeListModel.clear();
        mealPlanModel.clear();
        groceryListModel.clear();
    
        JSONArray recipeArray = fullData.getJSONArray("recipes");
        for (int i = 0; i < recipeArray.length(); i++) {
            Recipe recipe = new Recipe(recipeArray.getJSONObject(i));
            recipeCollection.add(recipe);
            recipeListModel.addElement(recipe.getName());
        }
    
        JSONArray mealArray = fullData.getJSONArray("mealPlan");
        for (int i = 0; i < mealArray.length(); i++) {
            mealPlanModel.addElement(mealArray.getString(i));
        }
    
        JSONArray groceryArray = fullData.getJSONArray("groceryList");
        for (int i = 0; i < groceryArray.length(); i++) {
            groceryListModel.addElement(groceryArray.getString(i));
        }
    }

    // MODIFIES: this
    // EFFECTS: Sets up a JList of recipe names with scroll support
    private JScrollPane createRecipeListPanel() {
        recipeListModel = new DefaultListModel<>();
        recipeList = new JList<>(recipeListModel);
        recipeList.setVisibleRowCount(10);
        recipeList.setFixedCellHeight(30);
        recipeList.setBackground(new Color(255, 250, 250));  // light pink white
        recipeList.setBorder(BorderFactory.createTitledBorder("✩✩My Recipe Collection✩✩"));

        recipeList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedName = recipeList.getSelectedValue();
                Recipe selected = findRecipeByName(selectedName);
                if (selected != null) {
                    showRecipeDetails(selected);
                }
            }
        });

        return new JScrollPane(recipeList);

    }

    // MODIFIES: this
    // EFFECTs: Returns a JPanel containing fields and a button for recipe input
    private JPanel createAddRecipeForm() {
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(255, 236, 239));
        formPanel.setLayout(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("✴︎Add a New Recipe✴︎"));

        JTextField nameField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextArea ingredientsArea = new JTextArea(3, 20);
        JTextArea stepsArea = new JTextArea(3, 20);
        JButton addButton = new JButton("Add Recipe!");

        setupAddRecipeButton(addButton, nameField, categoryField, ingredientsArea, stepsArea);

        addFieldsToForm(formPanel, nameField, categoryField, ingredientsArea, stepsArea, addButton);
        return formPanel;
    }

    // MODIFIES: this
    // EFFECTS: Creates and adds a recipe
    private void setupAddRecipeButton(JButton button, JTextField nameField, JTextField categoryField,
                                  JTextArea ingredientsArea, JTextArea stepsArea) {
        button.addActionListener(e -> {
            handleAddRecipe(nameField, categoryField, ingredientsArea, stepsArea);
        });
    }

    // MODIFIES: this
    // EFFECTS: Creates a new Recipe, adds ingredients and steps, and stores it in recipeCollection
    private void handleAddRecipe(JTextField nameField, JTextField categoryField,
                                JTextArea ingredientsArea, JTextArea stepsArea) {
        String name = nameField.getText().trim();
        String category = categoryField.getText().trim();
    

        if (name.isEmpty() || category.isEmpty()) {
            showMissingFieldWarning();
            return;
        }

        Recipe newRecipe = createRecipeFromInput(name, category, ingredientsArea.getText(), stepsArea.getText());

        recipeCollection.add(newRecipe);
        recipeListModel.addElement(newRecipe.getName());


        JOptionPane.showMessageDialog(this,
                "Recipe added: " + newRecipe.getName(),
                "Success",
                JOptionPane.INFORMATION_MESSAGE);

        clearRecipeForm(nameField, categoryField, ingredientsArea, stepsArea);

    }


    // EFFECTS: Returns a Recipe with ingredients and steps
    private Recipe createRecipeFromInput(String name, String category,
            String ingredientsText, String stepsText) {
        Recipe recipe = new Recipe(name, category);

        String[] ingredients = ingredientsText.trim().split("\n");
        for (String ingredient : ingredients) {
            if (!ingredient.isBlank()) {
                recipe.addIngredient(ingredient.trim());
            }
        }

        String[] steps = stepsText.trim().split("\n");
        for (String step : steps) {
            if (!step.isBlank()) {
                recipe.addStep(step.trim());
            }
        }

        return recipe;
    }


    // EFFECTS: Empties all input components
    private void clearRecipeForm(JTextField nameField, JTextField categoryField,
            JTextArea ingredientsArea, JTextArea stepsArea) {
        nameField.setText("");
        categoryField.setText("");
        ingredientsArea.setText("");
        stepsArea.setText("");
    }


    // MODIFIES: this
    // EFFECTS: Returns the searched Recipe, or null if not found
    private Recipe findRecipeByName(String name) {
        for (Recipe r : recipeCollection) {
            if (r.getName().equals(name)) {
                return r;
            }
        }
        return null;
    }


    // MODIFIES: this
    // EFFECTS: Shows the recipe details, including ingredients and steps
    private void showRecipeDetails(Recipe recipe) {
        StringBuilder message = new StringBuilder();
        message.append("Name: ").append(recipe.getName()).append("\n");
        message.append("Category: ").append(recipe.getCategory()).append("\n\n");
    
        message.append("Ingredients:\n");
        for (String i : recipe.getIngredients()) {
            message.append("• ").append(i).append("\n");
        }
    
        message.append("Steps:\n");
        for (String s : recipe.getSteps()) {
            message.append("→ ").append(s).append("\n");
        }
    
        JOptionPane.showMessageDialog(this, message.toString(), "📖 Recipe Details", JOptionPane.INFORMATION_MESSAGE);
    }
    


    // EFFECTS: Displays a warning message.
    private void showMissingFieldWarning() {
        JOptionPane.showMessageDialog(this,
                "Please fill in at least name and category.",
                "Missing Info",
                JOptionPane.WARNING_MESSAGE);
    }


    // MODIFIES: formPanel
    // EFFECTS: Adds input fields and labels to the form
    private void addFieldsToForm(JPanel formPanel, JTextField nameField, JTextField categoryField,
                             JTextArea ingredientsArea, JTextArea stepsArea, JButton addButton) {
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Category:"));
        formPanel.add(categoryField);

        formPanel.add(new JLabel("Ingredients (one per line):"));
        formPanel.add(new JScrollPane(ingredientsArea));

        formPanel.add(new JLabel("Steps (one per line):"));
        formPanel.add(new JScrollPane(stepsArea));

        formPanel.add(new JLabel()); 
        formPanel.add(addButton);
    }

    // MODIFIES: this
    // EFFECTS: Removes the selected recipe from the collection and updates the list
    private JPanel createDeleteButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(new Color(255, 236, 239));  
    
        panel.add(createDeleteRecipeButton());
        panel.add(createCategoryChartButton());
    
        return panel;
    }
    
    private JButton createDeleteRecipeButton() {
        JButton deleteButton = new JButton("Delete Recipe");
    
        deleteButton.addActionListener(e -> {
            int selectedIndex = recipeList.getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(this,
                        "Please select a recipe to delete.",
                        "No Selection",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
    
            String recipeName = recipeListModel.get(selectedIndex);
            recipeCollection.removeIf(r -> r.getName().equals(recipeName));
            recipeListModel.remove(selectedIndex);
    
            JOptionPane.showMessageDialog(this,
                    "Deleted recipe: " + recipeName,
                    "Deleted",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    
        return deleteButton;
    }
    
    private JButton createCategoryChartButton() {
        JButton chartButton = new JButton("📊 Show Category Chart");
        chartButton.addActionListener(e -> showCategoryChart());
        return chartButton;
    }


    // EFFECTS: Opens a new window with a pie chart of category distribution
    private void showCategoryChart() {
        Map<String, Integer> categoryCounts = new HashMap<>();
        for (Recipe r : recipeCollection) {
            categoryCounts.put(r.getCategory(),
                    categoryCounts.getOrDefault(r.getCategory(), 0) + 1);
        }

        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String, Integer> entry : categoryCounts.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Recipe Categories", dataset, true, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame chartFrame = new JFrame("Category Breakdown");
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartFrame.setContentPane(chartPanel);
        chartFrame.setSize(400, 300);
        chartFrame.setLocationRelativeTo(this);
        chartFrame.setVisible(true);
    }

    // EFFECTS: Shows and manages a simple text list
    private JPanel createListManagerPanel(
            String title,
            String addButtonText,
            String deleteButtonText,
            Consumer<String> onAdd,
            Supplier<DefaultListModel<String>> modelSupplier,
            Supplier<JList<String>> listSupplier
    ) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.setBackground(new Color(255, 255, 240));

        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        panel.add(createListInputPanel(addButtonText, deleteButtonText, onAdd, model, list), BorderLayout.SOUTH);

        bindModelAndList(title, model, list);
        return panel;
    }

    private JPanel createListInputPanel(
            String addButtonText,
            String deleteButtonText,
            Consumer<String> onAdd,
            DefaultListModel<String> model,
            JList<String> list
    ) {
        JPanel inputPanel = new JPanel(new FlowLayout());
        JTextField inputField = new JTextField(15);
        JButton addButton = new JButton(addButtonText);
        JButton deleteButton = new JButton(deleteButtonText);

        addButton.addActionListener(e -> {
            String input = inputField.getText().trim();
            if (!input.isEmpty()) {
                onAdd.accept(input);
                inputField.setText("");
            }
        });

        deleteButton.addActionListener(e -> {
            int selected = list.getSelectedIndex();
            if (selected != -1) {
                model.remove(selected);
            }
        });

        inputPanel.add(inputField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);
        return inputPanel;
    }

    private void bindModelAndList(String title, DefaultListModel<String> model, JList<String> list) {
        if (title.contains("Grocery")) {
            groceryListModel = model;
            groceryJList = list;
        } else if (title.contains("Meal")) {
            mealPlanModel = model;
            mealPlanJList = list;
        }
    }

    // MODIFIES: this
    // EFFECTS: Build a UI for assigning meals to days of the week
    private JPanel createMealPlanManagerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("✴︎Meal Plan✴︎"));
        panel.setBackground(new Color(255, 255, 240));

        mealPlanModel = new DefaultListModel<>();
        mealPlanJList = new JList<>(mealPlanModel);
        mealPlanJList.setVisibleRowCount(10);

        panel.add(new JScrollPane(mealPlanJList), BorderLayout.CENTER);
        panel.add(createMealPlanInputPanel(), BorderLayout.SOUTH);

        return panel;
    }

    // EFFECTS: Process user input of meals + dates and update the list
    private JPanel createMealPlanInputPanel() {
        JPanel inputPanel = new JPanel(new FlowLayout());
        JTextField mealField = new JTextField(10);
        JComboBox<String> daySelector = new JComboBox<>(new String[]{
                "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        });
    
        JButton addButton = new JButton("Add Meal");
        JButton deleteButton = new JButton("Remove Meal");
    
        addButton.addActionListener(e -> handleAddMeal(mealField, daySelector));
        deleteButton.addActionListener(e -> handleDeleteMeal());
    
        inputPanel.add(new JLabel("Day:"));
        inputPanel.add(daySelector);
        inputPanel.add(new JLabel("Meal:"));
        inputPanel.add(mealField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);
    
        return inputPanel;
    }

    // EFFECTS: Adds a meal to the meal plan.
    private void handleAddMeal(JTextField mealField, JComboBox<String> daySelector) {
        String meal = mealField.getText().trim();
        String day = (String) daySelector.getSelectedItem();
    
        if (!meal.isEmpty()) {
            mealPlanModel.addElement(day + ": " + meal);
            mealField.setText("");
        }
    }

    // EFFECTS: Remove selected meals from the meal plan.
    private void handleDeleteMeal() {
        int selected = mealPlanJList.getSelectedIndex();
        if (selected != -1) {
            mealPlanModel.remove(selected);
        }
    }

    public static void main(String[] args) {
        new MainGUI();
    }
}
