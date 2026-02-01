package it.unibo.workitout.view.main.impl;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.Dimension;
import java.awt.Toolkit;

import it.unibo.workitout.view.main.contracts.MainView;

/**
 * Implementation of Main View interface.
 */
public class MainViewImpl extends JFrame implements MainView {
    private static final String WIKI = "WIKI";

    private final JTabbedPane tabbedPane = new JTabbedPane();

    private final JPanel mainPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    /**
     * Builds a new Main view.
     */
    public MainViewImpl() {
        super("Workit-out");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int width = (int) (screen.getWidth() * 0.3);
        final int height = (int) (screen.getHeight() * 0.4);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);

        mainPanel.setLayout(cardLayout);
        mainPanel.add(tabbedPane, WIKI);
        this.getContentPane().add(mainPanel, BorderLayout.CENTER);
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
        if(title.equals(WIKI)){
            tabbedPane.addTab(title, panel);
        } else {
            mainPanel.add(panel, title);
        }
    }

    public void showView(String name) {
        cardLayout.show(mainPanel, name);
    }
}
