import javax.swing.*;
import java.awt.*;

public class NamePage extends JFrame {
    public NamePage() {
        setTitle("Customer Name");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextField nameField = new JTextField();
        JButton nextButton = new JButton("Next");

        nextButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                Order order = new Order(name);
                new PizzaEditor(order);
                dispose();
            }
        });

        add(new JLabel("Name:"), BorderLayout.NORTH);
        add(nameField, BorderLayout.CENTER);
        add(nextButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}