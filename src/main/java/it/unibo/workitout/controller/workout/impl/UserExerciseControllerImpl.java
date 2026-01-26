package it.unibo.workitout.controller.workout.impl;

import it.unibo.workitout.controller.user.impl.UserProfileControllerImpl;
import it.unibo.workitout.controller.workout.contracts.UserExerciseController;
import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.NutritionalTarget;
import it.unibo.workitout.model.user.model.impl.UserGoal;;

public class UserExerciseControllerImpl implements UserExerciseController {

    private final double bmr;
    private final double tdee;
    private final double dailyCalories;    
    private final ActivityLevel activityLevel;
    private final UserGoal userGoal;
   

    public UserExerciseControllerImpl(
        final double bmr, 
        final double tdee, 
        final double dailyCalories, 
        final ActivityLevel activityLevel, 
        final UserGoal userGoal
    ) {
        this.bmr = bmr;
        this.tdee = tdee;
        this.dailyCalories = dailyCalories;
        this.activityLevel = activityLevel;
        this.userGoal = userGoal;
    }

    @Override
    public void checkWeek() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkWeek'");
    }

    



    
}
