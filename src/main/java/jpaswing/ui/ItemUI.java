package jpaswing.ui;

import jpaswing.entity.Item;
import jpaswing.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

@Component
public class ItemUI extends JFrame {
    private PanelBanner panelBanner;
    private PanelListaItems panelListaItems;
    private PanelImagen panelImagen;
    private PanelInformacion panelInformacion;
@Autowired
private ItemRepository itemRepository;
    public ItemUI() throws SQLException {
        setTitle( "Isaac Items" );
        setSize( 1000, 850 );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        setLayout( new BorderLayout( ) );

        panelBanner = new PanelBanner( );
        add( panelBanner, BorderLayout.NORTH );

        JPanel panelCentro = new JPanel( );
        panelCentro.setLayout( new BorderLayout( ) );

        add( panelCentro, BorderLayout.CENTER );

        panelImagen = new PanelImagen();
        panelCentro.add(panelImagen,BorderLayout.EAST);

        panelListaItems = new PanelListaItems( this, panelImagen);
        panelCentro.add( panelListaItems, BorderLayout.WEST );

        panelInformacion = new PanelInformacion();
        panelCentro.add(panelInformacion, BorderLayout.CENTER);
        Item item = itemRepository.findFirstByOrderByIdAsc();
        if (item != null) {
            panelInformacion.update(item);
        }
    }

}
