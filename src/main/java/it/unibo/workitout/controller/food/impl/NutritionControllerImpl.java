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
import java.util.function.Consumer;

/**
 * Implementation of NutritionController.
 */
public final class NutritionControllerImpl implements NutritionController {
    private static final int MAX_GRAMS = 2000;
    private static final double HUNDRED = 100.0;
    private static final double KCAL_PER_PROT_CARB = 4.0;
    private static final double KCAL_PER_FAT = 9.0;
    private final FoodRepository repository;
    private final DailyLogManager logManager;
    private final NutritionView view;
    private final Runnable goToDashboard;
    private final Consumer<Map<String, Double>> onNutrientsUpdate;

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
                                   final Runnable goToDashboard,
                                   final Consumer <Map<String, Double>> onNutrientsUpdate) {
        this.repository = Objects.requireNonNull(repository);
        this.logManager = Objects.requireNonNull(logManager);
        this.view = Objects.requireNonNull(view);
        this.goToDashboard = Objects.requireNonNull(goToDashboard);
        this.onNutrientsUpdate = Objects.requireNonNull(onNutrientsUpdate);
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
        final double kcalValue = (food.getKcalPer100g() * grams) / HUNDRED;
        final double protGrams = (food.getProteins() * kcalValue) / KCAL_PER_PROT_CARB;
        final double carbGrams = (food.getCarbs() * kcalValue) / KCAL_PER_PROT_CARB;
        final double fatGrams = (food.getFats() * kcalValue) / KCAL_PER_FAT;
        Map<String, Double> nutrients = Map.of(
            "kcal", kcalValue,
            "proteins", protGrams,
            "carbs", carbGrams,
            "fats", fatGrams
        );
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
