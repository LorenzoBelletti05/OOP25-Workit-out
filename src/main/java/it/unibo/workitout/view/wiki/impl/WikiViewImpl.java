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
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;
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
public final class WikiViewImpl extends JPanel implements WikiView {
    //constants
    private static final long serialVersionUID = 1L;
    private static final String LIST = "LIST";
    private static final String DETAIL = "DETAIL";
    private static final String FONT_FAMILY = "Serif";
    private static final int TEXT_SIZE = 18;
    private static final int BODY_SIZE = 18;
    //components
    private final CardLayout layout = new CardLayout();
    private final JPanel mainPanel = new JPanel(layout);
    //list
    private final DefaultListModel<WikiContent> listModel = new DefaultListModel<>();
    private final JList<WikiContent> contentList = new JList<>(listModel);
    //search
    private final JTextField searchField = new JTextField(10);
    //detail
    private final JLabel detailTitle = new JLabel();
    private final JTextArea detailArea = new JTextArea();
    //buttons
    private final JButton bBack = new JButton("Back");
    private final JButton bAll = new JButton("Tutti");
    private final JButton bArticles = new JButton("Articoli");
    private final JButton bVideos = new JButton("Video");
    private final JButton bPrioFood = new JButton("Cibo");
    private final JButton bPrioEx = new JButton("Esercizi");
    private final JButton backButtonView = new JButton("Back");
    private final JLabel feedbackLabel = new JLabel("");

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
        //list panel
        final JPanel listPanel = new JPanel(new BorderLayout(10, 10));
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //north panel
        final JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        //south panel
        final JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(backButtonView);
        listPanel.add(southPanel, BorderLayout.SOUTH);
        //row1
        final JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row1.add(new JLabel("Cerca:"));
        row1.add(searchField);
        row1.add(bAll);
        row1.add(bArticles);
        row1.add(bVideos);
        //row2
        final JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row2.add(new JLabel("Priorità in base ai tuoi dati:"));
        row2.add(bPrioFood);
        row2.add(bPrioEx);
        //row3
        feedbackLabel.setFont(new Font(FONT_FAMILY, Font.BOLD, BODY_SIZE));
        final JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row3.add(feedbackLabel);
        //add rows to north panel
        northPanel.add(row1);
        northPanel.add(row2);
        northPanel.add(row3);
        //set cells
        contentList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                final JList<?> list, 
                final Object value, 
                final int index, 
                final boolean isSelected, 
                final boolean cellHasFocus) {
                    //set label
                    final JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, 
                        value, 
                        index, 
                        isSelected, 
                        cellHasFocus
                    );
                    final WikiContent content = (WikiContent) value;
                    //set text
                    if (content.isVideo()) {
                        label.setText("<html><b>[VIDEO] </b><b>" 
                            + content.getTitle() + "</b><br>" + content.getText() + "</html>");
                    } else {
                        label.setText("<html><b>[ARTICOLO] </b>" 
                            + content.getTitle() + "</html>");
                    }
                    return label;
                }
            }
        );
        //add components to listpanel
        listPanel.add(northPanel, BorderLayout.NORTH);
        listPanel.add(new JScrollPane(contentList), BorderLayout.CENTER);
        mainPanel.add(listPanel, LIST);
    }

    /**
     * Set the DetailView.
     */
    private void setupDetailView() {
        final JPanel detailPanel = new JPanel(new BorderLayout(10, 10));
        detailPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //title
        detailTitle.setFont(new Font(FONT_FAMILY, Font.BOLD, TEXT_SIZE));
        //area
        detailArea.setEditable(false);
        detailArea.setLineWrap(true);
        detailArea.setWrapStyleWord(true);
        detailArea.setFont(new Font(FONT_FAMILY, Font.PLAIN, BODY_SIZE));
        detailArea.setMargin(new Insets(10, 10, 10, 10));
        //add to detailpanel
        detailPanel.add(detailTitle, BorderLayout.NORTH);
        detailPanel.add(new JScrollPane(detailArea), BorderLayout.CENTER);
        //south panel
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
     * Get the search query string.
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
            if (Desktop.isDesktopSupported() 
                && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Browser non supportato.",
                    "Errore Browser", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (final IOException | URISyntaxException e) {
            JOptionPane.showMessageDialog(
                this, 
                "Impossibile aprire il video.\nURL: " + url, 
                "Errore Browser", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Open the main view.
     */
    @Override
    public void addMainBackListener(final ActionListener listener) {
        this.backButtonView.addActionListener(listener);
    }

    /**
     * Update the label with filter info.
     * 
     * @param message the message.
     */
    @Override
    public void updateLabel(final String message) {
        this.feedbackLabel.setText(message);
    }
}
