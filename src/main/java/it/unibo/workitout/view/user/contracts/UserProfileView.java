package it.unibo.workitout.view.user.contracts;

import javax.swing.JButton;

import it.unibo.workitout.controller.user.contracts.UserProfileController;
import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.BMRStrategyChoice;
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
    public BMRStrategyChoice getBMRStrategyInput();
    public void setController(UserProfileController controller);
    public void close();
    public void setVisible(boolean visible);
    public JButton getBackButton();
    public void setBackButton(boolean visible);
    public void setNameInput(String name);
    public void setSurnameInput(String surname);
    public void setAgeInput(int age);
    public void setHeightInput(double height);
    public void setWeightInput(double weight);
    public void setSexInput(Sex sex);
    public void setActivityInput(ActivityLevel activityLevel);
    public void setUserGoalInput(UserGoal userGoal);
    public void setBMRStrategyInput(String strategy);
}
