import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
 
import javax.swing.*;
 
public class SelectionMenu extends JFrame implements ActionListener {
    private Container cp;
    private JPanel panel;
    private JLabel selection;
    private JButton add, mysubject, drop, logout;
    private String currentuser;
 
    public SelectionMenu(String user) {
        this.currentuser = user;
        Intitial();
        setComponent();
        setupFrame();
    }
 
    private void Intitial() {
        cp = this.getContentPane();
 
        panel = new BackgroundPanel("./Image/BackgroundSelection3.png");
        panel.setLayout(null);
    }
    private void setComponent() {
        selection = new JLabel("Select");
        selection.setBounds(250, 30, 300, 50);selection.setForeground(Color.BLACK);
        selection.setFont(new Font("", Font.BOLD, 30));
        panel.add(selection);
        
        ImageIcon iconRegis = new ImageIcon("./Image/button_registration.png");
        Image scaled = iconRegis.getImage().getScaledInstance(150, 60, Image.SCALE_SMOOTH);
        add = new JButton(new ImageIcon(scaled));
        add.setBounds(70, 120, 130, 50);
        add.setFont(new Font("", Font.BOLD, 12));
        
        ImageIcon iconRepeal = new ImageIcon("./Image/button_repeal.png");
        Image scaledRepeal = iconRepeal.getImage().getScaledInstance(140, 60, Image.SCALE_SMOOTH);
        drop = new JButton(new ImageIcon(scaledRepeal));
        drop.setBounds(230, 120, 130, 50);
        drop.setFont(new Font("", Font.BOLD, 12));
        
        ImageIcon iconMylist = new ImageIcon("./Image/button_my-list.png");
        Image scaledMylist = iconMylist.getImage().getScaledInstance(140, 60, Image.SCALE_SMOOTH);
        mysubject = new JButton(new ImageIcon(scaledMylist));
        mysubject.setBounds(390, 120, 130, 50);
        mysubject.setFont(new Font("", Font.BOLD, 12));
        
        ImageIcon iconLogout = new ImageIcon("./Image/LogoutIcon.png");
        Image scaledLogout = iconLogout.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        logout = new JButton(new ImageIcon(scaledLogout));
        logout.setBounds(540, 10, 30, 30);
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
         cp.add(panel);
 
    }
    private void setupFrame() {
        this.setSize(600, 250);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private boolean checkrepeal(String user) {
        boolean nosubject = false;
        try (BufferedReader br = new BufferedReader(new FileReader("Lib/sub_regis.csv"))) {
            String s;
            while ((s = br.readLine()) != null) {
                String data[] = s.split(",");
                if (data[0].equals(user)) {
                    nosubject = false;
                    return nosubject;
                }
            }
        } catch (Exception eboo) {
            eboo.printStackTrace();
        }
        return !nosubject;
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
            mysubject.setEnabled(false);
            drop.setEnabled(false);
            new Add(this, currentuser);
        }
        if (e.getSource() == mysubject) {
            mysubject.setEnabled(false);
            add.setEnabled(false);
            drop.setEnabled(false);
            new MyList(this, currentuser);
        }
        if (e.getSource() == drop) {
            if (checkrepeal(currentuser)) {
                JOptionPane.showMessageDialog(this, "Please Add Subject");
            } else {
                drop.setEnabled(false);
                mysubject.setEnabled(false);
                add.setEnabled(false);
                new Repeal(this, currentuser);
            }
 
        }
    }
 
    public void enableAddButton() {
        add.setEnabled(true);
        mysubject.setEnabled(true);
        drop.setEnabled(true);
    }
}