package it.unibo.workitout.model.user.model.impl;

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

    private final UUID id;
    private final String name;
    private final String surname;
    private final Sex sex;
    private int age;
    private double height;
    private double weight;
    private ActivityLevel activityLevel;
    private UserGoal userGoal;
    private String strategy;

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
     */
    public UserProfile(
        final String name,
        final String surname,
        int age,
        double height,
        double weight,
        final Sex sex,
        ActivityLevel activityLevel,
        UserGoal userGoal,
        String strategy
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
        if(this.strategy == null) {
            this.strategy = "MifflinStJeorStrategy";
        } else {
            this.strategy = strategy;
        }
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
     */
    public UserProfile(
        final UUID id,
        final String name,
        final String surname,
        int age,
        double height,
        double weight,
        final Sex sex,
        ActivityLevel activityLevel,
        UserGoal userGoal,
        String strategy
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
        if(this.strategy == null) {
            this.strategy = "MifflinStJeorStrategy";
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
    public UserGoal getGoal() {
        return userGoal;
    }

    public String getStrategy() {
        return strategy;
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
    public void setGoal(final UserGoal userGoal) {
        this.userGoal = userGoal;
    }
}
