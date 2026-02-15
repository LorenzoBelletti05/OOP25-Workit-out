package it.unibo.workitout.model.user.model.impl;

/**
 * Represents the biological sex of the user.
 */
public enum Sex {
    NOT_DEFINED("Prefer not to say"),
    MALE("Male"),
    FEMALE("Female");

    private final String sex;

    /**
     * Constructor of sex enum.
     * 
     * @param sex the user biological sex
     */
    Sex(final String sex) {
        this.sex = sex;
    }

    /**
     * @return the selected sex
     */
    @Override
    public String toString() {
        return sex;
    }
}


