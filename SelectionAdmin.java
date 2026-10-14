import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SelectionAdmin extends JFrame implements ActionListener{
    Container cp;
    JPanel panel;
    JLabel selection;
    JButton add,drop;
    String currentuser;
    public SelectionAdmin(String user){
        this.currentuser = user;
        Intitial();
        setComponent();
        setupFrame();
    }
    public void Intitial(){
        cp = this.getContentPane();
        cp.setBackground(Color.WHITE);
        cp.setLayout(null);
        panel = new JPanel();
        panel.setLayout(null);
    }
    public void setComponent(){
        selection = new JLabel("Select");
        selection.setBounds(245, 20, 300, 50);
        selection.setFont(new Font("",Font.BOLD,30));

        add = new JButton("AddSubject");
        add.setBounds(140, 100, 130, 50);
        add.setFont(new Font("",Font.BOLD,12));

        drop = new JButton("Repeal");
        drop.setBounds(310, 100, 130, 50);
        drop.setFont(new Font("",Font.BOLD,12));

        panel.setBounds(0,0,600,300);
        panel.add(selection);
        panel.add(add);
        panel.add(drop);
        add.addActionListener(this);
        drop.addActionListener(this);
        cp.add(panel);

    }
    public void setupFrame(){

        this.setSize(600, 250);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent e) { 
        if (e.getSource()==add) {
            add.setEnabled(false);
            new AddAdmin(this);
        }
        if (e.getSource()==drop) {
            drop.setEnabled(false);
            new RemoveSubjectAdmin(this,currentuser);
        }
    }
    public void enableAddButton() {
        add.setEnabled(true);
        drop.setEnabled(true);
    }
}