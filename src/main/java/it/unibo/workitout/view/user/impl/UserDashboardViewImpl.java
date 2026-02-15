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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

/**
 * This class is the home page of the GUI.
 */
public final class UserDashboardViewImpl extends JPanel implements UserDashboardView {
    private static final long serialVersionUID = 1L;

    private static final String ARIAL_FONT = "Arial";
    private static final String SEPARATOR = "/";
    private static final String GRAMS = "g";

    private static final Font TITLE_FONT = new Font(ARIAL_FONT, Font.BOLD, 26);
    private static final Font TEXT_FONT = new Font(ARIAL_FONT, Font.BOLD, 18);
    private static final Font MACRO_FONT = new Font(ARIAL_FONT, Font.PLAIN, 18);

    private static final int N_0 = 0;
    private static final int N_10 = 10;
    private static final int N_20 = 20;
    private static final int N_30 = 30;
    private static final int N_40 = 40;
    private static final int N_50 = 50;
    private static final int N_100 = 100;

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

    /**
     * The constructor of the dashboard.
     */
    public UserDashboardViewImpl() {
        this.setLayout(new BorderLayout());
        dashboardGUI();
    }

    /**
     * This method create the dashboard GUI.
     */
    private void dashboardGUI() {
        final JPanel topPanel = new JPanel(new BorderLayout());
        final JPanel centerPanel = new JPanel(new GridLayout(3, 1, 0, 0));
        final JPanel bottomPanel = new JPanel(new GridLayout(1, 3, 10, 0));

        welcomeTitle = new JLabel("Hello!");
        welcomeTitle.setFont(TITLE_FONT);

        bProfile = new JButton("Profile");
        final Dimension bSize = new Dimension(N_100, N_30);
        bProfile.setPreferredSize(bSize);

        topPanel.add(welcomeTitle, BorderLayout.CENTER);
        topPanel.add(bProfile, BorderLayout.EAST);
        topPanel.setBorder(new EmptyBorder(N_0, N_0, N_20, N_0));
        panel.add(topPanel, BorderLayout.NORTH);
        panel.setBorder(new EmptyBorder(N_10, N_10, N_10, N_10));

        final JPanel progressBarPanel = new JPanel(new BorderLayout());
        final JPanel caloriesPanel = new JPanel(new BorderLayout());

        progressBarPanel.setBorder(new EmptyBorder(N_0, N_50, N_0, N_50));
        caloriesBar = new JProgressBar();
        caloriesBar.setPreferredSize(new Dimension(N_100, N_40));
        caloriesBar.setFont(TEXT_FONT);
        caloriesBar.setStringPainted(true);

        showCalories = new JLabel("0 / 0 kcal", SwingConstants.CENTER);
        showCalories.setFont(TEXT_FONT);

        caloriesPanel.add(caloriesBar, BorderLayout.CENTER);
        caloriesPanel.add(showCalories, BorderLayout.SOUTH);

        final JPanel macroPanel = new JPanel(new GridLayout(1, 3, 0, 0));
        lCarbs = new JLabel("Carbs: 0 / 0 g", SwingConstants.CENTER);
        lProteins = new JLabel("Proteins: 0 / 0 g", SwingConstants.CENTER);
        lFats = new JLabel("Fats: 0 / 0 g", SwingConstants.CENTER);

        lCarbs.setFont(MACRO_FONT);
        lProteins.setFont(MACRO_FONT);
        lFats.setFont(MACRO_FONT);

        showCalories.setBorder(new EmptyBorder(N_10, N_0, N_20, N_0));

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

        bFood.setPreferredSize(bSize);
        bInfo.setPreferredSize(bSize);
        bExercise.setPreferredSize(bSize);

        bottomPanel.add(bFood);
        bottomPanel.add(bInfo);
        bottomPanel.add(bExercise);

        bottomPanel.setBorder(new EmptyBorder(N_20, N_0, N_0, N_0));
        panel.add(bottomPanel, BorderLayout.SOUTH);

        this.add(panel, BorderLayout.CENTER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showData(final UserManager userManager) {
        if (userManager == null) {
            JOptionPane.showMessageDialog(this, "The user manager is not linked", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        final String name = userManager.getUserProfile().getName();
        welcomeTitle.setText("Hello " + name + "!");
        final int dailyCal = (int) userManager.getDailyCalories();
        final int consumedCal = (int) userManager.getConsumedCalories();
        caloriesBar.setMaximum(dailyCal);
        caloriesBar.setMinimum(0);
        caloriesBar.setValue(consumedCal);

        showCalories.setText(consumedCal + SEPARATOR + dailyCal + " kcal");

        final NutritionalTarget macroTarget = userManager.getMacronutrients();
        final int consumedCarbs = (int) userManager.getConsumedCarbs();
        final int consumedProteins = (int) userManager.getConsumedProteins();
        final int consumedFats = (int) userManager.getConsumedFats();

        lCarbs.setText("Carbs: " + consumedCarbs + SEPARATOR + (int) macroTarget.getCarbsG() + " " + GRAMS);
        lProteins.setText("Proteins: " + consumedProteins + SEPARATOR + (int) macroTarget.getProteinsG() + " " + GRAMS);
        lFats.setText("Fats: " + consumedFats + SEPARATOR + (int) macroTarget.getFatsG() + " " + GRAMS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addProfileActListener(final ActionListener al) {
        this.bProfile.addActionListener(al);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addFoodActListener(final ActionListener al) {
        this.bFood.addActionListener(al);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addInfoActListener(final ActionListener al) {
        this.bInfo.addActionListener(al);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addExerciseActListener(final ActionListener al) {
        this.bExercise.addActionListener(al);
    }
}
