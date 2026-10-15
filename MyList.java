import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.*;
import java.awt.*;
/**
 * หน้าดูรายวิชาที่ลงทะเบียนแล้ว
 *
 * แสดง: Header ข้อมูลนิสิต, ตารางรหัสวิชา/ชื่อวิชา/หน่วยกิต และ "Total Weight"
 * เมื่อปิดหน้าต่างจะเรียก menu.enableAddButton()
 */ 
public class MyList extends JFrame {
 
    private Container cp;
    private JPanel panelbody, panelhead, wrappanel;
    private JLabel weightlabel;
    private String user;
    private SelectionMenu menu;
 
/**
     * @param menu หน้าเมนูที่เรียกมา
     * @param user รหัสนิสิตที่ล็อกอินอยู่
     */    
    public MyList(SelectionMenu menu, String user) {
        this.menu = menu;
        this.user = user;
        Intitial();
        setComponent();
        setupFrame();
    }
 
/** สร้าง panel หัว, panel พื้นหลัง และ panel รายการวิชา */
    private void Intitial() {
        cp = this.getContentPane();
        cp.setLayout(new BorderLayout());
        panelhead = new BackgroundPanel("./Image/BlueIcon.png");
        panelhead.setLayout(new GridBagLayout());
        panelhead.setPreferredSize(new Dimension(1000, 200));
 
        wrappanel = new BackgroundPanel("./Image/Background.jpg");
        wrappanel.setLayout(new GridBagLayout());
 
        panelbody = new JPanel();
        panelbody.setLayout(new GridBagLayout());
        panelbody.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panelbody.setBackground(Color.WHITE);
 
    }

/** สร้าง label ข้อมูลนิสิต, scrollPane, Total Weight และโหลดรายวิชา */    
    private void setComponent() {
 
        GridBagConstraints ghead = new GridBagConstraints();
        JLabel mylist = new JLabel("My List");
        mylist.setFont(new Font("", Font.BOLD, 40));
        mylist.setForeground(Color.white);
        ghead.gridx = 0;
        ghead.gridy = 0;
        ghead.weightx = 1;
        ghead.insets = new Insets(10, 0, 0, 0);
        ghead.anchor = GridBagConstraints.CENTER;
        panelhead.add(mylist, ghead);
 
        JLabel user = new JLabel(getusername());
        user.setForeground(Color.white);
        ghead.gridx = 0;
        ghead.gridy = 1;
        ghead.anchor = GridBagConstraints.WEST;
        user.setFont(new Font("", Font.BOLD, 30));
        ghead.insets = new Insets(5, 100, 0, 0);
        panelhead.add(user, ghead);
 
        JLabel name = new JLabel(getname(getusername()));
        name.setForeground(Color.white);
        ghead.gridx = 0;
        ghead.gridy = 2;
        ghead.anchor = GridBagConstraints.WEST;
        name.setFont(new Font("", Font.BOLD, 30));
        ghead.insets = new Insets(5, 100, 0, 0);
        panelhead.add(name, ghead);
 
        JLabel year = new JLabel("Year :" + getyear(getusername()));
        year.setForeground(Color.white);
        ghead.gridx = 0;
        ghead.gridy = 3;
        ghead.anchor = GridBagConstraints.WEST;
        year.setFont(new Font("", Font.BOLD, 25));
        ghead.insets = new Insets(5, 100, 0, 0);
        panelhead.add(year, ghead);
 
        GridBagConstraints gwrap = new GridBagConstraints();
        gwrap.insets = new Insets(0, 0, 20, 0);
        JScrollPane scrollPane = new JScrollPane(panelbody);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        gwrap.gridx = 0;
        gwrap.gridy = 0;
        gwrap.insets = new Insets(20, 0, 20, 0);
        wrappanel.add(scrollPane, gwrap);
 
        weightlabel = new JLabel("Total Weight : " + calweight(getusername()));
 
        gwrap.gridx = 0;
        gwrap.gridy = 1;
        gwrap.anchor = GridBagConstraints.EAST;
        gwrap.insets = new Insets(0, 0, 60, 20);
        weightlabel.setFont(new Font("", Font.BOLD, 25));
        wrappanel.add(weightlabel, gwrap);
 
        writesubject(this.user);
 
        cp.add(panelhead, BorderLayout.NORTH);
        cp.add(wrappanel, BorderLayout.CENTER);
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

/** @return รหัสนิสิตปัจจุบัน */ 
    private String getusername() {
        return user;
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

/**
     * แสดงวิชาที่ user ลงไว้จาก Lib/sub_regis.csv เป็น label (รหัส, ชื่อวิชา, หน่วยกิต)
     * @param user รหัสนิสิต
     */    
    private void writesubject(String user) {
        GridBagConstraints glabel = new GridBagConstraints();
        glabel.anchor = GridBagConstraints.WEST;
 
        int row = 0;
 
        try (BufferedReader br = new BufferedReader(new FileReader("Lib/sub_regis.csv"))) {
            String s;
            while ((s = br.readLine()) != null) {
                String data[] = s.split(",");
 
                if (data[0].equals(user)) {
                    String code = data[1];
                    String subject = data[2];
                    String weight = data[3];
 
                    glabel.gridx = 0;
                    glabel.gridy = row;
                    glabel.insets = new Insets(10, 10, 10, 50);
                    JLabel labelCode = new JLabel(code);
                    labelCode.setFont(new Font("", Font.PLAIN, 20));
                    panelbody.add(labelCode, glabel);
 
                    glabel.gridx = 1;
                    glabel.insets = new Insets(10, 100, 10, 50);
                    JLabel labelsubject = new JLabel(subject);
                    labelsubject.setFont(new Font("", Font.PLAIN, 20));
                    panelbody.add(labelsubject, glabel);
 
                    glabel.gridx = 2;
                    glabel.insets = new Insets(10, 100, 10, 10);
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
 
/** @return ผลรวมหน่วยกิตของ user จาก Lib/sub_regis.csv */    
    private int calweight(String user) {
        int total = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("Lib/sub_regis.csv"))){
            String s;
            int data_weight;
            while ((s = br.readLine()) != null) {
                String data[] = s.split(",");
                if (data[0].equals(user)) {
                    data_weight = Integer.parseInt(data[3]);
                    total = total + data_weight;
                }
            }
        } catch (Exception ew) {
            ew.printStackTrace();
        }
        return total;
    }
 
}