import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SelectionMenu extends JFrame implements ActionListener {
    Container cp;
    JPanel panel,panel2;
    JLabel selection;
    JButton add, mysubject, drop, logout;
    String currentuser;

    public SelectionMenu(String user) {
        this.currentuser = user;
        Intitial();
        setComponent();
        setupFrame();
    }

    public void Intitial() {
        cp = this.getContentPane();
        cp.setBackground(Color.WHITE);
        cp.setLayout(null);
        panel = new JPanel();
        panel.setLayout(null);

        panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setBackground(Color.RED);
    }

    public void setComponent() {
        panel2.setBounds(0, 0, 600, 90);
        panel2.setBackground(Color.blue.brighter());
        selection = new JLabel("Select");
        selection.setBounds(250, 20, 300, 50);
        selection.setFont(new Font("", Font.BOLD, 30));
        panel2.add(selection);

        add = new JButton("Registration");
        add.setBounds(70, 120, 130, 50);
        add.setFont(new Font("", Font.BOLD, 12));

        drop = new JButton("Repeal");
        drop.setBounds(230, 120, 130, 50);
        drop.setFont(new Font("", Font.BOLD, 12));

        mysubject = new JButton("My list");
        mysubject.setBounds(390, 120, 130, 50);
        mysubject.setFont(new Font("", Font.BOLD, 12));

        logout = new JButton("Logout");
        logout.setBounds(500, 10, 50, 20);
        logout.setFont(new Font("", Font.BOLD, 7));

        panel.setBounds(0, 0, 600, 300);
        panel.add(add);
        panel.add(drop);
        panel.add(mysubject);
        panel.add(logout);


      

        add.addActionListener(this);
        mysubject.addActionListener(this);
        drop.addActionListener(this);
        logout.addActionListener(this);
        cp.add(panel2);cp.add(panel);

    }

    public void setupFrame() {

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
        if (e.getSource() == add) {
            add.setEnabled(false);
            new Add(this, currentuser);
        }
        if (e.getSource() == mysubject) {
            mysubject.setEnabled(false);
            new MyList(this, currentuser);
        }
        if (e.getSource() == drop) {
            drop.setEnabled(false);
            new Repeal(this, currentuser);
        }
    }

    public void enableAddButton() {
        add.setEnabled(true);
        mysubject.setEnabled(true);
        drop.setEnabled(true);
    }
}