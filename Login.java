import java.awt.*;
import javax.swing.*;
public class Login extends JFrame {

    Container cp;
    JPanel p;
    JLabel l;
    JLabel l1;
    JTextField t1;
    JLabel l2;
    JPasswordField t2;
    JButton b;

    public Login() {
        Intitial();
        setComponent();
        Finally();
    }

    public void Intitial() {
        cp = this.getContentPane();
        cp.setBackground(Color.LIGHT_GRAY);
        cp.setLayout(null);
        p = new JPanel();
        p.setLayout(null);
    }

    public void setComponent(){
        l = new JLabel("Registration");
        l.setBounds(0, 10, 300, 50);
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setFont(new Font("", Font.BOLD, 25));


        l1 = new JLabel("USER");
        t1 = new JTextField();
        l1.setBounds(50, 50, 50, 50);
        t1.setBounds(50,90, 200, 30);        
        l1.setFont(new Font("", Font.BOLD, 16));
        

        l2 = new JLabel("Password");
        t2 = new JPasswordField();
        l2.setBounds(50, 120, 100, 50);
        t2.setBounds(50,160, 200, 30);
        l2.setFont(new Font("", Font.BOLD, 16));

        b = new JButton("Login");
        b.setBounds(100, 220, 100, 40);

        p.add(l);
        p.add(l1);
        p.add(t1);
        p.add(l2);
        p.add(t2);
        p.add(b);

        p.setBounds(95, 80, 300, 300);
        cp.add(p);
    }

    public void Finally() {
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    
        /*public static void main(String[] args) {
        Container cp = f.getContentPane();
        cp.setBackground(Color.LIGHT_GRAY);
        cp.setLayout(null);

        JPanel p = new JPanel();
        p.setLayout(null);
        
        JLabel l = new JLabel("Registration");
        l.setBounds(0, 10, 300, 50);
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setFont(new Font("", Font.BOLD, 25));


        JLabel l1 = new JLabel("USER");
        JTextField t1 = new JTextField();
        l1.setBounds(50, 50, 50, 50);
        t1.setBounds(50,90, 200, 30);        
        l1.setFont(new Font("", Font.BOLD, 16));
        

        JLabel l2 = new JLabel("Password");
        JTextField t2 = new JTextField();
        l2.setBounds(50, 120, 100, 50);
        t2.setBounds(50,160, 200, 30);
        l2.setFont(new Font("", Font.BOLD, 16));

        JButton b = new JButton("Login");
        b.setBounds(100, 220, 100, 40);

        p.add(l);
        p.add(l1);
        p.add(t1);
        p.add(l2);
        p.add(t2);
        p.add(b);

        p.setBounds(95, 80, 300, 300);
        cp.add(p);

        f.setSize(500, 500);
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }*/
}
