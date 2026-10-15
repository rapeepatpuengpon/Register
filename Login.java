import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class Login extends JFrame implements ActionListener {

    Container cp;
    JPanel p, bgpanel;
    JLabel regis;
    JLabel user;
    JTextField textuser;
    JLabel pass, show;
    JPasswordField textpass;
    JCheckBox showpass;
    JButton login;
    JLabel warningLabel;

    public Login() {
        Intitial();
        setComponent();
        Finally();
    }

    public void Intitial() {
        BackgroundPanel bgPanel = new BackgroundPanel("./Image/BackgroundLogin.png");
        bgPanel.setLayout(null);
        setContentPane(bgPanel);
        cp = this.getContentPane();
        p = new JPanel();
        p.setBackground(Color.WHITE);
        p.setLayout(null);

    }

    public void setComponent() {
        regis = new JLabel("Registration");
        regis.setBounds(0, 1, 300, 50);
        regis.setHorizontalAlignment(JLabel.CENTER);
        regis.setFont(new Font("", Font.BOLD, 25));

        user = new JLabel("USER");
        textuser = new JTextField();
        user.setBounds(50, 50, 50, 50);
        textuser.setBounds(50, 90, 200, 30);
        user.setFont(new Font("", Font.BOLD, 16));

        pass = new JLabel("Password");
        textpass = new JPasswordField();
        pass.setBounds(50, 120, 100, 50);
        textpass.setBounds(50, 160, 200, 30);
        pass.setFont(new Font("", Font.BOLD, 16));
        
        ImageIcon iconlogin = new ImageIcon("./Image/LoginIcon.png");
        Image scaled = iconlogin.getImage().getScaledInstance(120, 60, Image.SCALE_SMOOTH);
        login = new JButton(new ImageIcon(scaled));
        login.setBounds(100, 240, 100, 40);

        showpass = new JCheckBox();
        showpass.setBounds(46, 190, 20, 20);
        show = new JLabel("show password");
        show.setBounds(70, 190, 100, 20);
        show.setFont(new Font("", Font.BOLD, 9));

        warningLabel = new JLabel();
        warningLabel.setBounds(50, 120, 190, 160);
        warningLabel.setFont(new Font("", Font.BOLD, 11));

        p.add(regis);
        p.add(user);
        p.add(textuser);
        p.add(pass);
        p.add(textpass);
        p.add(login);
        p.add(warningLabel);
        p.add(showpass);
        p.add(show);
        login.addActionListener(this);
        showpass.addActionListener(this);
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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showpass) {
            if (showpass.isSelected()) {
                textpass.setEchoChar((char) 0);
            } else
                textpass.setEchoChar('•');
        }
        if (e.getSource() == login) {
            login();
        }
    }

    public String getusername() {
        return textuser.getText();
    }

    public String getpassword() {
        return new String(textpass.getPassword());
    }

    public void login() {
        String user = getusername();
        String pass = getpassword();
        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username or Password isempty!!!");
            return;
        }
        if (checklogin(user, pass)) {
            this.dispose();
            new SelectionMenu(user);
        } else if (checkloginAdmin(user, pass)) {
            this.dispose();
            new SelectionAdmin(user);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid");
        }
    }

    public boolean checklogin(String user, String pass) {
        try (BufferedReader br = new BufferedReader(new FileReader("./Lib/Nisit.csv"))) {
            br.readLine();
            String s;
            while ((s = br.readLine()) != null) {
                String[] data = s.split(",");

                if (data[0].equals(user) && data[2].equals(pass)) {
                    return true;
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public boolean checkloginAdmin(String user, String pass) {
        try (BufferedReader br = new BufferedReader(new FileReader("./Lib/Admin.csv"))) {
            br.readLine();
            String s;
            while ((s = br.readLine()) != null) {
                String[] data = s.split(",");
                if (data[0].equals(user) && data[2].equals(pass)) {
                    return true;
                }
            }
        } catch (Exception e) {

        }
        return false;
    }
}