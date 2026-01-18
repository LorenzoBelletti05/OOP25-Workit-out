package it.unibo.workitout.model.user.model.impl;

/**
 * Represents the biological sex of the user.
 */
public enum Sex {
    NOT_DEFINED("Not Defined"),
    MALE("Male"),
    FEMALE("Female");

    private final String sex;

    private Sex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString(){
        return sex;
    }
}


