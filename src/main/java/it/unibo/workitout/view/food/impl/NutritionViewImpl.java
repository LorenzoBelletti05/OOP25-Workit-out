package it.unibo.workitout.view.food.impl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import it.unibo.workitout.view.food.contracts.NutritionView;
import it.unibo.workitout.controller.food.contracts.NutritionController;
import it.unibo.workitout.model.food.api.Food;


public class NutritionViewImpl extends JPanel implements NutritionView {
    private NutritionController controller; 
    private final JTable foodTable;
    private final FoodTableModel tableModel;
    private final JLabel summaryLabel;
    private final JTextField searchField;

    public NutritionViewImpl() {
        this.setLayout(new BorderLayout());

        //Ricerca
        final JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.searchField = new JTextField(20);
        final JButton searchButton = new JButton("Cerca");
        final JButton highProteinButton = new JButton("Proteici");
        final JButton lowCarbsButton = new JButton("Pochi Carbo");
        final JButton lowFatButton = new JButton("Magri");
        final JButton resetButton = new JButton("Tutti");

        northPanel.add(new JLabel("Cerca:"));
        northPanel.add(searchField);
        northPanel.add(searchButton);
        northPanel.add(new JSeparator(JSeparator.VERTICAL));
        northPanel.add(highProteinButton);
        northPanel.add(lowCarbsButton);
        northPanel.add(lowFatButton);
        northPanel.add(resetButton);


        //Tabella
        this.tableModel = new FoodTableModel();
        this.foodTable = new JTable(tableModel);
        final JScrollPane scrollPane = new JScrollPane(foodTable);

        //Riepilogo e percentuali
        final JPanel southPanel = new JPanel(new BorderLayout());
        this.summaryLabel = new JLabel("Caricamento dati...", SwingConstants.CENTER);
        this.summaryLabel.setFont(new Font("Arial", Font.BOLD, 14));
        this.summaryLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        southPanel.add(summaryLabel, BorderLayout.CENTER);

        this.add(northPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);

        //LISTENERS
        //Listener per la ricerca
        searchButton.addActionListener(e -> {
            if (controller != null) {
                controller.searchFood(searchField.getText());
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
                        final int modelRow = foodTable.convertRowIndexToModel(row);
                        final Food selectedFood = tableModel.getFoodAt(modelRow);
                        handleFoodSelection(selectedFood);
                    }
                }
            }
        });

        //Listener per filtri
        highProteinButton.addActionListener(e -> {
            if (controller != null) {
                controller.filterHighProtein();
            }
        });

        lowCarbsButton.addActionListener(e -> {
            if (controller != null) {
                controller.filterLowCarbs();
            }
        });

        lowFatButton.addActionListener(e -> {
            if (controller != null) {
                controller.filterLowFat();
            }
        });

        resetButton.addActionListener(e -> {
            if (controller != null) {
                updateTable(controller.getAllFoods());
            }
        });
    }

    public void setController(final NutritionController controller) {
        this.controller = controller;
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
                if (grams <= 0) {
                    throw new NumberFormatException();
                }
                controller.addFoodToDailyLog(food, grams);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Inserisci un numero intero positivo.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
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
