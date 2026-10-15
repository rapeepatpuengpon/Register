import java.awt.*;
import javax.swing.*;

public class BackgroundPanel extends JPanel {
    private Image image;

    public BackgroundPanel(String path) {
        image = new ImageIcon(path).getImage();
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}