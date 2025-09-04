import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Ingredients extends JPanel {
    PepesPizza gui;
    Map<String, ButtonGroup> ingredientGroups;
    ButtonGroup crustGroup, sizeGroup;
    JPanel centerPanel;

    public Ingredients(PepesPizza gui) {
        this.gui = gui;
        setLayout(new BorderLayout());

        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0, 1));
        add(new JScrollPane(centerPanel), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton back = new JButton("Back");
        JButton submit = new JButton("Next");
        bottom.add(back);
        bottom.add(submit);
        add(bottom, BorderLayout.SOUTH);

        back.addActionListener(e -> gui.showPage("PizzaType"));
        submit.addActionListener(e -> {
            saveSelections();
            gui.orderSummaryPage.showSummary(gui.customerName, gui.selectedPizza, gui.currentOrder);
            gui.showPage("OrderSummary");
        });
    }

    public Map<String,String> getPizzaDefaults(String pizza) {
        Map<String,String> defaults = new HashMap<>();

        String[][] data;

        if (pizza.equals("Cheese")) {
            data = new String[][] {{"Cheese","Regular"}, {"Sauce","Regular"}, {"Crust","Thin"}, {"Size","Medium"}};
        } else if (pizza.equals("Pepperoni")) {
            data = new String[][] {{"Cheese","Regular"}, {"Sauce","Regular"}, {"Pepperoni","Regular"}, {"Crust","Thin"}, {"Size","Medium"}};
        } else if (pizza.equals("Sausage")) {
            data = new String[][] {{"Cheese","Regular"}, {"Sauce","Regular"}, {"Sausage","Regular"}, {"Crust","Thick"}, {"Size","Large"}};
        } else if (pizza.equals("Meat Lover's")) {
            data = new String[][] {{"Cheese","Regular"}, {"Sauce","Regular"}, {"Pepperoni","Regular"}, {"Sausage","Regular"}, {"Crust","Thick"}, {"Size","Large"}};
        } else {
            data = new String[0][0];
        }

        for (int i = 0; i < data.length; i++) {
            defaults.put(data[i][0], data[i][1]);
        }

        return defaults;
    }


    public void loadPizza(String pizza, Map<String,String> defaults) {
        centerPanel.removeAll();
        ingredientGroups = new HashMap<>();

        centerPanel.add(new JLabel("Customize:"));

        String[] order = {"Size","Crust","Sauce","Cheese","Pepperoni","Sausage"};
        for (String ing : order) {
            if (!defaults.containsKey(ing)) continue;

            centerPanel.add(new JLabel(ing));

            if (ing.equals("Size")) {
                JRadioButton small = new JRadioButton("Small");
                JRadioButton med = new JRadioButton("Medium");
                JRadioButton large = new JRadioButton("Large");
                sizeGroup = new ButtonGroup();
                sizeGroup.add(small); sizeGroup.add(med); sizeGroup.add(large);
                switch (defaults.get("Size")) {
                    case "Small": small.setSelected(true); break;
                    case "Large": large.setSelected(true); break;
                    default: med.setSelected(true);
                }
                JPanel row = new JPanel(); row.add(small); row.add(med); row.add(large); centerPanel.add(row);
            }

            else if (ing.equals("Crust")) {
                JRadioButton thin = new JRadioButton("Thin");
                JRadioButton thick = new JRadioButton("Thick");
                crustGroup = new ButtonGroup();
                crustGroup.add(thin); crustGroup.add(thick);
                if (defaults.get("Crust").equals("Thin")) thin.setSelected(true); else thick.setSelected(true);
                JPanel row = new JPanel(); row.add(thin); row.add(thick); centerPanel.add(row);
            }

            else {
                JRadioButton none = new JRadioButton("None");
                JRadioButton reg = new JRadioButton("Regular");
                JRadioButton extra = new JRadioButton("Extra");
                ButtonGroup group = new ButtonGroup();
                group.add(none); group.add(reg); group.add(extra);
                switch (defaults.get(ing)) {
                    case "None": none.setSelected(true); break;
                    case "Extra": extra.setSelected(true); break;
                    default: reg.setSelected(true);
                }
                JPanel row = new JPanel(); row.add(none); row.add(reg); row.add(extra); centerPanel.add(row);
                ingredientGroups.put(ing, group);
            }
        }

        revalidate();
        repaint();
    }

    private void saveSelections() {
        for (String ing : ingredientGroups.keySet()) {
            ButtonGroup g = ingredientGroups.get(ing);
            for (Enumeration<AbstractButton> e = g.getElements(); e.hasMoreElements(); ) {
                JRadioButton b = (JRadioButton) e.nextElement();
                if (b.isSelected()) gui.currentOrder.put(ing, b.getText());
            }
        }
        for (Enumeration<AbstractButton> e = crustGroup.getElements(); e.hasMoreElements(); ) {
            JRadioButton b = (JRadioButton) e.nextElement(); if (b.isSelected()) gui.currentOrder.put("Crust", b.getText());
        }
        for (Enumeration<AbstractButton> e = sizeGroup.getElements(); e.hasMoreElements(); ) {
            JRadioButton b = (JRadioButton) e.nextElement(); if (b.isSelected()) gui.currentOrder.put("Size", b.getText());
        }
    }
}






