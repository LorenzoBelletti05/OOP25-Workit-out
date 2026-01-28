package it.unibo.workitout.view.workout.impl;

import it.unibo.workitout.view.workout.contracts.PlanViewer;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Graphical {@link DrawNumberView} implementation.
 */
public final class PlanViewerImpl extends JPanel implements PlanViewer {

    private final String[] indexColumnName = {"Esercizio", "Volume/Tempo", "Peso/Velocità", "Kcal", "Stato"};

    private final JTable table;
    private final DefaultTableModel tableModel; 

    final JButton searchButton = new JButton("Cerca scheda");
    final JButton planButton = new JButton("Vis. piano");
    final JButton sheetButton = new JButton("Vis. scheda"); //associated with a combo box with all the data calculated from the logic
    final JButton checkMarkButton = new JButton("Segna Completato +");

    private final JTextField searchInTable = new JTextField(15);    


    public PlanViewerImpl() {        
        this.setLayout(new BorderLayout());

        JPanel chearchPanel = new JPanel();
        chearchPanel.add(new JLabel("Nome/Data:"));
        chearchPanel.add(searchInTable);
        chearchPanel.add(planButton);
        chearchPanel.add(sheetButton);        

        tableModel = new DefaultTableModel(indexColumnName, 0);
        table = new JTable(tableModel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(checkMarkButton);

        this.add(chearchPanel, BorderLayout.NORTH);
        this.add(new JScrollPane(table), BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

    }

}
