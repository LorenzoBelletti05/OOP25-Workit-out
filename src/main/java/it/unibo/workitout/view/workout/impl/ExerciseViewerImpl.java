package it.unibo.workitout.view.workout.impl;

import java.util.List;
import java.util.Optional;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import it.unibo.workitout.controller.workout.impl.UserExerciseControllerImpl;
import it.unibo.workitout.model.workout.impl.Exercise;
import it.unibo.workitout.view.workout.contracts.ExerciseViewer;

/**
 * Class view for show raw exercise.
 */
public final class ExerciseViewerImpl extends JPanel implements ExerciseViewer {

    private static final long serialVersionUID = 1L;
    private static final String ALL_FILTER = "All";
    private static final int FIELD_SIZE = 15;

    //Const for based caloris at minutes.
    private static final int BASE_CALORIES = 1;
    private final String[] indexColumnName = {"Name", "Kcal/Min", "Physical target", "Type Exercise"};
    private final DefaultTableModel modelRawExercise;
    private final JButton searchButton = new JButton("Find");
    private final JTable tableRawExercise;
    private final JTextField searchField = new JTextField(FIELD_SIZE);
    private final JButton backButtonView = new JButton("Back");

    private final JComboBox<String> typeComboBox = new JComboBox<>(new String[] {
        ALL_FILTER,
        "CARDIO",
        "STRENGTH",
    });

    private final JComboBox<String> targetComboBox = new JComboBox<>(new String[] {
        ALL_FILTER,
        "LOSE_WEIGHT",
        "BUILD_MUSCLE",
        "MAINTAIN_WEIGHT",
        "GAIN_WEIGHT",
    });

    /**
     * Costructor that set all the view.
     */
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
        this.tableRawExercise = new JTable(modelRawExercise);
        final JScrollPane scrollPane = new JScrollPane(tableRawExercise);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(btnPanel, BorderLayout.NORTH);
        final JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(backButtonView);
        this.add(southPanel, BorderLayout.SOUTH);

        //call the static method to take the 
        final List<Exercise> rawExercise = UserExerciseControllerImpl.getInstance().getRawExercise();
        this.setExercises(rawExercise);

        searchButton.addActionListener(e -> {
            final String dataInserted = searchField.getText().trim();

            if (!dataInserted.isEmpty()) {

                this.setExercises(UserExerciseControllerImpl.getInstance()
                    .orderListBasedOn(
                        "Name", 
                        rawExercise, 
                        Optional.of(dataInserted)
                    ));
            } else {
                this.setExercises(rawExercise);
            }

        });

        typeComboBox.addActionListener(e -> {
            if (ALL_FILTER.equals(typeComboBox.getSelectedItem().toString())) {
                this.setExercises(rawExercise);
                return;
            }
            this.setExercises(UserExerciseControllerImpl.getInstance()
                .orderListBasedOn(
                    "type", 
                    rawExercise, 
                    Optional.of(typeComboBox.getSelectedItem().toString())
                )
            );
        });

        targetComboBox.addActionListener(e -> {
            if (ALL_FILTER.equals(targetComboBox.getSelectedItem().toString())) {
                this.setExercises(rawExercise);
                return;
            }
            this.setExercises(UserExerciseControllerImpl.getInstance()
                .orderListBasedOn(
                    "target", 
                    rawExercise, 
                    Optional.of(targetComboBox.getSelectedItem().toString())
                )
            );
        });
    }

    /** {@inheritDoc} */
    @Override
    public void setExercises(final List<Exercise> exercises) {
        modelRawExercise.setRowCount(0);
        if (exercises == null) {
            return;
        }
        for (final Exercise exercise : exercises) {
            if (exercise != null) {
                modelRawExercise.addRow(new Object[] {
                    exercise.getName(),
                    exercise.calorieBurned(BASE_CALORIES),
                    exercise.getExerciseAttitude(),
                    exercise.getExerciseType(),
                });
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public int getExercise() {
        return tableRawExercise.getSelectedRow();
    }

    /**
     * Open the main view.
     */
    @Override
    public void addMainBackListener(final ActionListener listener) {
        this.backButtonView.addActionListener(listener);
    }

}
