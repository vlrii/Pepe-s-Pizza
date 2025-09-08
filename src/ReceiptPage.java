import javax.swing.*;
import java.awt.*;

public class ReceiptPage extends JFrame {
    public ReceiptPage(Order order) {
        setTitle("Receipt");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextArea receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        StringBuilder sb = new StringBuilder();
        sb.append("Customer: ").append(order.getCustomerName()).append("\n\n");
        for (Pizza p : order.getPizzas()) {
            sb.append(p.getName())
                    .append(" (").append(p.getSize()).append(", ").append(p.getCrust()).append(") - $")
                    .append(p.calculatePrice())
                    .append("\nToppings: ");
            for (Ingredient ing : p.getIngredients()) {
                if (!ing.getLevel().equals("None")) sb.append(ing.getName()).append("(").append(ing.getLevel()).append(") ");
            }
            sb.append("\n\n");
        }
        sb.append("Total: $").append(order.getTotalPrice()).append("\n");
        sb.append("Estimated Time: ").append(order.getTotalWaitTime()).append(" min");
        receiptArea.setText(sb.toString());

        add(new JScrollPane(receiptArea), BorderLayout.CENTER);

        setVisible(true);
    }
}