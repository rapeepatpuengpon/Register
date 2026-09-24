import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SelectionMenu extends JFrame implements ActionListener{
    Container cp;
    JPanel panel;
    JLabel selection;
    JButton add,mysubject,drop;
    public SelectionMenu(){
        Intitial();
        setComponent();
        Finally();
    }
    public void Intitial(){
        cp = this.getContentPane();
        cp.setBackground(Color.LIGHT_GRAY);
        cp.setLayout(null);
        panel = new JPanel();
        panel.setLayout(null);
    }
    public void setComponent(){
        selection = new JLabel("รายการเลือก");
        selection.setBounds(200, 20, 300, 50);
        selection.setFont(new Font("",Font.BOLD,30));

        add = new JButton("ลงทะเบียนเรียน");
        add.setBounds(70, 100, 130, 50);
        add.setFont(new Font("",Font.BOLD,12));

        drop = new JButton("ถอนรายวิชา");
        drop.setBounds(230, 100, 130, 50);
        drop.setFont(new Font("",Font.BOLD,12));

        mysubject = new JButton("ผลลงทะเบียน");
        mysubject.setBounds(390, 100, 130, 50);
        mysubject.setFont(new Font("",Font.BOLD,12));
        

        panel.setBounds(0,0,600,300);
        panel.add(selection);
        panel.add(add);
        panel.add(drop);
        panel.add(mysubject);
        add.addActionListener(this);
        cp.add(panel);

    }
    public void Finally(){
        this.setSize(600, 250);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void actionPerformed(ActionEvent e) {
       add();
    }
    public void add(){
       new Add();
    }
   
    
}
