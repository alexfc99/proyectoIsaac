package jpaswing.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class PanelPersonajes extends JPanel {
    private JLabel label;
    private JTextField idField;
    private JTextField nameField;
    private int currentPersonajeId = 1;
    private JTextField searchField;

    @Autowired
    public PanelPersonajes(PanelBanner panelBanner) {
        setLayout(new BorderLayout());


        add(panelBanner, BorderLayout.NORTH);


        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBorder(new TitledBorder("Info"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField();
        idField.setEditable(false);
        idField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(idLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        infoPanel.add(idField, gbc);

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        nameField.setEditable(false);
        nameField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        infoPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        infoPanel.add(nameField, gbc);

        add(infoPanel, BorderLayout.WEST);


        JPanel imagePanel = new JPanel(new GridBagLayout());
        imagePanel.setBorder(new TitledBorder("Image"));
        label = new JLabel("Imagen");
        imagePanel.add(label);
        add(imagePanel, BorderLayout.CENTER);


        JPanel searchPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Busqueda:");
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    buscarPersonajePorNombre(searchField.getText());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);


        JPanel southPanel = new JPanel(new BorderLayout());


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton prevButton = new JButton("<<");
        JButton nextButton = new JButton(">>");
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);


        southPanel.add(searchPanel, BorderLayout.WEST);
        southPanel.add(buttonPanel, BorderLayout.CENTER);

        add(southPanel, BorderLayout.SOUTH);

        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPersonajeId = Math.max(1, currentPersonajeId - 1);
                try {
                    cargarInformacionPersonaje(currentPersonajeId);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPersonajeId++;
                try {
                    cargarInformacionPersonaje(currentPersonajeId);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        try {
            cargarInformacionPersonaje(currentPersonajeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarInformacionPersonaje(int personajeId) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/IsaacItems");
            String sql = "SELECT id, name, url FROM characters WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, personajeId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String imgUrl = rs.getString("url");

                idField.setText(String.valueOf(id));
                nameField.setText(name);

                URL url = new URL(imgUrl);
                Image image = ImageIO.read(url);
                if (image != null) {
                    Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    ImageIcon imageIcon = new ImageIcon(scaledImage);
                    label.setIcon(imageIcon);
                    label.setText("");
                }
            } else {
                if (personajeId > 1) {
                    currentPersonajeId = personajeId - 1;
                    cargarInformacionPersonaje(currentPersonajeId);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
            }
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
            }
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
            }
        }
    }

    private void buscarPersonajePorNombre(String nombre) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/IsaacItems");
            String sql = "SELECT id, name, url FROM characters WHERE name LIKE ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + nombre + "%");
            rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String imgUrl = rs.getString("url");

                idField.setText(String.valueOf(id));
                nameField.setText(name);

                URL url = new URL(imgUrl);
                Image image = ImageIO.read(url);
                if (image != null) {
                    Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    ImageIcon imageIcon = new ImageIcon(scaledImage);
                    label.setIcon(imageIcon);
                    label.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró ningún personaje con ese nombre", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
            }
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
            }
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
            }
        }
    }
}