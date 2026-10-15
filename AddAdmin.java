import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
/**
 * AddAdmin extends JFrame implements ActionListener - แอดมินเพิ่มวิชาใหม่ลงระบบ (1000x800)
 *
 * ส่วนแสดงผล:
 *   - ซ้าย: ComboBox เลือกปี (1-4) และรายการวิชาของปีนั้น
 *   - ขวา: ฟอร์มกรอก Code, Subject, Weight, Year พร้อม label แจ้งเตือนแต่ละช่อง
 *
 * Validation เมื่อกดปุ่ม Add:
 *   - code, subject, weight, year ห้ามว่าง
 *   - code และ subject ห้ามซ้ำกับที่มีใน subject.csv
 *   ผ่าน -> append ลง subject.csv, สลับไปแสดงปีนั้น และล้างฟอร์ม
 *
 * เมื่อปิดหน้าต่างจะเรียก menu.enableAddButton()
 */ 
public class AddAdmin extends JFrame implements ActionListener {
 
    private Container cp;
    private JPanel panelhead, panelbody, wrappanel, paneladd;
    private JComboBox<String> boxyear = new JComboBox<>();
    private JLabel codelable, subjectlable, weightlable, yearlabel;
    private JLabel warningcode, warningsubject, warningweight, warningyear;
    private JTextField codetext, subjecttext, weighttext, yeartext;
    private JButton addsubject;
    private SelectionAdmin menu;

/** @param menu หน้าเมนูแอดมินที่เรียกมา */    
    public AddAdmin(SelectionAdmin menu) {
        this.menu = menu;
        Intitial();
        setComponent();
        setupFrame();
    }

/** สร้าง panel หัว, panel พื้นหลัง, panel รายการวิชา และ panel ฟอร์มเพิ่มวิชา */    
    private void Intitial() {
        cp = this.getContentPane();
        cp.setLayout(new BorderLayout());
        panelhead = new BackgroundPanel("./Image/BlueIcon.png");
        panelhead.setLayout(new GridBagLayout());
        panelhead.setPreferredSize(new Dimension(1000, 150));
 
        wrappanel = new BackgroundPanel("./Image/Background.jpg");
        wrappanel.setLayout(new GridBagLayout());
 
        panelbody = new JPanel();
        panelbody.setLayout(new GridBagLayout());
        panelbody.setBackground(Color.WHITE);
        panelbody.setBorder(BorderFactory.createLineBorder(Color.black,1));
 
        paneladd = new JPanel();
        paneladd.setLayout(new GridBagLayout());
        paneladd.setBackground(Color.WHITE);
        paneladd.setBorder(BorderFactory.createLineBorder(Color.black,1));
        paneladd.setPreferredSize(new Dimension(250, 350));
 
    }

/** สร้าง ComboBox ปี, scrollPane, ปุ่ม Add, ฟอร์มกรอกข้อมูล และโหลดวิชาปี 1 */    
    private void setComponent() {
 
        GridBagConstraints ghead = new GridBagConstraints();
        JLabel add = new JLabel("AddSubject");
        add.setFont(new Font("", Font.BOLD, 40));
        add.setForeground(Color.white);
        ghead.gridx = 0;
        ghead.gridy = 0;
        ghead.weightx = 1;
        ghead.anchor = GridBagConstraints.CENTER;
        panelhead.add(add, ghead);
 
        GridBagConstraints gwrap = new GridBagConstraints();
        boxyear.addItem("1");
        boxyear.addItem("2");
        boxyear.addItem("3");
        boxyear.addItem("4");
        boxyear.addActionListener(this);
        gwrap.gridx = 0;
        gwrap.gridy = 0;
        gwrap.insets = new Insets(0, 0, 15, 0);
        gwrap.anchor = GridBagConstraints.WEST;
        boxyear.setPreferredSize(new Dimension(100, 40));
        wrappanel.add(boxyear, gwrap);
 
        JScrollPane scrollPane = new JScrollPane(panelbody);
        scrollPane.setPreferredSize(new Dimension(570, 350));
        gwrap.gridx = 0;
        gwrap.gridy = 1;
        gwrap.anchor = GridBagConstraints.CENTER;
        wrappanel.add(scrollPane, gwrap);
 
        gwrap.insets = new Insets(10, 0, 50, 0);
        ImageIcon iconAdd = new ImageIcon("./Image/button_add.png");
        Image scaledAdd = iconAdd.getImage().getScaledInstance(130, 50, Image.SCALE_SMOOTH);
        addsubject = new JButton(new ImageIcon(scaledAdd));
        gwrap.gridx = 1;
        gwrap.gridy = 2;
        gwrap.anchor = GridBagConstraints.EAST;
        addsubject.setPreferredSize(new Dimension(100, 50));
        wrappanel.add(addsubject, gwrap);
        addsubject.addActionListener(this);
 
        gwrap.insets = new Insets(0, 0, 15, 0);
        gwrap.gridx = 1;
        gwrap.gridy = 1;
        wrappanel.add(paneladd, gwrap);
 
        GridBagConstraints gadd = new GridBagConstraints();
        codelable = new JLabel("Input Code ");
        gadd.gridx = 0;
        gadd.gridy = 0;
        gadd.anchor = GridBagConstraints.WEST;
        gadd.insets = new Insets(0, 0, 10, 0);
        paneladd.add(codelable, gadd);
 
        codetext = new JTextField();
        gadd.gridx = 0;
        gadd.gridy = 1;
        gadd.anchor = GridBagConstraints.WEST;
        codetext.setPreferredSize(new Dimension(150, 20));
        paneladd.add(codetext, gadd);
 
        warningcode = new JLabel("");
        gadd.gridx = 0;
        gadd.gridy = 2;
        gadd.anchor = GridBagConstraints.WEST;
        paneladd.add(warningcode, gadd);
 
        subjectlable = new JLabel("Input Subject ");
        gadd.gridx = 0;
        gadd.gridy = 3;
        gadd.anchor = GridBagConstraints.WEST;
        paneladd.add(subjectlable, gadd);
 
        subjecttext = new JTextField();
        gadd.gridx = 0;
        gadd.gridy = 4;
        gadd.anchor = GridBagConstraints.WEST;
        subjecttext.setPreferredSize(new Dimension(150, 20));
        paneladd.add(subjecttext, gadd);
 
        warningsubject = new JLabel("");
        gadd.gridx = 0;
        gadd.gridy = 5;
        gadd.anchor = GridBagConstraints.WEST;
        paneladd.add(warningsubject, gadd);
 
        weightlable = new JLabel("Input Weight ");
        gadd.gridx = 0;
        gadd.gridy = 6;
        gadd.anchor = GridBagConstraints.WEST;
        paneladd.add(weightlable, gadd);
 
        weighttext = new JTextField();
        gadd.gridx = 0;
        gadd.gridy = 7;
        gadd.anchor = GridBagConstraints.WEST;
        weighttext.setPreferredSize(new Dimension(150, 20));
        paneladd.add(weighttext, gadd);
 
        warningweight = new JLabel("");
        gadd.gridx = 0;
        gadd.gridy = 8;
        gadd.anchor = GridBagConstraints.WEST;
        paneladd.add(warningweight, gadd);
 
        yearlabel = new JLabel("Input Year");
        gadd.gridx = 0;
        gadd.gridy = 9;
        gadd.anchor = GridBagConstraints.WEST;
        paneladd.add(yearlabel, gadd);
 
        yeartext = new JTextField();
        gadd.gridx = 0;
        gadd.gridy = 10;
        gadd.anchor = GridBagConstraints.WEST;
        yeartext.setPreferredSize(new Dimension(150, 20));
        paneladd.add(yeartext, gadd);
 
        warningyear = new JLabel("");
        gadd.gridx = 0;
        gadd.gridy = 11;
        gadd.anchor = GridBagConstraints.WEST;
        paneladd.add(warningyear, gadd);
 
        codetext.addActionListener(this);
        subjecttext.addActionListener(this);
        weighttext.addActionListener(this);
        yeartext.addActionListener(this);
 
        cp.add(panelhead, BorderLayout.NORTH);
        cp.add(wrappanel, BorderLayout.CENTER);
 
        writesubject("1");
    }

/** ตั้งขนาด ตำแหน่ง ตั้ง listener ตอนปิดหน้าต่าง และแสดงหน้าต่าง */    
    private void setupFrame() {
        this.setSize(1000, 800);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
         this.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                menu.enableAddButton();
            }
        });
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
 
/**
     * จัดการ event:
     *  - boxyear: โหลดรายการวิชาของปีที่เลือก
     *  - addsubject: ตรวจ validation แล้วบันทึกวิชาใหม่
     */    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boxyear) {
            String selectedYear = (String) boxyear.getSelectedItem();
            writesubject(selectedYear);
        }
 
        if (e.getSource() == addsubject) {
            String getcode = codetext.getText().trim();
            String getsubject = subjecttext.getText().trim();
            String getweight = weighttext.getText().trim();
            String getyear = yeartext.getText().trim();
 
            boolean ok = true;
 
            if (getcode.isEmpty()) {
                warningcode.setForeground(Color.red);
                showWarningcode("Code is Emply");
                ok = false;
            }
 
            if (checkduplicatecode(getcode)) {
                warningcode.setForeground(Color.red);
                showWarningcode("Code is Duplicate");
                ok = false;
            }
 
            if (getsubject.isEmpty()) {
                warningsubject.setForeground(Color.red);
                showWarningsubject("Subject is Emply");
                ok = false;
            }
 
            if (checkduplicatesubject(getsubject)) {
                warningsubject.setForeground(Color.red);
                showWarningsubject("Subject is Duplicate");
                ok = false;
            }
 
            if (getweight.isEmpty()) {
                warningweight.setForeground(Color.red);
                showWarningweight("Weight is Emply");
                ok = false;
            }
 
            if (getyear.isEmpty()) {
                warningyear.setForeground(Color.red);
                showWarningyear("Year is Emply");
                ok = false;
            }
 
            if (!ok)
                return;
 
            addsubject(getcode, getsubject, getweight, getyear);
 
            boxyear.setSelectedItem(getyear);
            writesubject(getyear);
 
            codetext.setText("");
            subjecttext.setText("");
            weighttext.setText("");
            yeartext.setText("");
 
            clearWarnings();
        }
    }

 /** ล้างข้อความแจ้งเตือนทุกช่อง */    
    private void clearWarnings() {
        warningcode.setText(" ");
        warningsubject.setText(" ");
        warningweight.setText(" ");
        warningyear.setText(" ");
    }

/** แสดงข้อความแจ้งเตือนใต้ช่อง Code */    
    private void showWarningcode(String msg) {
        warningcode.setText(msg);
    }
 
/** แสดงข้อความแจ้งเตือนใต้ช่อง Subject */    
    private void showWarningsubject(String msg) {
        warningsubject.setText(msg);
    }
 
/** แสดงข้อความแจ้งเตือนใต้ช่อง Weight */    
    private void showWarningweight(String msg) {
        warningweight.setText(msg);
    }
 
/** แสดงข้อความแจ้งเตือนใต้ช่อง Year */    
    private void showWarningyear(String msg) {
        warningyear.setText(msg);
    }

/** @return true ถ้ารหัสวิชานี้มีอยู่แล้วใน Lib/subject.csv */    
    private boolean checkduplicatecode(String code) {
        try (BufferedReader br = new BufferedReader(new FileReader("Lib/subject.csv"))) {
            br.readLine();
            String s;
            while ((s = br.readLine()) != null) {
                String data[] = s.split(",");
 
                if (data[0].equals(code)) {
                    return true;
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return false;
    }

/** @return true ถ้าชื่อวิชานี้มีอยู่แล้วใน Lib/subject.csv */
    private boolean checkduplicatesubject(String subject) {
        try (BufferedReader br = new BufferedReader(new FileReader("Lib/subject.csv"))) {
            br.readLine();
            String s;
            while ((s = br.readLine()) != null) {
                String data[] = s.split(",");
 
                if (data[1].trim().equals(subject)) {
                    return true;
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return false;
    }

 /**
     * แสดงวิชาของปีที่เลือกจาก Lib/subject.csv เป็น label (รหัส, ชื่อ, หน่วยกิต)
     * @param year ชั้นปีที่ต้องการแสดง
     */   
    private void writesubject(String year) {
 
        panelbody.removeAll();
 
        GridBagConstraints glabel = new GridBagConstraints();
        glabel.anchor = GridBagConstraints.WEST;
 
        int row = 0;
 
        try (BufferedReader br = new BufferedReader(new FileReader("Lib/subject.csv"))) {
            String s;
            while ((s = br.readLine()) != null) {
                String data[] = s.split(",");
 
                if (data[3].equals(year)) {
                    String code = data[0];
                    String subject = data[1];
                    String weight = data[2];
 
                    glabel.gridx = 0;
                    glabel.gridy = row;
                    glabel.insets = new Insets(10, 10, 10, 50);
                    JLabel labelCode = new JLabel(code);
                    labelCode.setFont(new Font("", Font.PLAIN, 20));
                    panelbody.add(labelCode, glabel);
 
                    glabel.gridx = 1;
                    glabel.insets = new Insets(10, 0, 10, 50);
                    JLabel labelsubject = new JLabel(subject);
                    labelsubject.setFont(new Font("", Font.PLAIN, 20));
                    panelbody.add(labelsubject, glabel);
 
                    glabel.gridx = 2;
                    glabel.insets = new Insets(10, 0, 10, 10);
                    JLabel labelweight = new JLabel(weight);
                    labelweight.setFont(new Font("", Font.PLAIN, 20));
                    panelbody.add(labelweight, glabel);
 
                    row++;
                }
            }
            glabel.gridx = 0;
            glabel.gridy = row;
            glabel.weighty = 1.0;
            panelbody.add(new JLabel(""), glabel);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
 
        panelbody.revalidate();
        panelbody.repaint();
    }
 
    private void addsubject(String code, String subject, String weight, String year) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Lib/subject.csv", true))) {
            bw.write(code + "," + subject + "," + weight + "," + year);
            bw.newLine();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
 
}