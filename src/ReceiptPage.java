import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class ReceiptPage extends JFrame {
    private static final DecimalFormat MONEY = new DecimalFormat("0.00");

    public ReceiptPage(Order order) {
        setTitle("Order Receipt");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JTextArea receiptArea = new JTextArea();
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        receiptArea.setEditable(false);

        StringBuilder sb = new StringBuilder();
        sb.append("Customer: ").append(order.getCustomerName()).append("\n\n");

        for (Pizza p : order.getPizzas()) {
            sb.append(p.getQuantity())
                    .append("x ")
                    .append(p.getName())
                    .append(" (")
                    .append(p.getSize())
                    .append(", ")
                    .append(p.getCrust())
                    .append(") - $")
                    .append(MONEY.format(p.calculatePrice()));

            StringBuilder toppings = new StringBuilder();
            for (Ingredient ing : p.getIngredients()) {
                if (isChangedFromDefault(ing)) {
                    toppings.append(ing.getName())
                            .append("(")
                            .append(ing.getLevel())
                            .append(") ");
                }
            }

            if (toppings.length() > 0) {
                sb.append(" | ").append(toppings.toString().trim());
            }
            sb.append("\n");
        }

        sb.append("\nTotal: $").append(MONEY.format(order.getTotalPrice()));
        sb.append("\nEstimated Time: ").append(order.getTotalWaitTime()).append(" min");

        receiptArea.setText(sb.toString());
        add(new JScrollPane(receiptArea), BorderLayout.CENTER);

        JButton backBtn = new JButton("Back to Start");
        backBtn.addActionListener(e -> {
            dispose();
            new NamePage();
        });
        add(backBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    private boolean isChangedFromDefault(Ingredient ing) {
        if (ing.getName().equals("Cheese")) {
            return !ing.getLevel().equals("Regular");
        } else {
            return !ing.getLevel().equals("None");
        }
    }
}