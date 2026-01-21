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

import java.time.LocalDate;

/**
 * Implementation of {@link WorkoutPlan}.
 *
 * <p>
 * This class aggregates multiples {@link WorkoutSheetImpl} istence to represent the training program.
 * </p>
 */
public final class WorkoutPlanImpl extends NameFunction implements WorkoutPlan {

    private Map<LocalDate, WorkoutSheet> workoutPlan; //map to store localDate of the workoutsheet and his date (assumed 1 workout a day)

    public WorkoutPlanImpl(final String namePlan) {
        super(namePlan);
        workoutPlan = new TreeMap<>(); //used treemap to have key value ordered
    }

    private double sumAll(ToDoubleFunction<WorkoutSheet> sheetPlan) {
        double sum = 0.0;
        for (WorkoutSheet sheet : workoutPlan.values()) {
            sum+=sheetPlan.applyAsDouble(sheet);
        }
        return sum;
    }

    private <T extends PlannedExercise> Set<T> getExerciseSubdivision(Class<T> exerciseClass) {
        Set<T> allExercise = new HashSet<>();
        for (WorkoutSheet sheet : workoutPlan.values()) {
            for (PlannedExercise plannExercise : sheet.getWorkoutSheet()) {
                if (exerciseClass.isInstance(plannExercise)){
                    allExercise.add(exerciseClass.cast(plannExercise));
                }
            }
        }
        return Set.copyOf(allExercise);
    }

    @Override
    public Map<LocalDate, WorkoutSheet> getWorkoutPlan() {
        return Collections.unmodifiableMap(this.workoutPlan);
    }

    //Following two methods return the sum of: volume and burned calories
    @Override
    public double getVolume() {
        return sumAll(WorkoutSheet::getVolume);
    }

    @Override
    public double getBurnedCalories() {
        return sumAll(WorkoutSheet::getBurnedCalories);
    }


    //Following two methods return the set of: workoutSheets and of all exercise
    @Override
    public Set<WorkoutSheet> getSheets() {
        return Set.copyOf(workoutPlan.values()); //give an unmodifiable set of workoutSheet
    }

    @Override
    public Set<PlannedExercise> getAllExercise() {
        Set<PlannedExercise> allExercise = new HashSet<>();
        for (WorkoutSheet sheet : workoutPlan.values()) {
            allExercise.addAll(sheet.getWorkoutSheet());
        }
        return Set.copyOf(allExercise);
    }


    //Following two methods return the set of each type of the exercise: strenght or cardio
    @Override
    public Set<StrengthPlannedExercise> getStrenghtExercise() {
        return getExerciseSubdivision(StrengthPlannedExercise.class);
    }

    @Override
    public Set<CardioPlannedExercise> getCardiotExercise() {
        return getExerciseSubdivision(CardioPlannedExercise.class);
    }

}
