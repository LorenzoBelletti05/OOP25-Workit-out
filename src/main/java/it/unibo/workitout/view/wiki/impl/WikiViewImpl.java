package it.unibo.workitout.view.wiki.impl;

import it.unibo.workitout.view.wiki.contracts.WikiView;
import it.unibo.workitout.model.wiki.contracts.WikiContent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Implementation of Wiki View.
 */
public class WikiViewImpl extends JPanel implements WikiView {
    private static final long serialVersionUID = 1L;
    private static final String LIST = "LIST";
    private static final String DETAIL = "DETAIL";
    private static final int TEXT_SIZE = 18;
    private static final int BODY_SIZE = 18;

    private final CardLayout layout = new CardLayout();
    private final JPanel mainPanel = new JPanel(layout);

    private final DefaultListModel<WikiContent> listModel = new DefaultListModel<>();
    private final JList<WikiContent> contentList = new JList<>(listModel);
    private final JTextField searchField = new JTextField(10);

    private final JLabel detailTitle = new JLabel();
    private final JTextArea detailArea = new JTextArea();

    private final JButton bBack = new JButton("Back");
    private final JButton bAll = new JButton("Tutti");
    private final JButton bArticles = new JButton("Articoli");
    private final JButton bVideos = new JButton("Video");
    private final JButton bPrioFood = new JButton("Cibo");
    private final JButton bPrioEx = new JButton("Esercizi");

    /**
     * Builds a new wiki interactive view.
     */
    public WikiViewImpl() { 
        super.setLayout(new BorderLayout());
        setupListView();
        setupDetailView();
        super.add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Set the ListView.
     */
    private void setupListView() {
        final JPanel listPanel = new JPanel(new BorderLayout(10, 10));
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        final JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));

        final JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row1.add(new JLabel("Cerca:"));
        row1.add(searchField);
        row1.add(bAll);
        row1.add(bArticles);
        row1.add(bVideos);

        final JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row2.add(new JLabel("Priorità in base ai tuoi dati:"));
        row2.add(bPrioFood);
        row2.add(bPrioEx);

        northPanel.add(row1);
        northPanel.add(row2);

        contentList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index, 
                                                        final boolean isSelected, final boolean cellHasFocus) {
                final JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                final WikiContent content = (WikiContent) value;

                if (content.isVideo()) {
                   label.setText("<html><b>[VIDEO] </b><b>" + content.getTitle() + "</b><br>" + content.getText() + "</html>");
                } else {
                   label.setText("<html><b>[ARTICOLO] </b>" + content.getTitle() + "</html>");
                }
                return label;
            }
        });

        listPanel.add(northPanel, BorderLayout.NORTH);
        listPanel.add(new JScrollPane(contentList), BorderLayout.CENTER);
        mainPanel.add(listPanel, LIST);
    }

    /**
     * Set the DeatailView.
     */
    private void setupDetailView() {
        final JPanel detailPanel = new JPanel(new BorderLayout(10, 10));
        detailPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        detailTitle.setFont(new Font("Serif", Font.BOLD, TEXT_SIZE));

        detailArea.setEditable(false);
        detailArea.setLineWrap(true);
        detailArea.setWrapStyleWord(true);
        detailArea.setFont(new Font("Serif", Font.PLAIN, BODY_SIZE));
        detailArea.setMargin(new Insets(10, 10, 10, 10));

        detailPanel.add(detailTitle, BorderLayout.NORTH);
        detailPanel.add(new JScrollPane(detailArea), BorderLayout.CENTER);
        final JPanel southJPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southJPanel.add(bBack);
        detailPanel.add(southJPanel, BorderLayout.SOUTH);

        mainPanel.add(detailPanel, DETAIL);
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
        final DefaultListModel<WikiContent> newModel = new DefaultListModel<>();
        contents.forEach(newModel::addElement);
        contentList.setModel(newModel);
    }

    /**
     * Listener for the items in the list.
     */
    @Override
    public void addSelectionListener(final Consumer<WikiContent> listener) {
        contentList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int index = contentList.locationToIndex(e.getPoint());
                if (index >= 0 && contentList.getCellBounds(index, index).contains(e.getPoint())) {
                    final WikiContent selected = contentList.getModel().getElementAt(index);
                    listener.accept(selected);
                    contentList.clearSelection();
                }
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
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(final DocumentEvent e) { 
                action.run(); 
            }

            @Override
            public void removeUpdate(final DocumentEvent e) { 
                action.run(); 
            }

            @Override
            public void insertUpdate(final DocumentEvent e) { 
                action.run(); 
            }
        });
    }

    /**
     * Listener for Show all filters.
     */
    @Override
    public void addAllFilterListener(final Runnable action) {
        this.bAll.addActionListener(e -> action.run());
    }

    /**
     * Listener for articles.
     */
    @Override
    public void addArticlesFilterListener(final Runnable action) {
        this.bArticles.addActionListener(e -> action.run());
    }

    /**
     * Listener for videos.
     */
    @Override
    public void addVideosFilterListener(final Runnable action) {
        this.bVideos.addActionListener(e -> action.run());
    }

    /**
     * Listener for food priority.
     */
    @Override
    public void addPrioFoodListener(final Runnable action) {
        this.bPrioFood.addActionListener(e -> action.run());
    }

    /**
     * Listener for exercise priority.
     */
    @Override
    public void addPrioExerciseListener(final Runnable action) {
        this.bPrioEx.addActionListener(e -> action.run());
    }

    /**
     * Open video in the browser.
     */
    @Override
    public void showVideoPlayer(final String url) throws URISyntaxException {
        try {
            if (java.awt.Desktop.isDesktopSupported() 
                && java.awt.Desktop.getDesktop().isSupported(java.awt.Desktop.Action.BROWSE)) {
                java.awt.Desktop.getDesktop().browse(new URI(url));
            } else {
                JOptionPane.showMessageDialog(this, 
                "Browser non supportato.", "Errore Browser", 
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (final IOException | URISyntaxException e) {
            JOptionPane.showMessageDialog(this, 
                "Impossibile aprire il video.\nURL: " + url, "Errore Browser", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
