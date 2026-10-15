import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;
/**
 * แอดมินลบวิชาออกจากระบบ 
 *
 * ส่วนแสดงผล: ComboBox เลือกปี (1-4), รายวิชาของปีนั้นเป็น JRadioButton, ปุ่ม Remove
 *
 * การทำงาน:
 *   - เปลี่ยนปีใน ComboBox -> โหลดรายการวิชาของปีนั้นใหม่
 *   - กด Remove: ต้องเลือกวิชาก่อน ("Please select subject")
 *     จากนั้นอ่าน subject.csv กรองบรรทัดที่ code ตรงกันออก แล้วเขียนทับและแจ้ง "Remove Success"
 *
 * เมื่อปิดหน้าต่างจะเรียก menu.enableAddButton()
 */
public class RemoveSubjectAdmin extends JFrame implements ActionListener {
    private Container cp;
    private JPanel headpanel, centerpanel, removepanel;
    private JLabel repeal;
    private JButton removebutton;
    private ButtonGroup group;
    private JScrollPane scrollpane;
    private JComboBox<String> comboBox;
    private SelectionAdmin menu;
  
/** @param menu หน้าเมนูแอดมินที่เรียกมา */
    public RemoveSubjectAdmin(SelectionAdmin menu) {
        this.menu = menu;
        Intitial();
        setComponent();
        setupFrame();
    }

/** สร้าง panel หัว, panel กลาง และ panel รายการวิชา */
    private void Intitial() {
        cp = this.getContentPane();
        cp.setBackground(Color.WHITE);
        cp.setLayout(new BorderLayout());

        headpanel = new BackgroundPanel("./Image/BlueIcon.png");
        headpanel.setLayout(new GridBagLayout());
        headpanel.setPreferredSize(new Dimension(100, 150));

        centerpanel = new BackgroundPanel("./Image/Background.jpg");
        centerpanel.setLayout(new GridBagLayout());
        centerpanel.setPreferredSize(new Dimension(200, 200));
        centerpanel.setBackground(Color.WHITE);

        removepanel = new JPanel();
        removepanel.setLayout(new GridBagLayout());
        removepanel.setBackground(Color.WHITE);
    }

/** สร้าง ComboBox ปี, scrollPane, ปุ่ม Remove และโหลดวิชาของปีที่เลือก */
    private void setComponent() {
        GridBagConstraints ghead = new GridBagConstraints();
        repeal = new JLabel("Repeal");
        repeal.setForeground(Color.white);
        repeal.setFont(new Font("", Font.BOLD, 40));
        ghead.gridx = 0;
        ghead.gridy = 0;
        headpanel.add(repeal, ghead);
        cp.add(headpanel, BorderLayout.NORTH);

        scrollpane = new JScrollPane(removepanel);
        scrollpane.setPreferredSize(new Dimension(700, 500));

        GridBagConstraints gremove = new GridBagConstraints();
        comboBox = new JComboBox<>(new String[] { "1", "2", "3", "4" });
        comboBox.setPreferredSize(new Dimension(50, 30));
        comboBox.setFont(new Font("", Font.BOLD, 20));
        gremove.gridx = 0;
        gremove.gridy = 0;
        gremove.anchor = GridBagConstraints.WEST;
        centerpanel.add(comboBox, gremove);

        scrollpane = new JScrollPane(removepanel);
        scrollpane.setPreferredSize(new Dimension(700, 400));
        gremove.gridx = 0;
        gremove.gridy = 1;
        gremove.insets = new Insets(15, 0, 30, 0);
        centerpanel.add(scrollpane, gremove);

        ImageIcon iconRemove = new ImageIcon("./Image/button_remove.png");
        Image scaledRemove = iconRemove.getImage().getScaledInstance(187, 74, Image.SCALE_SMOOTH);
        removebutton = new JButton(new ImageIcon(scaledRemove));
        removebutton.setFont(new Font("", Font.BOLD, 30));
        removebutton.setPreferredSize(new Dimension(140, 50));
        gremove.gridx = 0;
        gremove.gridy = 2;
        gremove.insets = new Insets(0, 0, 50, 0);
        gremove.anchor = GridBagConstraints.EAST;
        centerpanel.add(removebutton, gremove);

        cp.add(centerpanel, BorderLayout.CENTER);
        comboBox.addActionListener(this);
        removebutton.addActionListener(this);

        loadSubjects((String) comboBox.getSelectedItem());
    }

/** ตั้งขนาด ตำแหน่ง ตั้ง listener ตอนปิดหน้าต่าง และแสดงหน้าต่าง */    
    private void setupFrame() {
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

 /**
     * แสดงวิชาของปีที่เลือกจาก Lib/subject.csv เป็น JRadioButton (actionCommand = รหัสวิชา)
     * @param selectedYear ชั้นปีที่ต้องการแสดง
     */    
    private void loadSubjects(String selectedYear) {
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
                if (data.length >= 4 && data[3].trim().equals(selectedYear)) {
                    String code = data[0].trim();
                    String subject = data[1].trim();
                    String weight = data[2].trim();
                    JRadioButton radio = new JRadioButton(code + " |  " + subject + " - " + weight + "  weight");
                    radio.setBackground(Color.WHITE);
                    radio.setFont(new Font("", Font.BOLD, 20));
                    radio.setActionCommand(code);
                    group.add(radio);
                    g.gridy = row++;
                    removepanel.add(radio, g);
                }
            }
            g.weighty = 1;
            removepanel.add(new JLabel(""), g);
        } catch (Exception e) {
            e.printStackTrace();
        }
        removepanel.revalidate();
        removepanel.repaint();
    }

/**
     * นับจำนวนนิสิตที่ลงทะเบียนวิชาหนึ่ง ๆ (ใช้ป้องกันการลบวิชาที่ยังมีคนลงอยู่)
     * อ่าน ./Lib/sub_regis.csv แล้วนับบรรทัดที่ data[1] (รหัสวิชา) ตรงกับ code
     * @param code รหัสวิชาที่ต้องการนับ
     * @return จำนวนที่ลงทะเบียนวิชานี้ไว้ (0 ถ้าไม่มี)
     */    
    private int countRegistered(String code) {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("./Lib/sub_regis.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 2 && data[1].trim().equals(code)) {
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

/**
     * จัดการ event:
     *  - comboBox: โหลดรายการวิชาของปีที่เลือกใหม่
     *  - removebutton: ลบวิชาที่เลือกออกจาก Lib/subject.csv
     */
    public void actionPerformed(ActionEvent e2) {
        if (e2.getSource() == comboBox) {
            loadSubjects((String) comboBox.getSelectedItem());
            return;
        }
        if (group == null || group.getSelection() == null) {
            JOptionPane.showMessageDialog(this, "Please select subject");
            return;
        }

        
        String codetoRemove = group.getSelection().getActionCommand();
        int registered = countRegistered(codetoRemove);
        if (registered > 0) {
            JOptionPane.showMessageDialog(this, "Have nisit registed");
            return ;
        }

        ArrayList<String> keep = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./Lib/subject.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                boolean target = data[0].trim().equals(codetoRemove);
                if (!target)
                    keep.add(line);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./Lib/subject.csv"))) {
            for (String l : keep) {
                bw.write(l);
                bw.newLine();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        loadSubjects((String) comboBox.getSelectedItem());
        JOptionPane.showMessageDialog(this, "Remove Success");
    }
}