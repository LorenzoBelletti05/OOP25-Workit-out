package it.unibo.workitout.model.workout.impl;

import com.google.gson.annotations.SerializedName;
import it.unibo.workitout.model.user.model.impl.UserGoal;

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

    @SerializedName("goals")
    private final String exerciseMission;

    @SerializedName("type")
    private final ExerciseType typeExercise;

    /**
     * Costructor for a new exercise.
     * 
     * @param name the name of the exercise
     * 
     * @param caloriesBurned the ammount of calories that the exerc. will let the user burn.
     * 
     * @param exerciseMission a set of {@link UserGoal}, the value associeted to each exercise.
     * 
     * @param typeExercise the typology Strenght or Cardio.
     * 
     */
    public Exercise(final String name, final double caloriesBurned, final String exerciseMission, final ExerciseType typeExercise) {
        this.name = name;
        this.caloriesBurned = caloriesBurned;
        this.exerciseMission = exerciseMission; //creating a copy of the set
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
        return this.exerciseMission;
    }

    /**
     * Return the exercise type.
     * 
     * @return the exercise type {@link UserGoal}
     */
    public ExerciseType getExerciseType() {
        return this.typeExercise;
    }

}
