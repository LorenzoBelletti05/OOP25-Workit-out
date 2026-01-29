package it.unibo.workitout.view.workout.impl;

import javax.swing.JFrame;

public class TestWorkoutView {

    public static void main(String[] args) {
        
        JFrame frame = new JFrame("Test Exercise Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        
        ExerciseViewerImpl viewer = new ExerciseViewerImpl();
        PlanViewerImpl planViewr = new PlanViewerImpl();  

        frame.add(viewer);
        //frame.add(planViewr);
        frame.setVisible(true);
        
        
    }
    
}
