package it.unibo.workitout.view.main.impl;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import it.unibo.workitout.view.main.contracts.MainView;

/**
 * Implementation of Main View interface.
 */
public class MainViewImpl extends JFrame implements MainView {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 700;
    private final JTabbedPane tabbedPane = new JTabbedPane();

    /**
     * Builds a new Main view.
     */
    public MainViewImpl() {
        super("Workit-out");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * Start the Main Frame.
     */
    @Override
    public void start() {
        this.setVisible(true);
    }
    
    /**
     * Add a module in the Main Frame.
     */
    @Override
    public void addModule(final String title, final JPanel panel) {
        tabbedPane.addTab(title, panel);
    }
}
