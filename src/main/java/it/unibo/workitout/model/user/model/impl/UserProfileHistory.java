package it.unibo.workitout.model.user.model.impl;

import java.time.LocalDate;

/**
 * Represents a history of the user profile.
 */
public class UserProfileHistory {
    private final LocalDate date;

    /**
     * Constructor of a new history.
     * 
     * @param date the current date
     */
    UserProfileHistory(LocalDate date) {
        this.date = LocalDate.now();
    }

    /**
     * Return the date of this history.
     * 
     * @return the date
     */
    public LocalDate getDate() {
        return this.date;
    }
}
