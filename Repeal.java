import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

public class Repeal extends JFrame implements ActionListener{
    Container cp;
    JPanel headpanel,centerpanel,removepanel;
    JLabel repeal;
    JButton removebutton;
    ButtonGroup group;
    JScrollPane scrollpane;
    SelectionMenu menu;
    String currentuser;

    public Repeal(SelectionMenu menu,String user){
        this.menu = menu;
        this.currentuser = user;
        Intitial();
        setComponent();
        setupFrame();
    }
    public void Intitial(){
       cp = this.getContentPane();
       cp.setBackground(Color.WHITE);
       cp.setLayout(new BorderLayout());

       headpanel = new JPanel();
       headpanel.setLayout(new GridBagLayout());
       headpanel.setPreferredSize(new Dimension(1500,200));
       headpanel.setBackground(Color.LIGHT_GRAY);

       centerpanel = new JPanel();
       centerpanel.setLayout(new GridBagLayout());
       centerpanel.setPreferredSize(new Dimension(200,200));
       centerpanel.setBackground(Color.LIGHT_GRAY);
    
       removepanel = new JPanel();
       removepanel.setLayout(new GridBagLayout());  
       removepanel.setBackground(Color.WHITE);
    }
                                                                                                                                                                                                                                                                                                             
    public void setComponent(){
        
        GridBagConstraints ghead = new GridBagConstraints();
        repeal = new JLabel("Repeal");
        repeal.setFont(new Font("",Font.BOLD,40));
        ghead.gridx = 0;
        ghead.gridy = 0;
        headpanel.add(repeal,ghead);
        cp.add(headpanel,BorderLayout.NORTH);

    
    scrollpane = new JScrollPane(removepanel);
    scrollpane.setPreferredSize(new Dimension(700, 500));

    GridBagConstraints gremove = new GridBagConstraints();
    gremove.gridx = 0;
    gremove.gridy = 0;
    gremove.weightx = 1;
    gremove.weighty = 1;
    gremove.fill = GridBagConstraints.BOTH;
    gremove.insets = new Insets(60, 100, 150, 100);
    centerpanel.add(scrollpane, gremove);

    GridBagConstraints gcenter = new GridBagConstraints();
    removebutton = new JButton("Remove");
    removebutton.setFont(new Font("",Font.BOLD,16));
    removebutton.setBackground(Color.CYAN);
    gcenter.gridx = 0;
    gcenter.gridy = 0;
    gcenter.anchor = GridBagConstraints.SOUTHEAST;
    gcenter.insets = new Insets(0, 50, 100, 100);
    centerpanel.add(removebutton,gcenter);

    cp.add(centerpanel, BorderLayout.CENTER);
    removebutton.addActionListener(this);
    
    loadSubjects(currentuser);
    }
    public void setupFrame(){
        this.setSize(1000, 800);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                menu.enableAddButton();
            }
        });
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public void loadSubjects(String user){
    removepanel.removeAll();
    group = new ButtonGroup();

    GridBagConstraints g = new GridBagConstraints();
    g.gridx = 0;
    g.anchor = GridBagConstraints.NORTHWEST;  
    g.weightx = 1;                             
    g.fill = GridBagConstraints.HORIZONTAL;
    int row = 0;

    try (BufferedReader br = new BufferedReader(new FileReader("./Lib/sub_regis.csv"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",", -1);

            if (data.length >= 4 && data[0].trim().equals(user.trim())) {
                String code = data[1].trim();
                String subject = data[2].trim();

                JRadioButton radio = new JRadioButton(code + " - " + subject);
                radio.setBackground(Color.WHITE);
                radio.setFont(new Font("", Font.BOLD, 20));
                radio.setActionCommand(code);
                group.add(radio);
                g.gridy = row++;               
                removepanel.add(radio, g);     
            }
        }
        g.weighty = 1;
        removepanel.add(new JLabel(""),g);
    } catch (Exception e) {
        e.printStackTrace();
    }
    removepanel.revalidate();
    removepanel.repaint();
}
    public void actionPerformed(ActionEvent e2) {
        if (group == null ||group.getSelection()==null) {
            JOptionPane.showMessageDialog(this, "Please select subject");
            return;
        } 
        String codetoRemove = group.getSelection().getActionCommand(); 
        ArrayList<String> keep = new ArrayList<>(); 
        try(BufferedReader br = new BufferedReader(new FileReader("./Lib/sub_regis.csv"))) {
            String line;
            while ((line = br.readLine())!= null) {
                String[] data = line.split(",");
                boolean target = data.length >= 2 && data[0].trim().equals(currentuser.trim())&&data[1].trim().equals(codetoRemove);
                if (!target) keep.add(line);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("./Lib/sub_regis.csv"))) {
            for(String l : keep){ 
                bw.write(l);
                bw.newLine();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        loadSubjects(currentuser);
        JOptionPane.showMessageDialog(this,"Success");
    }
}