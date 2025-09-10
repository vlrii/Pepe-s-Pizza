import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PizzaEditor extends JFrame {
    private DefaultListModel<Pizza> pizzaListModel = new DefaultListModel<>();
    private JList<Pizza> pizzaJList = new JList<>(pizzaListModel);
    private JPanel ingredientPanel = new JPanel();
    private JLabel currentPizzaPriceLabel = new JLabel("Current Pizza: $0.00");
    private JLabel totalPriceLabel = new JLabel("Total: $0.00");
    private JLabel totalTimeLabel = new JLabel("Estimated Time: 0 min");
    private JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
    private Order order;
    private Pizza currentPizza;
    private DecimalFormat money = new DecimalFormat("0.00");

    public PizzaEditor(Order order) {
        this.order = order;
        setTitle("Pizza Builder - " + order.getCustomerName());
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel presetPanel = new JPanel();
        presetPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        String[] presets = {"Cheese","Pepperoni","Sausage","Meat Lovers","Veggie","Hawaiian","Chicken Bacon Ranch","Custom"};
        for (String name : presets) {
            JButton btn = new JButton(name);
            btn.setPreferredSize(new Dimension(160,40));
            btn.setFont(new Font("Arial", Font.PLAIN, 14));
            btn.addActionListener(e -> createPreset(name));
            presetPanel.add(btn);
        }
        add(presetPanel, BorderLayout.NORTH);

        JPanel middlePanel = new JPanel(new GridLayout(1,2,10,0));

        ingredientPanel.setLayout(new BoxLayout(ingredientPanel, BoxLayout.Y_AXIS));
        JScrollPane ingredientScroll = new JScrollPane(ingredientPanel);
        ingredientScroll.setBorder(BorderFactory.createTitledBorder("Customize Pizza"));
        middlePanel.add(ingredientScroll);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Pizza Info"));

        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        currentPizzaPriceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalPriceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalTimeLabel.setFont(new Font("Arial", Font.BOLD, 16));

        currentPizzaPriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        totalPriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        totalTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        quantitySpinner.setAlignmentX(Component.CENTER_ALIGNMENT);

        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(currentPizzaPriceLabel);
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(totalPriceLabel);
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(totalTimeLabel);
        rightPanel.add(Box.createVerticalStrut(15));

        JPanel qtyPanel = new JPanel();
        qtyPanel.add(new JLabel("Quantity:"));
        quantitySpinner.setPreferredSize(new Dimension(50,25));
        qtyPanel.add(quantitySpinner);
        qtyPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(qtyPanel);
        rightPanel.add(Box.createVerticalStrut(20));

        JButton addBtn = new JButton("Add Pizza");
        JButton removeBtn = new JButton("Remove Pizza");
        JButton submitBtn = new JButton("Submit Order");

        addBtn.setFont(labelFont);
        removeBtn.setFont(labelFont);
        submitBtn.setFont(labelFont);

        addBtn.setMaximumSize(new Dimension(200,40));
        removeBtn.setMaximumSize(new Dimension(200,40));
        submitBtn.setMaximumSize(new Dimension(200,40));

        addBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        addBtn.addActionListener(e -> addPizza());
        removeBtn.addActionListener(e -> removePizza());
        submitBtn.addActionListener(e -> submitOrder());

        rightPanel.add(addBtn);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(removeBtn);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(submitBtn);

        middlePanel.add(rightPanel);
        add(middlePanel, BorderLayout.CENTER);

        JScrollPane orderScroll = new JScrollPane(pizzaJList);
        orderScroll.setBorder(BorderFactory.createTitledBorder("Your Order"));
        orderScroll.setPreferredSize(new Dimension(950,180));
        pizzaJList.setCellRenderer(new PizzaCellRenderer());
        add(orderScroll, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void createPreset(String name) {
        Pizza p = new Pizza(name);
        ArrayList<String> relevant = new ArrayList<>();
        Ingredient sauce = new Ingredient("Tomato Sauce",2.0);

        switch(name) {
            case "Cheese": relevant.add("Cheese"); break;
            case "Pepperoni": relevant.add("Cheese"); relevant.add("Pepperoni"); break;
            case "Sausage": relevant.add("Cheese"); relevant.add("Sausage"); break;
            case "Meat Lovers": relevant.add("Cheese"); relevant.add("Pepperoni"); relevant.add("Sausage"); relevant.add("Bacon"); break;
            case "Veggie": relevant.add("Cheese"); relevant.add("Mushroom"); relevant.add("Olives"); relevant.add("Peppers"); break;
            case "Hawaiian": relevant.add("Cheese"); relevant.add("Ham"); relevant.add("Pineapple"); break;
            case "Chicken Bacon Ranch": relevant.add("Cheese"); relevant.add("Chicken"); relevant.add("Bacon"); sauce = new Ingredient("Ranch",2.0); break;
            case "Custom":
                String[] all = {"Cheese","Pepperoni","Sausage","Bacon","Mushroom","Olives","Peppers","Ham","Pineapple","Chicken"};
                for (String t : all) {
                    Ingredient ing = new Ingredient(t,t.equals("Cheese")?2.0:2.5);
                    ing.setLevel(t.equals("Cheese")?"Regular":"None");
                    p.addIngredient(ing);
                }
                relevant.clear();
                loadPizza(p,relevant,sauce);
                return;
        }

        for(String t : relevant) {
            Ingredient ing = new Ingredient(t,t.equals("Cheese")?2.0:2.5);
            ing.setLevel("Regular");
            p.addIngredient(ing);
        }
        p.setSauce(sauce);
        p.setQuantity(1);
        quantitySpinner.setValue(1);
        loadPizza(p,relevant,sauce);
    }

    private void loadPizza(Pizza p, ArrayList<String> relevant, Ingredient sauce) {
        currentPizza = p;
        ingredientPanel.removeAll();

        JPanel sizePanel = new JPanel();
        sizePanel.add(new JLabel("Size:"));
        ButtonGroup sizeGroup = new ButtonGroup();
        for(String s : new String[]{"Small","Medium","Large"}) {
            JRadioButton rb = new JRadioButton(s, s.equals(p.getSize()));
            rb.addActionListener(e -> { p.setSize(s); updateLabels(); });
            sizeGroup.add(rb);
            sizePanel.add(rb);
        }
        ingredientPanel.add(sizePanel);

        JPanel crustPanel = new JPanel();
        crustPanel.add(new JLabel("Crust:"));
        ButtonGroup crustGroup = new ButtonGroup();
        for(String c : new String[]{"Thin","Thick"}) {
            JRadioButton rb = new JRadioButton(c, c.equals(p.getCrust()));
            rb.addActionListener(e -> { p.setCrust(c); updateLabels(); });
            crustGroup.add(rb);
            crustPanel.add(rb);
        }
        ingredientPanel.add(crustPanel);

        JPanel sauceTypePanel = new JPanel();
        sauceTypePanel.add(new JLabel("Sauce Type:"));
        ButtonGroup stGroup = new ButtonGroup();
        for(String type : new String[]{"Tomato Sauce","BBQ Sauce","Ranch"}) {
            JRadioButton rb = new JRadioButton(type, sauce.getName().equals(type));
            rb.addActionListener(e -> { currentPizza.setSauce(new Ingredient(type,2.0)); updateLabels(); });
            stGroup.add(rb);
            sauceTypePanel.add(rb);
        }
        ingredientPanel.add(sauceTypePanel);

        JPanel sauceLevelPanel = new JPanel();
        sauceLevelPanel.add(new JLabel("Sauce:"));
        ButtonGroup slGroup = new ButtonGroup();
        for(String lvl : new String[]{"None","Light","Regular","Extra"}) {
            JRadioButton rb = new JRadioButton(lvl, lvl.equals(sauce.getLevel()));
            rb.addActionListener(e -> { currentPizza.getSauce().setLevel(lvl); updateLabels(); });
            slGroup.add(rb);
            sauceLevelPanel.add(rb);
        }
        ingredientPanel.add(sauceLevelPanel);

        for(Ingredient ing : p.getIngredients()) {
            if(!relevant.isEmpty() && !relevant.contains(ing.getName())) continue;
            JPanel ingPanel = new JPanel();
            ingPanel.add(new JLabel(ing.getName()+":"));
            ButtonGroup g = new ButtonGroup();
            for(String lvl : new String[]{"None","Light","Regular","Extra"}) {
                JRadioButton rb = new JRadioButton(lvl, lvl.equals(ing.getLevel()));
                rb.addActionListener(e -> { ing.setLevel(lvl); updateLabels(); });
                g.add(rb);
                ingPanel.add(rb);
            }
            ingredientPanel.add(ingPanel);
        }

        ingredientPanel.revalidate();
        ingredientPanel.repaint();
        updateLabels();
    }

    private void addPizza() {
        if(currentPizza != null) {
            Pizza copy = currentPizza.clone();
            copy.setQuantity((Integer) quantitySpinner.getValue());
            order.addPizza(copy);
            pizzaListModel.addElement(copy);
            quantitySpinner.setValue(1);
            updateLabels();
        }
    }

    private void removePizza() {
        Pizza p = pizzaJList.getSelectedValue();
        if(p != null) {
            order.removePizza(p);
            pizzaListModel.removeElement(p);
            updateLabels();
        }
    }

    private void submitOrder() {
        new ReceiptPage(order);
        dispose();
    }

    private void updateLabels() {
        if(currentPizza != null)
            currentPizzaPriceLabel.setText("Current Pizza: $"+money.format(currentPizza.calculatePrice()));
        totalPriceLabel.setText("Total: $"+money.format(order.getTotalPrice()));
        totalTimeLabel.setText("Estimated Time: "+order.getTotalWaitTime()+" min");
        pizzaJList.repaint();
    }

    private class PizzaCellRenderer extends JLabel implements ListCellRenderer<Pizza> {
        @Override
        public Component getListCellRendererComponent(JList<? extends Pizza> list, Pizza pizza, int index, boolean isSelected, boolean cellHasFocus) {
            String text = pizza.getQuantity() + "x " + pizza.getName() + " (" + pizza.getSize() + ", " + pizza.getCrust() + ") - $" + money.format(pizza.calculatePrice());
            ArrayList<String> toppingsList = new ArrayList<>();
            for (Ingredient ing : pizza.getIngredients()) {
                String name = ing.getName();
                String level = ing.getLevel();
                if (!name.equals("Cheese") || !level.equals("Regular")) {
                    switch (level) {
                        case "None": toppingsList.add("No " + name); break;
                        case "Light": toppingsList.add("Light " + name); break;
                        case "Regular": toppingsList.add(name); break;
                        case "Extra": toppingsList.add("Extra " + name); break;
                    }
                }
            }
            if (pizza.getSauce() != null) {
                String sName = pizza.getSauce().getName();
                String sLevel = pizza.getSauce().getLevel();
                switch (sLevel) {
                    case "None": toppingsList.add("No " + sName); break;
                    case "Light": toppingsList.add("Light " + sName); break;
                    case "Regular": toppingsList.add(sName); break;
                    case "Extra": toppingsList.add("Extra " + sName); break;
                }
            }
            if (!toppingsList.isEmpty()) {
                text += " | " + String.join(", ", toppingsList);
            }
            setText(text);
            setOpaque(true);
            setBackground(isSelected ? Color.LIGHT_GRAY : Color.WHITE);
            setFont(new Font("Arial", Font.PLAIN, 14));
            return this;
        }
    }
}