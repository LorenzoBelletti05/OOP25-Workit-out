package it.unibo.workitout.view.user.impl;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.unibo.workitout.controller.user.contracts.UserProfileController;
import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.BMRStrategyChoise;
import it.unibo.workitout.model.user.model.impl.Sex;
import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.view.user.contracts.UserProfileView;


import java.awt.BorderLayout;
import java.awt.GridLayout;

/**
 * The constructor for first log-in GUI.
 */
public class UserProfileViewImpl extends JPanel implements UserProfileView {
    private final JPanel panel = new JPanel();
    private final JTextField nameField = new JTextField();
    private final JTextField surnameField = new JTextField();
    private final JTextField ageField = new JTextField();
    private final JTextField heightField = new JTextField();
    private final JTextField weightField = new JTextField();
    private final JComboBox<Sex> sexCombo = new JComboBox<>(Sex.values());
    private final JComboBox<ActivityLevel> activityLevelCombo = new JComboBox<>(ActivityLevel.values());
    private final JComboBox<UserGoal> userGoalCombo = new JComboBox<>(UserGoal.values());
    private final JComboBox<BMRStrategyChoise> strategyCombo = new JComboBox<>(BMRStrategyChoise.values());
    private final JButton calculateButton = new JButton("Save");
    private final JButton backButton = new JButton("Back");

    private UserProfileController controller = null;

    public UserProfileViewImpl() {
        this.setLayout(new BorderLayout());
        profileGUI();
        this.add(panel, BorderLayout.CENTER);
    }

    private void profileGUI() {
        panel.setLayout(new BorderLayout());
        JPanel secondPanel = new JPanel(new GridLayout(0, 2, 10, 10));

        secondPanel.setBorder(BorderFactory.createEmptyBorder(10, 90, 30, 90));
        secondPanel.add(new JLabel("Name:"));
        secondPanel.add(nameField);
        secondPanel.add(new JLabel("Surname:"));
        secondPanel.add(surnameField);
        secondPanel.add(new JLabel("Age:"));
        secondPanel.add(ageField);
        secondPanel.add(new JLabel("Height:"));
        secondPanel.add(heightField);
        secondPanel.add(new JLabel("Weight:"));
        secondPanel.add(weightField);

        secondPanel.add(new JLabel("Sesso:"));
        secondPanel.add(sexCombo);
        secondPanel.add(new JLabel("Activity Level:"));
        secondPanel.add(activityLevelCombo);
        secondPanel.add(new JLabel("User Goal:"));
        secondPanel.add(userGoalCombo);
        secondPanel.add(new JLabel("Calculate BMR with:"));
        secondPanel.add(strategyCombo);

        panel.add(secondPanel, BorderLayout.NORTH);

        JPanel calculatePanel = new JPanel();
        calculatePanel.add(calculateButton);
        backButton.setEnabled(false);
        calculatePanel.add(backButton);
        panel.add(calculatePanel, BorderLayout.SOUTH);

        calculateButton.addActionListener (al -> {
            if (controller != null) {
                controller.calculateProfile();
            } else {
                showErrorController("Controller not linked ");
            }
        });
    }

    private void showErrorController(String errorDescription) {
        JOptionPane.showMessageDialog(this, errorDescription, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public JButton getBackButton() {
        return backButton;
    }

    @Override
    public String getNameInput() {
        return nameField.getText();
    }

    @Override
    public String getSurnameInput() {
        return surnameField.getText();
    }

    @Override
    public String getAgeInput() {
        return ageField.getText();
    }

    @Override
    public String getHeightInput() {
        return heightField.getText();
    }

    @Override
    public String getWeightInput() {
        return weightField.getText();
    }

    @Override
    public Sex getSexInput() {
        return (Sex) sexCombo.getSelectedItem();
    }

    @Override
    public ActivityLevel getActivityInput() {
        return (ActivityLevel) activityLevelCombo.getSelectedItem();
    }

    @Override
    public UserGoal UserGoalInput() {
        return (UserGoal) userGoalCombo.getSelectedItem();
    }

    @Override
    public BMRStrategyChoise getBMRStrategyInput() {
        return (BMRStrategyChoise) strategyCombo.getSelectedItem();
    }

    @Override
    public void setController(UserProfileController controller) {
        this.controller = controller;
    }

    @Override
    public void close() {
        this.setVisible(false);
    }

    @Override
    public void setBackButton(boolean visible) {
        this.backButton.setEnabled(visible);
    }
}
