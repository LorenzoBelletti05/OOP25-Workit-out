package it.unibo.workitout.view.workout.impl;

import java.util.List;
import java.util.Optional;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import it.unibo.workitout.controller.workout.impl.UserExerciseControllerImpl;
import it.unibo.workitout.model.workout.impl.Exercise;
import it.unibo.workitout.view.workout.contracts.ExerciseViewer;

public class ExerciseViewerImpl extends JPanel implements ExerciseViewer {

    private static final long serialVersionUID = 1L;
    private static final String All_FILTER = "All";
    private static final int FIELD_SIZE = 15;
    
    private final String[] indexColumnName = {"Name", "Kcal/Min", "Physical target", "Type Exercise"};
    private final DefaultTableModel modelRawExercise;
    final JButton searchButton = new JButton("Find");    
    private final JTable tableRawExercise;    
    private final JTextField searchField = new JTextField(FIELD_SIZE);

    private final JComboBox<String> typeComboBox = new JComboBox<>(new String[] {
        All_FILTER,
        "CARDIO",
        "STRENGTH"
    });

    private final JComboBox<String> targetComboBox = new JComboBox<>(new String[] {
        All_FILTER,
        "LOSE_WEIGHT",
        "BUILD_MUSCLE",
        "MAINTAIN_WEIGHT",
        "GAIN_WEIGHT"
    });

    public ExerciseViewerImpl() {
        super(new BorderLayout());

        final JPanel btnPanel = new JPanel();
        btnPanel.add(searchField);
        btnPanel.add(searchButton);
        btnPanel.add(targetComboBox);
        btnPanel.add(typeComboBox);

        this.modelRawExercise = new DefaultTableModel(indexColumnName, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(final int row, final int column) {
                return false;
            }
        };
        this.tableRawExercise  = new JTable(modelRawExercise);
        final JScrollPane scrollPane = new JScrollPane(tableRawExercise);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(btnPanel, BorderLayout.NORTH);

        //call the static method to take the 
        final List<Exercise> rawExercise = UserExerciseControllerImpl.getIstance().getRawExercise();
        this.setExercises(rawExercise);

        searchButton.addActionListener(e -> {
            final String dataInserted = searchField.getText().trim();

            if(!dataInserted.isEmpty()) {

                this.setExercises(UserExerciseControllerImpl.getIstance()
                    .orderListBasedOn(
                        "Name", 
                        rawExercise, 
                        Optional.of(dataInserted)
                    ));
            }else {
                this.setExercises(rawExercise);
            }

        });

        typeComboBox.addActionListener(e -> {
            if(typeComboBox.getSelectedItem().toString().equals(All_FILTER)) {
                this.setExercises(rawExercise);
                return;
            }
            this.setExercises(UserExerciseControllerImpl.getIstance()
                .orderListBasedOn(
                    "type", 
                    rawExercise, 
                    Optional.of(typeComboBox.getSelectedItem().toString())
                )
            );
        });

        targetComboBox.addActionListener(e -> {
            if(targetComboBox.getSelectedItem().toString().equals(All_FILTER)) {
                this.setExercises(rawExercise);
                return;
            }
            this.setExercises(UserExerciseControllerImpl.getIstance()
                .orderListBasedOn(
                    "target", 
                    rawExercise, 
                    Optional.of(targetComboBox.getSelectedItem().toString())
                )
            );
        });
    }

    @Override
    public void setExercises(final List<Exercise> exercises) {
        modelRawExercise.setRowCount(0);
        if(exercises == null) {
            return;
        }
        for (final Exercise exercise : exercises) {
            if(exercise != null) {
                modelRawExercise.addRow(new Object[] {
                    exercise.getName(),
                    exercise.calorieBurned(1),
                    exercise.getExerciseAttitude(),
                    exercise.getExerciseType()
                });
            }
        }
    }

    @Override
    public int getExercise() {
        return tableRawExercise.getSelectedRow();
    }

}
