import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class PizzaEditor extends JFrame {
    private DefaultListModel<Pizza> pizzaListModel = new DefaultListModel<>();
    private JList<Pizza> pizzaJList = new JList<>(pizzaListModel);
    private JPanel ingredientPanel = new JPanel(new GridLayout(0, 1));
    private JLabel currentPizzaPriceLabel = new JLabel("Current Pizza: $0.00");
    private JLabel totalPriceLabel = new JLabel("Total: $0.00");
    private JLabel totalTimeLabel = new JLabel("Estimated Time: 0 min");
    private Order order;
    private Pizza currentPizza;

    public PizzaEditor(Order order) {
        this.order = order;

        setTitle("Pizza Editor - " + order.getCustomerName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLayout(new BorderLayout());

        pizzaJList.setCellRenderer(new PizzaCellRenderer());
        pizzaJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Pizza selected = pizzaJList.getSelectedValue();
                if (selected != null) loadPizza(selected);
            }
        });

        JScrollPane pizzaScroll = new JScrollPane(pizzaJList);
        pizzaScroll.setPreferredSize(new Dimension(300, 400));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(pizzaScroll, BorderLayout.CENTER);

        JPanel orderSummaryPanel = new JPanel(new GridLayout(0, 1));
        orderSummaryPanel.add(totalPriceLabel);
        orderSummaryPanel.add(totalTimeLabel);
        leftPanel.add(orderSummaryPanel, BorderLayout.SOUTH);

        add(leftPanel, BorderLayout.WEST);

        JScrollPane ingredientScroll = new JScrollPane(ingredientPanel);
        add(ingredientScroll, BorderLayout.CENTER);

        JPanel presetPanel = new JPanel(new GridLayout(0, 1));
        addPresetButton(presetPanel, "Cheese");
        addPresetButton(presetPanel, "Pepperoni");
        addPresetButton(presetPanel, "Sausage");
        addPresetButton(presetPanel, "Meat Lovers");
        addPresetButton(presetPanel, "Veggie");
        addPresetButton(presetPanel, "Custom");
        add(presetPanel, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
        JButton addPizzaButton = new JButton("Add Pizza");
        addPizzaButton.addActionListener(e -> addCurrentPizza());
        buttonPanel.add(addPizzaButton);

        JButton removePizzaButton = new JButton("Remove Pizza");
        removePizzaButton.addActionListener(e -> removeSelectedPizza());
        buttonPanel.add(removePizzaButton);

        add(buttonPanel, BorderLayout.SOUTH);

        JButton submitButton = new JButton("Submit Order");
        submitButton.addActionListener(e -> {
            StringBuilder summary = new StringBuilder();
            summary.append("Name: ").append(order.getCustomerName()).append("\n\n");
            for (Pizza pizza : order.getPizzas()) {
                summary.append(pizza.getName())
                        .append(" (").append(pizza.getSize())
                        .append(", ").append(pizza.getCrust()).append(") - $")
                        .append(String.format("%.2f", pizza.calculatePrice()))
                        .append("\nToppings: ");
                for (Ingredient ing : pizza.getIngredients()) {
                    if (!ing.getLevel().equals("None")) {
                        summary.append(ing.getName())
                                .append(" (").append(ing.getLevel()).append(") ");
                    }
                }
                summary.append("\n\n");
            }
            summary.append("Total: $").append(String.format("%.2f", order.getTotalPrice()))
                    .append("\nEstimated Time: ").append(order.getTotalWaitTime()).append(" min");

            JOptionPane.showMessageDialog(this, summary.toString(), "Order Confirmed", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new NamePage();
        });
        add(submitButton, BorderLayout.NORTH);

        setVisible(true);
    }

    private void addPresetButton(JPanel panel, String name) {
        JButton btn = new JButton(name);
        btn.addActionListener(e -> createPizzaFromPreset(name));
        panel.add(btn);
    }

    private void createPizzaFromPreset(String name) {
        Pizza pizza = new Pizza(name);
        pizza.setCrust("Thick");
        pizza.setSize("Medium");

        List<Ingredient> ingredients = new ArrayList<>();
        switch (name.toLowerCase()) {
            case "cheese":
                ingredients.add(new Ingredient("Cheese", 2.0));
                break;
            case "pepperoni":
                ingredients.add(new Ingredient("Cheese", 2.0));
                ingredients.add(new Ingredient("Pepperoni", 2.5));
                break;
            case "sausage":
                ingredients.add(new Ingredient("Cheese", 2.0));
                ingredients.add(new Ingredient("Sausage", 2.5));
                break;
            case "meat lovers":
                ingredients.add(new Ingredient("Cheese", 2.0));
                ingredients.add(new Ingredient("Pepperoni", 2.5));
                ingredients.add(new Ingredient("Sausage", 2.5));
                ingredients.add(new Ingredient("Bacon", 2.5));
                break;
            case "veggie":
                ingredients.add(new Ingredient("Cheese", 2.0));
                ingredients.add(new Ingredient("Mushroom", 1.5));
                ingredients.add(new Ingredient("Olives", 1.5));
                ingredients.add(new Ingredient("Peppers", 1.5));
                break;
            case "custom":
                String[] allToppings = {"Cheese","Pepperoni","Sausage","Bacon","Mushroom","Olives","Peppers"};
                for (String t : allToppings) {
                    Ingredient ing = new Ingredient(t, t.equals("Cheese") ? 2.0 : 2.5);
                    if (t.equals("Cheese")) ing.setLevel("Regular");
                    else ing.setLevel("None");
                    ingredients.add(ing);
                }
                break;
        }
        for (Ingredient ing : ingredients) pizza.addIngredient(ing);
        loadPizza(pizza);
    }

    private void loadPizza(Pizza pizza) {
        currentPizza = pizza;
        ingredientPanel.removeAll();

        JPanel sizePanel = new JPanel();
        sizePanel.add(new JLabel("Size:"));
        ButtonGroup sizeGroup = new ButtonGroup();
        for (String s : new String[]{"Small","Medium","Large"}) {
            JRadioButton rb = new JRadioButton(s);
            rb.setSelected(s.equals(pizza.getSize()));
            rb.addActionListener(e -> { currentPizza.setSize(s); updatePriceAndTime(); });
            sizeGroup.add(rb);
            sizePanel.add(rb);
        }
        ingredientPanel.add(sizePanel);

        JPanel crustPanel = new JPanel();
        crustPanel.add(new JLabel("Crust:"));
        ButtonGroup crustGroup = new ButtonGroup();
        for (String c : new String[]{"Thin","Thick"}) {
            JRadioButton rb = new JRadioButton(c);
            rb.setSelected(c.equals(pizza.getCrust()));
            rb.addActionListener(e -> { currentPizza.setCrust(c); updatePriceAndTime(); });
            crustGroup.add(rb);
            crustPanel.add(rb);
        }
        ingredientPanel.add(crustPanel);

        for (Ingredient ing : pizza.getIngredients()) {
            JPanel radioPanel = new JPanel();
            radioPanel.add(new JLabel(ing.getName()));
            ButtonGroup group = new ButtonGroup();
            for (String level : new String[]{"None","Light","Regular","Extra"}) {
                JRadioButton rb = new JRadioButton(level);
                rb.setSelected(level.equals(ing.getLevel()));
                rb.addActionListener(e -> { ing.setLevel(level); updatePriceAndTime(); });
                group.add(rb);
                radioPanel.add(rb);
            }
            ingredientPanel.add(radioPanel);
        }

        ingredientPanel.add(currentPizzaPriceLabel);
        ingredientPanel.revalidate();
        ingredientPanel.repaint();
        updatePriceAndTime();
    }

    private void addCurrentPizza() {
        if (currentPizza != null) {
            Pizza newPizza = currentPizza.clone();
            order.addPizza(newPizza);
            pizzaListModel.addElement(newPizza);
            updatePriceAndTime();
        }
    }

    private void removeSelectedPizza() {
        Pizza pizza = pizzaJList.getSelectedValue();
        if (pizza != null) {
            order.removePizza(pizza);
            pizzaListModel.removeElement(pizza);
            updatePriceAndTime();
        }
    }

    private void updatePriceAndTime() {
        if (currentPizza != null)
            currentPizzaPriceLabel.setText(String.format("Current Pizza: $%.2f", currentPizza.calculatePrice()));
        else
            currentPizzaPriceLabel.setText("Current Pizza: $0.00");

        totalPriceLabel.setText(String.format("Total: $%.2f", order.getTotalPrice()));
        totalTimeLabel.setText("Estimated Time: " + order.getTotalWaitTime() + " min");
        pizzaJList.repaint();
    }

    private class PizzaCellRenderer extends JLabel implements ListCellRenderer<Pizza> {
        @Override
        public Component getListCellRendererComponent(JList<? extends Pizza> list, Pizza value, int index, boolean isSelected, boolean cellHasFocus) {
            StringBuilder toppings = new StringBuilder();
            for (Ingredient ing : value.getIngredients()) {
                if (!ing.getLevel().equals("None")) {
                    toppings.append(ing.getName())
                            .append("(")
                            .append(ing.getLevel())
                            .append(") ");
                }
            }
            setText(String.format("%s (%s, %s) - $%.2f", value.getName(), value.getSize(), value.getCrust(), value.calculatePrice()));
            if (toppings.length() > 0) setText(getText() + " | " + toppings.toString().trim());
            setOpaque(true);
            setBackground(isSelected ? Color.LIGHT_GRAY : Color.WHITE);
            return this;
        }
    }
}