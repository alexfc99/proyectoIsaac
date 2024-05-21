package jpaswing.ui;

import org.springframework.stereotype.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
@Component
public class PanelBanner extends JPanel {
    public final static String RUTA_IMAGEN = "src/main/resources/banner2.jpg";
    private JLabel labImagen;
    public PanelBanner( )
    {
        labImagen = new JLabel( );
        labImagen.setIcon( new ImageIcon( RUTA_IMAGEN ) );
        add(labImagen);
    }
}
