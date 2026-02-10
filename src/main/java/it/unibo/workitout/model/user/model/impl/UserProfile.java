package it.unibo.workitout.model.user.model.impl;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents the profile of a user, including physical attributes and goals.
 */
public final class UserProfile {
    private static final double ZERO = 0;
    private static final int MAX_AGE = 110;
    private static final double MAX_HEIGHT = 210;
    private static final double MAX_WEIGHT = 310;
    private static final String ERR_MESS_AGE = "The age must be positive and less of 110";
    private static final String ERR_MESS_HEIGHT = "The height must be positive and less of 210";
    private static final String ERR_MESS_WEIGHT = "The weight must be positive and less of 310";
    private static final String ERR_BURNED_CALORIES = "The burned calories must be positive";
    private static final String ERR_CONSUMED_CALORIES = "The consumed calories must be positive";
    private static final String ERR_CONSUMED_CARBS = "The consumed carbs must be positive";
     private static final String ERR_CONSUMED_PROTEINS = "The consumed proteins must be positive";
      private static final String ERR_CONSUMED_FATS = "The consumed fats must be positive";
    private static final String MIFFLIN_STRATEGY = "MifflinStJeorStrategy";
    private static final String HARRIS_STRATEGY = "HarrisBenedictStrategy";

    private final UUID id;
    private String name;
    private String surname;
    private Sex sex;
    private int age;
    private double height;
    private double weight;
    private ActivityLevel activityLevel;
    private UserGoal userGoal;
    private String strategy;
    private String lastAccess;
    private double burnedCalories;
    private double consumedCalories;
    private double consumedCarbs;
    private double consumedProteins;
    private double consumedFats;

    /**
     * Constructor for a new user.
     * 
     * @param name          the user's name
     * @param surname       the user's surname
     * @param age           the user's age
     * @param height        the user's height in cm
     * @param weight        the user's weight in kg
     * @param sex           the user's biological sex
     * @param activityLevel the user's activity level
     * @param userGoal      the user's fitness goal
     * @param strategy      the user's strategy for calculate the BMR
     */
    public UserProfile(
        final String name,
        final String surname,
        final int age,
        final double height,
        final double weight,
        final Sex sex,
        final ActivityLevel activityLevel,
        final UserGoal userGoal,
        final String strategy
    ) {
        if (age < ZERO || age > MAX_AGE) {
            throw new IllegalArgumentException(ERR_MESS_AGE);
        }
        if (height < ZERO || height > MAX_HEIGHT) {
            throw new IllegalArgumentException(ERR_MESS_HEIGHT);
        }
        if (weight < ZERO || weight > MAX_WEIGHT) {
            throw new IllegalArgumentException(ERR_MESS_WEIGHT);
        }

        this.id = UUID.randomUUID();
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.sex = sex;
        this.height = height;
        this.weight = weight;
        this.activityLevel = activityLevel;
        this.userGoal = userGoal;
        if (strategy == null) {
            this.strategy = MIFFLIN_STRATEGY;
        } else {
            this.strategy = strategy;
        }
        this.lastAccess = LocalDate.now().toString();
    }

    /**
     * Constructor for an existing user.
     * 
     * @param id            the user's identifier
     * @param name          the user's name
     * @param surname       the user's surname
     * @param age           the user's age
     * @param height        the user's height in cm
     * @param weight        the user's weight in kg
     * @param sex           the user's biological sex
     * @param activityLevel the user's activity level
     * @param userGoal      the user's fitness goal
     * @param strategy      the user's strategy for calculate the BMR
     */
    public UserProfile(
        final UUID id,
        final String name,
        final String surname,
        final int age,
        final double height,
        final double weight,
        final Sex sex,
        final ActivityLevel activityLevel,
        final UserGoal userGoal,
        final String strategy
    ) {
        if (age < ZERO || age > MAX_AGE) {
            throw new IllegalArgumentException(ERR_MESS_AGE);
        }
        if (height < ZERO || height > MAX_HEIGHT) {
            throw new IllegalArgumentException(ERR_MESS_HEIGHT);
        }
        if (weight < ZERO || weight > MAX_WEIGHT) {
            throw new IllegalArgumentException(ERR_MESS_WEIGHT);
        }

        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.sex = sex;
        this.height = height;
        this.weight = weight;
        this.activityLevel = activityLevel;
        this.userGoal = userGoal;
        if (strategy == null) {
            this.strategy = MIFFLIN_STRATEGY;
        } else {
            this.strategy = strategy;
        }
    }

    /**
     * @return the identifier of the user
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * @return the surname of the user
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @return the age of the user
     */
    public int getAge() {
        return age;
    }

    /**
     * @return the biological sex of the user
     */
    public Sex getSex() {
        return sex;
    }

    /**
     * @return the height of the user
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return the weight of the user
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @return the activity level of the user
     */
    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }

    /**
     * @return the fitness goal of the user
     */
    public UserGoal getUserGoal() {
        return userGoal;
    }

    /**
     * @return the strategy name
     */
    public String getStrategy() {
        return strategy;
    }

    /**
     * @return the date of the last access of user
     */
    public LocalDate getLastAccess() {
        if (this.lastAccess == null) {
            return LocalDate.now();
        } else {
            return LocalDate.parse(this.lastAccess);
        }
    }

    /**
     * @return the total of burned calories
     */
    public double getBurnedCalories() {
        return burnedCalories;
    }

    /**
     * @return the total of consumed calories
     */
    public double getConsumedCalories() {
        return consumedCalories;
    }

    /**
     * @return the total of consumed carbs
     */
     public double getConsumedCarbs() {
        return consumedCarbs;
    }

    /**
     * @return the total of consumed proteins
     */
    public double getConsumedProteins() {
        return consumedProteins;
    }

    /**
     * @return the total of consumed fats
     */
    public double getConsumedFats() {
        return consumedFats;
    }

    /**
     * Set a new user's name
     * 
     * @param name sets the new user's name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Set a new user's surname
     * 
     * @param surname sets the new user's name
     */
    public void setSurname(final String surname) {
        this.surname = surname;
    }

    /**
     * Set a new user's age.
     * 
     * @param age the new age, must be positive
     */
    public void setAge(final int age) {
        if (age < ZERO || age > MAX_AGE) {
            throw new IllegalArgumentException(ERR_MESS_AGE);
        }
        this.age = age;
    }

    /**
     * Set a new user's sex.
     * 
     * @param sex the new sex
     */
    public void setSex(final Sex sex) {
        this.sex = sex;
    }

    /**
     * Set a new user's height.
     * 
     * @param height the new height, must be positive
     */
    public void setHeight(final double height) {
        if (height < ZERO || height > MAX_HEIGHT) {
            throw new IllegalArgumentException(ERR_MESS_HEIGHT);
        }
        this.height = height;
    }

    /**
     * Set a new user's height.
     * 
     * @param weight the new weight, must be positive
     */
    public void setWeight(final double weight) {
        if (weight < ZERO || weight > MAX_WEIGHT) {
            throw new IllegalArgumentException(ERR_MESS_WEIGHT);
        }
        this.weight = weight;
    }

    /**
     * Update the user's activity level.
     * 
     * @param activityLevel the new activity level
     */
    public void setActivityLevel(final ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
    }

    /**
     * Update the user's fitness goal.
     * 
     * @param userGoal the new goal
     */
    public void setUserGoal(final UserGoal userGoal) {
        this.userGoal = userGoal;
    }

    /**
     * Update the user's strategy choice.
     * 
     * @param strategy the new strategy for calculate BMR
     */
    public void setStrategy(final String strategy) {
        if (MIFFLIN_STRATEGY.equals(strategy)
            || HARRIS_STRATEGY.equals(strategy)) {

            this.strategy = strategy;
        }
    }

    /**
     * Sets the last access with current date.
     */
    public void setLastAccess() {
        this.lastAccess = LocalDate.now().toString();
    }

    /**
     * @param burnedCalories Update the user's burned calories from exercise
     */
    public void setBurnedCalories(final double burnedCalories) {
        if (burnedCalories < 0) {
            throw new IllegalArgumentException(ERR_BURNED_CALORIES);
        }
        this.burnedCalories = burnedCalories;
    }

    /**
     * @param consumedCalories Update the user's consumed calories
     */
    public void setConsumedCalories(final double consumedCalories) {
        if (consumedCalories < 0) {
            throw new IllegalArgumentException(ERR_CONSUMED_CALORIES);
        }
        this.consumedCalories = consumedCalories;
    }

    /**
     * @param consumedCarbs Update the user's consumed carbs
     */
    public void setConsumedCarbs(final double consumedCarbs) {
        if (consumedCarbs < 0) {
            throw new IllegalArgumentException(ERR_CONSUMED_CARBS);
        }
        this.consumedCarbs = consumedCarbs;
    }

    /**
     * @param consumedCalories Update the user's consumed proteins
     */
    public void setConsumedProteins(final double consumedProteins) {
        if (consumedProteins < 0) {
            throw new IllegalArgumentException(ERR_CONSUMED_PROTEINS);
        }
        this.consumedProteins = consumedProteins;
    }

    /**
     * @param consumedCalories Update the user's consumed fats
     */
    public void setConsumedFats(final double consumedFats) {
        if (consumedFats < 0) {
            throw new IllegalArgumentException(ERR_CONSUMED_FATS);
        }
        this.consumedFats = consumedFats;
    }
}
