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
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Set;

/**
 * Implementation of Wiki View (To do with JavaFX).
 */
public class WikiViewImpl implements WikiView {
    private static final String FRAME_NAME = "Workit-out Wiki";
    private static final String LIST = "LIST";
    private static final String DETAIL = "DETAIL";

    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;
    private static final int TEXT_SIZE = 18;

    private final JFrame frame = new JFrame(FRAME_NAME);
    private final CardLayout layout = new CardLayout();
    private final JPanel mainPanel = new JPanel(layout);

    private final DefaultListModel<WikiContent> listModel = new DefaultListModel<>();
    private final JList<WikiContent> contentList = new JList<>(listModel);
    private final JTextField searchField = new JTextField(10);

    private final JLabel detailTitle = new JLabel();
    private final JTextArea detailArea = new JTextArea();
    private final JButton bBack = new JButton("Back");

    /**
     * Builds a new wiki interactive view.
     */
    public WikiViewImpl() { 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setupListView();
        setupDetailView();

        frame.add(mainPanel);
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setLocationByPlatform(true);
    }

    /**
     * Set the ListView.
     */
    private void setupListView() {
        final JPanel listPanel = new JPanel(new BorderLayout(10, 10));
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        final JPanel north = new JPanel(new FlowLayout());
        north.add(new JLabel("Search:"));
        north.add(searchField);

        listPanel.add(north, BorderLayout.NORTH);
        listPanel.add(new JScrollPane(contentList), BorderLayout.CENTER);

        mainPanel.add(listPanel, LIST);
    }

    /**
     * Set the DeatilView.
     */
    private void setupDetailView() {
        final JPanel detailPanel = new JPanel(new BorderLayout(10, 10));
        detailPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        detailTitle.setFont(new Font("Arial", Font.BOLD, TEXT_SIZE));
        detailArea.setEditable(false);
        detailArea.setLineWrap(true);
        detailArea.setWrapStyleWord(true);

        detailPanel.add(detailTitle, BorderLayout.NORTH);
        detailPanel.add(new JScrollPane(detailArea), BorderLayout.CENTER);
        detailPanel.add(bBack, BorderLayout.SOUTH);

        mainPanel.add(detailPanel, DETAIL);
    }

    /** 
     * Starts the view.
     */ 
    @Override
    public void start() {
        this.frame.setVisible(true);
    }

    /**
     * Show the list of contents.
     */
    @Override
    public void showList() { 
        layout.show(mainPanel, LIST); 
    }

    /**
     * Show the detail of contents.
     */
    @Override
    public void showDetail(final String title, final String text) {
        detailTitle.setText(title);
        detailArea.setText(text);
        layout.show(mainPanel, DETAIL);
    }

    /**
     * Update the view.
     */
    @Override
    public void updateContents(final Set<WikiContent> contents) {
        listModel.clear();
        contents.forEach(listModel::addElement);
    }

    /**
     * Listener for the items in the list.
     */
    @Override
    public void addSelectionListener(final java.util.function.Consumer<WikiContent> listener) {
        contentList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && contentList.getSelectedValue() != null) {
                listener.accept(contentList.getSelectedValue());
            }
        });
    }

    /**
     * Back button Listener.
     */
    @Override
    public void addBackListener(final Runnable listener) {
        bBack.addActionListener(e -> listener.run());
    }

    /**
     * Get the serach query string.
     */
    @Override
    public String getSearchQuery() {
        return this.searchField.getText();
    }

    /**
     * Search Listener.
     */
    @Override
    public void addSearchListener(final Runnable action) {
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {

            @Override
            public void changedUpdate(final javax.swing.event.DocumentEvent e) { 
                action.run(); 
            }

            @Override
            public void removeUpdate(final javax.swing.event.DocumentEvent e) { 
                action.run(); 
            }

            @Override
            public void insertUpdate(final javax.swing.event.DocumentEvent e) { 
                action.run(); 
            }
        });
    }

}
