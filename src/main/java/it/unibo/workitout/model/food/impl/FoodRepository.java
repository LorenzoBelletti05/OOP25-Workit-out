package it.unibo.workitout.model.food.impl;

import it.unibo.workitout.model.food.api.Food;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Repository for managing food data.
 */
public final class FoodRepository {
    private static final double PROT_DIVISOR = 4.0;
    private static final double FAT_DIVISOR = 9.0;
    private static final double HIGH_PROT_LIMIT = 15.0;
    private static final double LOW_FAT_LIMIT = 3.0;
    private static final double LOW_CARB_LIMIT = 5.0;
    private static final int CSV_COLUMNS = 5;

    private final List<Food> database = new ArrayList<>();

    /**
     * Adds a food to the database.
     * 
     * @param food food to add
     */
    public void addFood(final Food food) {
        database.add(food);
    }

    /**
     * Loads food data from a CSV file.
     * 
     * @param filePath path to CSV
     */
    public void loadFromFile(final String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    final String[] parts = line.split(",");
                    if (parts.length == CSV_COLUMNS) {
                        parseLine(parts);
                    }
                }
            }
        } catch (final IOException e) {
            throw new IllegalStateException("Failed to load food file", e);
        }
    }

    private void parseLine(final String[] parts) {
        try {
            final String name = parts[0].trim();
            final double kcal = Double.parseDouble(parts[1].trim());
            final double pP = Double.parseDouble(parts[2].trim());
            final double pC = Double.parseDouble(parts[3].trim());
            final double pF = Double.parseDouble(parts[4].trim());
            this.addFood(new FoodImpl(name, kcal, pP, pC, pF));
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException("Invalid data in CSV", e);
        }
    }

    /**
     * Sorts food by name.
     *
     * @param query search string
     * @return filtered list
     */
    public List<Food> sortByName(final String query) {
        return database.stream()
            .filter(f -> f.getName().toLowerCase(java.util.Locale.ROOT).contains(query.toLowerCase(java.util.Locale.ROOT)))
            .collect(Collectors.toList());
    }

    /**
     * Returns the sorted list of foods by calories.
     *
     * @return sorted list of foods 
     */
    public List<Food> sortByKcalAscending() {
        return database.stream()
            .sorted(Comparator.comparingDouble(Food::getKcalPer100g))
            .collect(Collectors.toList());
    }

    /**
     * Returns the reversed sorted list of foods by calories.
     * 
     * @return reversed sorted list of foods 
     */
    public List<Food> sortByKcalDescending() {
        return database.stream()
            .sorted(Comparator.comparingDouble(Food::getKcalPer100g).reversed())
            .collect(Collectors.toList());
    }

    /**
     * Filters high protein foods.
     * 
     * @return sorted high protein foods
     */
    public List<Food> getHighProteinFoods() {
        return database.stream()
            .filter(f -> f.getProteins() * f.getKcalPer100g() / PROT_DIVISOR > HIGH_PROT_LIMIT)
            .sorted(Comparator.comparingDouble((Food f) -> 
                f.getProteins() * f.getKcalPer100g() / PROT_DIVISOR).reversed())
            .collect(Collectors.toList());
    }

    /** 
     * Filters low fat foods.
     * 
     * @return sorted low fat foods 
     */
    public List<Food> getLowFatFoods() {
        return database.stream()
            .filter(f -> f.getFats() * f.getKcalPer100g() / FAT_DIVISOR < LOW_FAT_LIMIT)
            .sorted(Comparator.comparingDouble(f -> 
                f.getFats() * f.getKcalPer100g() / FAT_DIVISOR))
            .collect(Collectors.toList());
    }

    /**
     * Filters low carbs foods.
     * 
     * @return sorted low carbs foods
     */
    public List<Food> getLowCarbsFoods() {
        return database.stream()
            .filter(f -> f.getCarbs() * f.getKcalPer100g() / PROT_DIVISOR < LOW_CARB_LIMIT)
            .sorted(Comparator.comparingDouble(f -> 
                f.getCarbs() * f.getKcalPer100g() / PROT_DIVISOR))
            .collect(Collectors.toList());
    }

    /** 
     * Returns a copy of the database.
     * 
     * @return all foods list
     */
    public List<Food> getAllFoods() {
        return List.copyOf(database);
    }
}
