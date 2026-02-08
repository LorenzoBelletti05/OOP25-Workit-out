package it.unibo.workitout.model.user.model.impl;

/**
 * Represents the biological sex of the user.
 */
public enum Sex {
    NOT_DEFINED("Prefer not to say"),
    MALE("Male"),
    FEMALE("Female");

    private final String sex;

    Sex(final String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return sex;
    }
}


