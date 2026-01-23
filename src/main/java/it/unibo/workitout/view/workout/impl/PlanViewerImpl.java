package it.unibo.workitout.view.workout.impl;

import it.unibo.workitout.view.workout.contracts.PlanViewer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Graphical {@link DrawNumberView} implementation.
 */
public final class PlanViewerImpl implements PlanViewer {

    private static final String FRAME_NAME = "";   

   
    private final JFrame frame = new JFrame(FRAME_NAME);

    /**
     * Constructor.
     */
    public PlanViewerImpl() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JPanel(new BorderLayout()));
        final JPanel pNorth = new JPanel(new FlowLayout());
        final JTextField tNumber = new JTextField(10);
        
        pNorth.add(tNumber);
        
        final JPanel pSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        
        frame.getContentPane().add(pNorth, BorderLayout.NORTH);
        frame.getContentPane().add(pSouth, BorderLayout.SOUTH);
        
        
        

        frame.pack();
        frame.setLocationByPlatform(true);
    }

    @Override
    public void start() {
        this.frame.setVisible(true);
    }

    // public void main(String[] args) {
    //     new PlanViewImpl();
    // }

    

}
