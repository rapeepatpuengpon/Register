import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * เมนูหลักของแอดมิน 
 * ปุ่ม: add    -> เปิด AddAdmin (เพิ่มวิชาเข้าระบบ)
 *     drop   -> เปิด RemoveSubjectAdmin (ลบวิชาออกจากระบบ)
 *     logout -> ยืนยันแล้วกลับหน้า Login
 * เมื่อเปิดหน้าลูกจะ disable ปุ่มทั้งหมด และเปิดกลับด้วย enableAddButton()
 */ 
public class SelectionAdmin extends JFrame implements ActionListener{
    private Container cp;
    private JPanel panel;
    private JLabel selection;
    private JButton add,drop,logout;

/** สร้างเมนูแอดมิน (ไม่รับ parameter) */    
    public SelectionAdmin(){
        Intitial();
        setComponent();
        setupFrame();
    }

/** สร้าง panel พื้นหลังของเมนู */    
    private void Intitial(){
        cp = this.getContentPane();
        
        panel = new BackgroundPanel("./Image/BackgroundSelection3.png");
        panel.setLayout(null);
 
        
    }

/** สร้างและวางปุ่มทั้งหมดพร้อมผูก ActionListener */    
    private void setComponent(){
        selection = new JLabel("Select");
        selection.setBounds(250, 30, 300, 50);
        selection.setFont(new Font("",Font.BOLD,30));
        
        ImageIcon iconAdd = new ImageIcon("./Image/button_addsubject (1).png");
        Image scaled = iconAdd.getImage().getScaledInstance(150, 56, Image.SCALE_SMOOTH);
        add = new JButton(new ImageIcon(scaled));
        add.setBounds(140, 100, 130, 50);
        
        ImageIcon iconRepeal = new ImageIcon("./Image/button_repeal.png");
        Image scaledRepeal = iconRepeal.getImage().getScaledInstance(145, 58, Image.SCALE_SMOOTH);
        drop = new JButton(new ImageIcon(scaledRepeal));
        drop.setBounds(320, 100, 130, 50);
        drop.setFont(new Font("",Font.BOLD,12));
 
        ImageIcon iconLogout = new ImageIcon("./Image/LogoutIcon.png");
        Image scaledLogout = iconLogout.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        logout = new JButton(new ImageIcon(scaledLogout));
        logout.setBounds(540, 10, 30, 30);
 
 
        panel.setBounds(0,0,600,300);
        panel.add(selection);
        panel.add(add);
        panel.add(drop);
        panel.add(logout);
        add.addActionListener(this);
        drop.addActionListener(this);
        logout.addActionListener(this);
        cp.add(panel);
 
    }

/** ตั้งขนาด ตำแหน่ง และแสดงหน้าต่าง */    
    private void setupFrame(){
        this.setSize(600, 250);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

/** จัดการปุ่ม logout / add / drop (ดูที่คำอธิบายคลาส) */    
    public void actionPerformed(ActionEvent e) { 
         if (e.getSource() == logout) {
            int confirm = JOptionPane.showConfirmDialog(this, "Do you want to logout?", "Confirm Logout",JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose();
                new Login();
            }
        }
        if (e.getSource()==add) {
            add.setEnabled(false);
            drop.setEnabled(false);
            logout.setEnabled(false);
            new AddAdmin(this);
        }
        if (e.getSource()==drop) {
            drop.setEnabled(false);
            add.setEnabled(false);
            logout.setEnabled(false);
            new RemoveSubjectAdmin(this);
        }
    }

/** เปิดปุ่มทุกปุ่มกลับ (ถูกเรียกเมื่อหน้าลูกปิด) */    
    public void enableAddButton() {
        add.setEnabled(true);
        drop.setEnabled(true);
        logout.setEnabled(true);
    }
}