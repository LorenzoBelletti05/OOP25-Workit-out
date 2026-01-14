package it.unibo.workitout.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.MifflinStJeorStrategy;
import it.unibo.workitout.model.user.model.impl.Sex;
import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.model.user.model.impl.UserManager;
import it.unibo.workitout.model.user.model.impl.UserProfile;

/**
 * Test for the class UserProfile.
 */
class UserProfileTest {

    private static final String NAME = "Mario";
    private static final String SURNAME = "Rossi";
    private static final int AGE = 30;
    private static final double HEIGHT = 170;
    private static final double WEIGHT = 80;
    private static final Sex SEX = Sex.MALE;
    private static final ActivityLevel AL = ActivityLevel.HIGH;
    private static final UserGoal UG = UserGoal.MAINTAIN_WEIGHT;

    private final UserProfile us = new UserProfile(
        NAME, 
        SURNAME,
        AGE,
        HEIGHT,
        WEIGHT,
        SEX,
        AL,
        UG
    );

    @Test
    void testUserProfile() {
        assertEquals(NAME, us.getName());
        assertEquals(SURNAME, us.getSurname());
        assertEquals(AGE, us.getAge());
        assertEquals(HEIGHT, us.getHeight());
        assertEquals(WEIGHT, us.getWeight());
        assertEquals(SEX, us.getSex());
        assertEquals(AL, us.getActivityLevel());
        assertEquals(UG, us.getGoal());
    }

    @Test
    void testUpdateUserProfile() {
        UserProfile user = new UserProfile(NAME, SURNAME, AGE, HEIGHT, WEIGHT, SEX, AL, UG);
    }
}
