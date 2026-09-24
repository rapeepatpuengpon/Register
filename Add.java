import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class Add extends JFrame {

    Container cp ;
    JPanel p ;
    JPanel pbutton ;
    JLabel regis;
    JLabel code;
    JTextField textcode;
    JButton add;
    JButton confirm; 
    SelectionMenu menu;


    public Add(SelectionMenu menu){
        this.menu = menu;
        Intitial();
        setComponent();
        setupFrame();
    }
    public void Intitial(){
        cp = this.getContentPane();
        cp.setLayout(new GridBagLayout());
        cp.setBackground(Color.WHITE);
        p = new JPanel();
        p.setLayout(new GridBagLayout());
        p.setPreferredSize(new Dimension(600, 400));
        p.setBackground(Color.WHITE);
    }
    public void setComponent(){
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(15, 15, 15, 15);
        regis = new JLabel("Registration");
        regis.setFont(new Font("SansSerif", Font.BOLD, 35));
        g.gridx = 0; 
        g.gridy = 0; 
        g.gridwidth = 2; 
        p.add(regis, g);

        g.gridwidth = 1;

        code = new JLabel("Course Code :");
        code.setFont(new Font("SansSerif", Font.BOLD, 25));
        g.gridx = 0; 
        g.gridy = 1; 
        g.anchor = GridBagConstraints.EAST; 
        p.add(code, g);

        JTextField textcode= new JTextField(10); 
        textcode.setFont(new Font("SansSerif", Font.PLAIN, 25));
        g.gridx = 1; 
        g.gridy = 1; 
        g.anchor = GridBagConstraints.WEST; 
        p.add(textcode, g);

        pbutton = new JPanel();
        pbutton.setBackground(Color.WHITE); // สีเดียวกับพื้นหลัง Panel p
        
        JButton add = new JButton("Add");
        add.setFont(new Font("SansSerif", Font.BOLD, 17));
        
        JButton confirm = new JButton("Confirm");
        confirm.setFont(new Font("SansSerif", Font.BOLD, 17));
        pbutton.add(add);
        pbutton.add(confirm);
        g.gridx = 0; 
        g.gridy = 2; 
        g.gridwidth = 2; // สั่งให้กินพื้นที่ 2 คอลัมน์เพื่อให้อยู่กึ่งกลางพอดี
        g.anchor = GridBagConstraints.CENTER;
        p.add(pbutton, g);
        cp.add(p);
    }

    public void setupFrame(){
        this.setSize(1000, 800); 
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
        public void windowClosed(WindowEvent e) {
            menu.enableAddButton();
        }
        });
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
   
}