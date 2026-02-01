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

    private final String[] indexColumnName = {"Name", "Kcal/Min", "Physical target", "Type Exercise"};
    private DefaultTableModel modelRawExercise;


    final JButton searchButton = new JButton("Find");    
    
    final JComboBox<String> typeComboBox = new JComboBox<>(new String[]{
        "All",
        "CARDIO",
        "STRENGTH"
    });

    final JComboBox<String> targetComboBox = new JComboBox<>(new String[]{
        "All",
        "LOSE_WEIGHT",
        "BUILD_MUSCLE",
        "MAINTAIN_WEIGHT",
        "GAIN_WEIGHT"
    });

    final JLabel infoInsertionLable = new JLabel("Name exercise");
    final JTextField searchField = new JTextField(15);

    JTable tableRawExercise; //the table for the exercise
    JPanel btnPanel; //the panel of the button
    

    public ExerciseViewerImpl() {
        
        this.setLayout(new BorderLayout());
        btnPanel = new JPanel();
        btnPanel.add(infoInsertionLable);
        btnPanel.add(searchField);
        btnPanel.add(searchButton);
        
        btnPanel.add(targetComboBox);
        btnPanel.add(typeComboBox);
        
        
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

        //call the static method to take the 
        List<Exercise> rawExercise = UserExerciseControllerImpl.getRawExercise();
        this.setExercises(rawExercise);

        searchButton.addActionListener(e -> {
            String dataInserted = searchField.getText().trim();            

            if(!dataInserted.isEmpty()) {
                
                this.setExercises(UserExerciseControllerImpl.orderListBasedOn("Name", rawExercise, Optional.of(dataInserted)));
            }else {
                this.setExercises(rawExercise);
            }
        
        });        

        typeComboBox.addActionListener(e -> {
            if(typeComboBox.getSelectedItem().toString().equals("All")) {
                this.setExercises(rawExercise);
                return;
            }
            this.setExercises((UserExerciseControllerImpl.orderListBasedOn("type", rawExercise, Optional.of(typeComboBox.getSelectedItem().toString()))));
        });

        targetComboBox.addActionListener(e -> {
            if(targetComboBox.getSelectedItem().toString().equals("All")) {
                this.setExercises(rawExercise);
                return;
            }
            this.setExercises((UserExerciseControllerImpl.orderListBasedOn("target", rawExercise, Optional.of(targetComboBox.getSelectedItem().toString()))));
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
