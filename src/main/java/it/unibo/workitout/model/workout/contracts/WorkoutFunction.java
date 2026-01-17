package it.unibo.workitout.model.workout.contracts;

/**
 * A general interface that contains common method.
 */
interface WorkoutFunction {

    /**
     * Method that return the volume required.
     * 
     * @return the volume. It depends where is called.
     */
    public double getVolume();

    /**
     * Method that returned the burned calories required based on where is called.
     * 
     * @return the burned calories. It depends where is called.
     */
    public double getBurnedCalories();
    
}