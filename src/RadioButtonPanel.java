import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RadioButtonDemo extends JPanel implements ActionListener {

    private JRadioButton catRB;
    private JRadioButton dogRB;
    private JRadioButton fishRB;


    public RadioButtonDemo() {
        setPreferredSize(new Dimension(100,150));
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        catRB = new JRadioButton("Cat");
        dogRB = new JRadioButton("Dog");
        fishRB = new JRadioButton("Fish");

        //used to provide mutual exclusion
        ButtonGroup bg = new ButtonGroup();
        bg.add(catRB);
        bg.add(dogRB);
        bg.add(fishRB);
        catRB.setSelected(true);

        //Bind the listeners
        catRB.addActionListener(this);
        dogRB.addActionListener(this);
        fishRB.addActionListener(this);

        //Put on the panel
        add(catRB);
        add(dogRB);
        add(fishRB);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==catRB){
            System.out.println("Meow");
        }
        if (e.getSource()==dogRB){
            System.out.println("Woof");
        }
        if (e.getSource()==fishRB){
            System.out.println("Glub...Glub");
        }
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Radio Button Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new RadioButtonDemo();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }

}
