package it.unibo.workitout.model.user.model.impl;

import java.time.LocalDate;

public class UserProfileHistory {
    private final LocalDate date;


    UserProfileHistory(LocalDate date) {
        this.date = LocalDate.now();
    }

    public LocalDate getDate() {
        return this.date;
    }
}
