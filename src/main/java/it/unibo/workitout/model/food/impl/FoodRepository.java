package it.unibo.workitout.model.food.impl;

import it.unibo.workitout.model.food.api.Food;
import java.util.*;
import java.util.stream.Collectors;

public class FoodRepository {
    private final List<Food> database = new ArrayList<>();

    //Aggiunta manuale del food (forse da cambiare)
    public void addFood(Food food) {
        database.add(food);
    }

    //Algoritmo di ricerca
    public List<Food> sortByName(String query) {
        return database.stream()
            .filter(f -> f.getName().toLowerCase().contains(query.toLowerCase()))
            .collect(Collectors.toList());
    }

    public List<Food> sortByKcalAscending() {
        return database.stream()
            .sorted(Comparator.comparingDouble(Food::getKcalPer100g))
            .collect(Collectors.toList());
    }

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