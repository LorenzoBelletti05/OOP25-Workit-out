package it.unibo.workitout.model.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.Sex;
import it.unibo.workitout.model.user.model.impl.UserGoal;
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
    private static final int NEW_AGE = 31;
    private static final double NEW_HEIGHT = 175;
    private static final double NEW_WEIGHT = 75;
    private static final int NEGATIVE_AGE = -20;
    private static final double NEGATIVE_HEIGHT = -160;
    private static final double NEGATIVE_WEIGHT = -80;
    private static final int OVER_LIMIT_AGE = 120;
    private static final double OVER_LIMIT_HEIGHT = 240;
    private static final double OVER_LIMIT_WEIGHT = 320;

    private final UserProfile us = new UserProfile(
        NAME, 
        SURNAME,
        AGE,
        HEIGHT,
        WEIGHT,
        SEX,
        AL,
        UG,
        null
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
        assertEquals(UG, us.getUserGoal());
    }

    @Test
    void testUpdateUserProfile() {
        final UserProfile user = new UserProfile(NAME, SURNAME, AGE, HEIGHT, WEIGHT, SEX, AL, UG, null);
        user.setAge(NEW_AGE);
        user.setHeight(NEW_HEIGHT);
        user.setWeight(NEW_WEIGHT);

        assertEquals(NEW_AGE, user.getAge());
        assertEquals(NEW_HEIGHT, user.getHeight());
        assertEquals(NEW_WEIGHT, user.getWeight());

        assertThrows(IllegalArgumentException.class, () -> user.setAge(NEGATIVE_AGE));
        assertThrows(IllegalArgumentException.class, () -> user.setHeight(NEGATIVE_HEIGHT));
        assertThrows(IllegalArgumentException.class, () -> user.setWeight(NEGATIVE_WEIGHT));

        assertThrows(IllegalArgumentException.class, () -> user.setAge(OVER_LIMIT_AGE));
        assertThrows(IllegalArgumentException.class, () -> user.setHeight(OVER_LIMIT_HEIGHT));
        assertThrows(IllegalArgumentException.class, () -> user.setWeight(OVER_LIMIT_WEIGHT));
    }
}
