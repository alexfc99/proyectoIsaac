package jpaswing.ui;

import jpaswing.entity.Item;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

@Component
public class PanelListaItems extends JPanel implements ListSelectionListener {
    private ItemUI principal;
    private JScrollPane scrollItems;
    private JList<String> items;
    private DefaultListModel<String> listModel;
    private JTextField searchField;
    private PanelImagen panelImagen;

    public PanelListaItems(ItemUI pPrincipal, PanelImagen pPanelImagen) throws SQLException {
        principal = pPrincipal;
        panelImagen = pPanelImagen;
        setLayout(new BorderLayout());
        setBorder(new TitledBorder("Items"));
        setPreferredSize(new Dimension(200, 200));

        listModel = new DefaultListModel<>();
        items = new JList<>(listModel);
        scrollItems = new JScrollPane(items);
        items.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        items.addListSelectionListener(this);
        scrollItems.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollItems.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollItems, BorderLayout.CENTER);

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtrarLista(searchField.getText());
            }
        });
        add(searchField, BorderLayout.SOUTH);

        cargarDatosDesdeBD();
    }

    private void cargarDatosDesdeBD() throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/IsaacItems")) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name FROM item");
            listModel.clear();

            while (resultSet.next()) {
                String item = resultSet.getString("name");
                listModel.addElement(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar datos desde la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filtrarLista(String texto) {
        DefaultListModel<String> filteredModel = new DefaultListModel<>();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/IsaacItems")) {
            String query = "SELECT name FROM item WHERE name LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + texto + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String item = resultSet.getString("name");
                filteredModel.addElement(item);
            }

            items.setModel(filteredModel);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al filtrar datos desde la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            String selectedItem = items.getSelectedValue();
            if (selectedItem != null) {
                try {
                    Item item = obtenerItemDesdeBD(selectedItem);
                    principal.actualizarPanelInformacion(item);

                    Image image = PanelImagen.getImageFromDatabase(selectedItem);
                    panelImagen.updateImage(image);
                    panelImagen.setImageSize(200, 200);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error al obtener datos del item seleccionado", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private Item obtenerItemDesdeBD(String nombre) throws SQLException {
        Item item = null;
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/IsaacItems")) {
            String query = "SELECT * FROM item WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nombre);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                item = new Item();
                item.setId(Long.valueOf(resultSet.getString("id")));
                item.setName(resultSet.getString("name"));
                item.setQuality(Integer.parseInt(resultSet.getString("quality")));
                item.setDescription(resultSet.getString("description"));
            }
        }
        return item;
    }
}
