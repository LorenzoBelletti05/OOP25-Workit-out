package it.unibo.workitout.model.main;

import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.UserGoal;

/**
 * General class used to save the user data from the workout model.
 */
public class WorkoutUserData {

    private final double bmr;
    private final double tdee;
    private final double dailyCalories;
    private final ActivityLevel activityLevel;
    private final UserGoal userGoal;
    private final String localDate;

    /**
     * Costructor that gived all data save it in the field.
     * 
     * @param bmr data.
     * @param tdee data.
     * @param dailyCalories data.
     * @param activityLevel data.
     * @param userGoal data.
     * @param localDate data.
     * 
     */
    public WorkoutUserData(
        final double bmr, 
        final double tdee, 
        final double dailyCalories, 
        final ActivityLevel activityLevel, 
        final UserGoal userGoal,
        final String localDate
    ) {
        this.bmr = bmr;
        this.tdee = tdee;
        this.dailyCalories = dailyCalories;
        this.activityLevel = activityLevel;
        this.userGoal = userGoal;
        this.localDate = localDate;
    }

    /**
     * Getter method to return bmr.
     * 
     * @return the bmr.
     * 
     */
    public double getBMR() {
        return this.bmr;
    }

    /**
     * Getter method to return tdee.
     * 
     * @return tdee.
     * 
     */
    public double getTDEE() {
        return this.tdee;
    }

    /**
     * Getter method to return daily calories.
     * 
     * @return daily calories.
     * 
     */
    public double getDailyCalories() {
        return this.dailyCalories;
    }

    /**
     * Getter method to return activity level.
     * 
     * @return activity level.
     * 
     */
    public ActivityLevel getActivityLevel() {
        return this.activityLevel;
    }

    /**
     * Getter method to return user Goal.
     * 
     * @return user goal.
     * 
     */
    public UserGoal getUserGoal() {
        return this.userGoal;
    }

    /**
     * Getter method to return local date.
     * 
     * @return local date.
     * 
     */
    public String getLocalDate() {
        return this.localDate;
    }

}
