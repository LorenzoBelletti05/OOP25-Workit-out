package it.unibo.workitout.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.MifflinStJeorStrategy;
import it.unibo.workitout.model.user.model.impl.Sex;
import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.model.user.model.impl.UserManager;
import it.unibo.workitout.model.user.model.impl.UserProfile;

public class UserManagerTest {
    private static final double BMR = 1717.5;
    private static final double MULTIPLIER = 1.55;
    private static final double TDEE = BMR * MULTIPLIER;

    private UserProfile userProfile = new UserProfile("Leone", "Verdi", 30, 170, 80, Sex.MALE, ActivityLevel.MODERATE, UserGoal.MAINTAIN_WEIGHT);
    private UserManager userManager = new UserManager(new MifflinStJeorStrategy(), userProfile);

    @Test
    void testCaloriesMaintaingWeight() {
        final double exp = TDEE;

        assertEquals(TDEE, userManager.getTDEEE());
        assertEquals(exp, userManager.getDailyCalories());
    }

    @Test
    void testCaloriesLoseWeight() {
        final double exp = TDEE - 500;
        userProfile.setGoal(UserGoal.LOSE_WEIGHT);

        assertEquals(TDEE, userManager.getTDEEE());
        assertEquals(exp, userManager.getDailyCalories());
    }

    @Test
    void testCaloriesGainWeight() {
        final double exp = TDEE + 500;
        userProfile.setGoal(UserGoal.GAIN_WEIGHT);

        assertEquals(TDEE, userManager.getTDEEE());
        assertEquals(exp, userManager.getDailyCalories());
    }

    @Test
    void testCaloriesBuildMuscle() {
        final double exp = TDEE + 250;
        userProfile.setGoal(UserGoal.BUILD_MUSCLE);

        assertEquals(TDEE, userManager.getTDEEE());
        assertEquals(exp, userManager.getDailyCalories());
    }
}
