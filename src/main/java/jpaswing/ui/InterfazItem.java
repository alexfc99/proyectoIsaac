package jpaswing.ui;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

@Component
public class InterfazItem extends JFrame {
    private PanelBanner panelBanner;
    private PanelListaItems panelListaItems;

    public InterfazItem() throws SQLException {
        setTitle( "Isaac Items" );
        setSize( 1000, 850 );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        setLayout( new BorderLayout( ) );

        panelBanner = new PanelBanner( );
        add( panelBanner, BorderLayout.NORTH );
        JPanel panelCentro = new JPanel( );
        panelCentro.setLayout( new BorderLayout( ) );
        add( panelCentro, BorderLayout.CENTER );

        panelListaItems = new PanelListaItems( this );
        panelCentro.add( panelListaItems, BorderLayout.WEST );

    }

}
