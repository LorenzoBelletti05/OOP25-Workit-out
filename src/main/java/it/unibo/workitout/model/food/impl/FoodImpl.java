package it.unibo.workitout.model.food.impl;
import it.unibo.workitout.model.food.api.Food;
public class FoodImpl implements Food {
    //variabili private e final
    private final String name;
    private final double kcalPer100g;
    private final double pProtein;
    private final double pCarbs;
    private final double pFat;  

    public FoodImpl (String name, double kcal, double pP, double pC, double pF) {
        this.name = name;
        this.kcalPer100g = kcal;
        this.pProtein = pP;
        this.pCarbs = pC;
        this.pFat = pF;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getCalories() {
        return kcalPer100g;
    }

    @Override
    public String getNutrients() {
        //Calcolo dei grammi basato sulle calorie totali e le percentuali
        double gProtein = (kcalPer100g * pProtein) / 4.0;
        double gCarbs = (kcalPer100g * pCarbs) / 4.0;
        double gFat = (kcalPer100g * pFat) / 9.0;

        return String.format("P: %.0f%% (%.1fg), C: %.0f%% (%.1fg), F: %.0f%% (%.1fg)", pProtein * 100, gProtein, pCarbs * 100, gCarbs, pFat * 100, gFat);
    }
}
