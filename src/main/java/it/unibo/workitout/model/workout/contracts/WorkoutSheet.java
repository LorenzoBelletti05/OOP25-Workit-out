package it.unibo.workitout.model.workout.contracts;

import java.util.Optional;
import java.util.Set;

/**
 * WorkoutSheets method definition.
 */
public interface WorkoutSheet extends WorkoutFunction {

    /**
     * Return the specified exercise.
     * 
     * @param nameExercise the name of the exercise required.
     * 
     * @return an optional of plannedExercise which contains or the exercise or null.
     */
    Optional<PlannedExercise> getExercise(String nameExercise);

    /**
     * Return the success or not, for the operation of adding a PlannedExercise.
     * 
     * @param exercise the planned exercise, could be strenght or cardio.
     *
     * @return the operation of adding result in a boolean state.
     */
    Boolean addExercise(PlannedExercise exercise);

    /**
     * Return the success or not, for the operation of remouving a PlannedExercise.
     * 
     * @param exercise the planned exercise, could be strenght or cardio.
     *
     * @return the operation of remouving result in a boolean state.
     */
    Boolean remouveExercise(PlannedExercise exercise);

    /**
     * Public getter that return the class unmoodifiable structure data.
     * 
     * @return the unmodifiable set of planned exercise.
     */
    Set<PlannedExercise> getWorkoutSheet();

}
