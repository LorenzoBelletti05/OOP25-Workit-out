package it.unibo.workitout.controller.workout.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import it.unibo.workitout.controller.workout.contracts.UserExerciseController;
import it.unibo.workitout.model.main.WorkoutUserData;
import it.unibo.workitout.model.main.dataManipulation.loadSaveData;
import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.model.workout.contracts.WorkoutPlan;
import it.unibo.workitout.model.workout.contracts.WorkoutSheet;
import it.unibo.workitout.model.workout.impl.WorkoutCreatorImpl;;

public class UserExerciseControllerImpl implements UserExerciseController {

    private final double bmr;
    private final double tdee;
    private final double dailyCalories;
    private final ActivityLevel activityLevel;
    private final UserGoal userGoal;
    private final String pathToManageWorkoutPlan = "Workit-out\\src\\main\\resources\\data\\workout\\workoutPlan.json";
    private final String pathToWorkoutUserData = "Workit-out\\src\\main\\resources\\data\\workout\\workoutDataUser.json";
    private WorkoutUserData workoutUserData;
    private LocalDate localDate; 
    private final WorkoutPlan generatedWorkoutPlan;

    public UserExerciseControllerImpl(
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
        loadSaveData.saveWorkoutuserDataIn(pathToWorkoutUserData, workoutUserData);
    }

    private WorkoutPlan checkAndCreate() {

        //load from json the plan
        WorkoutPlan workoutPlan = loadSaveData.loadWorkoutPlan(pathToManageWorkoutPlan);
        workoutUserData = loadSaveData.getWorkoutuserDataIn(pathToWorkoutUserData);

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
                loadSaveData.saveWorkoutPlan(pathToManageWorkoutPlan, plan);
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

}
