package it.unibo.workitout.model.workout.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.ToDoubleFunction;

import it.unibo.workitout.model.workout.contracts.PlannedExercise;
import it.unibo.workitout.model.workout.contracts.WorkoutSheet;

/**
 * Represents a single daily training session.
 * 
 * <p>
 * His purpose is to act as contaienr for a group of {@link PlannedExercise} for a specific train.
 * </p>
 */
public class WorkoutSheetImpl extends NameFunction implements WorkoutSheet {
      
    private Set<PlannedExercise> exercisesSheet = new HashSet<>();
    
    public WorkoutSheetImpl(final String nameSheet) {
        super(nameSheet);        
    }

    /**
     * Private method to avoid code duplication.
     * 
     * @param plnExe that is used to call the right method when the interested one is pressed.
     * @return the sum in double.
     */
    private double sumAll(ToDoubleFunction<PlannedExercise> plnExe) {
        double sum = 0.0;
        for (PlannedExercise plannedExercise : exercisesSheet) {
            sum+=plnExe.applyAsDouble(plannedExercise);
        }
        return sum;
    }

    @Override
    public Optional<PlannedExercise> getExercise(final String nameExercise) {
        return exercisesSheet.stream().filter(b -> b.getName().equals(nameExercise)).findAny();
    }

    @Override
    public Boolean addExercise(final PlannedExercise exercise) {
        return exercisesSheet.add(exercise);
    }

    @Override
    public Boolean remouveExercise(final PlannedExercise exercise) {
        return exercisesSheet.remove(exercise);
    }

    @Override
    public double getVolume() {
        return sumAll(PlannedExercise::getVolume);
    }

    @Override
    public double getBurnedCalories() {
        return sumAll(PlannedExercise::getBurnedCalories);
    }
}


