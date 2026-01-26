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
    private final double caloriesBurned;
    private final Set<AttitudeExercise> exerciseMission;
    private final ExerciseType typeExercise;

    /**
     * Costructor for a new exercise.
     * 
     * @param name the name of the exercise
     * 
     * @param caloriesBurned the ammount of calories that the exerc. will let the user burn.
     * 
     * @param exerciseMission a set of {@link AttitudeExercise}, the value associeted to each exercise.
     */
    public Exercise(final String name, final double caloriesBurned, final Set<AttitudeExercise> exerciseMission, ExerciseType typeExercise) {
        this.name = name;
        this.caloriesBurned = caloriesBurned;
        this.exerciseMission = new HashSet<>(exerciseMission); //creating a copy of the set
        this.typeExercise = typeExercise;
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
    public double calorieBurned(final double minutes) {
        return this.caloriesBurned * minutes;
    }

    /**
     * Return the attitude.
     * 
     *  @return the attitude for the specific exercise.
     */
    public String getExerciseAttitude() {
        return exerciseMission.toString();
    }

    /**
     * Return the exercise type.
     * 
     * @return the exercise type {@link ExerciseType}
     */
    public ExerciseType getExerciseType() {
        return this.typeExercise;
    }

}
