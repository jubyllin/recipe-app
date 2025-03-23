package ui;

import javax.swing.*;
import java.awt.*;

// Represents the main GUI window for the application.
public class MainGUI extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel recipePanel;
    private JPanel mealPlanPanel;
    private JPanel groceryListPanel;

    public MainGUI() {
        super("👩🏻‍🍳 My Recipe App 👩🏻‍🍳");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(900, 600));
        setLayout(new BorderLayout());

        initPanels();
        initTabs();

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

        mealPlanPanel = new JPanel();
        mealPlanPanel.setBackground(new Color(255, 249, 196));  // Light yellow

        groceryListPanel = new JPanel();
        groceryListPanel.setBackground(new Color(227, 242, 253)); // Light blue
    }



    // EFFECTs: returns a JPanel containing fields and a button for recipe input
    private JPanel createAddRecipeForm() {
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(255, 236, 239));
        formPanel.setLayout(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add a New Recipe"));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel categoryLabel = new JLabel("Category:");
        JTextField categoryField = new JTextField();

        JLabel ingredientsLabel = new JLabel("Ingredients:");
        JTextArea ingredientsArea = new JTextArea(3, 20);
        JScrollPane ingredientsScroll = new JScrollPane(ingredientsArea);

        JLabel stepsLabel = new JLabel("Steps:");
        JTextArea stepsArea = new JTextArea(3, 20);
        JScrollPane stepsScroll = new JScrollPane(stepsArea);

        JButton addButton = new JButton("Add Recipe");


        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(categoryLabel);
        formPanel.add(categoryField);
        formPanel.add(ingredientsLabel);
        formPanel.add(ingredientsScroll);
        formPanel.add(stepsLabel);
        formPanel.add(stepsScroll);
        formPanel.add(new JLabel()); 
        formPanel.add(addButton);

        return formPanel;
    }


    public static void main(String[] args) {
        new MainGUI();
    }
}
