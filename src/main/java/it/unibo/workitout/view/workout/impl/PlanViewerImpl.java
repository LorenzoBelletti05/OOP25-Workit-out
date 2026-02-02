package it.unibo.workitout.view.workout.impl;

import it.unibo.workitout.controller.workout.contracts.UserExerciseController;
import it.unibo.workitout.controller.workout.impl.UserExerciseControllerImpl;
import it.unibo.workitout.model.workout.contracts.PlannedExercise;
import it.unibo.workitout.model.workout.contracts.WorkoutPlan;
import it.unibo.workitout.model.workout.contracts.WorkoutSheet;
import it.unibo.workitout.model.workout.impl.CardioPlannedExerciseImpl;
import it.unibo.workitout.model.workout.impl.ExerciseType;
import it.unibo.workitout.model.workout.impl.StrengthPlannedExerciseImpl;
import it.unibo.workitout.view.main.impl.MainViewImpl;
import it.unibo.workitout.view.workout.contracts.PlanViewer;
import java.awt.BorderLayout;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
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

    private List<PlannedExercise> currentExercises;

    private final String[] indexColumnName = {"Exercise", "Sets/Reps", "Time", "Weight/Distance", "Kcal", "State"};

    private  JTable table;
    private  DefaultTableModel tableModel; 
    private  UserExerciseController userExerciseController;

    final JButton searchButton = new JButton("Find sheet");
    final JButton planButton = new JButton("Vis. plan");
    final JButton sheetButton = new JButton("Vis. sheet"); //associated with a combo box with all the data calculated from the logic
    final JButton checkMarkButton = new JButton("Check as completed +");
    final JButton backButton = new JButton("Back");

    private final JTextField searchInTable = new JTextField(15);  
    
    MainViewImpl mainView = new MainViewImpl();

    public PlanViewerImpl() {         
        
        createView();

        setTable();
    }    

    private void createView() {
        this.setLayout(new BorderLayout());

        JPanel chearchPanel = new JPanel();
        chearchPanel.add(new JLabel("Name/Data:"));
        chearchPanel.add(searchInTable);
        chearchPanel.add(planButton);
        chearchPanel.add(sheetButton);        
        chearchPanel.add(backButton);

        tableModel = new DefaultTableModel(indexColumnName, 0);
        table = new JTable(tableModel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(checkMarkButton);

        this.add(chearchPanel, BorderLayout.NORTH);
        this.add(new JScrollPane(table), BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        checkMarkButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow != -1) {
                PlannedExercise selectedExercise = currentExercises.get(selectedRow);

                if(selectedExercise.getExercise().getExerciseType().name().toString().equals(ExerciseType.STRENGTH.toString())) {
                    String weight = JOptionPane.showInputDialog(this, "Insert new weight (kg):");
                    String sets = JOptionPane.showInputDialog(this, "Insert new sets (kg):");
                    String reps = JOptionPane.showInputDialog(this, "Insert new reps (kg):");
                }
            }        
        });

        backButton.addActionListener(e -> {
            mainView.showView("DASHBOARD");
        });
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }

    @Override
    public void close() {
        this.setVisible(false);
    }

    /*private void showErrorController(String errorDescription) {
        JOptionPane.showMessageDialog(this, errorDescription, "Error", JOptionPane.ERROR_MESSAGE);
    }*/

    public JButton getBackButton() {
        return backButton;
    }    

    private void setTable() {        

        WorkoutPlan plan = UserExerciseControllerImpl.getIstance().getGeneratedWorkoutPlan();

        if (plan == null || plan.getWorkoutPlan() == null) {
            System.out.println("In attesa di dati...");
            tableModel.setRowCount(0); 
            return; 
        }

        tableModel.setRowCount(0);

        for (WorkoutSheet workoutSheet : plan.getWorkoutPlan().values()) {
            for (PlannedExercise exercisePlanned : workoutSheet.getWorkoutSheet()) {
                
                Object[] row = new Object[6];
                row[0] = exercisePlanned.getExercise().getName();
                double min = 0;
                
                if(exercisePlanned instanceof StrengthPlannedExerciseImpl) {
                    var exerStrenght = (StrengthPlannedExerciseImpl) exercisePlanned;
                    min = exerStrenght.getSets() * 3;
                    row[1] = exerStrenght.getSets() + " x " + exerStrenght.getReps();
                    row[2] = min;
                    row[3] =  exerStrenght.getWeight();
                } else if(exercisePlanned instanceof CardioPlannedExerciseImpl) {
                    var exerCardio = (CardioPlannedExerciseImpl) exercisePlanned;
                    min = exerCardio.getMinutes();
                    row[1] = "/";
                    row[2] = min;
                    row[3] = exerCardio.getDistance();
                }

                row[4] = String.format("%.0f", exercisePlanned.getExercise().calorieBurned(min));
                row[5] = exercisePlanned.isComplited() ? "Done" : "N.C.";

                tableModel.addRow(row);

            }
        }

    }

}
