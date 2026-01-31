package it.unibo.workitout.view.main.contracts;

import javax.swing.JPanel;

/**
 * Interface for Main View.
 */
public interface MainView {
    /**
     * Start the Main Frame.
     */
    void start();

    /**
     * Add a module in the Main Frame.
     * 
     * @param title name of module.
     * @param panel Jpanle of module.
     */
    void addModule(String title, JPanel panel);

    void showView(String string);
}
