package it.unibo.workitout.controller.workout.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.swing.JOptionPane;
import it.unibo.workitout.controller.workout.contracts.UserExerciseController;
import it.unibo.workitout.model.main.WorkoutUserData;
import it.unibo.workitout.model.main.dataManipulation.LoadSaveData;
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
import it.unibo.workitout.view.workout.impl.PlanViewerImpl;

/**
 * Controller class that manage the view and the model of the workout part and comunicate with the other controllers.
 */
public class UserExerciseControllerImpl implements UserExerciseController {

    private double bmr;
    private double tdee;
    private double dailyCalories;
    private ActivityLevel activityLevel;
    private UserGoal userGoal;
    private String path_To_Manage_Workout_Plan = LoadSaveData.createPath("workoutPlan.json");
    private String pathToWorkoutUserData = LoadSaveData.createPath("workoutDataUser.json");
    private final static  String pathToRawExercise = "Workit-out\\src\\main\\resources\\data\\workout\\exercise.json";
    private WorkoutUserData workoutUserData;
    private LocalDate localDate; 
    private WorkoutPlan generatedWorkoutPlan;
    private static UserExerciseControllerImpl instance;
    private PlanViewerImpl planView;
    private Runnable navigationTask;
    private final Integer DAYS_IN_WEEK = 7;

    /**
     * Costructor empty.
     */
    public UserExerciseControllerImpl() {
        
    }

    /**
     * method that from the data given from the user save it (with the current date) in a JSON.
     */
    private void callSaveUserData() {
        this.localDate = LocalDate.now();

        workoutUserData = new WorkoutUserData(
            bmr, 
            tdee,
            dailyCalories, 
            activityLevel, 
            userGoal, 
            localDate.toString()
        );

        LoadSaveData.saveWorkoutuserDataIn(pathToWorkoutUserData, workoutUserData);

    }

    /**
     * Method that based on  the data call the workoutCreatorImpl to create the workout.
     * 
     * @return the WorkoutPlan generated.
     */
    private WorkoutPlan checkAndCreate() {

        //try to load the dataUser from the json.
        try {
            workoutUserData = LoadSaveData.loadWorkoutuserDataIn(pathToWorkoutUserData); 
        } catch (NullPointerException e) {
            workoutUserData = null; //means that the json is empty and a new plan must be generated.
        }

        //Check if the dataUser are present then get it and put in the local var, otherwise take the data from the user module.
        if (workoutUserData != null) { 
            this.bmr = workoutUserData.getBmr();
            this.tdee = workoutUserData.getTdee();
            this.dailyCalories = workoutUserData.getdailyCalories();
            this.activityLevel = workoutUserData.getActivityLevel();
            this.userGoal = workoutUserData.getUserGoal();
            //DEBUG
            System.out.println("LOG: Dati caricati con successo per " + userGoal);
        } else {
            final UserProfile mainProfile = LoadSaveData.loadUserProfile(LoadSaveData.createPath("user_profile.json"));
            if (mainProfile != null) {
                this.activityLevel = mainProfile.getActivityLevel();
                this.userGoal = mainProfile.getGoal();
            } else {
                return null; 
            }
        }

        //Try to load from json the plan, otherwise, set it null.
        WorkoutPlan workoutPlan = null;
        try {
            workoutPlan = LoadSaveData.loadWorkoutPlan(path_To_Manage_Workout_Plan);
        } catch (final NullPointerException e) {
            workoutPlan = null;
        }

        //Check if the workoutPlan and the oldData json are full (!= null) or not (== null).
        if (workoutPlan != null && workoutUserData != null) {

            final LocalDate date = LocalDate.parse(workoutUserData.getLocalDate());

            //from the data of the json check the data if they are the same from the one given in the costructor.
            if(java.time.temporal.ChronoUnit.DAYS.between(date, LocalDate.now()) <= DAYS_IN_WEEK) {
                return workoutPlan;
            } 
        }

        //If jsons are empty (because both have to return the same result) 
        //generate the plan and save it on the json with the workout suer data.
        return generateAfterAll();
    }

    /**
     * Method that generaate the Plan and save it.
     * 
     * @return the workoutPlan.
     */
    private WorkoutPlan generateAfterAll() {
        //DEBUG
        System.out.println("Generazione per: " + userGoal + " con BMR: " + bmr);
        
        //Try generate the plan with the datam save the plan and the data in the json.
        try {
            final WorkoutPlan plan = new WorkoutCreatorImpl().generatePlan(bmr, tdee, dailyCalories, activityLevel, userGoal);
            if (plan != null) {
                LoadSaveData.saveWorkoutPlan(path_To_Manage_Workout_Plan, plan);
                callSaveUserData();

                return plan;
            }

        } catch (IOException e) {
            //DEGUB
            System.out.println("" + e.getMessage());
        }
        return null;
    }

    public Map<String, WorkoutSheet> getWorkoutPlan() {
        return this.generatedWorkoutPlan.getWorkoutPlan();
    }

    public List<WorkoutSheet> getWorkoutSheets() {
        List<WorkoutSheet> listSheets = new ArrayList<>();
        for (var element : generatedWorkoutPlan.getWorkoutPlan().values()) {
            listSheets.add(element);
        }
        return listSheets;
    }

    public List<Exercise> getRawExercise(){
        try {
            return List.copyOf(LoadSaveData.loadSavedDataFrom(pathToRawExercise, Exercise[].class));
        }catch (IOException e) {
            e.getMessage();
            return null;
        }
    }

    public List<Exercise> orderListBasedOn(String conditionSort, List<Exercise> rawExercise, Optional<String> data) {
        List<Exercise> currentRawExercise = new ArrayList<>();        
        switch (conditionSort) {
            case "Name":
                for (var element : rawExercise) {
                    if(element.getName().toUpperCase().contains(data.get().toUpperCase())) {
                        currentRawExercise.add(element);
                    }
                }
                break;           

            case "type":
                for (var element : rawExercise) {
                    if(element.getExerciseType().name().toString().toUpperCase().contains(data.get().toUpperCase())) {
                        currentRawExercise.add(element);
                    }
                }                
                break;

            case "target":
                for (var element : rawExercise) {
                    if(element.getExerciseAttitude().toString().toUpperCase().contains(data.get().toUpperCase())) {
                        currentRawExercise.add(element);
                    }
                }
                break;                

            default:
                break;
        }
        return currentRawExercise;
    }

    public void setDataUser(
        final double bmr,
        final double tdee,
        final double dailyCalories,
        final ActivityLevel activityLevel,
        final UserGoal userGoal
    ) {

        this.bmr = bmr;
        this.tdee = tdee;
        this.dailyCalories = dailyCalories;
        this.activityLevel = activityLevel;
        this.userGoal = userGoal;

        generatedWorkoutPlan = checkAndCreate();
    }

    //creating a single istance to avoid creating unnecessary istance with different behaviour
    public static UserExerciseControllerImpl getIstance() {
        if(instance == null) {
            instance = new UserExerciseControllerImpl();
        }
        return instance;
    }

    public WorkoutPlan getGeneratedWorkoutPlan() {
        if( this.generatedWorkoutPlan == null) {
            this.generatedWorkoutPlan = LoadSaveData.loadWorkoutPlan(LoadSaveData.createPath("workoutPlan.json"));
        }
        return this.generatedWorkoutPlan;
    }

    public void setNavigationTask(Runnable navigationTask) {
        this.navigationTask = navigationTask;
    }

    public void refreshTableWorkoutData(Runnable navigationTask) {

        if (this.generatedWorkoutPlan == null) {       
            
            int response = JOptionPane.showConfirmDialog(
                null, 
                "As a person of sound mind, do you declare and self-certify your motor skills and guarantee \nthat your physical health allows you to perform physical activities?", 
                "integrity", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE
            );

            if (response == JOptionPane.YES_OPTION) {
                
                this.generatedWorkoutPlan = checkAndCreate(); 

                if (this.generatedWorkoutPlan == null) {
                    JOptionPane.showMessageDialog(null, "Impossibile generare: mancano i dati del profilo!");
                }
                
                // if(this.planView != null) {
                //     this.planView.setTable();
                // }
            } else {
                
                return;
            }
        }        
        
        if (this.planView != null) {
            this.planView.setTable();
        }

        if (navigationTask != null) {
            navigationTask.run();
        }
        
            
    }

    public void setView(PlanViewerImpl view) {
        this.planView = view;
    }

    public void saveCurrentPlan() {
        if (this.generatedWorkoutPlan != null) {
            LoadSaveData.saveWorkoutPlan(path_To_Manage_Workout_Plan, this.generatedWorkoutPlan);
            System.out.println("LOG: Piano salvato su disco dopo la modifica.");
        }
    }

    public void replaceExercise(String date, PlannedExercise oldEx, PlannedExercise newEx) {
        if (this.generatedWorkoutPlan != null) {            
            Map<String, WorkoutSheet> planMap = new HashMap<>(this.generatedWorkoutPlan.getWorkoutPlan());
            WorkoutSheet oldSheet = planMap.get(date);            
            
            if (oldSheet != null) {                                
                Set<PlannedExercise> list = new HashSet<>(oldSheet.getWorkoutSheet());                
                if (list.remove(oldEx)) {
                    list.add(newEx);
                    WorkoutSheet newSheet = new WorkoutSheetImpl(oldSheet.getName());
                    for (PlannedExercise p : list) {
                        newSheet.addExercise(p);
                    }
                    planMap.put(date, newSheet);

                    WorkoutPlan newPlan = new WorkoutPlanImpl(this.generatedWorkoutPlan.getName());
                                    
                    for (Map.Entry<String, WorkoutSheet> entry : planMap.entrySet()) {
                        newPlan.addWorkSheet(entry.getKey(), entry.getValue());
                    }
                    
                    this.generatedWorkoutPlan = newPlan;
                        saveCurrentPlan();
                        System.out.println("LOG: Piano aggiornato con successo nel JSON.");
                    } else {
                        System.out.println("LOG: Esercizio vecchio non trovato nel set.");
                    }
            }
        }
    }

    public void setProfile(double totKcal) {
        //chiamata al metodo di del controller di diego a cui passo i dati
    }

}
