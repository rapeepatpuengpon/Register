import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SelectionAdmin extends JFrame implements ActionListener{
    Container cp;
    JPanel panel,panel2;
    JLabel selection;
    JButton add,drop,logout;
    String currentuser;
    public SelectionAdmin(String user){
        this.currentuser = user;
        Intitial();
        setComponent();
        setupFrame();
    }
    public void Intitial(){
        cp = this.getContentPane();
        
        panel = new BackgroundPanel("./Image/BackgroundSelection3.png");
        panel.setLayout(null);

        
    }
    public void setComponent(){
        selection = new JLabel("Select");
        selection.setBounds(250, 30, 300, 50);
        selection.setFont(new Font("",Font.BOLD,30));

        add = new JButton("AddSubject");
        add.setBounds(140, 100, 130, 50);
        add.setFont(new Font("",Font.BOLD,12));

        drop = new JButton("Repeal");
        drop.setBounds(320, 100, 130, 50);
        drop.setFont(new Font("",Font.BOLD,12));

        logout = new JButton("Logout");
        logout.setBounds(510, 20, 60, 20);
        logout.setFont(new Font("", Font.BOLD, 5));

        panel.setBounds(0,0,600,300);
        panel.add(selection);
        panel.add(add);
        panel.add(drop);
        panel.add(logout);
        add.addActionListener(this);
        drop.addActionListener(this);
        logout.addActionListener(this);
        cp.add(panel);

    }
    public void setupFrame(){
        this.setSize(600, 250);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent e) { 
         if (e.getSource() == logout) {
            int confirm = JOptionPane.showConfirmDialog(this, "Do you want to logout?", "Confirm Logout",JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose();
                new Login();
            }
        }
        if (e.getSource()==add) {
            add.setEnabled(false);
            new AddAdmin(this);
        }
        if (e.getSource()==drop) {
            drop.setEnabled(false);
            new RemoveSubjectAdmin(this);
        }
    }
    public void enableAddButton() {
        add.setEnabled(true);
        drop.setEnabled(true);
    }
}