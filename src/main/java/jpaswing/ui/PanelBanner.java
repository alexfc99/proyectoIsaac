package jpaswing.ui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
