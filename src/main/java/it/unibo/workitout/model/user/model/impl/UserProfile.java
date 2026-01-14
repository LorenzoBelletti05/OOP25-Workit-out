package it.unibo.workitout.model.user.model.impl;

import java.util.UUID;

/**
 * Represents the profile of a user, including physical attributes and goals.
 */
public final class UserProfile {
    private final UUID id;
    private final String name;
    private final String surname;
    private final int age;
    private final double height;
    private final double weight;
    private final Sex sex;
    private final ActivityLevel activityLevel;
    private final UserGoal goal;

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
     * @param goal          the user's fitness goal
     */
    public UserProfile(
        final String name,
        final String surname,
        final int age,
        final double height,
        final double weight,
        final Sex sex,
        final ActivityLevel activityLevel,
        final UserGoal goal
    ) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.sex = sex;
        this.height = height;
        this.weight = weight;
        this.activityLevel = activityLevel;
        this.goal = goal;
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
     * @param goal          the user's fitness goal
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
        final UserGoal goal
    ) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.sex = sex;
        this.height = height;
        this.weight = weight;
        this.activityLevel = activityLevel;
        this.goal = goal;
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
        return goal;
    }

}
