package it.unibo.workitout.controller.workout.contracts;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.model.workout.contracts.PlannedExercise;
import it.unibo.workitout.model.workout.contracts.WorkoutPlan;
import it.unibo.workitout.model.workout.contracts.WorkoutSheet;
import it.unibo.workitout.model.workout.impl.Exercise;
import it.unibo.workitout.view.workout.impl.PlanViewerImpl;

/**
 * Interface that contains the definition of the methot to manage all the data from and for the view, from the model.
 */
public interface UserExerciseController {

    /**
     * It call the method in loadSaveData to save in the local variable of the class.
     * It can be taken with the {@link getWorkoutPlan}.
     * 
     * @return the workoutplan type.
     */
    WorkoutPlan getGeneratedWorkoutPlan();

    /**
     * Give the generated workoutplan from the local provate variable.
     * 
     * @return the hole workoutplan.
     */
    Map<String, WorkoutSheet> getWorkoutPlan();

    /**
     * It return the list of sheets in the workoutplan.
     * 
     * @return the list of the sheets.
     */
    List<WorkoutSheet> getWorkoutSheets();

    /**
     * Get the list of raw exercise from the file, which call the method from LoadSaveData.
     * 
     * @return the list of raw exercise.
     */
    List<Exercise> getRawExercise();

    /**
     * Method that sort the list of rawExercise, based on the parameter.
     * 
     * @param conditionSort the condition which on the method have to sort.
     * @param rawExercise the list of the raw exercise.
     * @param data value to comapare with the element of the raw exercise.
     * 
     * @return the list of exercise modify.
     * 
     */
    List<Exercise> orderListBasedOn(String conditionSort, List<Exercise> rawExercise, Optional<String> data);

    /**
     * Method that save the data given from the userProfile.
     * 
     * @param bmr the bmr
     * @param tdee the tdee
     * @param dailyCalories the calories
     * @param activityLevel the activity level
     * @param userGoal the goal
     */
    void setDataUser(
        double bmr,
        double tdee,
        double dailyCalories,
        ActivityLevel activityLevel,
        UserGoal userGoal
    );

    /**
     * Given the runnable of the view and save it on the class.
     * 
     * @param navigationTask runnable saved in the class.
     */
    void setNavigationTask(Runnable navigationTask);

    /**
     * Save in the class the current view passed as parameter.
     * 
     * @param view passed to save it on the class.
     * 
     */
    void setView(PlanViewerImpl view);

    /**
     * Save the plan calling the LoadSaveData method to save the hole workout.
     */
    void saveCurrentPlan();

    /**
     * It create a new exercise based on the oldExercise with the new data inserted from the user.
     * 
     * @param date the date of the exercise.
     * @param oldEx the oldExercise need to take the data.
     * @param newEx the new exercise to replace the old with.
     * 
     */
    void replaceExercise(String date, PlannedExercise oldEx, PlannedExercise newEx);

    /**
     * Link to set and give to the userProfileController the data from the model exercise.
     * 
     * @param totKcal the total calories calculated.
     * 
     */
    void setProfile(double totKcal);

}
