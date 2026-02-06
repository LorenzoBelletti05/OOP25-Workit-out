package it.unibo.workitout.model.food.impl;

import it.unibo.workitout.model.food.api.DailyLog;
import it.unibo.workitout.model.food.api.Food;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Optional;

/**
 * Manager for the daily food logs history.
 */
public final class DailyLogManager {
    private static final int HISTORY_COLUMNS = 3;
    private final Map<LocalDate, DailyLog> history = new TreeMap<>();

    /**
     * @return the log for the current date.
     */
    public DailyLog getCurrentLog() {
        return getLogByDate(LocalDate.now());
    }

    /**
     * @param date the date to retrieve.
     * @return the daily log for that date.
     */
    public DailyLog getLogByDate(final LocalDate date) {
        return history.computeIfAbsent(date, d -> new DailyLogImpl(d));
    }

    /**
     * @param filePath the path where to save history.
     */
    public void saveHistory(final String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, StandardCharsets.UTF_8))) {
            for (final DailyLog log : history.values()) {
                final LocalDate date = log.getDate();
                for (final Map.Entry<Food, Integer> entry : log.getFoodsConsumed().entrySet()) {
                    writer.println(date + "," + entry.getKey().getName() + "," + entry.getValue());
                }
            }
        } catch (final IOException e) {
            throw new IllegalStateException("Failed to save history", e);
        }
    }

    /**
     * @param filePath the path from where to load history.
     * @param repository the food repository.
     */
    public void loadHistory(final String filePath, final FoodRepository repository) {
        final File file = new File(filePath);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String line = br.readLine();
            while (line != null) {
                final String[] parts = line.split(",");
                if (parts.length == HISTORY_COLUMNS) {
                    processHistoryLine(parts, repository);
                }
                line = br.readLine();
            }
        } catch (IOException | NumberFormatException e) {
            throw new IllegalStateException("Failed to load history", e);
        }
    }

    private void processHistoryLine(final String[] parts, final FoodRepository repository) {
        final LocalDate date = LocalDate.parse(parts[0]);
        final String foodName = parts[1];
        final int grams = Integer.parseInt(parts[2]);

        final Optional<Food> food = repository.getAllFoods().stream()
            .filter(f -> f.getName().equalsIgnoreCase(foodName))
            .findFirst();

        if (food.isPresent()) {
            this.getLogByDate(date).addFoodEntry(food.get(), grams);
        }
    }

    /**
     * @return a copy of the full history.
     */
    public Map<LocalDate, DailyLog> getFullHistory() {
        return new HashMap<>(history);
    }
}
