package it.unibo.workitout.model.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.HarrisBenedictStrategy;
import it.unibo.workitout.model.user.model.impl.MifflinStJeorStrategy;
import it.unibo.workitout.model.user.model.impl.Sex;
import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.model.user.model.impl.UserProfile;

/**
 * Test for the two strategy for calculate the BMR.
 */
class BMRStrategyTest {

    private static final double DELTA = 0.001;
    private static final int MALE_AGE = 30;
    private static final double MALE_HEIGHT = 170;
    private static final double MALE_WEIGHT = 80;
    private static final int FEMALE_AGE = 25;
    private static final double FEMALE_HEIGHT = 165;
    private static final double FEMALE_WEIGHT = 60;
    private static final String SURNAME = "Bianchi";
    private static final String MALE_NAME = "Luca";
    private static final String FEMALE_NAME = "Lucia";

    @Test
    void testHarrisBenedictMale() {
        final UserProfile maleUser = new UserProfile(
            MALE_NAME, SURNAME, 
            MALE_AGE, MALE_HEIGHT, 
            MALE_WEIGHT, Sex.MALE, 
            ActivityLevel.MODERATE, UserGoal.MAINTAIN_WEIGHT
        );
        final HarrisBenedictStrategy strategy = new HarrisBenedictStrategy();
        final double expectedBMR = 1805.642;

        assertEquals(expectedBMR, strategy.calculateBMR(maleUser), DELTA);
    }

    @Test
    void testHarrisBenedictFemale() {
        final UserProfile femaleUser = new UserProfile(FEMALE_NAME, SURNAME,
            FEMALE_AGE, FEMALE_HEIGHT, 
            FEMALE_WEIGHT, Sex.FEMALE, 
            ActivityLevel.MODERATE, UserGoal.MAINTAIN_WEIGHT
        );
        final HarrisBenedictStrategy strategy = new HarrisBenedictStrategy();
        final double expectedBMR = 1405.333;

        assertEquals(expectedBMR, strategy.calculateBMR(femaleUser));
    }

    @Test
    void testMifflinMale() {
        final UserProfile maleUser = new UserProfile(
            MALE_NAME, SURNAME, 
            MALE_AGE, MALE_HEIGHT, 
            MALE_WEIGHT, Sex.MALE,
            ActivityLevel.MODERATE, UserGoal.MAINTAIN_WEIGHT
        );
        final MifflinStJeorStrategy strategy = new MifflinStJeorStrategy();
        final double expectedBMR = 1717.5;
 
        assertEquals(expectedBMR, strategy.calculateBMR(maleUser));
    }

    @Test
    void testMifflinfemale() {
        final UserProfile femaleUser = new UserProfile(
            FEMALE_NAME, SURNAME, 
            FEMALE_AGE, FEMALE_HEIGHT, 
            FEMALE_WEIGHT, Sex.FEMALE,
            ActivityLevel.MODERATE, UserGoal.MAINTAIN_WEIGHT
        );
        final MifflinStJeorStrategy strategy = new MifflinStJeorStrategy();
        final double expectedBMR = 1345.25;

        assertEquals(expectedBMR, strategy.calculateBMR(femaleUser));
    }
}
