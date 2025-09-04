import javax.swing.*;
import java.awt.*;

public class PizzaType extends JPanel {
    public PizzaType(PepesPizza app) {
        setLayout(new GridLayout(5, 1));
        add(new JLabel("Order:", SwingConstants.CENTER));

        String[] pizzas = {"Cheese", "Pepperoni", "Sausage", "Meat Lover's"};
        for (String pizza : pizzas) {
            JButton btn = new JButton(pizza);
            btn.addActionListener(e -> {
                app.selectedPizza = pizza;
                app.currentOrder = app.ingredientsPage.getPizzaDefaults(pizza);
                app.ingredientsPage.loadPizza(app.selectedPizza, app.currentOrder);
                app.showPage("Ingredients");
            });
            add(btn);
        }
    }
}



