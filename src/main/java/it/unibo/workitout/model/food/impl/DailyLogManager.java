package it.unibo.workitout.model.food.impl;

import it.unibo.workitout.model.food.api.DailyLog;
import it.unibo.workitout.model.food.api.Food;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DailyLogManager {
    //storico di tutti i giorni
    private final Map<LocalDate, DailyLog> history = new TreeMap<>();

    //Restituisce il log della giornata attuale e lo cambia allo scoccare della mezzanotte
    public DailyLog getCurrentLog() {
        return getLogByDate(LocalDate.now());
    }

    //recupera i dati dei giorni passati, quindi tutto lo storico
    public DailyLog getLogByDate(LocalDate date) {
        //Se il log non esiste o non è mai esistito, lo crea
        return history.computeIfAbsent(date, d -> new DailyLogImpl(d));
    }

    //Salva lo storico sul csv
    public void saveHistory(String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (DailyLog log : history.values()) {
                LocalDate date = log.getDate();
                for (Map.Entry<Food, Integer> entry : log.getFoodsConsumed().entrySet()) {
                    writer.println(date + "," + entry.getKey().getName() + "," + entry.getValue());
                }
            }
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio dello storico: " + e.getMessage());
        }
    }

    //Carica lo storico leggendo il file csv
    public void loadHistory(String filePath, FoodRepository repository) {
        File file = new File(filePath);
        if (!file.exists()) return; //se il file non esiste, non fa nulla

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    LocalDate date = LocalDate.parse(parts[0]);
                    String foodName = parts[1];
                    int grams = Integer.parseInt(parts[2]);

                    //Cerca i food nel repository
                    Optional<Food> food = repository.getAllFoods().stream()
                        .filter(f -> f.getName().equalsIgnoreCase(foodName))
                        .findFirst();
                    
                    if (food.isPresent()) {
                        this.getLogByDate(date).addFoodEntry(food.get(), grams);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Errore nel caricamento dello storico: " + e.getMessage());
        }
    }

    public Map<LocalDate, DailyLog> getFullHistory() {
        return new HashMap<>(history);
    }
}
