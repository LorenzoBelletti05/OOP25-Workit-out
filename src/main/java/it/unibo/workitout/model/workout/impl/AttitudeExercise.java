package it.unibo.workitout.model.workout.impl;

/**
 * Enum that representing the specific goal (attitude) of an exercise.
 * 
 * <p>
 * This classification is used for each exercise to help the system
 * to suggest the best exercise based on the goal.
 * Each exercise can have more than one Attitude
 * (e.g. Wheight lifting could be associeted to weight-loss & muscle-gain)
 */
public enum AttitudeExercise {

    WHEIGHT_LOSS, //Exercise focused on high energy usage
    MUSCLE_GAIN, //Exercise focused on hypertrophy and increase lean body mass.
    WHEIGHT_MAINTENANCE, //Exercise focused to let the user mantaine the physical and metabbolic condition and healt.
    WHEIGHT_GAIN //Exercise focused on general mass increased, could be combined with calori surplus.
}
