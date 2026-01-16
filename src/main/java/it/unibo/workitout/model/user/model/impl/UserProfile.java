package it.unibo.workitout.model.user.model.impl;

import java.util.UUID;

/**
 * Represents the profile of a user, including physical attributes and goals.
 */
public final class UserProfile {
    private final UUID id;
    private final String name;
    private final String surname;
    private final Sex sex;
    private int age;
    private double height;
    private double weight;
    private ActivityLevel activityLevel;
    private UserGoal userGoal;

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
        UserGoal userGoal
    ) {
        if(age < 0) {
            throw new IllegalArgumentException("The age must be positive");
        }
        if(height < 0) {
            throw new IllegalArgumentException("The height must be positive");
        }
        if(weight < 0) {
            throw new IllegalArgumentException("The weight must be positive");
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
        UserGoal userGoal
    ) {
        if(age < 0 || age > 110) {
            throw new IllegalArgumentException("The age must be positive and less of 110");
        }
        if(height < 0 || height > 210) {
            throw new IllegalArgumentException("The height must be positive and less of 210");
        }
        if(weight < 0 || weight > 310) {
            throw new IllegalArgumentException("The weight must be positive and less of 310");
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

    /**
     * Set a new user's age.
     * @param age the new age, must be positive
     */
    public void setAge(final int age) {
        if(age < 0 || age > 110) {
            throw new IllegalArgumentException("The age must be positive and less of 110");
        }
        this.age = age;
    }

    /**
     * Set a new user's height.
     * @param height the new height, must be positive
     */
    public void setHeight(final double height) {
        if(height < 0 || height > 210) {
            throw new IllegalArgumentException("The height must be positive and less of 210");
        }
        this.height = height;
    }

    /**
     * Set a new user's height.
     * @param weight the new weight, must be positive
     */
    public void setWeight(final double weight) {
        if(weight < 0 || weight > 310) {
            throw new IllegalArgumentException("The weight must be positive and less of 310");
        }
        this.weight = weight;
    }

    /**
     * Update the user's activity level.
     * @param activityLevel the new activity level
     */
    public void setActivityLevel(final ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
    }

    /**
     * Update the user's fitness goal.
     * @param userGoal the new goal
     */
    public void setGoal(final UserGoal userGoal) {
        this.userGoal = userGoal;
    }
}
