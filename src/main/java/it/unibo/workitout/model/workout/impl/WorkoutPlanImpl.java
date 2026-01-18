package it.unibo.workitout.model.workout.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    private Map<LocalDate, List<WorkoutSheet>> workoutPlan;
    
    public WorkoutPlanImpl(final String namePlan) {
        super(namePlan);
    }

    private double sumAll(ToDoubleFunction<PlannedExercise> sheetPlan) {
        double sum = 0.0;
        for (List<WorkoutSheet> sheetExercisesList : workoutPlan.values()) {
            for (WorkoutSheet workoutSheet : sheetExercisesList) {
                
                sum+=sheetPlan.applyAsDouble((PlannedExercise) workoutSheet);
            }
            
        }
        return sum;
    }

    /**
     * Public getter that return this unmoodifiable structure data.
     * 
     * @return the unmodifiable set of Workoutsheet.
     */
    public Map<LocalDate, List<WorkoutSheet>> getWorkoutSheet() {
        return Collections.unmodifiableMap(this.workoutPlan);
    }

    @Override
    public double getVolume() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBurnedCalories'");
    }

    @Override
    public double getBurnedCalories() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBurnedCalories'");
    }

    @Override
    public Set<WorkoutSheetImpl> getSheets() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSheets'");
    }

    @Override
    public Set<PlannedExercise> getAllExercise() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllExercise'");
    }

    @Override
    public Set<StrengthPlannedExercise> getStrenghtExercise() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getStrenghtExercise'");
    }

    @Override
    public Set<CardioPlannedExercise> getCardiotExercise() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCardiotExercise'");
    }    

}
