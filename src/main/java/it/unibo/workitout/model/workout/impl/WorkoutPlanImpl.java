package it.unibo.workitout.model.workout.impl;

import java.util.Set;

import it.unibo.workitout.model.workout.contracts.CardioPlannedExercise;
import it.unibo.workitout.model.workout.contracts.PlannedExercise;
import it.unibo.workitout.model.workout.contracts.StrengthPlannedExercise;
import it.unibo.workitout.model.workout.contracts.WorkoutPlan;

/**
 * Implementation of {@link WorkoutPlan}.
 * 
 * <p>
 * This class aggregates multiples {@link WorkoutSheet} istence to represent the training program.
 * </p>
 */
public class WorkoutPlanImpl implements WorkoutPlan {

    @Override
    public Set<WorkoutSheet> getSheets() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSheets'");
    }

    @Override
    public Set<PlannedExercise> getAllExercise() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllExercise'");
    }

    @Override
    public double getTotalBurnedCalories() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTotalBurnedCalories'");
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

    @Override
    public double getTotalStrenghtVolume() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTotalStrenghtVolume'");
    }

}
