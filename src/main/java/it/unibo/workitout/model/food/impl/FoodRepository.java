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
    public void addFood(final Food food) {
        database.add(food);
    }

    //Carica i dati dal file csv
    public void loadFromFile(final String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                final String[] parts = line.split(",");
                if (parts.length == 5) {
                    try {
                        final String name = parts[0].trim();
                        final double kcal = Double.parseDouble(parts[1].trim());
                        final double pP = Double.parseDouble(parts[2].trim());
                        final double pC = Double.parseDouble(parts[3].trim());
                        final double pF = Double.parseDouble(parts[4].trim());

                        this.addFood(new FoodImpl(name, kcal, pP, pC, pF));
                    } catch (NumberFormatException e) {
                        System.err.println("Errore nel formato numerico della riga: " + line);
                    }

                }
            }
        } catch (IOException e) {
            System.err.println("Errore caricamento file: " + e.getMessage());
        }
    }

    //Metodi di ricerca
    public List<Food> sortByName(final String query) {
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
    //Mostra solo i cibi con più del 15% di proteine
    public List<Food> getHighProteinFoods() {
        return database.stream()
            .filter(f -> (f.getProteins() * f.getKcalPer100g() / 4.0) > 15.0)
            .sorted(Comparator.comparingDouble((Food f) -> (f.getProteins() * f.getKcalPer100g() / 4.0)).reversed())
            .collect(Collectors.toList());
    }
    //Mostra solo i cibi con pochi grassi, ordinandoli dal più al meno magro
    public List<Food> getLowFatFoods() {
        return database.stream()
            .filter(f -> (f.getFats() * f.getKcalPer100g() / 9.0) < 3.0)
            .sorted(Comparator.comparingDouble(f -> (f.getFats() * f.getKcalPer100g() / 9.0)))
            .collect(Collectors.toList());
    }
    //Mostra solo i cibi con pochi carboidrati, ordinati dal minor contenuto di carboidrati in su
    public List<Food> getLowCarbsFoods() {
        return database.stream()
            .filter(f -> (f.getCarbs() * f.getKcalPer100g() / 4.0) < 5.0)
            .sorted(Comparator.comparingDouble(f -> (f.getCarbs() * f.getKcalPer100g() / 4.0)))
            .collect(Collectors.toList());
    }

    public List<Food> getAllFoods() {
        //restituisce una copia del database
        return new ArrayList<>(database);
    }
}