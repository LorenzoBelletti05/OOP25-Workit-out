package it.unibo.workitout.view.user.test;

import it.unibo.workitout.controller.user.impl.UserProfileControllerImpl;
import it.unibo.workitout.view.user.impl.UserDashboardViewImpl;
import it.unibo.workitout.view.user.impl.UserProfileViewImpl;

public class TestUserView {
    public static void main(String[] args) {
        UserProfileViewImpl firstView = new UserProfileViewImpl();
        new UserProfileControllerImpl(firstView);

        new UserDashboardViewImpl();
    }
}
