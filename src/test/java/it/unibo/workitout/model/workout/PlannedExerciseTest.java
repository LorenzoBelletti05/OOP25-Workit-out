package it.unibo.workitout.model.workout;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.EnumSet;

import org.junit.jupiter.api.Test;
import it.unibo.workitout.model.workout.contracts.PlannedExercise;
import it.unibo.workitout.model.workout.impl.AttitudeExercise;
import it.unibo.workitout.model.workout.impl.Exercise;
import it.unibo.workitout.model.workout.impl.PlannedExerciseImpl;

class PlannedExerciseTest {

    @Test
    void testPlannedExerciseClass() {

        int sets = 3;
        int reps = 12;
        double weight = 10.0;
        
        double caloriesPerMinute = 10.0;    //calories per minutes, the rate
        Integer minutes = 14;               //time for the execution of the all exercise

        final Exercise exercise = new Exercise("Affondi", caloriesPerMinute, EnumSet.of(AttitudeExercise.MUSCLE_GAIN));
        final PlannedExercise planExe = new PlannedExerciseImpl(exercise, minutes, sets, reps, weight);

        double expectedCalories = caloriesPerMinute * minutes;
        double totalVolume =  sets * reps * weight;

        assertNotNull(planExe);
        assertEquals(planExe.getExercise(), exercise);
        assertEquals(planExe.getCalculatedBurnedCalories(), expectedCalories);
        assertEquals(planExe.getSets(), sets);
        assertEquals(planExe.getReps(), reps);
        assertEquals(planExe.getWeight(), weight);
        assertEquals(planExe.getVolumeExercise(), totalVolume);
    }    
}
