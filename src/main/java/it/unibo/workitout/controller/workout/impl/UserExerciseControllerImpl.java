package it.unibo.workitout.controller.workout.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import it.unibo.workitout.controller.workout.contracts.UserExerciseController;
import it.unibo.workitout.model.main.WorkoutUserData;
import it.unibo.workitout.model.main.dataManipulation.LoadSaveData;
import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.model.workout.contracts.WorkoutPlan;
import it.unibo.workitout.model.workout.contracts.WorkoutSheet;
import it.unibo.workitout.model.workout.impl.Exercise;
import it.unibo.workitout.model.workout.impl.WorkoutCreatorImpl;

public class UserExerciseControllerImpl implements UserExerciseController {

    private double bmr;
    private double tdee;
    private double dailyCalories;
    private ActivityLevel activityLevel;
    private UserGoal userGoal;
    private String pathToManageWorkoutPlan = LoadSaveData.createPath("workoutPlan.json");
    private String pathToWorkoutUserData = LoadSaveData.createPath("workoutDataUser.json");
    private static final String pathToRawExercise = LoadSaveData.createPath("exercise.json");
    private WorkoutUserData workoutUserData;
    private LocalDate localDate; 
    private WorkoutPlan generatedWorkoutPlan;
    private static UserExerciseControllerImpl instance;

    public UserExerciseControllerImpl() {

    }

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

    private WorkoutPlan checkAndCreate() {

        //load from json the plan
        WorkoutPlan workoutPlan = LoadSaveData.loadWorkoutPlan(pathToManageWorkoutPlan);
        workoutUserData = LoadSaveData.loadWorkoutuserDataIn(pathToWorkoutUserData);

        //check if the workoutPlan and the oldData json are full or not
        if(workoutPlan != null && workoutUserData != null) {

            LocalDate date = LocalDate.parse(workoutUserData.getLocalDate());
            long daysInWeek = java.time.temporal.ChronoUnit.DAYS.between(date, LocalDate.now());

            //from the data of the json check the data if they are the same from the one gived in the custractor
            if(daysInWeek <= 7) {

                if(this.bmr != workoutUserData.getBmr()
                    || this.tdee != workoutUserData.getTdee()
                    || this.dailyCalories != workoutUserData.getdailyCalories()
                    || this.activityLevel != workoutUserData.getActivityLevel()
                    || this.userGoal != workoutUserData.getUserGoal()
                ) {
                    return generateAfterAll();

                }else {
                    return workoutPlan;
                }
            } else {
                return generateAfterAll();
            }
        }

        //if jsons are empty (because both have to return the same result) generate the plan and save it on the json with the workout suer data
        return generateAfterAll();
    }

    private WorkoutPlan generateAfterAll() {
        WorkoutPlan plan;
        try {
            plan = new WorkoutCreatorImpl().generatePlan(bmr, tdee, dailyCalories, activityLevel, userGoal);
            if(plan != null) {
                LoadSaveData.saveWorkoutPlan(pathToManageWorkoutPlan, plan);
                callSaveUserData();

                return plan;
            }
        }catch (IOException e) {
            System.out.println("" + e.getMessage());
        }
        return null;
    }

    public Map<LocalDate, WorkoutSheet> getWorkoutPlan() {
        return this.generatedWorkoutPlan.getWorkoutPlan();
    }

    public static List<Exercise> getRawExercise(){
        try {
            return List.copyOf(LoadSaveData.loadSavedDataFrom(pathToRawExercise, Exercise[].class));
        }catch (IOException e) {
            e.getMessage();
            return null;
        }
    }

    public static List<Exercise> orderListBasedOn(String conditionSort, List<Exercise> rawExercise, Optional<String> data) {
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
    
}
