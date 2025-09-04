import javax.swing.*;
import java.awt.*;
import java.util.*;

public class PepesPizza extends JFrame {
    CardLayout cardLayout;
    JPanel cardPanel;

    String customerName;
    String selectedPizza;

    Map<String, Map<String, String>> pizzaDefaults;
    Map<String, String> currentOrder;

    Name namePage;
    PizzaType pizzaTypePage;
    Ingredients ingredientsPage;
    OrderSummary orderSummaryPage;

    public PepesPizza() {
        setTitle("Pepe's Pizza");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        setupDefaults();

        namePage = new Name(this);
        pizzaTypePage = new PizzaType(this);
        ingredientsPage = new Ingredients(this);
        orderSummaryPage = new OrderSummary(this);

        cardPanel.add(namePage, "Name");
        cardPanel.add(pizzaTypePage, "PizzaType");
        cardPanel.add(ingredientsPage, "Ingredients");
        cardPanel.add(orderSummaryPage, "OrderSummary");

        add(cardPanel);
        cardLayout.show(cardPanel, "Name");
    }

    private void setupDefaults() {
        pizzaDefaults = new HashMap<>();

        pizzaDefaults.put("Cheese", Map.of(
                "Cheese", "Regular",
                "Sauce", "Regular",
                "Crust", "Thin",
                "Size", "Medium"
        ));

        pizzaDefaults.put("Pepperoni", Map.of(
                "Cheese", "Regular",
                "Sauce", "Regular",
                "Pepperoni", "Regular",
                "Crust", "Thin",
                "Size", "Medium"
        ));

        pizzaDefaults.put("Sausage", Map.of(
                "Cheese", "Regular",
                "Sauce", "Regular",
                "Sausage", "Regular",
                "Crust", "Thick",
                "Size", "Large"
        ));

        pizzaDefaults.put("Meat Lover's", Map.of(
                "Cheese", "Regular",
                "Sauce", "Regular",
                "Pepperoni", "Regular",
                "Sausage", "Regular",
                "Crust", "Thick",
                "Size", "Large"
        ));
    }

    public void showPage(String name) {
        cardLayout.show(cardPanel, name);
    }
}



