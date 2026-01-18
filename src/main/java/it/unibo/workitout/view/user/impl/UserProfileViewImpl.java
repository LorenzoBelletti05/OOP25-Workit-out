package it.unibo.workitout.view.user.impl;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.unibo.workitout.view.user.contracts.UserProfileView;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * The constructor for first log-in GUI.
 */
public class UserProfileViewImpl implements UserProfileView {
    private static final String FRAME_NAME = "Workit-out User (First log-in)";
    private static final int PROPORTION = 2;
    
    private final JFrame frame = new JFrame(FRAME_NAME);
    private final JPanel panel = new JPanel();
    private final JTextField nameField = new JTextField();
    private final JTextField surnameField = new JTextField();
    private final JTextField ageField = new JTextField();
    private final JTextField heightField = new JTextField();
    private final JTextField weightField = new JTextField();

    UserProfileViewImpl() {
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        profileGUI();
        display();
    }

    private void profileGUI() {
        panel.setLayout(new BorderLayout());
        JPanel secondPanel = new JPanel(new GridLayout(0, 2));

        secondPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 30, 50));
        secondPanel.add(new JLabel("Name: "));
        secondPanel.add(nameField);
        secondPanel.add(new JLabel("Surname: "));
        secondPanel.add(surnameField);
        secondPanel.add(new JLabel("Age: "));
        secondPanel.add(ageField);
        secondPanel.add(new JLabel("Height: "));
        secondPanel.add(heightField);
        secondPanel.add(new JLabel("Weight: "));
        secondPanel.add(weightField);

        panel.add(secondPanel, BorderLayout.NORTH);
    }

    private void display() {
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int width = (int) screen.getWidth();
        final int height = (int) screen.getHeight();

        panel.setPreferredSize(new Dimension(width / PROPORTION, height / PROPORTION));
        frame.setLocationByPlatform(true);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new UserProfileViewImpl();
    }
}
