package jpaswing.ui;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
@Component
public class PanelImagen extends JPanel {
    private Image image;
    private int imageWidth;
    private int imageHeight;

    public PanelImagen() {
        setBorder(new TitledBorder("Image"));
        setPreferredSize(new Dimension(400,400));
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        if (image != null) {

            if (imageWidth > 0 && imageHeight > 0) {
                Image scaledImage = image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
                g.drawImage(scaledImage, 0, 0, this);
            } else {
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public void updateImage(Image newImage) {
        this.image = newImage;
        repaint();
    }

    public void setImageSize(int width, int height) {
        this.imageWidth = width;
        this.imageHeight = height;
        repaint();
    }

    public static Image getImageFromDatabase(String itemName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/IsaacItems");
            String sql = "SELECT url FROM item WHERE name = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, itemName);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String imgUrl = rs.getString("url");
                URL url = new URL(imgUrl);
                return ImageIO.read(url);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { }
            try { if (stmt != null) stmt.close(); } catch (Exception e) { }
            try { if (conn != null) conn.close(); } catch (Exception e) { }
        }

        return null;
    }
}
