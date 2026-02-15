package it.unibo.workitout.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import it.unibo.workitout.model.food.impl.FoodImpl;
import it.unibo.workitout.model.food.impl.DailyLogImpl;
import it.unibo.workitout.model.food.api.DailyLog;

/**
 * New class to test the daily log.
 */
final class DailyLogTest {
    private static final String PASTA_NAME = "Pasta";
    private static final double PASTA_KCAL = 350.0;
    private static final double PASTA_PROT = 12.0;
    private static final double PASTA_CARBS = 70.0;
    private static final double PASTA_FATS = 1.5;
    private static final int PASTA_AMOUNT = 100;

    private static final String POLLO_NAME = "Pollo";
    private static final double POLLO_KCAL = 110.0;
    private static final double POLLO_PROT = 23.0;
    private static final double POLLO_CARBS = 0.0;
    private static final double POLLO_FATS = 1.0;
    private static final int POLLO_AMOUNT = 200;
    private static final double POLLO_EXPECTED_TOTAL_KCAL = POLLO_KCAL * POLLO_AMOUNT / 100.0;
    private static final double TOTAL_EXPECTED_KCAL = PASTA_KCAL + POLLO_EXPECTED_TOTAL_KCAL;
    private static final double DELTA = 0.001;
    private DailyLog log;

    @BeforeEach
    void setUp() {
        this.log = new DailyLogImpl(java.time.LocalDate.now());
    }

    @Test
    void testAddMultipleFoods() {
        log.addFoodEntry(new FoodImpl(PASTA_NAME, PASTA_KCAL, PASTA_PROT, PASTA_CARBS, PASTA_FATS), PASTA_AMOUNT);
        log.addFoodEntry(new FoodImpl(POLLO_NAME, POLLO_KCAL, POLLO_PROT, POLLO_CARBS, POLLO_FATS), POLLO_AMOUNT);
        assertEquals(TOTAL_EXPECTED_KCAL, this.log.getTotalKcal(), DELTA);
    }
}
