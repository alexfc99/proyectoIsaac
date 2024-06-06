package jpaswing.ui;

import jpaswing.entity.Item;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

@Component
public class PanelInformacion extends JPanel {
    private JLabel label1;
    private JTextField idField;
    private JLabel label2;
    private JTextField nameField;
    private JLabel label3;
    private JTextField qualityField;
    private JLabel label4;
    private JTextArea descriptionArea;
    private JScrollPane descriptionScrollPane;

    public PanelInformacion() {
        setLayout(new GridLayout(4, 2, 10, 10));
        setBorder(new TitledBorder("Info"));

        label1 = new JLabel("ID:");
        idField = new JTextField();

        label2 = new JLabel("Name:");
        nameField = new JTextField();

        label3 = new JLabel("Quality:");
        qualityField = new JTextField();

        label4 = new JLabel("Description:");
        descriptionArea = new JTextArea();
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionScrollPane = new JScrollPane(descriptionArea);
        descriptionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(label1);
        add(idField);
        add(label2);
        add(nameField);
        add(label3);
        add(qualityField);
        add(label4);
        add(descriptionScrollPane);
    }

    public void update(Item item) {
        idField.setText(String.valueOf(item.getId()));
        nameField.setText(item.getName());
        qualityField.setText(String.valueOf(item.getQuality()));
        descriptionArea.setText(item.getDescription());
    }
}
