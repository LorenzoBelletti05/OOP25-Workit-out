package it.unibo.workitout.model.workout.contracts;

/**
 * A general interface that contains common method.
 */
interface WorkoutFunction {

    /**
     * Method that return the volume required (in weight and in distance).
     *
     * @return the volume. It depends where is called.
     */
    double getVolume();

    /**
     * Method that returned the burned calories required.
     * 
     * @return the burned calories. It depends where is called.
     */
    double getBurnedCalories();

    /**
     * Return the name of the planned exercise.
     *
     * @return the name of the exercise.
     */
    String getName();

}
