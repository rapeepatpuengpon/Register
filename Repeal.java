import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
 
import javax.swing.*;

/**
 * หน้าถอนวิชาของนิสิต 
 *
 * ส่วนแสดงผล: Header ข้อมูลนิสิต, รายวิชาที่ลงไว้เป็น JRadioButton, หน่วยกิตรวม, ปุ่ม Remove
 *
 * กฎเมื่อกดปุ่ม Remove:
 *   1. ต้องเลือกวิชาก่อน ("Please select subject")
 *   2. ต้องเหลืออย่างน้อย 1 วิชา (ถ้ามี <= 1 วิชาจะถอนไม่ได้)
 *   ผ่าน -> อ่าน sub_regis.csv ทั้งหมดเก็บใน list ยกเว้นบรรทัดที่ตรงกับ (user + code)
 *           แล้วเขียนไฟล์ทับ และแจ้ง "Success"
 *
 * เมื่อปิดหน้าต่างจะเรียก menu.enableAddButton()
 */
public class Repeal extends JFrame implements ActionListener {
    private Container cp;
    private JPanel headpanel, centerpanel, removepanel;
    private JLabel repeal,user,name,year,head,weight,currentweight;
    private JButton removebutton;
    private ButtonGroup group;
    private JScrollPane scrollpane;
    private SelectionMenu menu;
    private String currentuser;

/**
     * @param menu หน้าเมนูที่เรียกมา
     * @param user รหัสนิสิตที่ล็อกอินอยู่
     */
    public Repeal(SelectionMenu menu, String user) {
        this.menu = menu;
        this.currentuser = user;
        Intitial();
        setComponent();
        setupFrame();
    }

/** สร้าง panel หัว, panel กลาง และ panel รายการวิชาที่ลง */
    private void Intitial() {
        cp = this.getContentPane();
        cp.setBackground(Color.WHITE);
        cp.setLayout(new BorderLayout());
 
        headpanel = new BackgroundPanel("./Image/BlueIcon.png");
        headpanel.setLayout(new GridBagLayout());
        headpanel.setPreferredSize(new Dimension(1500, 200));
        headpanel.setBackground(Color.LIGHT_GRAY);
 
        centerpanel = new BackgroundPanel("./Image/Background.jpg");
        centerpanel.setLayout(new GridBagLayout());
        centerpanel.setPreferredSize(new Dimension(200, 200));
        centerpanel.setBackground(Color.WHITE);
 
        removepanel = new JPanel();
        removepanel.setLayout(new GridBagLayout());
        removepanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        removepanel.setBackground(Color.WHITE);
    }
 
/** สร้าง label ข้อมูลนิสิต, scrollPane, ปุ่ม Remove และโหลดรายวิชา */  
    private void setComponent() {
 
        GridBagConstraints ghead = new GridBagConstraints();
        repeal = new JLabel("Repeal");
        repeal.setFont(new Font("", Font.BOLD, 40));
        repeal.setForeground(Color.white);
        ghead.gridx = 0;
        ghead.gridy = 0;
        ghead.weightx = 1;
        headpanel.add(repeal, ghead);
        cp.add(headpanel, BorderLayout.NORTH);
 
        user = new JLabel(getusername());
        user.setForeground(Color.WHITE);
        ghead.gridx = 0;
        ghead.gridy = 1;
        ghead.insets = new Insets(0, 140, 0, 0);
        ghead.anchor = GridBagConstraints.WEST;
        user.setFont(new Font("", Font.BOLD, 30));
        headpanel.add(user, ghead);
 
        name = new JLabel(getname(getusername()));
        name.setForeground(Color.WHITE);
        ghead.gridx = 0;
        ghead.gridy = 2;
        ghead.anchor = GridBagConstraints.WEST;
        name.setFont(new Font("", Font.BOLD, 30));
        headpanel.add(name, ghead);
 
        year = new JLabel("Year :" + getyear(getusername()));
        year.setForeground(Color.WHITE);
        ghead.gridx = 0;
        ghead.gridy = 3;
        ghead.anchor = GridBagConstraints.WEST;
        year.setFont(new Font("", Font.BOLD, 25));
        headpanel.add(year, ghead);
 
        GridBagConstraints gremove = new GridBagConstraints();
        scrollpane = new JScrollPane(removepanel);
        scrollpane.setPreferredSize(new Dimension(700, 350));
        gremove.gridx = 0;
        gremove.gridy = 3;
        gremove.insets = new Insets(15, 0, 0, 0);
        centerpanel.add(scrollpane, gremove);
        
        ImageIcon iconRemove = new ImageIcon("./Image/button_remove.png");
        Image scaledRemove = iconRemove.getImage().getScaledInstance(140, 60, Image.SCALE_SMOOTH);
        removebutton = new JButton(new ImageIcon(scaledRemove));
        removebutton.setFont(new Font("", Font.BOLD, 20));
        removebutton.setPreferredSize(new Dimension(100,40));
        gremove.gridx = 0;
        gremove.gridy = 4;
        gremove.anchor = GridBagConstraints.EAST;
        gremove.insets = new Insets(15, 0, 50, 0);
        centerpanel.add(removebutton, gremove);
 
        cp.add(centerpanel, BorderLayout.CENTER);
        removebutton.addActionListener(this);
 
        loadSubjects(currentuser);
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
     * แสดงวิชาที่ user ลงไว้จาก Lib/sub_regis.csv เป็น JRadioButton
     * (actionCommand = รหัสวิชา) พร้อมหน่วยกิตรวม
     * @param user รหัสนิสิต
     */    
    private void loadSubjects(String user) {
        removepanel.removeAll();
        group = new ButtonGroup();
 
        GridBagConstraints g = new GridBagConstraints();
        g.anchor = GridBagConstraints.WEST;
        int row = 0;
 
        head = new JLabel("Code | Subject - Weight");
        g.gridx = 0;
        g.gridy = 0;
        head.setFont(new Font("", Font.BOLD, 25));
        removepanel.add(head, g);
 
        weight = new JLabel("Weight");
        g.gridx = 1;
        g.gridy = 0;
        weight.setFont(new Font("", Font.BOLD, 20));
        g.insets = new Insets(0, 0, 0, 80);
        removepanel.add(weight, g);
 
        currentweight = new JLabel(countweight(getusername()) + "");
        g.gridx = 1;
        g.gridy = 1;
        currentweight.setFont(new Font("", Font.BOLD, 20));
        g.anchor = GridBagConstraints.CENTER;
        removepanel.add(currentweight, g);
 
        g.anchor = GridBagConstraints.WEST;
 
        try (BufferedReader br = new BufferedReader(new FileReader("./Lib/sub_regis.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
 
                if (data.length >= 4 && data[0].trim().equals(user.trim())) {
                    String code = data[1].trim();
                    String subject = data[2].trim();
                    String weight = data[3].trim();
 
                    JRadioButton radio = new JRadioButton(code + " | " + subject + " - " + weight );
                     g.gridy = row + 1;
 
                    g.gridx = 0;
                    g.weightx = 1;
                    radio.setBackground(Color.WHITE);
                    radio.setFont(new Font("", Font.BOLD, 20));
                    radio.setActionCommand(code);
                    group.add(radio);
                    removepanel.add(radio, g);
                    row++;
                }
            }
            g.gridx = 0;
            g.gridy = row + 1;
            g.weighty = 1;
            removepanel.add(new JLabel(""), g);
        } catch (Exception e) {
            e.printStackTrace();
        }
        removepanel.revalidate();
        removepanel.repaint();
    }

/** @return รหัสนิสิตปัจจุบัน */    
    private String getusername() {
        return currentuser;
    }

/** @return ชื่อนิสิตจาก Lib/Nisit.csv (คืน "" ถ้าไม่พบ) */
    private String getname(String user) {
        try (BufferedReader br = new BufferedReader(new FileReader("Lib/Nisit.csv"))) {
            br.readLine();
            String s;
            while ((s = br.readLine()) != null) {
                String data[] = s.split(",");
 
                if (data[0].equals(user)) {
                    return data[1];
                }
 
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return "";
    }

/** @return ผลรวมหน่วยกิตของ user จาก Lib/sub_regis.csv */
    private int countweight(String user) {
        int total = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("Lib/sub_regis.csv"))) {
            String s;
            while ((s = br.readLine()) != null) {
                String data[] = s.split(",");
                // โครงสร้างไฟล์: user, code, subject, weight, year
                if (data[0].equals(user)) {
                    total += Integer.parseInt(data[3].trim());
                }
            }
        } catch (Exception ew) {
            ew.printStackTrace();
        }
        return total;
    }
 
/** @return ชั้นปีของ user (อ่านจาก Lib/sub_regis.csv คอลัมน์ที่ 5) */    
    private String getyear(String user) {
        try (BufferedReader br = new BufferedReader(new FileReader("Lib/sub_regis.csv"))) {
            String s;
            while ((s = br.readLine()) != null) {
                String data[] = s.split(",");
 
                if (data[0].equals(user)) {
                    return data[4];
                }
 
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return "";
    }

/** @return จำนวนวิชาที่ user ลงทะเบียนอยู่ใน Lib/sub_regis.csv */ 
    private int countsubject(String user) {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("Lib/sub_regis.csv"))) {
            String s;
            while ((s = br.readLine()) != null) {
                String data[] = s.split(",");
                // โครงสร้างไฟล์: user, code, subject, weight, year
                if (data[0].equals(user)) {
                    count++;
                }
            }
        } catch (Exception ew) {
            ew.printStackTrace();
        }
        return count;
    }

/**
     * จัดการปุ่ม Remove: ตรวจเงื่อนไข 2 ข้อ (ดูที่คำอธิบายคลาส)
     * แล้วลบวิชาที่เลือกออกจาก Lib/sub_regis.csv และโหลดรายการใหม่
     */  
    public void actionPerformed(ActionEvent e2) {
        if (group == null || group.getSelection() == null) {
            JOptionPane.showMessageDialog(this, "Please select subject");
            return;
        }
 
        else if (countsubject(currentuser) <= 1) {
            JOptionPane.showMessageDialog(this, "Subject much have more 1");
            return;
        }
 
        String codetoRemove = group.getSelection().getActionCommand(); // จะดึงค่ารหัสวิชาที่กดเลือก
        ArrayList<String> keep = new ArrayList<>(); // สร้างมาเพื่ออ่านไฟล์ไปเก็บไว้ในลิสต์
        try (BufferedReader br = new BufferedReader(new FileReader("./Lib/sub_regis.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // อ่านค่าในไฟล์แล้วบรรทัดไหนที่ไม่ถูกเลือกจะเอาไปเก็บใน keep
                // เป็นลิสต์ที่สร้างมาเก็บ
                boolean target = data.length >= 2 && data[0].trim().equals(currentuser.trim())
                        && data[1].trim().equals(codetoRemove);
                if (!target)
                    keep.add(line);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        // เขียนไฟล์ที่เก็บไว้ในลิสต์ keep คืน
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./Lib/sub_regis.csv"))) {
            for (String l : keep) {
                bw.write(l);
                bw.newLine();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        loadSubjects(currentuser);
        JOptionPane.showMessageDialog(this, "Success");
    }
 
}