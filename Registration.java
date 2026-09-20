import java.awt.*;
import javax.swing.*;
public class Registration {
        public static void main(String[] args) {
        JFrame f = new JFrame("Vehicle registration");
        Container cp = f.getContentPane();
        cp.setLayout(null);
        
        JLabel l = new JLabel("Registration");
        l.setBounds(430, 300, 190, 50);
        //l.setSize(100);
        l.setFont(new Font("", Font.BOLD, 30));


        JLabel l1 = new JLabel("USER");
        JTextField t1 = new JTextField();
        l1.setBounds(350, 390, 80, 50);
        t1.setBounds(350,440, 200, 30);        
        l1.setFont(new Font("", Font.BOLD, 19));
        

        JLabel l2 = new JLabel("Password");
        JTextField t2 = new JTextField();
        l2.setBounds(350, 500, 100, 50);
        t2.setBounds(350,550, 200, 30);
        l2.setFont(new Font("", Font.BOLD, 19));

        JButton b = new JButton("Login");
        b.setBounds(350, 620, 100, 50);

        cp.add(l);
        cp.add(l1);
        cp.add(t1);
        cp.add(l2);
        cp.add(t2);
        cp.add(b);


        f.setSize(1000, 1000);
        f.setLocationRelativeTo(null);
        //f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
}



