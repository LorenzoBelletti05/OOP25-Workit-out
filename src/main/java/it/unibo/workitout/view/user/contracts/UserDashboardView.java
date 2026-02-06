package it.unibo.workitout.view.user.contracts;

import javax.swing.JButton;

import it.unibo.workitout.model.user.model.impl.UserManager;

public interface UserDashboardView {
    void showData(UserManager userManager);
    void setVisible(boolean status);
    JButton getProfileButton();
    JButton getFoodButton();
    JButton getInfoButton();
    JButton getExerciseButton();
}
