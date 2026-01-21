package it.unibo.workitout.model.workout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.EnumSet;
import org.junit.jupiter.api.Test;
import it.unibo.workitout.model.workout.contracts.CardioPlannedExercise;
import it.unibo.workitout.model.workout.contracts.StrengthPlannedExercise;
import it.unibo.workitout.model.workout.impl.AttitudeExercise;
import it.unibo.workitout.model.workout.impl.CardioPlannedExerciseImpl;
import it.unibo.workitout.model.workout.impl.Exercise;
import it.unibo.workitout.model.workout.impl.StrengthPlannedExerciseImpl;

class PlannedExerciseTest {

    //Exercise general data
    final double caloriesPerMinute = 10.0;
    final Integer minutes = 20;
    final String nameExe = "Affondi";

    //Cardio data
    final double distance = 30;

    //Strenght data
    final Integer sets = 5;
    final Integer reps = 7;
    final double weight = 20;

    final Exercise exercise = new Exercise(nameExe, caloriesPerMinute, EnumSet.of(AttitudeExercise.MUSCLE_GAIN));
    private CardioPlannedExercise cardioPlannedExercise = new CardioPlannedExerciseImpl(exercise, minutes, distance);
    private StrengthPlannedExercise strenghtPlannedExercise = new StrengthPlannedExerciseImpl(exercise, minutes, sets, reps, weight);

    @Test
    void testGetExercise() {
        assertEquals(exercise, cardioPlannedExercise.getExercise());
        assertEquals(exercise, strenghtPlannedExercise.getExercise());
    }

    @Test
    void testGetMinutes() {
        assertEquals(cardioPlannedExercise.getMinutes(), minutes);
        assertEquals(strenghtPlannedExercise.getMinutes(), minutes);
    }

    @Test
    void testGetBurnedCalories() {
        double totalCalories = minutes * caloriesPerMinute;
        assertEquals(cardioPlannedExercise.getBurnedCalories(), totalCalories);
        assertEquals(strenghtPlannedExercise.getBurnedCalories(), totalCalories);
    }

    @Test
    void testGetName() {
        assertEquals(cardioPlannedExercise.getName(), nameExe);
        assertEquals(strenghtPlannedExercise.getName(), nameExe);
        assertEquals(exercise.getName(), nameExe);
    }

    @Test
    void testGetVolume() {
        double volume = sets * reps * weight;
        assertEquals(cardioPlannedExercise.getVolume(), distance);
        assertEquals(strenghtPlannedExercise.getVolume(), volume);
    }

    //Cardio part
    @Test
    void testGetDistance() {
        assertEquals(cardioPlannedExercise.getDistance(), distance);
    }

    //Strenght part
    @Test
    void testGetWeight() {
        assertEquals(strenghtPlannedExercise.getWeight(), weight);
    }

    @Test
    void testgetSets() {
        assertEquals(strenghtPlannedExercise.getSets(), sets);
    }

    @Test
    void testGetReps() {
        assertEquals(strenghtPlannedExercise.getReps(), reps);
    }

}
