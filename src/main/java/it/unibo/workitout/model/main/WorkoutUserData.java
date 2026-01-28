package it.unibo.workitout.model.main;

import java.time.LocalDate;

import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.UserGoal;

public class WorkoutUserData {

    private final double bmr;
    private final double tdee;
    private final double dailyCalories;    
    private final ActivityLevel activityLevel;
    private final UserGoal userGoal;
    private final String localDate;

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

    public double getBmr() {
        return this.bmr;
    }

    public double getTdee() {
        return this.tdee;
    }

    public double getdailyCalories() {
        return this.dailyCalories;
    }    

    public ActivityLevel getActivityLevel() {
        return this.activityLevel;
    }

    public UserGoal getUserGoal() {
        return this.userGoal;
    }

    public String getLocalDate() {
        return this.localDate;
    }
    
}
