package it.unibo.workitout.controller.workout.contracts;

import it.unibo.workitout.model.workout.contracts.WorkoutPlan;

/**
 * Interface that contains the definition of the methot to manage all the data from and for the view, from the model.
 */
public interface UserExerciseController {

    WorkoutPlan getGeneratedWorkoutPlan();    

}
