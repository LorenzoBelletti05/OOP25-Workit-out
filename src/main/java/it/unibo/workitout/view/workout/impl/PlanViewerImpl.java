package it.unibo.workitout.view.workout.impl;

import it.unibo.workitout.controller.workout.impl.UserExerciseControllerImpl;
import it.unibo.workitout.model.workout.contracts.PlannedExercise;
import it.unibo.workitout.model.workout.contracts.WorkoutPlan;
import it.unibo.workitout.model.workout.contracts.WorkoutSheet;
import it.unibo.workitout.model.workout.impl.CardioPlannedExerciseImpl;
import it.unibo.workitout.model.workout.impl.StrengthPlannedExerciseImpl;
import it.unibo.workitout.view.main.impl.MainViewImpl;
import it.unibo.workitout.view.workout.contracts.PlanViewer;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Graphical {@link DrawNumberView} implementation.
 */
public final class PlanViewerImpl extends JPanel implements PlanViewer {

    private static final long serialVersionUID = 1L;
    private static final int SEARCH_FIELD_COLUMNS = 15;
    private static final int GRID_HGAP = 10;
    private static final int GRID_VGAP = 10;
    private static final int STRENGTH_TIME_MULT = 3;
    private static final int TABLE_COLUMNS_COUNT = 7;

    //Const for magic number
    private static final int KCAL_COL_INDEX = 5;
    private static final int STATE_COL_INDEX = 6;

    private transient List<PlannedExercise> currentExercises = new ArrayList<>();
    private transient List<String> rawDates = new ArrayList<>(); 

    private final String[] indexColumnName = {"Date", "Exercise", "Sets/Reps", "Time", "Weight/Distance", "Kcal", "State"};

    private JTable table;
    private DefaultTableModel tableModel;

    private final JButton planButton = new JButton("Vis. plan");
    private final JButton checkMarkButton = new JButton("Check as completed +");
    private final JButton backButton = new JButton("Back");
    private int currentDayIndex;

    private final JTextField searchInTable = new JTextField(SEARCH_FIELD_COLUMNS);

    private transient MainViewImpl mainView = new MainViewImpl();

    /**
     * Costructor that set the visibility and call all the set for the view.
     */
    public PlanViewerImpl() { 
        UserExerciseControllerImpl.getInstance().setView(this);
        createView();
        setTable();
    }

    private void createView() {
        this.setLayout(new BorderLayout());

        final JPanel chearchPanel = new JPanel();
        chearchPanel.add(new JLabel("Name/Data:"));
        chearchPanel.add(searchInTable);
        chearchPanel.add(planButton);

        tableModel = new DefaultTableModel(indexColumnName, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(final int row, final int column) {
                return false;
            }
        };

        table = new JTable(tableModel);

        final JPanel bottomPanel = new JPanel();
        bottomPanel.add(checkMarkButton);
        bottomPanel.add(backButton);

        this.add(chearchPanel, BorderLayout.NORTH);
        this.add(new JScrollPane(table), BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        checkMarkButton.addActionListener(e -> {
            final int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                final String rawFinalDate = rawDates.get(selectedRow);
                final PlannedExercise selectedExercise = currentExercises.get(selectedRow);

                if (selectedExercise.isComplited()) {
                    JOptionPane.showMessageDialog(this, 
                        "This exercise has already been completed. Good job! Keep going!",
                         "Exercise already done", 
                        JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                privatePageEdit(selectedExercise, rawFinalDate);
            }
        });

        planButton.addActionListener(e -> {
            final int totalDays = UserExerciseControllerImpl.getInstance().getWorkoutSheets().size();

            if (currentDayIndex > 0 && currentDayIndex < totalDays) {
                currentDayIndex++;
            } else if (currentDayIndex == totalDays) {
                currentDayIndex = 0;
            } else {
                currentDayIndex = 1;
            }

            setTable();

        });

        backButton.addActionListener(e -> {
            mainView.showView("DASHBOARD");
        });

        if (currentDayIndex == 0) {
            planButton.setText("Vis: All Plan");
        } else {
            planButton.setText("Vis: Week " + currentDayIndex);
        }
    }

    /**
     * Method to resolve SpotBugs, serializable-deserializable.
     * 
     * @param oisData the InputStream
     * @throws ClassNotFoundException the error may be throw.
     * @throws IOException the error may be throw.
     */
    private void readObject(final ObjectInputStream oisData) throws ClassNotFoundException, IOException {
        oisData.defaultReadObject();
        this.currentExercises = new ArrayList<>();
        this.rawDates = new ArrayList<>();
        this.mainView = new MainViewImpl();
    }

    /**
     * The page showed when the user press the check and save button.
     * 
     * @param plannedExercise the planned exercise selected.
     * 
     * @param dateExercise the date of the exercise.
     * 
     */
    private void privatePageEdit(final PlannedExercise plannedExercise, final String dateExercise) {
        final JDialog pageDialog = new JDialog();
        pageDialog.setTitle("Modify: " + plannedExercise.getExercise().getName());
        pageDialog.setModal(true);
        pageDialog.setLayout(new GridLayout(0, 2, GRID_HGAP, GRID_VGAP));

        if (plannedExercise instanceof StrengthPlannedExerciseImpl) {
            final var strenghtExercise = (StrengthPlannedExerciseImpl) plannedExercise;
            final JTextField setsField = new JTextField(String.valueOf(strenghtExercise.getSets()));
            final JTextField repsField = new JTextField(String.valueOf(strenghtExercise.getReps()));
            final JTextField weightField = new JTextField(String.valueOf(strenghtExercise.getWeight()));

            pageDialog.add(new JLabel("Sets:")); 
            pageDialog.add(setsField);
            pageDialog.add(new JLabel("Reps:")); 
            pageDialog.add(repsField);
            pageDialog.add(new JLabel("Weight (kg):")); 
            pageDialog.add(weightField);

            final JButton saveBtn = new JButton("Save-Done");
            saveBtn.addActionListener(al -> {

                //recreate the exercise from 0
                try {
                    //take the sets (modify or not) from the pop up page after save.
                    final Integer finalSets = Integer.parseInt(setsField.getText());
                    final Integer finalReps = Integer.parseInt(repsField.getText());
                    final double finalWeight = Double.parseDouble(weightField.getText());
                    final int finalMinutes = finalSets * STRENGTH_TIME_MULT;

                    //recreate the exercise from zero for the strenght type.
                    final PlannedExercise newEx = new StrengthPlannedExerciseImpl(
                        plannedExercise.getExercise(),
                        finalMinutes,
                        finalSets,
                        finalReps,
                        finalWeight
                    );

                    final double finalKcal = newEx.getExercise().calorieBurned(finalMinutes);

                    //set the exercise as completed
                    newEx.setCompletedExercise(true);

                    //replace in the generated workout the old planned exercise with the new
                    UserExerciseControllerImpl.getInstance().replaceExercise(dateExercise, plannedExercise, newEx);

                    //call the methot to give data to User module
                    UserExerciseControllerImpl.getInstance().setProfile(finalKcal);

                    setTable();
                    pageDialog.dispose();
                } catch (final NumberFormatException e) {
                    JOptionPane.showMessageDialog(pageDialog, "Insert valid number");
                }
            });

            pageDialog.add(saveBtn);
        } else if (plannedExercise instanceof CardioPlannedExerciseImpl) {
            final var cardioExercise = (CardioPlannedExerciseImpl) plannedExercise;
            final JTextField distField = new JTextField(String.valueOf(cardioExercise.getDistance()));
            final JTextField minField = new JTextField(String.valueOf(cardioExercise.getMinutes()));

            pageDialog.add(new JLabel("Distance (km):"));
            pageDialog.add(distField);
            pageDialog.add(new JLabel("Time (min):"));
            pageDialog.add(minField);

            final JButton saveBtn = new JButton("Save & Done");
            saveBtn.addActionListener(al -> {

                try {
                    final double finalDistance = Double.parseDouble(distField.getText());
                    final Integer finalMin = Integer.parseInt(minField.getText());

                    //recreate the exercise from 0
                    final PlannedExercise newEx = new CardioPlannedExerciseImpl(
                        plannedExercise.getExercise(),
                        finalMin,
                        finalDistance
                    );

                    final double finalKcal = newEx.getExercise().calorieBurned(finalMin);

                    newEx.setCompletedExercise(true);
                    UserExerciseControllerImpl.getInstance().replaceExercise(dateExercise, plannedExercise, newEx);

                    UserExerciseControllerImpl.getInstance().setProfile(finalKcal);
                    setTable();
                    pageDialog.dispose();
                } catch (final NumberFormatException e) {
                    JOptionPane.showMessageDialog(pageDialog, "Insert valid numbers!");
                }
            });
            pageDialog.add(saveBtn);
        }

        pageDialog.pack();
        pageDialog.setLocationRelativeTo(this);
        pageDialog.setVisible(true);
    }

    @Override
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP", 
        justification = "Directly exposing the JButton is a choice to simplify " 
            + "Controller-View interaction. While it compromises encapsulation"
        )
    public JButton getBackButton() {
        return backButton;
    }

    @Override
    public void setTable() {

        final WorkoutPlan plan = UserExerciseControllerImpl.getInstance().getGeneratedWorkoutPlan();

        tableModel.setRowCount(0);
        this.currentExercises.clear();
        this.rawDates.clear();

        if (plan == null) {
            return;
        }

        //order the date because the date showed are been already sorted.
        final List<String> sortedRawDates = new ArrayList<>(plan.getWorkoutPlan().keySet());
        Collections.sort(sortedRawDates);

        //take all the sheet, from the sheet take the size (menas the day) and then set a start day and an end.
        final List<WorkoutSheet> allSheets = UserExerciseControllerImpl.getInstance().getWorkoutSheets();
        final int totalDays = allSheets.size();
        final int start = (currentDayIndex == 0) ? 0 : currentDayIndex - 1;
        final int end = (currentDayIndex == 0) ? totalDays : currentDayIndex;

        if (allSheets == null || allSheets.isEmpty()) {
            return;
        }

        //set the button text
        planButton.setText(currentDayIndex == 0 ? "Vis: All Plan" : "Vis: Day " + currentDayIndex);

        final Object[] row = new Object[TABLE_COLUMNS_COUNT];

        //For that from start to end (the sheets present) get from allSheet the i-n sheet and set his day based on his order.
        for (int i = start; i < end; i++) {

            //take the i-n sheet
            final WorkoutSheet workoutSheet = allSheets.get(i);
            final String dayLabel = "Day " + (i + 1);
            final String rawFinalDate = sortedRawDates.get(i);

            //From the i-n sheet take all his planned exercise and set his data to show.
            for (final PlannedExercise exercisePlanned : workoutSheet.getWorkoutSheet()) {

                this.currentExercises.add(exercisePlanned);
                this.rawDates.add(rawFinalDate);
                row[0] = dayLabel;
                row[1] = exercisePlanned.getExercise().getName();

                double min = 0;

                if (exercisePlanned instanceof StrengthPlannedExerciseImpl) {
                    final var exerStrenght = (StrengthPlannedExerciseImpl) exercisePlanned;
                    min = exerStrenght.getSets() * STRENGTH_TIME_MULT;
                    row[2] = exerStrenght.getSets() + " x " + exerStrenght.getReps();
                    row[3] = "/";
                    row[4] = exerStrenght.getWeight();
                } else if (exercisePlanned instanceof CardioPlannedExerciseImpl) {
                    final var exerCardio = (CardioPlannedExerciseImpl) exercisePlanned;
                    min = exerCardio.getMinutes();
                    row[2] = "/";
                    row[3] = min;
                    row[4] = exerCardio.getDistance();
                }
                row[KCAL_COL_INDEX] = String.format("%.0f", exercisePlanned.getExercise().calorieBurned(min));
                row[STATE_COL_INDEX] = exercisePlanned.isComplited() ? "Done" : "N.C.";
                tableModel.addRow(row);
            }
        }
    }
}
