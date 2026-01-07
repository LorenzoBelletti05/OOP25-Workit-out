package it.unibo.workitout.model.food.impl;

import it.unibo.workitout.model.food.api.DailyLog;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DailyLogManager {
    //storico di tutti i giorni
    private final Map<LocalDate, DailyLog> history = new TreeMap<>();

    //Restituisce il log della giornata attuale e lo cambia allo scoccare della mezzanotte
    public DailyLog getCurrentLog() {
        LocalDate today = LocalDate.now();
        return getLogByDate(today);
    }

    //recupera i dati dei giorni passati, quindi tutto lo storico
    public DailyLog getLogByDate(LocalDate date) {
        //Se il log non esiste o non è mai esistito, lo crea
        return history.computeIfAbsent(date, d -> new DailyLogImpl(d));
    }

    //restituisce le calorie totali di ieri e le relative percentuali
    public double getYesterdayCalories() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return getLogByDate(yesterday).getTotalKcal();
    }

    public Map<LocalDate, DailyLog> getFullHistory() {
        return new HashMap<>(history);
    }
}
