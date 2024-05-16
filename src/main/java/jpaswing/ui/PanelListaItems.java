package jpaswing.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.*;

public class PanelListaItems extends JPanel implements ListSelectionListener, ActionListener {
    private InterfazItem principal;
    private JScrollPane scrollItems;
    private JList<String> items;
    private DefaultListModel<String> listModel;
    private JLabel imagenLabel;
    public PanelListaItems(InterfazItem pPrincipal) throws SQLException {
        principal = pPrincipal;
        setLayout(new BorderLayout());
        setBorder(new TitledBorder("Items"));
        setPreferredSize(new Dimension(200, 500));

        listModel = new DefaultListModel<>();
        items = new JList<>(listModel);
        JScrollPane scrollItems = new JScrollPane(items);
        items.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        items.addListSelectionListener(this);
        scrollItems.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollItems.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollItems, BorderLayout.CENTER);

        cargarDatosDesdeBD();
    }
    private void cargarDatosDesdeBD() throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/IsaacItems")) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name FROM items");
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

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
