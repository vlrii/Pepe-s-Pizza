import javax.swing.*;
import java.awt.*;

public class Name extends JPanel {
    PepesPizza gui;
    JTextField nameField;
    JButton next;

    public Name(PepesPizza app) {
        this.gui = app;
        setLayout(new BorderLayout());

        JPanel top = new JPanel();
        top.add(new JLabel("Name:"));
        nameField = new JTextField(15);
        top.add(nameField);
        add(top, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        next = new JButton("Next");
        bottom.add(next);
        add(bottom, BorderLayout.SOUTH);

        next.addActionListener(e -> {
            gui.customerName = nameField.getText();
            gui.showPage("PizzaType");
        });
    }

    public void clearName() {
        nameField.setText("");
    }
}



