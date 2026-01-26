package it.unibo.workitout.view.user.contracts;

import it.unibo.workitout.model.user.model.impl.UserManager;

public interface UserDashboardView {
    void showData(UserManager userManager);
    void setVisible(boolean status);
}
