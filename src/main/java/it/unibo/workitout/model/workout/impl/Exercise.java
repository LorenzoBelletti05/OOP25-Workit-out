package it.unibo.workitout.model.workout.impl;

import java.util.Set;

public class Exercise{

    private final String name;
    private final double calorieBurned;
    private final Set<AttitudeExercise> exerciseMission;

    public Exercise(final String name, final  double calorieBurned, final  Set<AttitudeExercise> exerMission) {
        this.name = name;
        this.calorieBurned = calorieBurned;
        this.exerciseMission = exerMission;
    }

    public String getName() {
        return name;
    }

    public double calorieBurned(final int minutes) {
        return this.calorieBurned * minutes;
    }

}
