package it.unibo.workitout.model.food.api;

//interfaccia Food
public interface Food {
    /** @return name of the food */
    String getName();
    /** @return calories per 100g */
    double getKcalPer100g();
    /** @return proteins percentage */
    double getProteins();
    /** @return carbohydrates percentage */
    double getCarbs();
    /** @return fats percentage */
    double getFats();
}

