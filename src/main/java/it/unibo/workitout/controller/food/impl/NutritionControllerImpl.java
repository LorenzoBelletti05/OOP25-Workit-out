package it.unibo.workitout.controller.food.impl;

import it.unibo.workitout.controller.food.contracts.NutritionController;
import it.unibo.workitout.model.food.impl.FoodRepository;
import it.unibo.workitout.model.food.impl.DailyLogManager;
import it.unibo.workitout.view.food.contracts.NutritionView;
import it.unibo.workitout.model.food.api.DailyLog;
import it.unibo.workitout.model.food.api.Food;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;
import java.util.Objects;

/**
 * Implementation of NutritionController.
 */
public final class NutritionControllerImpl implements NutritionController {
    private final FoodRepository repository;
    private final DailyLogManager logManager;
    private final NutritionView view;
    private static final String FOODS_PATH = "Workit-out/src/main/resources/data/food/foods.csv";
    private static final String HISTORY_PATH = "Workit-out/src/main/resources/data/food/history.csv";
    private static final int MAX_GRAMS = 2000;

    /**
     * @param repository the food database.
     * @param logManager the manager for daily logs.
     * @param view the user interface.
     */

    public NutritionControllerImpl(final FoodRepository repository, final DailyLogManager logManager, final NutritionView view) {
        this.repository = Objects.requireNonNull(repository);
        this.logManager = Objects.requireNonNull(logManager);
        this.view = Objects.requireNonNull(view);
    }

    @Override
    public void start() {
        //Carica i dati all'avvio
        repository.loadFromFile(FOODS_PATH);
        logManager.loadHistory(HISTORY_PATH, repository);
        //Riempie la tabella nella view
        view.updateTable(repository.getAllFoods());
        //Aggiorna il riepilogo
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
        if (grams <= 0) {
            System.out.println("Quantità non valida.");
            return;
        }

        if (grams > MAX_GRAMS) {
            System.out.println("Quantità eccessiva (max 2kg).");
            return;
        }
        
        logManager.getCurrentLog().addFoodEntry(food, grams);
        logManager.saveHistory(HISTORY_PATH);
        refreshViewSummary();
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