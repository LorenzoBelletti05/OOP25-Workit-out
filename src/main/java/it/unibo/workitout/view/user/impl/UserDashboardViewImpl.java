package it.unibo.workitout.view.user.impl;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import it.unibo.workitout.model.user.model.impl.UserManager;
import it.unibo.workitout.view.user.contracts.UserDashboardView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

public class UserDashboardViewImpl implements UserDashboardView {
    private static final String FRAME_NAME = "Workit-out User (Dashboard)";
    private static final int PROPORTION = 2;

    final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    final int width = (int) screen.getWidth();
    final int height = (int) screen.getHeight();

    private final JFrame frame = new JFrame(FRAME_NAME);
    private final JPanel panel = new JPanel(new BorderLayout());

    private JLabel welcomeTitle;

    private JButton bProfile;
    private JButton bFood;
    private JButton bInfo;
    private JButton bExercise;

    private JProgressBar caloriesBar;

    public UserDashboardViewImpl() {
        dashboardGUI();
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display();
    }


    private void dashboardGUI() {
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel(new BorderLayout());
        JPanel bottomPanel = new JPanel(new BorderLayout());

        welcomeTitle = new JLabel("Hello!");
        bProfile = new JButton ("Profile");

        topPanel.add(welcomeTitle, BorderLayout.CENTER);
        topPanel.add(bProfile, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);

        caloriesBar = new JProgressBar();
        caloriesBar.setStringPainted(true);

        centerPanel.add(caloriesBar, BorderLayout.CENTER);
        centerPanel.setPreferredSize(new Dimension(width / PROPORTION*20, height / PROPORTION*60));
        
        panel.add(centerPanel, BorderLayout.CENTER);

        bFood = new JButton("Food");
        bInfo = new JButton("Info");
        bExercise = new JButton("Exercise");

        bottomPanel.add(bFood, BorderLayout.WEST);
        bottomPanel.add(bInfo, BorderLayout.CENTER);
        bottomPanel.add(bExercise, BorderLayout.EAST);

        panel.add(bottomPanel, BorderLayout.SOUTH);
    }


    private void display() {
        panel.setPreferredSize(new Dimension(width / PROPORTION, height / PROPORTION));
        frame.setLocationByPlatform(true);
        frame.pack();
        frame.setVisible(true);
    }


    @Override
    public void showData(UserManager userManager) {
        if(userManager == null){
            JOptionPane.showMessageDialog(frame, "The user manager is not linked", "Error!", JOptionPane.ERROR_MESSAGE);
            frame.dispose();
        }
        int dailyCal = (int) userManager.getDailyCalories();
        caloriesBar.setMaximum(dailyCal);
        caloriesBar.setMinimum(0);
        caloriesBar.setString("" + dailyCal + "kcal");
    }


    @Override
    public void setVisible(boolean status) {
        frame.setVisible(status);
    }
}
