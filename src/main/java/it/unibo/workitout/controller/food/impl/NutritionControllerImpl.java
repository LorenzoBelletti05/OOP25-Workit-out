package it.unibo.workitout.controller.food.impl;

import it.unibo.workitout.controller.food.contracts.NutritionController;
import it.unibo.workitout.model.food.impl.FoodRepository;
import it.unibo.workitout.model.main.datamanipulation.LoadSaveData;
import it.unibo.workitout.model.food.impl.DailyLogManager;
import it.unibo.workitout.view.food.contracts.NutritionView;
import it.unibo.workitout.model.food.api.DailyLog;
import it.unibo.workitout.model.food.api.Food;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;
import java.util.Objects;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Implementation of NutritionController.
 */
public final class NutritionControllerImpl implements NutritionController {
    private static final int MAX_GRAMS = 2000;
    private final FoodRepository repository;
    private final DailyLogManager logManager;
    private final NutritionView view;
    private final Runnable goToDashboard;

    /**
     * @param repository the food database.
     * @param logManager the manager for daily logs.
     * @param view the user interface.
     * @param goToDashboard the back button.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Repository is shared and managed externally")
    public NutritionControllerImpl(final FoodRepository repository,
                                   final DailyLogManager logManager,
                                   final NutritionView view,
                                   final Runnable goToDashboard) {
        this.repository = Objects.requireNonNull(repository);
        this.logManager = Objects.requireNonNull(logManager);
        this.view = Objects.requireNonNull(view);
        this.goToDashboard = Objects.requireNonNull(goToDashboard);
        this.view.getBackButton().addActionListener(al -> {
            this.goToDashboard.run();
        });
    }

    @Override
    public void start() {
        final String foodsPath = LoadSaveData.createPath(LoadSaveData.FOODS_FILE);
        final String historyPath = LoadSaveData.createPath(LoadSaveData.HISTORY_FILE);
        repository.loadFromFile(foodsPath);
        logManager.loadHistory(historyPath, repository);

        view.updateTable(repository.getAllFoods());
        refreshViewSummary();
    }

    @Override
    public void searchFood(final String query) {
        if (query == null || query.isEmpty()) {
            view.updateTable(repository.getAllFoods());
        } else {
            view.updateTable(repository.sortByName(query));
        }
    }

    @Override
    public void addFoodToDailyLog(final Food food, final int grams) {
        //Controllo dei limiti
        if (grams <= 0 || grams > MAX_GRAMS) {
            return;
        }

        logManager.getCurrentLog().addFoodEntry(food, grams);
        final String historyPath = LoadSaveData.createPath(LoadSaveData.HISTORY_FILE);
        logManager.saveHistory(historyPath);
        saveTotalsForUser();
        refreshViewSummary();
    }

    /**
     * Helper to export data for the User module.
     */
    private void saveTotalsForUser() {
        final DailyLog today = logManager.getCurrentLog();
        final String date = java.time.LocalDate.now().toString();
        final String statsRow = String.format("%s,%d,%d,%d,%d",
            date,
            (int) today.getTotalKcal(),
            (int) today.getTotalProteins(),
            (int) today.getTotalCarbs(),
            (int) today.getTotalFats()
        );

        final String statsPath = LoadSaveData.createPath(LoadSaveData.STATS_FILE);
        updateDailyStatsFile(statsPath, statsRow, date);
    }

    private void updateDailyStatsFile(final String path, final String newRow, final String date) {
        final List<String> allLines = LoadSaveData.loadCsvFile(path);
        boolean found = false;

        for (int i = 0; i < allLines.size(); i++) {
            if (allLines.get(i).startsWith(date)) {
                allLines.set(i, newRow);
                found = true;
                break;
            }
        }

        if (!found) {
            allLines.add(newRow);
        }

        LoadSaveData.saveCsvFile(path, allLines);
    }

    @Override
    public void filterHighProtein() {
        view.updateTable(repository.getHighProteinFoods());
    }

    @Override
    public void filterLowCarbs() {
        view.updateTable(repository.getLowCarbsFoods());
    }

    @Override
    public void filterLowFat() {
        view.updateTable(repository.getLowFatFoods());
    }

    @Override
    public List<Food> getAllFoods() {
        return List.copyOf(repository.getAllFoods());
    }

    @Override
    public Map<String, Double> getTodayNutrients() {
        final DailyLog today = logManager.getCurrentLog();
        final Map<String, Double> nutrients = new HashMap<>();

        nutrients.put("calories", today.getTotalKcal());
        nutrients.put("proteins", today.getTotalProteins());
        nutrients.put("carbs", today.getTotalCarbs());
        nutrients.put("fats", today.getTotalFats());

        return Collections.unmodifiableMap(nutrients);
    }

    private void refreshViewSummary() {
        view.updateSummary(logManager.getCurrentLog().toString());
    }
}
