import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Login extends JFrame implements ActionListener {

    Container cp;
    JPanel p;
    JLabel regis;
    JLabel user;
    JTextField textuser;
    JLabel pass;
    JPasswordField textpass;
    JButton login;

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
        regis = new JLabel("Registration");
        regis.setBounds(0, 10, 300, 50);
        regis.setHorizontalAlignment(JLabel.CENTER);
        regis.setFont(new Font("", Font.BOLD, 25));


        user = new JLabel("USER");
        textuser = new JTextField();
        user.setBounds(50, 50, 50, 50);
        textuser.setBounds(50,90, 200, 30);        
        user.setFont(new Font("", Font.BOLD, 16));
        

        pass = new JLabel("Password");
        textpass = new JPasswordField();
        pass.setBounds(50, 120, 100, 50);
        textpass.setBounds(50,160, 200, 30);
        pass.setFont(new Font("", Font.BOLD, 16));

        login = new JButton("Login");
        login.setBounds(100, 220, 100, 40);

        p.add(regis);
        p.add(user);
        p.add(textuser);
        p.add(pass);
        p.add(textpass);
        p.add(login);

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

    @Override
    public void actionPerformed(ActionEvent e) {
       login();
    }
    public String getusername(){
        return "";
    }
    public String getpassword(){
        return "";
    }
    public void login(){
        
    }
    public boolean checklogin(){
        return false;
    }
}