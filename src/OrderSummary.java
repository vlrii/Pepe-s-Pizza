import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class OrderSummary extends JPanel {
    PepesPizza gui;
    JTextArea summaryArea;
    JButton back, submit;

    public OrderSummary(PepesPizza gui) {
        this.gui = this.gui;
        setLayout(new BorderLayout());

        summaryArea = new JTextArea();
        summaryArea.setEditable(false);
        add(new JScrollPane(summaryArea), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        back = new JButton("Back");
        submit = new JButton("Submit Order");
        bottom.add(back);
        bottom.add(submit);
        add(bottom, BorderLayout.SOUTH);

        back.addActionListener(e -> gui.showPage("Ingredients"));

        submit.addActionListener(e -> {
            JOptionPane.showMessageDialog(gui, "Order placed");
            gui.currentOrder.clear();
            gui.customerName = "";
            gui.showPage("Name");
        });
    }

    public void showSummary(String customerName, String pizza, Map<String,String> order) {
        summaryArea.setText("");
        summaryArea.append("Customer: " + customerName + "\n");
        summaryArea.append("Pizza: " + pizza + "\n\n");

        String[] displayOrder = {"Size","Crust","Sauce","Cheese","Pepperoni","Sausage"};

        for (String ing : displayOrder) {
            if (order.containsKey(ing)) {
                summaryArea.append(ing + ": " + order.get(ing) + "\n");
            }
        }
    }
}



