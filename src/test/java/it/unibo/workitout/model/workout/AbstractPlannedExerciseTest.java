package it.unibo.workitout.model.workout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.model.workout.contracts.CardioPlannedExercise;
import it.unibo.workitout.model.workout.contracts.PlannedExercise;
import it.unibo.workitout.model.workout.contracts.StrengthPlannedExercise;
import it.unibo.workitout.model.workout.impl.CardioPlannedExerciseImpl;
import it.unibo.workitout.model.workout.impl.Exercise;
import it.unibo.workitout.model.workout.impl.ExerciseType;
import it.unibo.workitout.model.workout.impl.StrengthPlannedExerciseImpl;

abstract class AbstractPlannedExerciseTest {

    //Exercise general data
    static final double CALORIES_PER_MINUTE = 10.0;
    static final Integer MINUTES = 20;
    static final String NAME_EXE = "Affondi";

    //Cardio data
    static final double DISTANCE = 30;

    //Strenght data
    static final Integer SETS = 5;
    static final Integer REPS = 7;
    static final double WEIGHT = 20;

    private final Exercise exercise = new Exercise(
        NAME_EXE, 
        CALORIES_PER_MINUTE, 
        UserGoal.BUILD_MUSCLE.toString(), 
        ExerciseType.CARDIO
    );
    private final Exercise exercise2 = new Exercise(
        NAME_EXE, 
        CALORIES_PER_MINUTE, 
        UserGoal.BUILD_MUSCLE.toString(), 
        ExerciseType.STRENGTH
    );
    private final CardioPlannedExercise cardioPlannedExercise = new CardioPlannedExerciseImpl(
        exercise, 
        MINUTES, 
        DISTANCE
    );
    private final StrengthPlannedExercise strenghtPlannedExercise = new StrengthPlannedExerciseImpl(
        exercise2, 
        MINUTES, 
        SETS, 
        REPS, 
        WEIGHT
    );

    //Abstract method.
    protected abstract PlannedExercise getExerciseImplementation();

    @Test
    void testGetExercise() {
        assertEquals(exercise, cardioPlannedExercise.getExercise());
        assertEquals(exercise2, strenghtPlannedExercise.getExercise());
    }

    @Test
    void testGetMinutes() {
        assertEquals(cardioPlannedExercise.getMinutes(), MINUTES);
        assertEquals(strenghtPlannedExercise.getMinutes(), MINUTES);
    }

    @Test
    void testGetBurnedCalories() {
        final double totalCalories = MINUTES * CALORIES_PER_MINUTE;
        assertEquals(cardioPlannedExercise.getBurnedCalories(), totalCalories);
        assertEquals(strenghtPlannedExercise.getBurnedCalories(), totalCalories);
    }

    @Test
    void testGetName() {
        assertEquals(cardioPlannedExercise.getName(), NAME_EXE);
        assertEquals(strenghtPlannedExercise.getName(), NAME_EXE);
        assertEquals(exercise.getName(), NAME_EXE);
    }

    @Test
    void testGetVolume() {
        final double volume = SETS * REPS * WEIGHT;
        assertEquals(cardioPlannedExercise.getVolume(), DISTANCE);
        assertEquals(strenghtPlannedExercise.getVolume(), volume);
    }

    //Cardio part
    @Test
    void testGetDistance() {
        assertEquals(cardioPlannedExercise.getDistance(), DISTANCE);
    }

    //Strenght part
    @Test
    void testGetWeight() {
        assertEquals(strenghtPlannedExercise.getWeight(), WEIGHT);
    }

    @Test
    void testgetSets() {
        assertEquals(strenghtPlannedExercise.getSets(), SETS);
    }

    @Test
    void testGetReps() {
        assertEquals(strenghtPlannedExercise.getReps(), REPS);
    }

    @Test
    void testGetExerciseType() {
        assertEquals(cardioPlannedExercise.getExercise().getExerciseType(), ExerciseType.CARDIO);
        assertEquals(strenghtPlannedExercise.getExercise().getExerciseType(), ExerciseType.STRENGTH);
    }

}
