package it.unibo.workitout.model.food.impl;
import it.unibo.workitout.model.food.api.Food;

public class FoodImpl implements Food {
    //variabili private e final
    private final String name;
    private final double kcalPer100g;
    private final double pProtein;
    private final double pCarbs;
    private final double pFats;  

    public FoodImpl (String name, double kcal, double pP, double pC, double pF) {
        this.name = name;
        this.kcalPer100g = kcal;
        this.pProtein = pP;
        this.pCarbs = pC;
        this.pFats = pF;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getKcalPer100g() {
        return kcalPer100g;
    }

    //calcola i grammi usando le percentuali
    @Override
    public double getProteins() {
        return (kcalPer100g * pProtein) / 4.0;
    }
    @Override
    public double getCarbs() {
        return (kcalPer100g * pCarbs) / 4.0;
    }
    @Override
    public double getFats() {
        return (kcalPer100g * pFats) / 9.0;
    }
}
