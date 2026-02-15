package it.unibo.workitout.model.workout.impl;

/**
 * General class for manage names.
 */
public class NameFunction {

    private final String name;

    /**
     * Constructor to set the name in a field.
     * 
     * @param name of the exercise
     */
    public NameFunction(final String name) {
        this.name = name;
    }

    /**
     * @return the name.
     */
    public String getName() {
        return this.name;
    }

}
