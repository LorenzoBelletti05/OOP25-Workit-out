package it.unibo.workitout.model.workout.contracts;

import java.util.Optional;
import java.util.Set;

/**
 * WorkoutSheets method definition.
 */
public interface WorkoutSheet extends WorkoutFunction {

    /**
     * Return the specified exercise
     * 
     * @return
     */
    public Optional<PlannedExercise> getExercise(final String nameExercise);

    /**
     * Return the success or not, for the operation of adding a PlannedExercise.
     * 
     * @return the operation of adding result in a boolean state.
     */
    public Boolean addExercise(final PlannedExercise exercise);

    /**
     * Return the success or not, for the operation of remouving a PlannedExercise.
     * 
     * @return the operation of remouving result in a boolean state.
     */
    public Boolean remouveExercise(final PlannedExercise exercise);

    /**
     * Public getter that return the class unmoodifiable structure data.
     * 
     * @return the unmodifiable set of planned exercise.
     */
    public Set<PlannedExercise> getWorkoutSheet();

}
