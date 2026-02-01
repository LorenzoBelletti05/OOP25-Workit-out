package it.unibo.workitout.model.workout.contracts;

import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.UserGoal;

public interface WorkoutCreator {

    /**
     * This method will use all tada taken from the workout controller, which take it from UserController.
     * 
     * <p>
     * How it works:
     * 
     * User Goal: choose some exercise rispect other.
     * Activity level: will be used to calcualte sets, reps, speed, weight, ecc...
     * Daily calories & TDEE: will be used to get the exercises on the target calories.
     */
    WorkoutPlan generatePlan(double bmr, double tdee, double dailyCalories, ActivityLevel activityLevel, UserGoal userGoal);   
    
}
