package ui;

import javax.swing.*;

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
import java.util.List;

// Represents the main GUI window for the application.
public class MainGUI extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel recipePanel;
    private JPanel mealPlanPanel;
    private JPanel groceryListPanel;
    private List<Recipe> recipeCollection = new ArrayList<>();
    private JList<String> recipeList;
    private DefaultListModel<String> recipeListModel;



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
        tabbedPane.addTab("🍽️ Meal Plan", mealPlanPanel);
        tabbedPane.addTab("🛒 Grocery List", groceryListPanel);
    }

    // MODIFIES: this
    // EFFECTS: Create colorful panels.
    private void initPanels() {
        recipePanel = new JPanel();
        recipePanel.setBackground(new Color(255, 236, 239));  // Light pink
        recipePanel.setLayout(new BorderLayout());

        recipePanel.add(createAddRecipeForm(), BorderLayout.NORTH);
        recipePanel.add(createRecipeListPanel(), BorderLayout.CENTER);  


        mealPlanPanel = new JPanel();
        mealPlanPanel.setBackground(new Color(255, 249, 196));  // Light yellow

        groceryListPanel = new JPanel();
        groceryListPanel.setBackground(new Color(227, 242, 253)); // Light blue

        recipePanel.add(createDeleteButtonPanel(), BorderLayout.SOUTH);
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
            JSONArray data = new JSONArray();
            for (Recipe recipe : recipeCollection) {
                data.put(recipe.toJson());
            }
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.write(data.toString(2));
                JOptionPane.showMessageDialog(this, "Saved successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Save failed: " + ex.getMessage());
            }
        }
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
                JSONArray data = new JSONArray(content);

                recipeCollection.clear();
                recipeListModel.clear();

                for (int i = 0; i < data.length(); i++) {
                    JSONObject json = data.getJSONObject(i);
                    Recipe recipe = new Recipe(json);
                    recipeCollection.add(recipe);
                    recipeListModel.addElement(recipe.getName());
                }

                JOptionPane.showMessageDialog(this, "Loaded successfully!");
            } catch (IOException | JSONException ex) {
                JOptionPane.showMessageDialog(this, "Load failed: " + ex.getMessage());
            }
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
        recipeList.setBorder(BorderFactory.createTitledBorder("My Recipes"));

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
        formPanel.setBorder(BorderFactory.createTitledBorder("Add a New Recipe"));

        JTextField nameField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextArea ingredientsArea = new JTextArea(3, 20);
        JTextArea stepsArea = new JTextArea(3, 20);
        JButton addButton = new JButton("Add Recipe");

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

            // Remove from model (recipeCollection)
            recipeCollection.removeIf(r -> r.getName().equals(recipeName));

            // Remove from JList
            recipeListModel.remove(selectedIndex);

            JOptionPane.showMessageDialog(this,
                    "Deleted recipe: " + recipeName,
                    "Deleted",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        panel.add(deleteButton);
        return panel;
    }




    public static void main(String[] args) {
        new MainGUI();
    }
}
