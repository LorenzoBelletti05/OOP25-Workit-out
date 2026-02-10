package it.unibo.workitout.model.workout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.EnumSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.model.workout.impl.Exercise;
import it.unibo.workitout.model.workout.impl.ExerciseType;

/**
 * Test class for Exercise implementation.
 */
class ExerciseTest {

    /**
     * Constant for calories per minute.
     */
    private static final double CALORIES_PER_MINUTE = 10.0;

    /**
     * Constant for testing duration.
     */
    private static final int MINUTES = 10;

    /**
     * Constant for expected burned calories.
     */
    private static final double EXPECTED_CALORIES = 100.0;
    private final Exercise exercise = new Exercise(
        "Affondi", 
        CALORIES_PER_MINUTE, 
        UserGoal.BUILD_MUSCLE.toString(), 
        ExerciseType.CARDIO
    );

    @Test
    void testGetName() {
        assertNotNull(exercise);
        assertEquals(exercise.getName(), "Affondi");
    }

    @Test
    void testcalorieBurned() {
        assertNotNull(exercise);
        assertEquals(exercise.calorieBurned(MINUTES), EXPECTED_CALORIES);
    }

    @Test
    void testGetExerciseAttitude() {
        assertNotNull(exercise);
        assertEquals(UserGoal.BUILD_MUSCLE.toString(), exercise.getExerciseAttitude());
    }

    @Test 
    void testGetExerciseType() {
        assertNotNull(exercise);
        assertEquals(exercise.getExerciseType(), ExerciseType.CARDIO);
    }

}
