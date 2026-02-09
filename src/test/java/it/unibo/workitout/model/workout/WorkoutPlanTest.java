package it.unibo.workitout.model.workout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.model.workout.contracts.PlannedExercise;
import it.unibo.workitout.model.workout.contracts.WorkoutPlan;
import it.unibo.workitout.model.workout.contracts.WorkoutSheet;
import it.unibo.workitout.model.workout.impl.Exercise;
import it.unibo.workitout.model.workout.impl.ExerciseType;
import it.unibo.workitout.model.workout.impl.StrengthPlannedExerciseImpl;
import it.unibo.workitout.model.workout.impl.WorkoutPlanImpl;
import it.unibo.workitout.model.workout.impl.WorkoutSheetImpl;

/**
 * Test class for a specific plannedExercise.
 */
public class WorkoutPlanTest {

    private static final double CALORIES_PER_MINUTE = 10.0;
    private static final String DATE_1 = "2025-12-24";
    private static final String DATE_2 = "2025-12-25";
    private static final int DEFAULT_MINUTES = 10;
    private static final int EXPECTED_SIZE = 2;

    private final Exercise exercise = new Exercise(
        "Squat", 
        CALORIES_PER_MINUTE, 
        UserGoal.BUILD_MUSCLE.toString(), 
        ExerciseType.STRENGTH
    );
    private final Exercise exercise2 = new Exercise(
        "Panca", 
        CALORIES_PER_MINUTE, 
        UserGoal.BUILD_MUSCLE.toString(), 
        ExerciseType.STRENGTH
    );

    private final PlannedExercise planExe = new StrengthPlannedExerciseImpl(
        this.exercise, 
        DEFAULT_MINUTES, 
        3, 
        12, 
        10
    );
    private final PlannedExercise planExe2 = new StrengthPlannedExerciseImpl(
        this.exercise2, 
        DEFAULT_MINUTES, 
        4, 
        10, 
        15
    );

    private WorkoutSheet sheet1;
    private WorkoutSheet sheet2;
    private WorkoutPlan plan;

    /**
     * Generic method used to set up the variables.
     */
    @BeforeEach
    void basicImplementationSetUp() {
        plan = new WorkoutPlanImpl("Allenamento Bulk");

        sheet1 = new WorkoutSheetImpl("Allenamento 1");
        sheet1.addExercise(planExe);

        sheet2 = new WorkoutSheetImpl("Allenamento 2");
        sheet2.addExercise(planExe2);

        plan.addWorkSheet(DATE_1, sheet1);
        plan.addWorkSheet(DATE_2, sheet2);
    }

    @Test
    void testGetWorkoutPlan() {
        assertEquals(EXPECTED_SIZE, plan.getWorkoutPlan().size());
        assertTrue(plan.getWorkoutPlan().containsKey(DATE_1));
        assertTrue(plan.getWorkoutPlan().containsKey(DATE_2));
    }

    @Test
    void testGetVolume() {
        assertEquals(sheet1.getVolume() + sheet2.getVolume(), plan.getVolume());
    }

    @Test
    void testGetBurnedCalories() {
        // Le calorie totali sono la somma delle calorie di planExe e planExe2
        assertEquals(
            planExe.getBurnedCalories() + planExe2.getBurnedCalories(), 
            plan.getBurnedCalories());
    }

    @Test
    void testGetAllExercise() {
        assertEquals(EXPECTED_SIZE, plan.getAllExercise().size());
        assertEquals(Set.of(planExe, planExe2), plan.getAllExercise());
    }

    @Test
    void testGetStrenghtExercise() {
        assertEquals(EXPECTED_SIZE, plan.getStrenghtExercise().size());
    }
}
