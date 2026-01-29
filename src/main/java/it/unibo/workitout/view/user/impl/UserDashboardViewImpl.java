package it.unibo.workitout.view.user.impl;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import it.unibo.workitout.model.user.model.impl.NutritionalTarget;
import it.unibo.workitout.model.user.model.impl.UserManager;
import it.unibo.workitout.view.user.contracts.UserDashboardView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
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
    private JLabel showCalories;

    private JButton bProfile;
    private JButton bFood;
    private JButton bInfo;
    private JButton bExercise;

    private JProgressBar caloriesBar;
    
    private JLabel lCarbs;
    private JLabel lProteins;
    private JLabel lFats;

    public UserDashboardViewImpl() {
        dashboardGUI();
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setPreferredSize(new Dimension(width / PROPORTION, height / PROPORTION));
        frame.setLocationByPlatform(true);
        frame.pack();
    }


    private void dashboardGUI() {
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel(new GridLayout(3,1,0,0));
        JPanel bottomPanel = new JPanel(new GridLayout(1,3,10,0));

        welcomeTitle = new JLabel("Hello!");
        bProfile = new JButton ("Profile");

        topPanel.add(welcomeTitle, BorderLayout.CENTER);
        topPanel.add(bProfile, BorderLayout.EAST);
        topPanel.setBorder(new EmptyBorder(10,10,10,10));
        panel.add(topPanel, BorderLayout.NORTH);

        JPanel progressBarPanel = new JPanel(new BorderLayout());
        JPanel caloriesPanel = new JPanel(new BorderLayout());

        caloriesBar = new JProgressBar();
        caloriesBar.setStringPainted(true);
        caloriesBar.setPreferredSize(new Dimension(300,30));

        showCalories = new JLabel("0 / 0 kcal", SwingConstants.CENTER);
        showCalories.setBorder(new EmptyBorder(5,0,5,0));

        caloriesPanel.add(caloriesBar, BorderLayout.CENTER);
        caloriesPanel.add(showCalories, BorderLayout.SOUTH);

        JPanel macroPanel = new JPanel(new GridLayout(1,3,0,0));
        lCarbs = new JLabel("Carbs: 0 / 0 g", SwingConstants.CENTER);
        lProteins = new JLabel("Proteins: 0 / 0 g", SwingConstants.CENTER);
        lFats = new JLabel("Fats: 0 / 0 g", SwingConstants.CENTER);

        macroPanel.add(lCarbs);
        macroPanel.add(lProteins);
        macroPanel.add(lFats);
        
        
        progressBarPanel.add(caloriesPanel, BorderLayout.NORTH);
        progressBarPanel.add(macroPanel, BorderLayout.SOUTH);

        centerPanel.add(new JLabel());
        centerPanel.add(progressBarPanel);
        centerPanel.add(new JLabel());
        
        panel.add(centerPanel, BorderLayout.CENTER);

        bFood = new JButton("Food");
        bInfo = new JButton("Info");
        bExercise = new JButton("Exercise");

        bottomPanel.add(bFood);
        bottomPanel.add(bInfo);
        bottomPanel.add(bExercise);
        
        bottomPanel.setBorder(new EmptyBorder(10,10,10,10));
        panel.add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void showData(UserManager userManager) {
        if(userManager == null){
            JOptionPane.showMessageDialog(frame, "The user manager is not linked", "Error!", JOptionPane.ERROR_MESSAGE);
            frame.dispose();
        }
        String name = userManager.getUserProfile().getName();
        welcomeTitle.setText("Hello " + name);
        int dailyCal = (int) userManager.getDailyCalories();
        int consumedCal = 0;
        caloriesBar.setMaximum(dailyCal);
        caloriesBar.setMinimum(0);
        caloriesBar.setValue(consumedCal);
        
        showCalories.setText(consumedCal + " /" + dailyCal + " kcal");

        NutritionalTarget macroTarget = userManager.getMacronutrients();
        double consumedCarbs = 0;
        double consumedProteins = 0;
        double consumedFats = 0;

        lCarbs.setText("Carbs: " + consumedCarbs + " g / " + (int) macroTarget.getCarbsG() + " g");
        lProteins.setText("Proteins: " + consumedProteins + " g / " + (int) macroTarget.getProteinsG() + " g" );
        lFats.setText("Fats: " + consumedFats +" g / " + (int) macroTarget.getFatsG() + " g");
    }


    @Override
    public void setVisible(boolean status) {
        frame.setVisible(status);
    }

    public JButton getProfileButton() {
        return bProfile;
    }

    public JButton getFoodButton() {
        return bFood;
    }

    public JButton getInfoButton() {
        return bInfo;
    }

    public JButton getExerciseButton() {
        return bExercise;
    }
}
