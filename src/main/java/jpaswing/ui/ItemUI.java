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
    private PanelListaItems panelListaItems;
    private PanelImagen panelImagen;
    private PanelInformacion panelInformacion;
    private PanelPersonajes panelPersonajes;

    private ItemRepository itemRepository;

    @Autowired
    public ItemUI(ItemRepository itemRepository) throws SQLException {
        this.itemRepository = itemRepository;
        setTitle("Isaac Items");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();


        PanelBanner panelBannerItems = new PanelBanner();

        JPanel panelItems = new JPanel(new BorderLayout());
        panelItems.add(panelBannerItems, BorderLayout.NORTH);
        panelImagen = new PanelImagen();
        panelItems.add(panelImagen, BorderLayout.EAST);
        panelInformacion = new PanelInformacion();
        panelListaItems = new PanelListaItems(this, panelImagen);
        panelItems.add(panelListaItems, BorderLayout.WEST);
        panelItems.add(panelInformacion, BorderLayout.CENTER);
        tabbedPane.addTab("Items", panelItems);


        PanelBanner panelBannerPersonajes = new PanelBanner();

        panelPersonajes = new PanelPersonajes(panelBannerPersonajes);
        tabbedPane.addTab("Personajes", panelPersonajes);

        add(tabbedPane);
        setSize(1000, 650);
        setLocationRelativeTo(null);

        Item item = itemRepository.findFirstByOrderByIdAsc();
        if (item != null) {
            panelInformacion.update(item);
        }
    }

    public void actualizarPanelInformacion(Item item) {
        panelInformacion.update(item);
    }
}
