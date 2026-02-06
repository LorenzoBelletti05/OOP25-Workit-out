package it.unibo.workitout.model.food.impl;

import it.unibo.workitout.model.food.api.Food;

/**
 * Implementation of Food interface.
 */
public final class FoodImpl implements Food {
    private final String name;
    private final double kcalPer100g;
    private final double pProtein;
    private final double pCarbs;
    private final double pFats;

    /**
     * Constructs a new food implementation.
     * 
     * @param name name of the food.
     * @param kcal calories per 100g.
     * @param pP proteins.
     * @param pC carbs.
     * @param pF fats.
     */
    public FoodImpl(final String name, final double kcal, final double pP,
                     final double pC, final double pF) {
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

    @Override
    public double getProteins() {
        return pProtein;
    }

    @Override
    public double getCarbs() {
        return pCarbs;
    }

    @Override
    public double getFats() {
        return pFats;
    }
}
