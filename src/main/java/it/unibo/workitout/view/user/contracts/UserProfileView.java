package it.unibo.workitout.view.user.contracts;

import javax.swing.JButton;

import it.unibo.workitout.controller.user.contracts.UserProfileController;
import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.BMRStrategyChoice;
import it.unibo.workitout.model.user.model.impl.Sex;
import it.unibo.workitout.model.user.model.impl.UserGoal;

public interface UserProfileView {
    String getNameInput();
    String getSurnameInput();
    String getAgeInput();
    String getHeightInput();
    String getWeightInput();
    Sex getSexInput();
    ActivityLevel getActivityInput();
    UserGoal UserGoalInput();
    BMRStrategyChoice getBMRStrategyInput();
    void setController(UserProfileController controller);
    void close();
    void setVisible(boolean visible);
    JButton getBackButton();
    void setBackButton(boolean visible);
    void setNameInput(String name);
    void setSurnameInput(String surname);
    void setAgeInput(int age);
    void setHeightInput(double height);
    void setWeightInput(double weight);
    void setSexInput(Sex sex);
    void setActivityInput(ActivityLevel activityLevel);
    void setUserGoalInput(UserGoal userGoal);
    void setBMRStrategyInput(String strategy);
}
