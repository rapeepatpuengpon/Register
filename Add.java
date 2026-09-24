import java.awt.event.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;

public class Add extends JFrame implements ActionListener {

    Container cp ;
    JPanel p ;
    JPanel pbutton ;
    JLabel regis;
    JLabel code;
    JTextField textcode;
    JLabel warninglabel;
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
        regis.setFont(new Font("", Font.BOLD, 35));
        g.gridx = 0; 
        g.gridy = 0; 
        g.gridwidth = 2; 
        p.add(regis, g);

        g.gridwidth = 1;

        code = new JLabel("Course Code :");
        code.setFont(new Font("", Font.BOLD, 25));
        g.gridx = 0; 
        g.gridy = 1; 
        g.anchor = GridBagConstraints.EAST; 
        p.add(code, g);

        textcode= new JTextField(10); 
        textcode.setFont(new Font("", Font.PLAIN, 25));
        g.gridx = 1; 
        g.gridy = 1; 
        g.anchor = GridBagConstraints.WEST; 
        p.add(textcode, g);

        warninglabel = new JLabel("warning");
        warninglabel.setFont(new Font("", Font.BOLD, 12));
        g.gridx = 1;
        g.gridy = 2;
        p.add(warninglabel,g);

        pbutton = new JPanel();
        pbutton.setBackground(Color.WHITE); 
        add = new JButton("Add");
        add.setFont(new Font("", Font.BOLD, 17));

        confirm = new JButton("Confirm");
        confirm.setFont(new Font("", Font.BOLD, 17));
        pbutton.add(add);
        pbutton.add(confirm);
        g.gridx = 0; 
        g.gridy = 3; 
        g.gridwidth = 2; 
        g.anchor = GridBagConstraints.CENTER;
        p.add(pbutton, g);
        add.addActionListener(this);
        confirm.addActionListener(this);
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

    @Override
    public void actionPerformed(ActionEvent e1) {
        add();
    }

    public String getCode(){
        return textcode.getText();
    }
    public void showWarning(String msg){
        warninglabel.setText(msg);
    }
    public void add(){
        String code = getCode();
        if (code.isEmpty()) {
            showWarning("Course Code is Emply!!!");
            return;
        }
        if(checkRegis(code)) {
            showWarning("Duplicate course code!!!");
            return ;
        }
        if(savesubject(code)) {
            showWarning("Register Complete!!!");
            return ;
        }
        else{
            showWarning("Course Code Not Found!!");
        }
        
    }
    public boolean checkRegis(String code){
        try(BufferedReader br = new BufferedReader(new FileReader("Lib/sub_regis.csv"))){
            String s;
            while ((s = br.readLine()) != null) {
                String[] data = s.split(",");

                if(data[0].equals(code) ) {
                    return true;
                }
            }
        } catch (Exception e2) {
          e2.printStackTrace();
        }
        return false;
    }

    public boolean savesubject(String code) {
        String subjectname = "";
        String weight = "";
        boolean found = false;
        try (BufferedReader br = new BufferedReader(new FileReader("Lib/subject.csv"))){
            br.readLine();
            String s;
            while ((s = br.readLine()) != null) {
                String[] data = s.split(",");

                if(data[0].equals(code)) {
                    subjectname = data[1];
                    weight = data[2];
                    found = true;
                    break;
                }
            }
        } catch (Exception e3) {
          e3.printStackTrace();
          return false;
        }
        
        if(found) {
            try(BufferedWriter bw = new BufferedWriter(new FileWriter("Lib/sub_regis.csv",true))) {
                bw.write(code + "," + subjectname + "," + weight + ",");
                bw.newLine();
                return true;
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
        return false;
    }
}
