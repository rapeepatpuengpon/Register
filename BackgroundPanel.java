import java.awt.*;
import javax.swing.*;
/**
 * BackgroundPanel extends JPanel - Panel ที่วาดรูปภาพเป็นพื้นหลัง
 *
 * ใช้เป็นพื้นหลังและ header ในทุกหน้าจอ
 * Field: image - รูปที่โหลดจาก path
 */
public class BackgroundPanel extends JPanel {
    private Image image;
/**
     * โหลดรูปพื้นหลังจาก path ที่กำหนด
     * @param path ตำแหน่งไฟล์รูป เช่น "./Image/Background.jpg"
     */
    public BackgroundPanel(String path) {
        image = new ImageIcon(path).getImage();
    }
/** วาดรูปยืดเต็มขนาดของ panel */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}