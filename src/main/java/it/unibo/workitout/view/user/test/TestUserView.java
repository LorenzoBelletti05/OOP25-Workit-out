package it.unibo.workitout.view.user.test;

import it.unibo.workitout.controller.user.impl.UserProfileControllerImpl;
import it.unibo.workitout.view.user.impl.UserProfileViewImpl;

public class TestUserView {
    public static void main(String[] args) {
        UserProfileViewImpl view = new UserProfileViewImpl();
        new UserProfileControllerImpl(view);
    }
}
