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

        mealPlanPanel = new JPanel();
        mealPlanPanel.setBackground(new Color(255, 249, 196));  // Light yellow

        groceryListPanel = new JPanel();
        groceryListPanel.setBackground(new Color(227, 242, 253)); // Light blue
    }

    public static void main(String[] args) {
        new MainGUI();
    }
}
