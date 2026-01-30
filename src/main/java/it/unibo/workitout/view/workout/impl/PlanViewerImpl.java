package it.unibo.workitout.view.workout.impl;

import it.unibo.workitout.view.workout.contracts.PlanViewer;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Graphical {@link DrawNumberView} implementation.
 */
public final class PlanViewerImpl extends JFrame implements PlanViewer {

    private final String[] indexColumnName = {"Exercise", "Volum/Time", "Weight/Speed", "Kcal", "State"};

    private final JTable table;
    private final DefaultTableModel tableModel; 

    final JButton searchButton = new JButton("Find sheet");
    final JButton planButton = new JButton("Vis. plan");
    final JButton sheetButton = new JButton("Vis. sheet"); //associated with a combo box with all the data calculated from the logic
    final JButton checkMarkButton = new JButton("Check as completed +");

    private final JTextField searchInTable = new JTextField(15);    


    public PlanViewerImpl() {        
        this.setLayout(new BorderLayout());

        JPanel chearchPanel = new JPanel();
        chearchPanel.add(new JLabel("Name/Data:"));
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
