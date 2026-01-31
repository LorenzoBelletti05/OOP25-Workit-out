package it.unibo.workitout.view.workout.impl;

import javax.swing.JFrame;

public class TestWorkoutView {

    public static void main(String[] args) {       
        
        PlanViewerImpl planViewr = new PlanViewerImpl();  

        planViewr.setTitle("Test Exercise Viewer");
        planViewr.setSize(600, 400);
        planViewr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        planViewr.setVisible(true);
        
        
    }
    
}
