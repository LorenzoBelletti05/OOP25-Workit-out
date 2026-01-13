package it.unibo.workitout.controller.food.impl;

import it.unibo.workitout.controller.food.contracts.NutritionController;
import it.unibo.workitout.model.food.impl.FoodRepository;
import it.unibo.workitout.model.food.impl.DailyLogManager;
import it.unibo.workitout.view.food.contracts.NutritionView;
import it.unibo.workitout.model.food.api.DailyLog;
import it.unibo.workitout.model.food.api.Food;
import java.util.Map;
import java.util.HashMap;

public class NutritionControllerImpl implements NutritionController {
    private final FoodRepository repository;
    private final DailyLogManager logManager;
    private final NutritionView view;
    private static final String FOODS_PATH = "Workit-out/src/main/resources/data/food/foods.csv";
    private static final String HISTORY_PATH = "Workit-out/src/main/resources/data/food/history.csv";

    public NutritionControllerImpl(FoodRepository repository, DailyLogManager logManager, NutritionView view) {
        this.repository = repository;
        this.logManager = logManager;
        this.view = view;
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
        logManager.getCurrentLog().addFoodEntry(food, grams);
        logManager.saveHistory(HISTORY_PATH);
        refreshViewSummary();
    }

    @Override
    public Map<String, Double> getTodayNutrients() {
        final DailyLog today = logManager.getCurrentLog();
        final Map<String, Double> nutrients = new HashMap<>();

        nutrients.put("calories", today.getTotalKcal());
        nutrients.put("proteins", today.getTotalProteins());
        nutrients.put("carbs", today.getTotalCarbs());
        nutrients.put("fats", today.getTotalFats());

        return nutrients;
    }

    private void refreshViewSummary() {
        view.updateSummary(logManager.getCurrentLog().toString());
    }
}