package it.unibo.workitout.model.user.model.impl;

import it.unibo.workitout.model.user.model.contracts.BMRCalculatorStrategy;

/**
 * Represent the implemetation of Harris Benedict equation for BMR.
 */
public final class HarrisBenedictStrategy implements BMRCalculatorStrategy {

    @Override
    public double calculateBMR(final UserProfile up) {
        final Sex sex = up.getSex();
        final int age = up.getAge();
        final double height = up.getHeight();
        final double weight = up.getWeight();
        final boolean male = sex == Sex.MALE;
        final boolean female = sex == Sex.FEMALE;
        final boolean nd = sex == Sex.NOT_DEFINED;
        final double bmrMale = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        final double bmrFemale = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);

        if (male) {
            return bmrMale;
        } else if (female) {
            return bmrFemale;
        } else if (nd) {
            return (bmrMale + bmrFemale) / 2;
        } else {
            throw new IllegalStateException("The sex specified doesen't exist:" + sex);
        }
    }
}
