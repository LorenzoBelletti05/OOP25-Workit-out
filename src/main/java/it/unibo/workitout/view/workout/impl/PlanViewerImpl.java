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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Graphical {@link DrawNumberView} implementation.
 */
public final class PlanViewerImpl extends JPanel implements PlanViewer {

    private List<PlannedExercise> currentExercises = new ArrayList<>();

    private final String[] indexColumnName = {"Date", "Exercise", "Sets/Reps", "Time", "Weight/Distance", "Kcal", "State"};

    private  JTable table;
    private  DefaultTableModel tableModel;     

    final JButton searchButton = new JButton("Find sheet");
    final JButton planButton = new JButton("Vis. plan");
    final JButton checkMarkButton = new JButton("Check as completed +");
    final JButton backButton = new JButton("Back");
    private int currentDayIndex = 0;

    private final JTextField searchInTable = new JTextField(15); 

    MainViewImpl mainView = new MainViewImpl();

    public PlanViewerImpl() { 
        UserExerciseControllerImpl.getIstance().setView(this);
        createView();
        setTable();
    }

    private void createView() {
        this.setLayout(new BorderLayout());

        JPanel chearchPanel = new JPanel();
        chearchPanel.add(new JLabel("Name/Data:"));
        chearchPanel.add(searchInTable);
        chearchPanel.add(planButton);

        tableModel = new DefaultTableModel(indexColumnName, 0);
        table = new JTable(tableModel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(checkMarkButton);
        bottomPanel.add(backButton);

        this.add(chearchPanel, BorderLayout.NORTH);
        this.add(new JScrollPane(table), BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        checkMarkButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow != -1) {
                final PlannedExercise selectedExercise = currentExercises.get(selectedRow);
                
                if (selectedExercise.isComplited()) {
                    JOptionPane.showMessageDialog(this, 
                        "This exercise has already been completed. Good job! Keep going!", 
                        "Exercise already done", 
                        JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                final String date = table.getValueAt(selectedRow, 0).toString();
                privatePageEdit(selectedExercise, date);

                //calculate the calories and then pass it to the controller
                UserExerciseControllerImpl.getIstance().setProfile(selectedExercise.getBurnedCalories());
            }
        });

        planButton.addActionListener(e -> {
            int totalDays = UserExerciseControllerImpl.getIstance().getWorkoutSheets().size();

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

    private void privatePageEdit(PlannedExercise plannedExercise, String dateExercise) {
        JDialog pageDialog = new JDialog();
        pageDialog.setTitle("Modifica: " + plannedExercise.getExercise().getName());
        pageDialog.setModal(true);
        pageDialog.setLayout(new GridLayout(0,2,10,10));

        if (plannedExercise instanceof StrengthPlannedExerciseImpl) {
            var strenghtExercise = (StrengthPlannedExerciseImpl) plannedExercise;
            JTextField setsField = new JTextField(String.valueOf(strenghtExercise.getSets()));
            JTextField repsField = new JTextField(String.valueOf(strenghtExercise.getReps()));
            JTextField weightField = new JTextField(String.valueOf(strenghtExercise.getWeight()));

            pageDialog.add(new JLabel("Sets:")); pageDialog.add(setsField);
            pageDialog.add(new JLabel("Reps:")); pageDialog.add(repsField);
            pageDialog.add(new JLabel("Weight (kg):")); pageDialog.add(weightField);

            JButton saveBtn = new JButton("Save-Done");
            saveBtn.addActionListener(al -> {

                //recreate the exercise from 0
                try {
                    // 1. RICREIAMO l'esercizio da zero con i nuovi dati
                    PlannedExercise newEx = new StrengthPlannedExerciseImpl(
                        plannedExercise.getExercise(),
                        plannedExercise.getMinutes(), // Manteniamo i minuti originali
                        Integer.parseInt(setsField.getText()),
                        Integer.parseInt(repsField.getText()),
                        Double.parseDouble(weightField.getText())
                    );

                    newEx.setCompletedExercise(true);

                    UserExerciseControllerImpl.getIstance().replaceExercise(dateExercise, plannedExercise, newEx);
                    setTable();
                    pageDialog.dispose();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(pageDialog, "Insert valid number");
                }
            });

            pageDialog.add(saveBtn);
        } 
        // Campi per Cardio
        else if (plannedExercise instanceof CardioPlannedExerciseImpl) {
            var cardioExercise = (CardioPlannedExerciseImpl) plannedExercise;
            JTextField distField = new JTextField(String.valueOf(cardioExercise.getDistance()));

            pageDialog.add(new JLabel("Distance (km):")); pageDialog.add(distField);

            JButton saveBtn = new JButton("Save & Done");
            saveBtn.addActionListener(al -> {

                //recreate the exercise from 0
                PlannedExercise newEx = new CardioPlannedExerciseImpl(
                    plannedExercise.getExercise(),
                    plannedExercise.getMinutes(),
                    Double.parseDouble(distField.getText())
                );

                newEx.setCompletedExercise(true);
                UserExerciseControllerImpl.getIstance().replaceExercise(dateExercise, plannedExercise, newEx);
                setTable();
                pageDialog.dispose();
            });
            pageDialog.add(saveBtn);
        }

        pageDialog.pack();
        pageDialog.setLocationRelativeTo(this);
        pageDialog.setVisible(true);
        } 

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }

    @Override
    public void close() {
        this.setVisible(false);
    }

    @Override
    public JButton getBackButton() {
        return backButton;
    }

    @Override
    public void setTable() {

        WorkoutPlan plan = UserExerciseControllerImpl.getIstance().getGeneratedWorkoutPlan();

        if (plan == null || plan.getWorkoutPlan() == null) {
            //DEBUG
            System.out.println("In attesa di dati...");
            tableModel.setRowCount(0); 
            return;
        }

        tableModel.setRowCount(0);
        this.currentExercises.clear();

        Map<String, WorkoutSheet> planExtended = plan.getWorkoutPlan();
        //DEBUG
        System.out.println("LOG: Giorni trovati nel piano: " + planExtended.size());
        Object[] row = new Object[7];

        for (Map.Entry<String, WorkoutSheet> planTotal : planExtended.entrySet()) {
            String dateLabel = planTotal.getKey(); 
            WorkoutSheet workoutSheet = planTotal.getValue();
            for (PlannedExercise exercisePlanned : workoutSheet.getWorkoutSheet()) {

                this.currentExercises.add(exercisePlanned);
                row[0] = dateLabel.toString();
                row[1] = exercisePlanned.getExercise().getName();
                double min = 0;

                if(exercisePlanned instanceof StrengthPlannedExerciseImpl) {
                    var exerStrenght = (StrengthPlannedExerciseImpl) exercisePlanned;
                    min = exerStrenght.getSets() * 3;
                    row[2] = exerStrenght.getSets() + " x " + exerStrenght.getReps();
                    row[3] = min;
                    row[4] =  exerStrenght.getWeight();
                } else if(exercisePlanned instanceof CardioPlannedExerciseImpl) {
                    var exerCardio = (CardioPlannedExerciseImpl) exercisePlanned;
                    min = exerCardio.getMinutes();
                    row[2] = "/";
                    row[3] = min;
                    row[4] = exerCardio.getDistance();
                }
                row[5] = String.format("%.0f", exercisePlanned.getExercise().calorieBurned(min));
                row[6] = exercisePlanned.isComplited() ? "Done" : "N.C.";
                tableModel.addRow(row);
            }
        }
    }
}
