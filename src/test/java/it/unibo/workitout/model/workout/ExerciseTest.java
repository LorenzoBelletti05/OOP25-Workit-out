package it.unibo.workitout.model.workout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.EnumSet;
import org.junit.jupiter.api.Test;
import it.unibo.workitout.model.workout.impl.AttitudeExercise;
import it.unibo.workitout.model.workout.impl.Exercise;

public class ExerciseTest {

    /**
     * Creating field for testing
     */
    final double caloriesPerMinute = 10.0;
    private Exercise exercise = new Exercise("Affondi", caloriesPerMinute, EnumSet.of(AttitudeExercise.MUSCLE_GAIN));

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
        assertEquals(exercise.getExerciseAttitude(), EnumSet.of(AttitudeExercise.MUSCLE_GAIN).toString());
    }

}
