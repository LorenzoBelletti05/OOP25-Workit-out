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
import it.unibo.workitout.model.user.model.impl.BMRStrategyChoice;
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
    private final JComboBox<BMRStrategyChoice> strategyCombo = new JComboBox<>(BMRStrategyChoice.values());
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

        secondPanel.add(new JLabel("Sex:"));
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
    public BMRStrategyChoice getBMRStrategyInput() {
        return (BMRStrategyChoice) strategyCombo.getSelectedItem();
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

    @Override
    public void setNameInput(String name) {
        this.nameField.setText(name);
    }

    @Override
    public void setSurnameInput(String surname) {
        this.surnameField.setText(surname);
    }

    @Override
    public void setAgeInput(int age) {
        this.ageField.setText(String.valueOf(age));
    }

    @Override
    public void setHeightInput(double height) {
        this.heightField.setText(String.valueOf(height));
    }

    @Override
    public void setWeightInput(double weight) {
        this.weightField.setText(String.valueOf(weight));
    }

    @Override
    public void setSexInput(Sex sex) {
        this.sexCombo.setSelectedItem(sex);
    }

    @Override
    public void setActivityInput(ActivityLevel activityLevel) {
        this.activityLevelCombo.setSelectedItem(activityLevel);
    }

    @Override
    public void setUserGoalInput(UserGoal userGoal) {
        this.userGoalCombo.setSelectedItem(userGoal);
    }

    @Override
    public void setBMRStrategyInput(String strategy) {
        this.strategyCombo.setSelectedItem(strategy);
    }
}
