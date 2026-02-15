package it.unibo.workitout.model.workout.contracts;

/**
 * A general interface that contains common methods.
 */
interface WorkoutFunction {

    /**
     * @return the volume. It depends where is called.
     */
    double getVolume();

    /**
     * @return the burned calories. It depends where is called.
     */
    double getBurnedCalories();

    /**
     * @return the name of the exercise.
     */
    String getName();

}
