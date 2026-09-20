import java.awt.*;
import javax.swing.*;

public class Registration1 {
    public static void main(String[] args) {
    JFrame f = new JFrame("Registration");
    Container cp = f.getContentPane();
    cp.setLayout(null);
    JPanel p = new JPanel();
    p.setLayout(null);

    JLabel l = new JLabel("Registration");
    l.setBounds(400, 20, 200, 50);
    l.setFont(new Font("", Font.BOLD, 35));

    JLabel l1 = new JLabel("Course Code");
    JTextField t = new JTextField();
    l1.setBounds(90, 100, 200, 50);
    t.setBounds(90,150,300,30);
    l1.setFont(new Font("", Font.BOLD, 25));

    JButton b = new JButton("Add");
    b.setBounds(350, 240, 100, 50);

    JButton b1 = new JButton("Confirm");
    b1.setBounds(470, 240, 100, 50);

    p.add(l);
    p.add(l1);
    p.add(t);
    p.add(b);
    p.add(b1);

    p.setBackground(Color.PINK);
    p.setBounds(250, 350, 1000, 400);
    cp.add(p);




    f.setSize(1500, 1000);
    f.setLocationRelativeTo(null);
    f.setResizable(false);
    f.setVisible(true);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
