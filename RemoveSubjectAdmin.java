import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

public class RemoveSubjectAdmin extends JFrame implements ActionListener{
    Container cp;
    JPanel headpanel,centerpanel,removepanel;
    JLabel repeal,years;
    JButton removebutton;
    ButtonGroup group;
    JScrollPane scrollpane;
    JComboBox<String> comboBox;
    SelectionAdmin menu;
    String currentuser;

    public RemoveSubjectAdmin(SelectionAdmin menu,String user){
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
    gremove.insets = new Insets(90, 100, 100, 100);
    centerpanel.add(scrollpane, gremove);

    GridBagConstraints gcenter = new GridBagConstraints();
    removebutton = new JButton("Remove");
    removebutton.setFont(new Font("",Font.BOLD,20));
    removebutton.setBackground(Color.WHITE);
    gcenter.gridx = 0;
    gcenter.gridy = 0;
    gcenter.anchor = GridBagConstraints.SOUTH;
    gcenter.insets = new Insets(100, 100, 30, 100);
    centerpanel.add(removebutton,gcenter);

    GridBagConstraints gcombo = new GridBagConstraints();
    comboBox = new JComboBox<>(new String[]{"1","2","3","4"});
    comboBox.setFont(new Font("",Font.BOLD,20));
    gcombo.gridx = 0;
    gcombo.gridy = 0;
    gcombo.insets = new Insets(30, 100, 0, 0);
    gcombo.anchor = GridBagConstraints.NORTHWEST;
    centerpanel.add(comboBox,gcombo);
    
    cp.add(centerpanel, BorderLayout.CENTER);
    comboBox.addActionListener(this);
    removebutton.addActionListener(this);
    
    
    loadSubjects((String)comboBox.getSelectedItem());
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
    public void loadSubjects(String selectedYear){
    removepanel.removeAll();
    group = new ButtonGroup();
    GridBagConstraints g = new GridBagConstraints();
    g.gridx = 0;
    g.anchor = GridBagConstraints.NORTHWEST;  
    g.weightx = 1;                             
    g.fill = GridBagConstraints.HORIZONTAL;
    int row = 0;

    try (BufferedReader br = new BufferedReader(new FileReader("./Lib/subject.csv"))) {
        String line;
        br.readLine();
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
                if (data.length>=4&&data[3].trim().equals(selectedYear)) {
                    String code = data[0].trim();
                    String subject = data[1].trim();
                    String weight = data[2].trim();
                    JRadioButton radio = new JRadioButton(code + " |  " + subject + "  " + weight + "  weight");
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
        if (e2.getSource() == comboBox) {
            loadSubjects((String) comboBox.getSelectedItem());
            return;
        }
        if (group == null || group.getSelection()==null) {
            JOptionPane.showMessageDialog(this, "Please select subject");
            return;
        } 
        String codetoRemove = group.getSelection().getActionCommand(); 
        ArrayList<String> keep = new ArrayList<>(); 
        try(BufferedReader br = new BufferedReader(new FileReader("./Lib/subject.csv"))) {
            String line;
            while ((line = br.readLine())!= null) {
                String[] data = line.split(",");
                boolean target = data[0].trim().equals(codetoRemove);
                if (!target) keep.add(line);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("./Lib/subject.csv"))) {
            for(String l : keep){ 
                bw.write(l);
                bw.newLine();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        loadSubjects((String)comboBox.getSelectedItem());
        JOptionPane.showMessageDialog(this,"Remove Success");
    }
}