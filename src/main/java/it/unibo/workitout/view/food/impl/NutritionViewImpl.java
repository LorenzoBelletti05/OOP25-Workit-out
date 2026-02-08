package it.unibo.workitout.view.food.impl;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import it.unibo.workitout.view.food.contracts.NutritionView;
import it.unibo.workitout.controller.food.contracts.NutritionController;
import it.unibo.workitout.model.food.api.Food;

/**
 * View implementation for the nutrition management system.
 */
public final class NutritionViewImpl extends JPanel implements NutritionView {
    private static final long serialVersionUID = 1L;
    private static final int SEARCH_FIELD_COLUMNS = 20;
    private static final int FONT_SIZE = 14;
    private static final int BORDER_SIZE = 10;

    private transient NutritionController controller;
    private final JTable foodTable;
    private final FoodTableModel tableModel;
    private final JLabel summaryLabel;
    private final JTextField searchField;
    private final JButton backButton;

    /**
     * Constructor for NutritionViewImpl.
     */
    public NutritionViewImpl() {
        super(new BorderLayout());

        //Ricerca
        final JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.searchField = new JTextField(SEARCH_FIELD_COLUMNS);
        final JButton searchButton = new JButton("Cerca");
        final JButton highProteinButton = new JButton("Proteici");
        final JButton lowCarbsButton = new JButton("Pochi Carbo");
        final JButton lowFatButton = new JButton("Magri");
        final JButton resetButton = new JButton("Tutti");
        this.backButton = new JButton("Home");

        northPanel.add(new JSeparator(SwingConstants.VERTICAL));
        northPanel.add(new JLabel("Cerca:"));
        northPanel.add(searchField);
        northPanel.add(searchButton);
        northPanel.add(new JSeparator(JSeparator.VERTICAL));
        northPanel.add(highProteinButton);
        northPanel.add(lowCarbsButton);
        northPanel.add(lowFatButton);
        northPanel.add(resetButton);
        northPanel.add(backButton);

        //Tabella
        this.tableModel = new FoodTableModel();
        this.foodTable = new JTable(tableModel);
        final JScrollPane scrollPane = new JScrollPane(foodTable);

        //Riepilogo e percentuali
        final JPanel southPanel = new JPanel(new BorderLayout());
        this.summaryLabel = new JLabel("Caricamento dati...", SwingConstants.CENTER);
        this.summaryLabel.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        this.summaryLabel.setBorder(BorderFactory.createEmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
        southPanel.add(summaryLabel, BorderLayout.CENTER);

        this.add(northPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);

        //LISTENERS
        //Listener per la ricerca
        searchButton.addActionListener(e -> {
            if (this.controller != null) {
                this.controller.searchFood(this.searchField.getText());
            }
        });

        //Listener per il doppio click
        foodTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 2 && controller != null) {
                    final int row = foodTable.getSelectedRow();
                    if (row != -1) {
                        //Converte l'indice se la tabella è ordinata
                        handleFoodSelection(tableModel.getFoodAt(foodTable.convertRowIndexToModel(row)));
                    }
                }
            }
        });

        //Listener per filtri
        highProteinButton.addActionListener(e -> {
            if (this.controller != null) {
                this.controller.filterHighProtein();
            }
        });

        lowCarbsButton.addActionListener(e -> {
            if (this.controller != null) {
                this.controller.filterLowCarbs();
            }
        });

        lowFatButton.addActionListener(e -> {
            if (this.controller != null) {
                this.controller.filterLowFat();
            }
        });

        resetButton.addActionListener(e -> {
            if (this.controller != null) {
                updateTable(this.controller.getAllFoods());
            }
        });
    }

    /**
     * Sets the controller for this view.
     * 
     * @param controller the nutrition controller to be used.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Controller must be shared")
    @Override
    public void setController(final NutritionController controller) {
        this.controller = Objects.requireNonNull(controller);
    }

    //Gestisce l'inserimento dei grammi
    private void handleFoodSelection(final Food food) {
        final String input = JOptionPane.showInputDialog(
            this,
            "Quanti grammi di " + food.getName() + " hai consumato?",
            "Inserimento pasto",
            JOptionPane.QUESTION_MESSAGE
        );

        if (input != null && !input.isEmpty()) {
            try {
                final int grams = Integer.parseInt(input);
                if (grams > 0) {
                    this.controller.addFoodToDailyLog(food, grams);
                } else {
                    JOptionPane.showMessageDialog(this, "Inserisci un numero intero positivo.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } catch (final NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Inserisci un numero intero positivo.",
                 "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "The button must be accessed by controller")
    public JButton getBackButton() {
        return this.backButton;
    }

    @Override
    public void updateTable(final List<Food> foods) {
        tableModel.setFoods(foods);
    }

    @Override
    public void updateSummary(final String text) {
        summaryLabel.setText(text);
    }
}
