package it.unibo.workitout.view.workout.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;

import it.unibo.workitout.model.workout.impl.AttitudeExercise;
import it.unibo.workitout.model.workout.impl.Exercise;

public class TestWorkoutView {

    public static void main(String[] args) {
        
        JFrame frame = new JFrame("Test Exercise Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        
        ExerciseViewerImpl viewer = new ExerciseViewerImpl();
        
        List<Exercise> dummyExercises = new ArrayList<>();
        
        //dummyExercises.add(new Exercise("Panca Piana", 5.0, Set.of(AttitudeExercise.MUSCLE_GAIN)));
        //dummyExercises.add(new Exercise("Corsa", 10.0, Set.of(AttitudeExercise.WHEIGHT_LOSS)));
        //dummyExercises.add(new Exercise("Squat", 6.5, Set.of(AttitudeExercise.WHEIGHT_MAINTENANCE)));

        
        viewer.setExercises(dummyExercises);
        
        frame.add(viewer);
        frame.setVisible(true);
    }
    
}
