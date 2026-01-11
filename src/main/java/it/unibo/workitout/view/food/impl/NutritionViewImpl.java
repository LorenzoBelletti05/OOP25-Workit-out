package it.unibo.workitout.view.food.impl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import it.unibo.workitout.view.food.contracts.NutritionView;
import it.unibo.workitout.model.food.api.Food;
import it.unibo.workitout.model.food.impl.FoodRepository;
import it.unibo.workitout.model.food.impl.DailyLogManager;

public class NutritionViewImpl extends JPanel implements NutritionView {
    private final FoodRepository repository;
    private final DailyLogManager manager;
    private JTextField searchField;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private JLabel summaryLabel;

    public NutritionViewImpl(FoodRepository repository, DailyLogManager manager) {
        this.repository = repository;
        this.manager = manager;

        //Margini layout principale
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Configurazione tabella
        String[] columns = {"Nome", "Kcal(100g)", "Proteine", "Carbo", "Grassi"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //Impedisce all'utente di scrivere nelle celle
            }
        };
        resultTable = new JTable(tableModel);
        resultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Aggiunta sezioni
        this.add(createSearchPanel(), BorderLayout.NORTH);
        this.add(new JScrollPane(resultTable), BorderLayout.CENTER);
        this.add(createSummaryPanel(), BorderLayout.SOUTH);

        //Caricamento dei dati
        updateTable(repository.getAllFoods());
        refreshSummary();
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        searchField = new JTextField(15);
        JButton searchBtn = new JButton("Cerca");
        JButton proteinBtn = new JButton("Proteine");
        JButton lowFatBtn = new JButton ("Low Fat");
        JButton lowCarbBtn = new JButton("Low Carb");

        //Listeners per i tasti
        searchBtn.addActionListener(e -> updateTable(repository.sortByName(searchField.getText())));
        proteinBtn.addActionListener(e -> updateTable(repository.getHighProteinFoods()));
        lowFatBtn.addActionListener(e -> updateTable(repository.getLowFatFoods()));
        lowCarbBtn.addActionListener(e -> updateTable(repository.getLowCarbsFoods()));

        panel.add(new JLabel("Cerca"));
        panel.add(searchField);
        panel.add(searchBtn);
        panel.add(new JSeparator(SwingConstants.VERTICAL));
        panel.add(proteinBtn);
        panel.add(lowFatBtn);
        panel.add(lowCarbBtn);
        return panel;
    }

    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Riepilogo giornaliero"));

        summaryLabel = new JLabel("Nessun dato oggi.");
        //Font provvisorio
        summaryLabel.setFont(new Font("Arial", Font.BOLD, 14));
        summaryLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(summaryLabel, BorderLayout.CENTER);
        return panel;
    }

    @Override
    public void updateTable(List<Food> foods) {
        tableModel.setRowCount(0); //Svuota la tabella
        for (Food f : foods) {
            tableModel.addRow(new Object[]{
                f.getName(),
                String.format("%.1f", f.getKcalPer100g()),
                String.format("%.1f", f.getProteins()),
                String.format("%.1f", f.getCarbs()),
                String.format("%.1f", f.getFats())
            });
        }
    }

    @Override
    public void updateSummary(String summary) {
        summaryLabel.setText(summary);
    }

    //Aggiorna il sommario usando i dati del manager
    private void refreshSummary() {
        String logInfo = manager.getCurrentLog().toString();
        updateSummary(logInfo);
    }
}
