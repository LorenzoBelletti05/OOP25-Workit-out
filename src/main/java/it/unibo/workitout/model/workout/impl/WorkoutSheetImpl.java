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
public final class WorkoutSheetImpl extends NameFunction implements WorkoutSheet {

    // private Set<PlannedExercise> exercisesSheet;

    private Set<StrengthPlannedExerciseImpl> strengthExs = new HashSet<>();
    private Set<CardioPlannedExerciseImpl> cardioExs = new HashSet<>();

    public WorkoutSheetImpl(final String nameSheet) {
        super(nameSheet);        
        // exercisesSheet = new HashSet<>();
        cardioExs = new HashSet<>();
        strengthExs = new HashSet<>();
    }

    /**
     * Private method to avoid code duplication.
     * 
     * @param plnExe that is used to call the right method when the interested one is pressed.
     * @return the sum in double.
     */
    private double sumAll(ToDoubleFunction<PlannedExercise> plnExe) {
        double sum = 0.0;
        for (PlannedExercise e : strengthExs) sum += plnExe.applyAsDouble(e);
        for (PlannedExercise e : cardioExs) sum += plnExe.applyAsDouble(e);
        return sum;
    }

    @Override
    public Set<PlannedExercise> getWorkoutSheet() {
        
        Set<PlannedExercise> mergeExercise = new HashSet<>();
        if (this.strengthExs != null) {
            mergeExercise.addAll(this.strengthExs);
        }
        if (this.cardioExs != null) {
            mergeExercise.addAll(this.cardioExs);
        }
        
        return Set.copyOf(mergeExercise);
    }

    @Override
    public Optional<PlannedExercise> getExercise(final String nameExercise) {
        return this.getWorkoutSheet().stream().filter(b -> b.getName().equals(nameExercise)).findAny();
    }

    @Override
    public Boolean addExercise(final PlannedExercise exercise) {
        if (exercise instanceof StrengthPlannedExerciseImpl) {
            return this.strengthExs.add((StrengthPlannedExerciseImpl) exercise);
        } else if (exercise instanceof CardioPlannedExerciseImpl) {
            return this.cardioExs.add((CardioPlannedExerciseImpl) exercise);
        }
        return false;
    }

    @Override
    public Boolean remouveExercise(final PlannedExercise exercise) {
        return this.strengthExs.remove(exercise) || this.cardioExs.remove(exercise);
    }

    @Override
    public double getVolume() {
        return sumAll(b -> b.getVolume());
    }

    @Override
    public double getBurnedCalories() {
        return sumAll(b -> b.getBurnedCalories());
    }

}