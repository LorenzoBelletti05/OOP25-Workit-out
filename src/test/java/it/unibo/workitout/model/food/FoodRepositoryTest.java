package it.unibo.workitout.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.workitout.model.food.impl.FoodImpl;
import it.unibo.workitout.model.food.impl.FoodRepository;

public class FoodRepositoryTest {
    private static final int MIN_EXPECTED_FOODS = 1;
    private static final String KNOWN_FOOD_NAME = "Pasta";
    private static final double PASTA_KCAL = 350.0;
    private static final double PASTA_PROT = 12.0;
    private static final double PASTA_CARBS = 70.0;
    private static final double PASTA_FATS = 1.5;
    private static final String FAKE_FOOD_NAME = "CiboInesistente";
    private FoodRepository repository;

    @BeforeEach
    void setUp() {
        this.repository = new FoodRepository();
        this.repository.addFood(new FoodImpl(KNOWN_FOOD_NAME, PASTA_KCAL, PASTA_PROT, PASTA_CARBS, PASTA_FATS));
    }

    @Test
    void testLoadFoods() {
        assertNotNull(this.repository.getAllFoods());
        assertTrue(this.repository.getAllFoods().size() >= MIN_EXPECTED_FOODS,
            "Il database dei cibi dovrebbe contenere almeno un elemento.");
    }

    @Test
    void testFoodSearch() {
        assertFalse(this.repository.sortByName(KNOWN_FOOD_NAME).isEmpty());
        assertTrue(this.repository.sortByName(FAKE_FOOD_NAME).isEmpty());
    }

    @Test
    void testFilters() {
        assertNotNull(this.repository.getHighProteinFoods());
        assertNotNull(this.repository.getLowCarbsFoods());
    }
}
