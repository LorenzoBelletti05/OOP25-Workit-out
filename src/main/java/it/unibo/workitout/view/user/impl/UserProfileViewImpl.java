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
 * The class is the first log-in and profile GUI.
 */
public final class UserProfileViewImpl extends JPanel implements UserProfileView {
    private static final long serialVersionUID = 1L;
    private static final int N_0 = 0;
    private static final int N_1 = 1;
    private static final int N_2 = 2;
    private static final int N_10 = 10;
    private static final int N_30 = 30;
    private static final int N_90 = 90;

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

    private transient UserProfileController controller;

    /**
     * Constructs the UserProfileViewImpl GUI.
     */
    public UserProfileViewImpl() {
        this.setLayout(new BorderLayout());
        profileGUI();
        this.add(panel, BorderLayout.CENTER);
    }

    /**
     * Create the GUI with components and layout.
     */
    private void profileGUI() {
        panel.setLayout(new BorderLayout());
        final JPanel secondPanel = new JPanel(new GridLayout(N_0, N_2, N_10, N_10));

        secondPanel.setBorder(BorderFactory.createEmptyBorder(N_10, N_90, N_30, N_90));
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

        final JPanel calculatePanel = new JPanel();
        calculatePanel.add(calculateButton);
        backButton.setEnabled(false);
        calculatePanel.add(backButton);
        panel.add(calculatePanel, BorderLayout.SOUTH);

        calculateButton.addActionListener(al -> {
            if (controller != null) {
                controller.calculateProfile();
            } else {
                showErrorController("Controller not linked ");
            }
        });
    }

    /**
     * Method for display an error message.
     * 
     * @param errorDescription the text description of the error
     */
    private void showErrorController(final String errorDescription) {
        JOptionPane.showMessageDialog(this, errorDescription, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JButton getBackButton() {
        return backButton;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNameInput() {
        return nameField.getText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSurnameInput() {
        return surnameField.getText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAgeInput() {
        return ageField.getText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHeightInput() {
        return heightField.getText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getWeightInput() {
        return weightField.getText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sex getSexInput() {
        return (Sex) sexCombo.getSelectedItem();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActivityLevel getActivityInput() {
        return (ActivityLevel) activityLevelCombo.getSelectedItem();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserGoal getUserGoalInput() {
        return (UserGoal) userGoalCombo.getSelectedItem();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BMRStrategyChoice getBMRStrategyInput() {
        return (BMRStrategyChoice) strategyCombo.getSelectedItem();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setController(final UserProfileController controller) {
        this.controller = controller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBackButton(final boolean visible) {
        this.backButton.setEnabled(visible);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNameInput(final String name) {
        this.nameField.setText(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSurnameInput(final String surname) {
        this.surnameField.setText(surname);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAgeInput(final int age) {
        this.ageField.setText(String.valueOf(age));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHeightInput(final double height) {
        this.heightField.setText(String.valueOf(height));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWeightInput(final double weight) {
        this.weightField.setText(String.valueOf(weight));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSexInput(final Sex sex) {
        this.sexCombo.setSelectedItem(sex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActivityInput(final ActivityLevel activityLevel) {
        this.activityLevelCombo.setSelectedItem(activityLevel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUserGoalInput(final UserGoal userGoal) {
        this.userGoalCombo.setSelectedItem(userGoal);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBMRStrategyInput(final String strategy) {
        if ("MifflinStJeorStrategy".equals(strategy)) {
        this.strategyCombo.setSelectedIndex(N_0);
        } else if ("HarrisBenedictStrategy".equals(strategy)) {
            this.strategyCombo.setSelectedIndex(N_1);
        }
    }
}
