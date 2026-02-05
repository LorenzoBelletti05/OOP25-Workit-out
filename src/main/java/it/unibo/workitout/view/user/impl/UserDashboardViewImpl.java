package it.unibo.workitout.view.user.impl;

import javax.swing.JButton;
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
import java.awt.Font;
import java.awt.GridLayout;

public class UserDashboardViewImpl extends JPanel implements UserDashboardView {
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

    private static final Font  TITLE_FONT = new Font("Arial", Font.BOLD, 26);

    public UserDashboardViewImpl() {
        this.setLayout(new BorderLayout());
        dashboardGUI();
    }


    private void dashboardGUI() {
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel(new GridLayout(3,1,0,0));
        JPanel bottomPanel = new JPanel(new GridLayout(1,3,10,0));

        welcomeTitle = new JLabel("Hello!");
        welcomeTitle.setFont(TITLE_FONT);

        bProfile = new JButton ("Profile");

        topPanel.add(welcomeTitle, BorderLayout.CENTER);
        topPanel.add(bProfile, BorderLayout.EAST);
        topPanel.setBorder(new EmptyBorder(0,0,20,0));
        panel.add(topPanel, BorderLayout.NORTH);
        panel.setBorder(new EmptyBorder(10,10,10,10));

        JPanel progressBarPanel = new JPanel(new BorderLayout());
        JPanel caloriesPanel = new JPanel(new BorderLayout());

        progressBarPanel.setBorder(new EmptyBorder(0,50,0,50));
        caloriesBar = new JProgressBar();
        caloriesBar.setStringPainted(true);

        showCalories = new JLabel("0 / 0 kcal", SwingConstants.CENTER);
        showCalories.setBorder(new EmptyBorder(5,0,20,0));

        caloriesPanel.add(caloriesBar, BorderLayout.CENTER);
        caloriesPanel.add(showCalories, BorderLayout.SOUTH);

        JPanel macroPanel = new JPanel(new GridLayout(1,3,0,0));
        lCarbs = new JLabel("Carbs: 0 / 0 g", SwingConstants.CENTER);
        lProteins = new JLabel("Proteins: 0 / 0 g", SwingConstants.CENTER);
        lFats = new JLabel("Fats: 0 / 0 g", SwingConstants.CENTER);

        showCalories.setBorder(new EmptyBorder(10,0,20,0));

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
        
        bottomPanel.setBorder(new EmptyBorder(20,0,0,0));
        panel.add(bottomPanel, BorderLayout.SOUTH);

        this.add(panel, BorderLayout.CENTER);
    }

    @Override
    public void showData(UserManager userManager) {
        if(userManager == null){
            JOptionPane.showMessageDialog(this, "The user manager is not linked", "Error!", JOptionPane.ERROR_MESSAGE);
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
        super.setVisible(status);
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
