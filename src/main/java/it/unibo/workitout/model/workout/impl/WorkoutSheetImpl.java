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

    public final Set<StrengthPlannedExerciseImpl> strengthExs;
    public final Set<CardioPlannedExerciseImpl> cardioExs;

    /**
     * Costructor that taken the name of the sheet it inizialize the two type of set exercise.
     * 
     * @param nameSheet name of the sheet.
     */
    public WorkoutSheetImpl(final String nameSheet) {
        super(nameSheet);
        cardioExs = new HashSet<>();
        strengthExs = new HashSet<>();
    }

    /**
     * Private method to avoid code duplication.
     * 
     * @param plnExe that is used to call the right method when the interested one is pressed
     * 
     * @return the sum in double
     */
    private double sumAll(final ToDoubleFunction<PlannedExercise> plnExe) {
        double sum = 0.0;
        for (final PlannedExercise e : strengthExs) {
            sum += plnExe.applyAsDouble(e);
        }

        for (final PlannedExercise e : cardioExs) {
            sum += plnExe.applyAsDouble(e);
        }

        return sum;
    }

    /** {@inheritDoc} */
    @Override
    public Set<PlannedExercise> getWorkoutSheet() {

        final Set<PlannedExercise> mergeExercise = new HashSet<>();
        if (this.strengthExs != null) {
            mergeExercise.addAll(this.strengthExs);
        }
        if (this.cardioExs != null) {
            mergeExercise.addAll(this.cardioExs);
        }

        return Set.copyOf(mergeExercise);
    }

    /** {@inheritDoc} */
    @Override
    public Optional<PlannedExercise> getExercise(final String nameExercise) {
        return this.getWorkoutSheet().stream().filter(b -> b.getName().equals(nameExercise)).findAny();
    }

    /** {@inheritDoc} */
    @Override
    public Boolean addExercise(final PlannedExercise exercise) {
        if (exercise instanceof StrengthPlannedExerciseImpl) {
            return this.strengthExs.add((StrengthPlannedExerciseImpl) exercise);
        } else if (exercise instanceof CardioPlannedExerciseImpl) {
            return this.cardioExs.add((CardioPlannedExerciseImpl) exercise);
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public Boolean remouveExercise(final PlannedExercise exercise) {
        return this.strengthExs.remove(exercise) || this.cardioExs.remove(exercise);
    }

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("PMD.LambdaCanBeMethodReference")
    public double getVolume() {
        return sumAll(e -> e.getVolume());
    }

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("PMD.LambdaCanBeMethodReference")
    public double getBurnedCalories() {
        return sumAll(e -> e.getBurnedCalories());
    }

}
