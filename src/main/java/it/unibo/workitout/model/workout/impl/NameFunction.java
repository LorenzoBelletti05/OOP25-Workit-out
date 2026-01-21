package it.unibo.workitout.model.workout.impl;

/**
 * General abstract class for manage names.
 */
public abstract class NameFunction {

    private final String name;

    /**
     * Costructor that given the exercise name save it in a field.
     * 
     * @param name name of the exercise saved in the field.
     */
    public NameFunction(final String name) {
        this.name = name;
    }

    /**
     * Return the name based where is implemented.
     * 
     * @return the name.
     */
    public String getName() {
        return this.name;
    }

}
