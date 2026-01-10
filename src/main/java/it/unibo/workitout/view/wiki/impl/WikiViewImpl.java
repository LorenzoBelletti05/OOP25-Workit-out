package it.unibo.workitout.view.wiki.impl;

import it.unibo.workitout.view.wiki.contracts.WikiView;
import it.unibo.workitout.model.wiki.contracts.WikiContent;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Set;

/**
 * Implementation of Wiki View (To do with JavaFX).
 */
public class WikiViewImpl implements WikiView {

    private static final String FRAME_NAME = "Workit-out Wiki";
    private static final String CLOSE = "Chiudi";
    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;
    private static final int TEXT_SIZE = 18;

    private final JFrame frame = new JFrame(FRAME_NAME);
    private final DefaultListModel<WikiContent> listModel = new DefaultListModel<>();
    private final JList<WikiContent> contentList = new JList<>(listModel);
    private final JTextField searchField = new JTextField(10);

    /**
     * Builds a new wiki interactive view.
     */
    public WikiViewImpl() { 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JPanel canvas = new JPanel(new BorderLayout(10, 10));
        canvas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        final JPanel panelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER));
        final JLabel titleLabel = new JLabel("Wiki - Contenuti Formativi");
        titleLabel.setFont(new Font("Arial", Font.BOLD, TEXT_SIZE));
        panelNorth.add(titleLabel);
        panelNorth.add(new JLabel(" Cerca: "));
        panelNorth.add(searchField);

        final JScrollPane jScrollCenter = new JScrollPane(contentList);

        final JPanel panelSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        final JButton bClose = new JButton(CLOSE);
        panelSouth.add(bClose);

        canvas.add(panelNorth, BorderLayout.NORTH);
        canvas.add(jScrollCenter, BorderLayout.CENTER);
        canvas.add(panelSouth, BorderLayout.SOUTH);

        frame.setContentPane(canvas);
        frame.pack();
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setLocationByPlatform(true);
    }

    /** 
     * Starts the view.
     */ 
    @Override
    public void start() {
        this.frame.setVisible(true);
    }

    /**
     * Update the view.
     */
    @Override
    public void updateContents(final Set<WikiContent> contents) {
        this.listModel.clear();
        for (final WikiContent content : contents) {
            this.listModel.addElement(content);
        }
    }
}
