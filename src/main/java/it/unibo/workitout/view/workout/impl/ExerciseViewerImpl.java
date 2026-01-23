package it.unibo.workitout.view.workout.impl;

import java.util.ArrayList;
import java.util.List;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import it.unibo.workitout.model.workout.impl.Exercise;
import it.unibo.workitout.view.workout.contracts.ExerciseViewer;

public class ExerciseViewerImpl extends JPanel implements ExerciseViewer {

    private final String[] indexColumnName = {"Name", "Kcal/Min", "Physical target"};
    private List<Exercise> rawExercise = new ArrayList<>();
    private DefaultTableModel modelRawExercise;


    final JButton searchButton = new JButton("Cerca");
    final JButton highProteinButton = new JButton("Proteici");
    final JButton lowCarbsButton = new JButton("Pochi Carbo");
    JTable tableRawExercise;

    public ExerciseViewerImpl() {
        this.setLayout(new BorderLayout());
        this.modelRawExercise = new DefaultTableModel(indexColumnName, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.tableRawExercise  = new JTable(modelRawExercise);
        JScrollPane scrollPane = new JScrollPane(tableRawExercise);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void setExercises(final List<Exercise> exercises) {
        modelRawExercise.setRowCount(0);
        for (final Exercise exercise : exercises) {
            modelRawExercise.addRow(new Object[] {
                exercise.getName(),
                exercise.calorieBurned(1),
                exercise.getExerciseAttitude(),
            });
        }
    }

    @Override
    public int getExercise() {
        return tableRawExercise.getSelectedRow();
    }

}
