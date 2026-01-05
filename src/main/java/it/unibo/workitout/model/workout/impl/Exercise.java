package it.unibo.workitout.model.workout.impl;

public class Exercise {

    private final String name;
    private final double calorieBurned;

    public Exercise(String name, double calorieBurned) {
        this.name = name;
        this.calorieBurned = calorieBurned;
    }

    public String getName() {
        return name;
    }

    public double calorieBurned(int minutes) {
        return this.calorieBurned * minutes;
    }
    
}
