package it.unibo.workitout.model.workout.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.ToDoubleFunction;
import it.unibo.workitout.model.workout.contracts.CardioPlannedExercise;
import it.unibo.workitout.model.workout.contracts.PlannedExercise;
import it.unibo.workitout.model.workout.contracts.StrengthPlannedExercise;
import it.unibo.workitout.model.workout.contracts.WorkoutPlan;
import it.unibo.workitout.model.workout.contracts.WorkoutSheet;

/**
 * Implementation of {@link WorkoutPlan}.
 *
 * <p>
 * This class aggregates multiples {@link WorkoutSheetImpl} istence to represent the training program.
 * </p>
 */
public final class WorkoutPlanImpl extends NameFunction implements WorkoutPlan {

    //map to store localDate of the workoutsheet and his date (assumed 1 workout a day)
    public final Map<String, WorkoutSheetImpl> workoutPlan; 

    /**
     * Constructor to set name of the plan using the {@link NameFunction} and than create a new plan.
     * 
     * @param namePlan created each time an istance is setted.
     */
    public WorkoutPlanImpl(final String namePlan) {
        super(namePlan);
        workoutPlan = new TreeMap<>(); //used treemap to have key value ordered
    }

    /**
     * Copy constructor for WorkoutPlanImpl.
     * Used for encapsulation problem.
     * 
     * @param otherPlan the plan to copy.
     * 
     */
    public WorkoutPlanImpl(final WorkoutPlan otherPlan) {
        this(otherPlan.getName());
        otherPlan.getWorkoutPlan().forEach(this::addWorkSheet);
    }

    /**
     * Method to sum all the data from the param and to avoid code duplication.
     * 
     * @param sheetPlan with all the planned exercise
     * @return the sum
     */
    private double sumAll(final ToDoubleFunction<WorkoutSheet> sheetPlan) {
        double sum = 0.0;
        for (final WorkoutSheet sheet : workoutPlan.values()) {
            sum += sheetPlan.applyAsDouble(sheet);
        }
        return sum;
    }

    /**
     * Methot with generics to separate the exercise give based on his class type.
     * 
     * @param <T> that extends PlannedExercise
     * @param exerciseClass the class of the exercise
     * @return the set of the specific type of the exercise class
     */
    private <T extends PlannedExercise> Set<T> getExerciseSubdivision(final Class<T> exerciseClass) {
        final Set<T> allExercise = new HashSet<>();
        for (final WorkoutSheet sheet : workoutPlan.values()) {
            for (final PlannedExercise plannExercise : sheet.getWorkoutSheet()) {
                if (exerciseClass.isInstance(plannExercise)) {
                    allExercise.add(exerciseClass.cast(plannExercise));
                }
            }
        }
        return Set.copyOf(allExercise);
    }

    /** {@inheritDoc} */
    @Override
    public Map<String, WorkoutSheet> getWorkoutPlan() {
        return Collections.unmodifiableMap(new TreeMap<>(this.workoutPlan));
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

    /** {@inheritDoc} */
    @Override
    public Set<WorkoutSheet> getSheets() {
        return Set.copyOf(workoutPlan.values()); //give an unmodifiable set of workoutSheet
    }

    /** {@inheritDoc} */
    @Override
    public Set<PlannedExercise> getAllExercise() {
        final Set<PlannedExercise> allExercise = new HashSet<>();
        for (final WorkoutSheet sheet : workoutPlan.values()) {
            allExercise.addAll(sheet.getWorkoutSheet());
        }
        return Set.copyOf(allExercise);
    }

    /** {@inheritDoc} */
    @Override
    public Set<StrengthPlannedExercise> getStrenghtExercise() {
        return getExerciseSubdivision(StrengthPlannedExercise.class);
    }

    /** {@inheritDoc} */
    @Override
    public Set<CardioPlannedExercise> getCardiotExercise() {
        return getExerciseSubdivision(CardioPlannedExercise.class);
    }

    /** {@inheritDoc} */
    @Override
    public void addWorkSheet(final String dateNext, final WorkoutSheet workoutSheet) {
        this.workoutPlan.put(dateNext, (WorkoutSheetImpl) workoutSheet);
    }

}
