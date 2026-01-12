package it.unibo.workitout.model.food;

import it.unibo.workitout.model.food.api.DailyLog;
import it.unibo.workitout.model.food.api.Food;
import it.unibo.workitout.model.food.impl.DailyLogManager;
import it.unibo.workitout.model.food.impl.FoodRepository;

import java.time.LocalDate;
import java.util.List;

public class NutritionTestLauncher {

    public static void main(String[] args) {
        // 1. Inizializzazione
        FoodRepository repo = new FoodRepository();
        DailyLogManager manager = new DailyLogManager();

        System.out.println("=== TEST INIZIALE NUTRITION DOMAIN ===\n");

        // 2. Test caricamento database
        System.out.println("1. Caricamento foods.csv...");
        // Assicurati che il percorso sia quello corretto nel tuo progetto
        repo.loadFromFile("Workit-out/src/main/resources/data/food/foods.csv");
        
        List<Food> allFoods = repo.getAllFoods();
        System.out.println("   Alimenti caricati totali: " + allFoods.size());
        
        if (allFoods.isEmpty()) {
            System.err.println("   ERRORE: Nessun alimento caricato! Controlla il percorso del file.");
            return;
        }

        // 3. Test ricerca
        System.out.println("\n2. Test Ricerca 'Banana'...");
        repo.sortByName("Banana").forEach(f -> 
            System.out.println("   Trovato: " + f.getName() + " (" + f.getKcalPer100g() + " kcal)"));

        // 4. Test diario alimentare
        System.out.println("\n3. Creazione Log Giornaliero...");
        DailyLog today = manager.getCurrentLog();
        
        // Prendiamo due cibi a caso dal database per il test
        Food f1 = repo.sortByName("Pasta").get(0);
        Food f2 = repo.sortByName("Bresaola").get(0);
        
        today.addFoodEntry(f1, 100); // 100g di pasta
        today.addFoodEntry(f2, 50);  // 50g di bresaola
        
        System.out.println("   Pasto aggiunto correttamente.");
        System.out.println("   Riepilogo: " + today.toString());

        // 5. Test salvataggio storico
        System.out.println("\n4. Salvataggio su history.csv...");
        manager.saveHistory("Workit-out/src/main/resources/data/food/history.csv");
        System.out.println("   File salvato.");

        // 6. Test ricaricamento storico
        System.out.println("\n5. Test Ricaricamento Storico...");
        DailyLogManager newManager = new DailyLogManager(); // Nuovo manager vuoto
        newManager.loadHistory("Workit-out/src/main/resources/data/food/history.csv", repo);
        
        if (newManager.getFullHistory().containsKey(LocalDate.now())) {
            System.out.println("   SUCCESSO: Lo storico è stato ricaricato correttamente!");
            System.out.println("   Dati ricaricati: " + newManager.getLogByDate(LocalDate.now()));
        } else {
            System.err.println("   ERRORE: Lo storico non è stato ricaricato.");
        }

        System.out.println("\n=== TEST COMPLETATO CON SUCCESSO ===");
    }
}