package it.unibo.workitout.view.workout.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;


import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.model.workout.impl.Exercise;
import it.unibo.workitout.model.workout.impl.ExerciseType;

public class TestWorkoutView {

    public static void main(String[] args) {
        
        JFrame frame = new JFrame("Test Exercise Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        
        ExerciseViewerImpl viewer = new ExerciseViewerImpl();
        PlanViewerImpl planViewr = new PlanViewerImpl();
        
        List<Exercise> dummyExercises = new ArrayList<>();
        
        dummyExercises.add(new Exercise("Panca Piana", 5.0, Set.of(UserGoal.BUILD_MUSCLE), ExerciseType.STRENGTH));
        dummyExercises.add(new Exercise("Corsa", 10.0, Set.of(UserGoal.LOSE_WEIGHT), ExerciseType.CARDIO));
        dummyExercises.add(new Exercise("Squat", 6.5, Set.of(UserGoal.MAINTAIN_WEIGHT), ExerciseType.STRENGTH));

        
        viewer.setExercises(dummyExercises);
        
        frame.add(viewer);
        frame.add(planViewr);
        frame.setVisible(true);
        
        
    }
    
}
