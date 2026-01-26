package it.unibo.workitout.controller.workout.contracts;

public interface UserExerciseController {

    /**
     * Check if the week of the workout plan is the same.
     * If is a new week it call the logic that create a new plan based on the obbiettive.
     * If the user goal or other data are been changed it call the logic to create a new plan 
     * or change the values for the current one.
     */
    void checkWeek();
    
}
