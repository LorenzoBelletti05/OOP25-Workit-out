package it.unibo.workitout.controller.workout.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.swing.JOptionPane;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.workitout.controller.main.contracts.MainController;
import it.unibo.workitout.controller.workout.contracts.UserExerciseController;
import it.unibo.workitout.model.main.WorkoutUserData;
import it.unibo.workitout.model.main.datamanipulation.LoadSaveData;
import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.model.user.model.impl.UserProfile;
import it.unibo.workitout.model.workout.contracts.PlannedExercise;
import it.unibo.workitout.model.workout.contracts.WorkoutPlan;
import it.unibo.workitout.model.workout.contracts.WorkoutSheet;
import it.unibo.workitout.model.workout.impl.Exercise;
import it.unibo.workitout.model.workout.impl.WorkoutCreatorImpl;
import it.unibo.workitout.model.workout.impl.WorkoutPlanImpl;
import it.unibo.workitout.model.workout.impl.WorkoutSheetImpl;
import it.unibo.workitout.view.workout.contracts.PlanViewer;

/**
 * Controller class that manage the view and the model of the workout part and comunicate with the other controllers.
 */
public final class UserExerciseControllerImpl implements UserExerciseController {

    private static final String PATH_RAW_EXERCISE = "/data/workout/exercise.json";
    private static final int DAYS_IN_WEEK = 7;
    private static final String PROFILE_FILE = "user_profile.json";

    private final String pathToManageWorkoutPlan = LoadSaveData.createPath("workoutPlan.json");
    private final String pathToWorkoutUserData = LoadSaveData.createPath("workoutDataUser.json");

    private double bmr;
    private double tdee;
    private double dailyCalories;
    private ActivityLevel activityLevel;
    private UserGoal userGoal;
    private WorkoutPlan generatedWorkoutPlan;
    private PlanViewer planView;
    private MainController mainController;

    private UserExerciseControllerImpl() {
        // Private constructor for Singleton
    }

    /**
     * Get instance of the controller.
     * 
     * @return the instance.
     * 
     */
    public static UserExerciseControllerImpl getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * Used to take and istance of the mainController.
     * 
     * @param mainController istance of the main controller.
     * 
     */
    public void setMainController(final MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * method that from the data given from the user save it (with the current date) in a JSON.
     * 
     * @throws IOException IO.
     */
    private void callSaveUserData() throws IOException {
        final WorkoutUserData data = new WorkoutUserData(
            this.bmr, this.tdee, 
            this.dailyCalories, 
            this.activityLevel, 
            this.userGoal, 
            LocalDate.now().toString()
        );
        LoadSaveData.saveWorkoutuserDataIn(this.pathToWorkoutUserData, data);

    }

    /**
     * Method that based on  the data call the workoutCreatorImpl to create the workout.
     * 
     * @return the WorkoutPlan generated.
     */
    private WorkoutPlan checkAndCreate() {

        //try to load the dataUser from the json.
        final WorkoutUserData workoutUserData = LoadSaveData.loadWorkoutuserDataIn(pathToWorkoutUserData);

        //Check if the dataUser are present then get it and put in the local var, 
        // otherwise take the data from the user module.
        if (workoutUserData != null) { 
            this.bmr = workoutUserData.getBMR();
            this.tdee = workoutUserData.getTDEE();
            this.dailyCalories = workoutUserData.getDailyCalories();
            this.activityLevel = workoutUserData.getActivityLevel();
            this.userGoal = workoutUserData.getUserGoal();            
        } else {
            final UserProfile mainProfile = LoadSaveData.loadUserProfile(
                LoadSaveData.createPath(PROFILE_FILE)
            );
            if (mainProfile != null) {
                this.activityLevel = mainProfile.getActivityLevel();
                this.userGoal = mainProfile.getUserGoal();
            } else {
                return null;
            }
        }

        //Try to load from json the plan, otherwise, set it null.
        final WorkoutPlan workoutPlan = LoadSaveData.loadWorkoutPlan(pathToManageWorkoutPlan);

        //Check if the workoutPlan and the oldData json are full (!= null) or not (== null).
        //If full check if the datee is between the week (7 days),
        // if true return the full plan, otherwise generate it.
        if (workoutPlan != null && workoutUserData != null) {

            final LocalDate date = LocalDate.parse(workoutUserData.getLocalDate());

            if (java.time.temporal.ChronoUnit.DAYS.between(date, LocalDate.now()) <= DAYS_IN_WEEK) {
                return workoutPlan;
            }
        }

        return generateAfterAll();
    }

    /**
     * Method that generaate the Plan and save it.
     * 
     * @return the workoutPlan.
     */
    private WorkoutPlan generateAfterAll() {

        try {
            
            List<Exercise> exercises = getRawExercise();
            System.out.println("DEBUG: Esercizi caricati: " + (exercises != null ? exercises.size() : "NULL"));
            
            System.out.println("DEBUG: Dati per generazione - BMR: " + bmr + ", Goal: " + userGoal);

            final WorkoutPlan plan = new WorkoutCreatorImpl().generatePlan(
                bmr, tdee, dailyCalories, activityLevel, userGoal
            );
            
            if (plan != null && plan.getWorkoutPlan() != null) {
                System.out.println("DEBUG: Schede nel piano: " + plan.getWorkoutPlan().size());
            } else {
                System.out.println("DEBUG: Il piano generato è vuoto o null!");
            }

            LoadSaveData.saveWorkoutPlan(pathToManageWorkoutPlan, plan);
            callSaveUserData();
            return plan;

        } catch (final Throwable e) {
            e.printStackTrace();
            return null;
        }
    

        //Try generate the plan with the datam save the plan and the data in the json.
        // try {
        //     final WorkoutPlan plan = new WorkoutCreatorImpl().generatePlan(
        //         bmr, 
        //         tdee, 
        //         dailyCalories, 
        //         activityLevel, 
        //         userGoal
        //     );

        //     //save the generated workoutplan
        //     LoadSaveData.saveWorkoutPlan(pathToManageWorkoutPlan, plan);
        //     //call the method to save the new user data
        //     callSaveUserData();

        //     return plan;

        // } catch (final IOException e) {
        //     e.printStackTrace(); 
        //     JOptionPane.showMessageDialog(null, "Errore salvataggio piano: " + e.toString());
        //     return null;
        // }
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, WorkoutSheet> getWorkoutPlan() {
        if (this.generatedWorkoutPlan == null) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(this.generatedWorkoutPlan.getWorkoutPlan());
    }

    /** {@inheritDoc} */
    @Override
    public List<WorkoutSheet> getWorkoutSheets() {

        if (this.generatedWorkoutPlan == null) {
            return Collections.emptyList();
        }

        final List<String> sortedExercise = new ArrayList<>(generatedWorkoutPlan.getWorkoutPlan().keySet());
        Collections.sort(sortedExercise);

        final List<WorkoutSheet> listSheets = new ArrayList<>();
        for (final var element : sortedExercise) {
            listSheets.add(generatedWorkoutPlan.getWorkoutPlan().get(element));
        }
        return listSheets;
    }

    /** {@inheritDoc} */
    @Override
    public List<Exercise> getRawExercise() {
        return LoadSaveData.loadFromResources(PATH_RAW_EXERCISE, Exercise[].class);
    }

    /** {@inheritDoc} */
    @Override
    public List<Exercise> orderListBasedOn(
        final String conditionSort, 
        final List<Exercise> rawExercise, 
        final Optional<String> data
    ) {
        final List<Exercise> currentRawExercise = new ArrayList<>();
        final String query = data.orElse("").toUpperCase(Locale.ROOT);

        for (final Exercise ex : rawExercise) {
            String target = "";
            switch (conditionSort) {
                case "Name":
                    target = ex.getName();
                    break;
                case "type":
                    target = ex.getExerciseType().name();
                    break;
                case "target":
                    target = ex.getExerciseAttitude();
                    break;
                default:
                    break;
            }
            if (target.toUpperCase(Locale.ROOT).contains(query)) {
                currentRawExercise.add(ex);
            }
        }
        return currentRawExercise;
    }

    /** {@inheritDoc} */
    @Override
    public void setDataUser(
        final double bmrValue,
        final double tdeeValue,
        final double dailyCaloriesValue,
        final ActivityLevel activityLevelValue,
        final UserGoal userGoalValue
    ) {

        this.bmr = bmrValue;
        this.tdee = tdeeValue;
        this.dailyCalories = dailyCaloriesValue;
        this.activityLevel = activityLevelValue;
        this.userGoal = userGoalValue;

        this.generatedWorkoutPlan = generateAfterAll();

    }

    /** {@inheritDoc} */
    @Override
    public WorkoutPlan getGeneratedWorkoutPlan() {
        if (this.generatedWorkoutPlan == null) {
            this.generatedWorkoutPlan = LoadSaveData.loadWorkoutPlan(
                LoadSaveData.createPath("workoutPlan.json")
            );
        }
        //return a copy for security
        if (this.generatedWorkoutPlan == null) {
            return null; 
        }
        return new WorkoutPlanImpl(this.generatedWorkoutPlan);

    }

    /** {@inheritDoc} */
    @Override
    public void setNavigationTask(final Runnable navigationTask) {
        // this.navigationTask = navigationTask;
    }

    /** {@inheritDoc} */
    @Override
    public void refreshTableWorkoutData(final Runnable navigationTask) {

        if (this.generatedWorkoutPlan == null) {
            this.generatedWorkoutPlan = LoadSaveData.loadWorkoutPlan(pathToManageWorkoutPlan);
            checkAndCreate();
        }

        if (this.planView != null) {
            this.planView.setTable();
        }

        if (navigationTask != null) {
            navigationTask.run();
        }
    }

    /** {@inheritDoc} */
    @Override
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2", 
        justification = "Storing a reference to the View is essential for the MVC pattern implementation."
    )
    public void setView(final PlanViewer view) {
        this.planView = view;
    }

    /** {@inheritDoc} */
    @Override
    public void saveCurrentPlan() {
        if (this.generatedWorkoutPlan != null) {
            try {
                LoadSaveData.saveWorkoutPlan(pathToManageWorkoutPlan, this.generatedWorkoutPlan);
            } catch (final IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving current plan: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void replaceExercise(final String date, final PlannedExercise oldEx, final PlannedExercise newEx) {
        if (this.generatedWorkoutPlan == null) {
            return;
        }

        final Map<String, WorkoutSheet> planMap = new HashMap<>(this.generatedWorkoutPlan.getWorkoutPlan());
        final WorkoutSheet oldSheet = planMap.get(date);

        if (oldSheet != null) {
            final Set<PlannedExercise> list = new HashSet<>(oldSheet.getWorkoutSheet());
            if (list.remove(oldEx)) {
                list.add(newEx);
                final WorkoutSheet newSheet = new WorkoutSheetImpl(oldSheet.getName());
                for (final PlannedExercise p : list) {
                    newSheet.addExercise(p);
                }
                planMap.put(date, newSheet);

                final WorkoutPlan newPlan = new WorkoutPlanImpl(this.generatedWorkoutPlan.getName());

                for (final Map.Entry<String, WorkoutSheet> entry : planMap.entrySet()) {
                    newPlan.addWorkSheet(entry.getKey(), entry.getValue());
                }

                this.generatedWorkoutPlan = newPlan;
                saveCurrentPlan();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setProfile(final double totKcal) {
        if (this.mainController != null) {
            this.mainController.communicateBurnedCalories(totKcal);
        }
    }

    /**
     * Inner class to avoid security problem with singleton.
     */
    private static final class Holder {
        private static final UserExerciseControllerImpl INSTANCE = new UserExerciseControllerImpl();
    }

}
