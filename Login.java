import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
public class Login extends JFrame implements ActionListener {

    Container cp;
    JPanel p;
    JLabel regis;
    JLabel user;
    JTextField textuser;
    JLabel pass;
    JPasswordField textpass;
    JButton login;
    JLabel warningLabel;

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

        warningLabel = new JLabel();
        warningLabel.setBounds(50,120,190,160);
        warningLabel.setFont(new Font("", Font.BOLD, 11));

 
        p.add(regis);
        p.add(user);
        p.add(textuser);
        p.add(pass);
        p.add(textpass);
        p.add(login);
        p.add(warningLabel);
        login.addActionListener(this);

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
        return textuser.getText();
    }
    public String getpassword(){
        return new String(textpass.getPassword());
         
    }
    public void showWarning(String msg){
        warningLabel.setText(msg);
    }
    public void login(){
        String user = getusername();
        String pass = getpassword();
    if (user.isEmpty() || pass.isEmpty()) {
        showWarning("Username or Password isempty!!!");
        return;
    }
    if (checklogin(user,pass)) {
        warningLabel.setForeground(Color.GREEN);
        showWarning("Login complete");
        this.dispose();
        new SelectionMenu(user);
    }
    else{
        warningLabel.setForeground(Color.RED);
        showWarning("invallid");
     }
    }
      public boolean checklogin(String user, String pass){
        try(BufferedReader br = new BufferedReader(new FileReader("./Lib/Nisit.csv"))){
            br.readLine();
            String s;
            while ((s = br.readLine()) != null) {
                String[] data = s.split(",");

                if(data[0].equals(user) && data[2].equals(pass)) {
                    return true;
                }
            }
        } catch (Exception e1) {
          e1.printStackTrace();
        }
        return false;
    }
    }