import javax.swing.*;
import java.awt.*;

public class PizzaType extends JPanel {
    public PizzaType(PepesPizza gui) {
        setLayout(new GridLayout(5, 1));
        add(new JLabel("Order:", SwingConstants.CENTER));

        String[] pizzas = {"Cheese", "Pepperoni", "Sausage", "Meat Lover's"};
        for (String pizza : pizzas) {
            JButton btn = new JButton(pizza);
            btn.addActionListener(e -> {
                gui.selectedPizza = pizza;
                gui.currentOrder = gui.ingredientsPage.getPizzaDefaults(pizza);
                gui.ingredientsPage.loadPizza(gui.selectedPizza, gui.currentOrder);
                gui.showPage("Ingredients");
            });
            add(btn);
        }
    }
}



