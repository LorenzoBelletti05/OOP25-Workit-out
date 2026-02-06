package it.unibo.workitout.view.workout.contracts;

import javax.swing.JButton;

public interface PlanViewer {

    void close();

    void setVisible(boolean visible);

    JButton getBackButton();

    void setTable();

}
