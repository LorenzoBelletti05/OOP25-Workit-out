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

public class WorkoutSheetTest {

    private final static double caloriesPerMinute = 10.0;

    private Exercise exercise = new Exercise("Affondi", caloriesPerMinute, UserGoal.BUILD_MUSCLE.toString(), ExerciseType.STRENGTH);
    private Exercise exercise2 = new Exercise("Push-up", caloriesPerMinute, UserGoal.MAINTAIN_WEIGHT.toString()+  ", "+ UserGoal.LOSE_WEIGHT.toString(), ExerciseType.STRENGTH);

    private PlannedExercise planExe = new StrengthPlannedExerciseImpl(exercise, 10, 3, 12, 10);
    private PlannedExercise planExe2 = new StrengthPlannedExerciseImpl(exercise2, 11, 4, 13, 11); 
    
    private WorkoutSheet sheet;

    @BeforeEach
    void basicImplementationSetUp() {
        sheet = new WorkoutSheetImpl("Scheda pre-Natale");       

        // planExe = new StrengthPlannedExerciseImpl(exercise, 10, 3, 12, 10);
        // planExe2 = new StrengthPlannedExerciseImpl(exercise2, 11, 4, 13, 11);
        
        sheet.addExercise(planExe);
        sheet.addExercise(planExe2);        
    }

    @Test
    void testGetWorkoutSheet() {       
        assertEquals(2,sheet.getWorkoutSheet().size()); 
        assertEquals(
            Set.of(planExe, planExe2), 
            sheet.getWorkoutSheet()
        );
    }

    @Test
    void testGetExercise() {        
        assertEquals(Optional.of(planExe), sheet.getExercise("Affondi"));
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
            + planExe2.getVolume(), 
            sheet.getVolume()
        );
    }

    @Test
    void testGetBurnedCalories() {
        assertEquals(planExe.getBurnedCalories()
            + planExe2.getBurnedCalories(),            
            sheet.getBurnedCalories()
        );
    }

    @Test
    void testGetExerciseType() {
        assertEquals(sheet.getExercise("Affondi").get().getExercise().getExerciseType(), ExerciseType.STRENGTH);
        assertEquals(sheet.getExercise("Push-up").get().getExercise().getExerciseType(), ExerciseType.STRENGTH);
    }

}
