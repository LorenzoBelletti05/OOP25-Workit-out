package it.unibo.workitout.view.workout.impl;

import java.util.ArrayList;
import java.util.List;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import it.unibo.workitout.model.workout.impl.Exercise;
import it.unibo.workitout.view.workout.contracts.ExerciseViewer;

public class ExerciseViewerImpl extends JPanel implements ExerciseViewer {

    private final String[] indexColumnName = {"Name", "Kcal/Min", "Physical target", "Type Exercise"};
    private List<Exercise> rawExercise = new ArrayList<>();
    private DefaultTableModel modelRawExercise;


    final JButton searchButton = new JButton("Cerca");
    final JButton kcalButton = new JButton("Ordina per Kcal");
    final JButton targhetButton = new JButton("Ordina per targht");
    final JButton typeButton = new JButton("Ordina per Type");
    JTable tableRawExercise; //the table for the exercise
    JPanel btnPanel; //the panel of the button
    

    public ExerciseViewerImpl() {
        
        this.setLayout(new BorderLayout());
        btnPanel = new JPanel();
        btnPanel.add(searchButton);
        btnPanel.add(kcalButton);
        btnPanel.add(targhetButton);
        btnPanel.add(typeButton);
        
        this.modelRawExercise = new DefaultTableModel(indexColumnName, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.tableRawExercise  = new JTable(modelRawExercise);
        JScrollPane scrollPane = new JScrollPane(tableRawExercise);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(btnPanel, BorderLayout.NORTH);
    }

    @Override
    public void setExercises(final List<Exercise> exercises) {
        modelRawExercise.setRowCount(0);
        for (final Exercise exercise : exercises) {
            modelRawExercise.addRow(new Object[] {
                exercise.getName(),
                exercise.calorieBurned(1),
                exercise.getExerciseAttitude(),
                exercise.getExerciseType()
            });
        }
    }

    @Override
    public int getExercise() {
        return tableRawExercise.getSelectedRow();
    }

}
