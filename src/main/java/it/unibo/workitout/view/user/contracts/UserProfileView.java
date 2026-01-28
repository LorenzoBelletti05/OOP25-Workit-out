package it.unibo.workitout.view.user.contracts;

import it.unibo.workitout.controller.user.contracts.UserProfileController;
import it.unibo.workitout.model.user.model.impl.ActivityLevel;
//import it.unibo.workitout.model.user.model.impl.BMRStrategyChoise;
import it.unibo.workitout.model.user.model.impl.Sex;
import it.unibo.workitout.model.user.model.impl.UserGoal;

public interface UserProfileView {
    public String getNameInput();
    public String getSurnameInput();
    public String getAgeInput();
    public String getHeightInput();
    public String getWeightInput();
    public Sex getSexInput();
    public ActivityLevel getActivityInput();
    public UserGoal UserGoalInput();
    //public BMRStrategyChoise getBMRStrategyInput();
    public void setController(UserProfileController controller);
    public void close();
}
