package it.unibo.workitout.model.food.impl;

import it.unibo.workitout.model.food.api.Food;
import java.util.*;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FoodRepository {
    private final List<Food> database = new ArrayList<>();

    //Aggiunta manuale del food (si può usare per implementarlo eventualmente)
    public void addFood(Food food) {
        database.add(food);
    }

    //Carica i dati dal file csv
    public void loadFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length == 5) {
                    try {
                        String name = parts[0].trim();
                        double kcal = Double.parseDouble(parts[1].trim());
                        double pP = Double.parseDouble(parts[2].trim());
                        double pC = Double.parseDouble(parts[3].trim());
                        double pF = Double.parseDouble(parts[4].trim());

                        this.addFood(new FoodImpl(name, kcal, pP, pC, pF));
                    } catch (NumberFormatException e) {
                        System.err.println("Errore nei numeri: " + line);
                    }

                }
            }
        } catch (IOException e) {
            System.err.println("Errore caricamento file: " + e.getMessage());
        }
    }

    //Metodi di ricerca
    public List<Food> sortByName(String query) {
        return database.stream()
            .filter(f -> f.getName().toLowerCase().contains(query.toLowerCase()))
            .collect(Collectors.toList());
    }
    //Ordina dal meno al più calorico
    public List<Food> sortByKcalAscending() {
        return database.stream()
            .sorted(Comparator.comparingDouble(Food::getKcalPer100g))
            .collect(Collectors.toList());
    }
    //Ordina dal più al meno calorico
    public List<Food> sortByKcalDescending() {
        return database.stream()
            .sorted(Comparator.comparingDouble(Food::getKcalPer100g).reversed())
            .collect(Collectors.toList());
    }

    public List<Food> getAllFoods() {
        //restituisce una copia del database
        return new ArrayList<>(database);
    }
}