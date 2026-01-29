package it.unibo.workitout.model.workout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.EnumSet;
import org.junit.jupiter.api.Test;

import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.model.workout.impl.Exercise;
import it.unibo.workitout.model.workout.impl.ExerciseType;

public class ExerciseTest {

    /**
     * Creating field for testing
     */
    final static double caloriesPerMinute = 10.0;
    private Exercise exercise = new Exercise("Affondi", caloriesPerMinute, EnumSet.of(UserGoal.BUILD_MUSCLE), ExerciseType.CARDIO);

    @Test
    void testGetName() {
        assertNotNull(exercise);
        assertEquals(exercise.getName(), "Affondi");
    }

    @Test
    void testcalorieBurned() {
        assertNotNull(exercise);
        assertEquals(exercise.calorieBurned(10), 100);
    }

    @Test
    void getExerciseAttitude() {
        assertNotNull(exercise);
        assertEquals(exercise.getExerciseAttitude(), EnumSet.of(UserGoal.BUILD_MUSCLE).toString());
    }

    @Test 
    void getExerciseTypeTest(){
        assertNotNull(exercise);
        assertEquals(exercise.getExerciseType(), ExerciseType.CARDIO);
    }

}
