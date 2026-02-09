package it.unibo.workitout.model.workout;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.model.workout.contracts.WorkoutCreator;
import it.unibo.workitout.model.workout.contracts.WorkoutPlan;
import it.unibo.workitout.model.workout.impl.WorkoutCreatorImpl;

class WorkoutCreatorTest {

    private static final double BMR = 1500.0;
    private static final double TDEE = 2200.0;
    private static final double DAILY_CALORIES = 2000.0;
    private static final double SAFETY_CALORIES = 1000.0;
    private static final int MAX_MODERATE_SHEETS = 4;
    private static final int MAX_HIGH_SHEETS = 6;

    private WorkoutCreator creator;

    @BeforeEach
    void basicImplementationSetUp() {
        try {
            creator = new WorkoutCreatorImpl();
        } catch (final IOException e) {
            fail("EXE non caricati correttamente: " + e.getMessage());
        }
    }

    @Test
    void testGeneratePlanBuildMuscle() {
        final WorkoutPlan plan = creator.generatePlan(
            BMR, 
            TDEE, 
            DAILY_CALORIES, 
            ActivityLevel.MODERATE, 
            UserGoal.BUILD_MUSCLE
        );

        assertNotNull(plan);
        assertFalse(plan.getSheets().isEmpty());
        assertTrue(plan.getSheets().size() <= MAX_MODERATE_SHEETS);
        assertFalse(plan.getAllExercise().isEmpty());
    }

    @Test
    void testGeneratePlanLoseWeight() {
        final WorkoutPlan plan = creator.generatePlan(
            BMR, 
            TDEE, 
            DAILY_CALORIES, 
            ActivityLevel.HIGH, 
            UserGoal.LOSE_WEIGHT
        );

        assertNotNull(plan);
        assertTrue(plan.getSheets().size() <= MAX_HIGH_SHEETS);
    }

    @Test
    void testSafetyCheck() {
        final WorkoutPlan plan = creator.generatePlan(
            BMR, 
            TDEE, 
            SAFETY_CALORIES, 
            ActivityLevel.LOW, 
            UserGoal.MAINTAIN_WEIGHT
        );
                assertNotNull(plan);
        assertFalse(plan.getSheets().isEmpty());
    }

    @Test
    void testActivityLevelVeryLow() {
        final WorkoutPlan plan = creator.generatePlan(
            BMR, 
            TDEE, 
            DAILY_CALORIES, 
            ActivityLevel.VERY_LOW, 
            UserGoal.BUILD_MUSCLE
        );

        assertNotNull(plan);
        assertTrue(plan.getSheets().isEmpty());
    }

}
