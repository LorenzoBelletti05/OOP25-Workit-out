package it.unibo.workitout.view.workout.contracts;

import java.util.List;

import it.unibo.workitout.model.workout.impl.Exercise;

/**
 * Interface of exercise viewr with two method.
 */
public interface ExerciseViewer {

    /**
     * Set the table of the all raw exercises took from the JSON file.
     * 
     * @param exercises the list with all the exercises.
     * 
     */
    void setExercises(List<Exercise> exercises);

    /**
     * Take a specific exercise based on where and which the user click.
     * 
     * @return the int value of the selected row.
     * 
     */
    int getExercise();

}
