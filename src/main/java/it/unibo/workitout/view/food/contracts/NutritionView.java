package it.unibo.workitout.view.food.contracts;

import it.unibo.workitout.model.food.api.Food;
import java.util.List;

public interface NutritionView {
    //Aggiorna la tabella dei risultati
    void updateTable(List<Food> foods);

    //Aggiorna il testo del DailyLog
    void updateSummary(String summary);
}
