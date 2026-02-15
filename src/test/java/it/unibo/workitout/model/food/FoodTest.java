package it.unibo.workitout.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import it.unibo.workitout.model.food.api.Food;
import it.unibo.workitout.model.food.impl.FoodImpl;

/**
 * New class to test the food implementation.
 */
final class FoodTest {
    private static final String NAME = "Pasta";
    private static final double PASTA_KCAL_PER_100G = 350.0;
    private static final double PASTA_PROT_PER_100G = 12.0;
    private static final double PASTA_CARBS_PER_100G = 70.0;
    private static final double PASTA_FATS_PER_100G = 1.5;
    private static final int TEST_GRAMS = 200;
    private static final double GRAMS_CONVERSION_FACTOR = 100.0;
    private static final double EXPECTED_KCAL_FOR_200G = 700.0;

    private final Food food = new FoodImpl(
        NAME,
        PASTA_KCAL_PER_100G,
        PASTA_PROT_PER_100G, 
        PASTA_CARBS_PER_100G, 
        PASTA_FATS_PER_100G
    );

    @Test
    void testNutrientsPerGrams() {
        final double calculatedKcal = food.getKcalPer100g() * TEST_GRAMS / GRAMS_CONVERSION_FACTOR;
        assertEquals(EXPECTED_KCAL_FOR_200G, calculatedKcal);
    }
}
