package zad1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Gui extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    Database database;
    Gui(Database database){
        this.database = database;
        setTitle("Language Switch Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JButton polishButton = new JButton("PL");
        polishButton.addActionListener((e)-> {
            tableModel.setRowCount(0);
            List<String> rowsInPl = database.findAll("pl");

            List<String[]> collect = rowsInPl.stream().map(el -> el.split("\t"))
                    .collect(Collectors.toList());

            for (String[] rowData : collect) {
                tableModel.addRow(rowData);
            }

        });


        JButton englishButton = new JButton("EN");
        englishButton.addActionListener(e-> {
                    tableModel.setRowCount(0);
                    List<String> rowsInEN = database.findAll("en_us");
                    List<String[]> collect = rowsInEN.stream().map(el -> el.split("\t"))
                            .collect(Collectors.toList());

                    for (String[] rowData : collect) {
                        tableModel.addRow(rowData);
                    }
                }
                );

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(polishButton);
        buttonPanel.add(englishButton);
        add(buttonPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new Object[]{"Lokalizacja", "Kraj", "Data od", "Data do", "Miejsce", "Cena", "Waluta"}, 0
        );
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }


}
