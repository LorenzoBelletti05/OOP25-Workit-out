package it.unibo.workitout.model.workout.impl;

import java.util.HashSet;
import java.util.Set;

/**
 * Represent a raw single exercise entity.
 * 
 * <p>
 * Is a class used as template for the exercise, defining
 * the calorie burnedrate.
 * </p>
 */
public class Exercise {

    private final String name;
    private final double calorieBurned;
    private final Set<AttitudeExercise> exerciseMission;

    /**
     * Costructor for a new exercise.
     * 
     * @param name the name of the exercise
     * 
     * @param calorieBurned the ammount of calories that the exerc. will let the user burn.
     * 
     * @param exerciseMission a set of {@link AttitudeExercise}, the value associeted to each exercise.
     */
    public Exercise(final String name, final double calorieBurned, final Set<AttitudeExercise> exerciseMission) {
        this.name = name;
        this.calorieBurned = calorieBurned;
        this.exerciseMission = new HashSet<>(exerciseMission); //creating a copy of the set
    }

    /**
     * Return the name of the exercise.
     * 
     * @return the exercise name.
     */
    public String getName() {
        return name;
    }

    /**
     * Calculate the estimated energy usage based on duration.
     * 
     * @param minutes the duration of the exercise in minutes.
     * 
     * @return the calories burned as double value.
     */
    public double calorieBurned(final int minutes) {
        return this.calorieBurned * minutes;
    }

    /**
     * Return the attitude.
     * 
     *  @return the attitude for the specific exercise.
     */
    public String getExerciseAttitude() {
        return exerciseMission.toString();
    }
}
