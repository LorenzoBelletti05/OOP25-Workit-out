package it.unibo.workitout.model.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.MifflinStJeorStrategy;
import it.unibo.workitout.model.user.model.impl.NutritionalTarget;
import it.unibo.workitout.model.user.model.impl.Sex;
import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.model.user.model.impl.UserManager;
import it.unibo.workitout.model.user.model.impl.UserProfile;

/**
 * Test for the class UserManager.
 */
class UserManagerTest {
    private static final double BMR = 1717.5;
    private static final double MULTIPLIER = 1.55;
    private static final double TDEE = BMR * MULTIPLIER;
    private static final double FULL_CALORIES = 500;
    private static final double HALF_CALORIES = FULL_CALORIES / 2;
    private static final double CARBO_PROTEIN_TO_CAL = 4;
    private static final double FAT_TO_CAL = 9;
    private static final int AGE = 30;
    private static final double HEIGHT = 170;
    private static final double WEIGHT = 80;

    private final UserProfile userProfile = new UserProfile("Leone", "Verdi",
    AGE, HEIGHT, 
    WEIGHT, Sex.MALE, 
    ActivityLevel.MODERATE, 
    UserGoal.MAINTAIN_WEIGHT
);
    private final UserManager userManager = new UserManager(new MifflinStJeorStrategy(), userProfile);

    @Test
    void testCaloriesMaintaingWeight() {
        final double exp = TDEE;

        assertEquals(TDEE, userManager.getTDEE());
        assertEquals(exp, userManager.getDailyCalories());
    }

    @Test
    void testCaloriesLoseWeight() {
        final double exp = TDEE - FULL_CALORIES;
        userProfile.setGoal(UserGoal.LOSE_WEIGHT);

        assertEquals(TDEE, userManager.getTDEE());
        assertEquals(exp, userManager.getDailyCalories());
    }

    @Test
    void testCaloriesGainWeight() {
        final double exp = TDEE + FULL_CALORIES;
        userProfile.setGoal(UserGoal.GAIN_WEIGHT);

        assertEquals(TDEE, userManager.getTDEE());
        assertEquals(exp, userManager.getDailyCalories());
    }

    @Test
    void testCaloriesBuildMuscle() {
        final double exp = TDEE + HALF_CALORIES;
        userProfile.setGoal(UserGoal.BUILD_MUSCLE);

        assertEquals(TDEE, userManager.getTDEE());
        assertEquals(exp, userManager.getDailyCalories());
    }

    @Test
    void testMacronutrientsCalculation() {
        final NutritionalTarget resultManager = userManager.getMacronutrients();
        final double expectedCalories = 2662.125;
        final double carboRatio = UserGoal.MAINTAIN_WEIGHT.getCarbRatio();
        final double proteinRatio = UserGoal.MAINTAIN_WEIGHT.getProteinRatio();
        final double fatRatio = UserGoal.MAINTAIN_WEIGHT.getFatRatio();
        final double exprectedCarbG = (expectedCalories * carboRatio) / CARBO_PROTEIN_TO_CAL;
        final double exprectedProteinG = (expectedCalories * proteinRatio) / CARBO_PROTEIN_TO_CAL;
        final double exprectedFatG = (expectedCalories * fatRatio) / FAT_TO_CAL;

        assertEquals(exprectedCarbG, resultManager.getCarbsG());
        assertEquals(exprectedProteinG, resultManager.getProteinsG());
        assertEquals(exprectedFatG, resultManager.getFatsG());
    }
}
