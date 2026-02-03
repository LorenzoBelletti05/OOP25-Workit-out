package it.unibo.workitout.model.user.model.impl;

import it.unibo.workitout.model.user.model.contracts.BMRCalculatorStrategy;

/**
 * Represent the implemetation of Mifflin St.Jeor equation for BMR.
 */
public final class MifflinStJeorStrategy implements BMRCalculatorStrategy {

    @Override
    public double calculateBMR(final UserProfile up) {
        final Sex sex = up.getSex();
        final int age = up.getAge();
        final double height = up.getHeight();
        final double weight = up.getWeight();
        final boolean male = sex == Sex.MALE;
        final boolean female = sex == Sex.FEMALE;
        final boolean nd = sex == Sex.NOT_DEFINED;
        final double base = (10 * weight) + (6.25 * height) - (5 * age);
        final double numberMale = 5;
        final double numberFemale = -161;
        final double numberNotDefined = (numberMale + numberFemale) / 2; 

        if (male) {
            return base + numberMale; 
        } else if (female) {
            return base + numberFemale;
        } else if (nd) {
            return base + numberNotDefined;
        } else {
            throw new IllegalStateException("The sex specified doesen't exist:" + sex);
        }
    }

    @Override
    public String toString() {
        return "MifflinStJeorStrategy";
    }
}
