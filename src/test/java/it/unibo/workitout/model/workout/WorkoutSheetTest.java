package it.unibo.workitout.model.workout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.model.workout.contracts.PlannedExercise;
import it.unibo.workitout.model.workout.contracts.WorkoutSheet;
import it.unibo.workitout.model.workout.impl.Exercise;
import it.unibo.workitout.model.workout.impl.ExerciseType;
import it.unibo.workitout.model.workout.impl.StrengthPlannedExerciseImpl;
import it.unibo.workitout.model.workout.impl.WorkoutSheetImpl;

/**
 * Test class for the sheet.
 */
class WorkoutSheetTest {

    private static final double CALORIES_PER_MINUTE = 10.0;
    private static final String SHEET_NAME = "Scheda pre-Natale";
    private static final String EXE_NAME_1 = "Affondi";
    private static final String EXE_NAME_2 = "Push-up";

    //Const
    private static final int MINUTES_1 = 10;
    private static final int MINUTES_2 = 11;
    private static final int SETS_1 = 3;
    private static final int REPS_1 = 12;
    private static final double WEIGHT_1 = 10.0;
    private static final int SETS_2 = 4;
    private static final int REPS_2 = 13;
    private static final double WEIGHT_2 = 11.0;
    private static final int EXPECTED_SIZE = 2;

    private final Exercise exercise = new Exercise(
        EXE_NAME_1, 
        CALORIES_PER_MINUTE, 
        UserGoal.BUILD_MUSCLE.toString(), 
        ExerciseType.STRENGTH
    );

    private final Exercise exercise2 = new Exercise(
        EXE_NAME_2, 
        CALORIES_PER_MINUTE, 
        UserGoal.MAINTAIN_WEIGHT.toString() + ", " + UserGoal.LOSE_WEIGHT.toString(), 
        ExerciseType.STRENGTH
    );

    private final PlannedExercise planExe = new StrengthPlannedExerciseImpl(
        exercise, 
        MINUTES_1, 
        SETS_1, 
        REPS_1, 
        WEIGHT_1
    );

    private final PlannedExercise planExe2 = new StrengthPlannedExerciseImpl(
        exercise2, 
        MINUTES_2, 
        SETS_2, 
        REPS_2, 
        WEIGHT_2
    );

    private WorkoutSheet sheet;

    /**
     * Method to set up data.
     */
    @BeforeEach
    void basicImplementationSetUp() {
        sheet = new WorkoutSheetImpl(SHEET_NAME);

        sheet.addExercise(planExe);
        sheet.addExercise(planExe2);
    }

    @Test
    void testGetWorkoutSheet() {
        assertEquals(EXPECTED_SIZE, sheet.getWorkoutSheet().size());
        assertEquals(
            Set.of(planExe, planExe2), 
            sheet.getWorkoutSheet()
        );
    }

    @Test
    void testGetExercise() {
        assertEquals(Optional.of(planExe), sheet.getExercise(EXE_NAME_1));
        assertEquals(Optional.empty(), sheet.getExercise("Corsa"));
    }

    @Test
    void testAddExercise() {
        sheet.addExercise(planExe);
        sheet.addExercise(planExe2);
    }

    @Test
    void testRemouveExercise() {
        assertTrue(sheet.remouveExercise(planExe));
        assertTrue(sheet.remouveExercise(planExe2));
    }

    @Test
    void testGetVolume() {
        assertEquals(planExe.getVolume() 
            + planExe2.getVolume(), sheet.getVolume()
        );
    }

    @Test
    void testGetBurnedCalories() {
        assertEquals(planExe.getBurnedCalories()
            + planExe2.getBurnedCalories(), sheet.getBurnedCalories()
        );
    }

    @Test
    void testGetExerciseType() {
        assertEquals(sheet
            .getExercise(EXE_NAME_1)
            .get().getExercise()
            .getExerciseType(), ExerciseType.STRENGTH);
        assertEquals(sheet.getExercise(EXE_NAME_2)
            .get()
            .getExercise()
            .getExerciseType(), ExerciseType.STRENGTH);
    }

}
