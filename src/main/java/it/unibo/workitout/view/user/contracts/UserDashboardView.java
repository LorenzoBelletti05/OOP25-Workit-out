package it.unibo.workitout.view.user.contracts;

import javax.swing.JButton;

import it.unibo.workitout.model.user.model.impl.UserManager;

public interface UserDashboardView {
    public void showData(UserManager userManager);
    public void setVisible(boolean status);
    public JButton getProfileButton();
    public JButton getFoodButton();
    public JButton getInfoButton();
    public JButton getExerciseButton();
}
